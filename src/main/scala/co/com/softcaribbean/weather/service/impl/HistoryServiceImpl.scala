package co.com.softcaribbean.weather.service.impl

import co.com.softcaribbean.weather.model.{BaseDomain, HistoryDTO, HistoryResponse}
import co.com.softcaribbean.weather.persistence.impl.HistoryRepositoryImpl
import co.com.softcaribbean.weather.service.HistoryService
import co.com.softcaribbean.weather.util.ErrorException

import scala.concurrent.{ExecutionContext, Future}

class HistoryServiceImpl extends HistoryService[HistoryDTO,BaseDomain,HistoryResponse]{

  import co.com.softcaribbean.weather.util.bd.DatabaseConfigProvider._

  override def insertHistory(history: HistoryDTO)(implicit ec: ExecutionContext): Future[BaseDomain] = {
    HistoryRepositoryImpl.insertHistory(history).run(config).map { rows =>
      if (rows == 1) {
        history
      } else if (rows == 0) {
        ErrorException(999, "No se inserto el historial del clima", "")
      }else{
        history
      }
    }
  }

  override def getHistory(implicit ec: ExecutionContext): Future[Seq[HistoryResponse]] = {
    HistoryRepositoryImpl.getHistory.run(config).map(_.map(HistoryResponse(_)))
  }

}

object HistoryServiceImpl extends HistoryServiceImpl
