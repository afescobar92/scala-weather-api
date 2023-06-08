package co.com.softcaribbean.weather.persistence.impl

import cats.data.Reader
import co.com.softcaribbean.weather.model.HistoryDTO
import co.com.softcaribbean.weather.persistence.HistoryRepository
import co.com.softcaribbean.weather.persistence.bd.HistoryTable.HistoryDAO
import co.com.softcaribbean.weather.persistence.bd.base.DataBaseConf

import scala.concurrent.{ExecutionContext, Future}

sealed trait HistoryRepositoryImpl extends HistoryRepository[DataBaseConf,HistoryDTO,Int]{

  override def insertHistory(historyDto: HistoryDTO)(implicit ec: ExecutionContext): Reader[DataBaseConf, Future[Int]] = Reader{
    dbConf: DataBaseConf =>
      import dbConf.profile.api._
      val findHistory = HistoryDAO.filter(h => h.location ===historyDto.location).map(_.location)
      val query = for {
        seq <- dbConf.db.run(findHistory.result)
      } yield {
        seq.headOption match {
          case Some(_) =>
            val updateAction = for { c <- HistoryDAO if c.location === historyDto.location } yield c
            Some(updateAction.update(historyDto))
          case _ => Some(HistoryDAO += historyDto)
        }
      }
      query.flatMap {
        case Some(action) =>
          dbConf.db.run(action)
        case _ => Future.successful(-1)
      }
  }

  override def getHistory(implicit ec: ExecutionContext): Reader[DataBaseConf, Future[Seq[HistoryDTO]]] = Reader{
    dbConf: DataBaseConf =>
      import dbConf.profile.api._
      val query = HistoryDAO.sortBy(_.location)
      dbConf.db.run(query.result)

  }
}

object HistoryRepositoryImpl extends HistoryRepositoryImpl
