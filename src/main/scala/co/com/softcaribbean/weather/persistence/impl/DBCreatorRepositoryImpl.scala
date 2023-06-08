package co.com.softcaribbean.weather.persistence.impl

import cats.data.Reader
import co.com.softcaribbean.weather.persistence.DBCreatorRepository
import co.com.softcaribbean.weather.persistence.bd.base.DataBaseConf

import scala.concurrent.{ExecutionContext, Future}

sealed trait DBCreatorRepositoryImpl extends DBCreatorRepository[DataBaseConf,String,Int]{

  override def createModelDB()(implicit ec: ExecutionContext): Reader[DataBaseConf, Future[Int]] = Reader{
    dbConf: DataBaseConf =>
      import dbConf.profile.api._

      val createVersion: DBIO[Int] =
        sqlu"""create table "VERSION"(
              "cdversion" varchar not null,
              "dsapi" varchar not null
              )
              """
      val createHistory: DBIO[Int] =
        sqlu"""
              create table "HISTORY"(
               "cdubicacion" varchar not null,
               "dsresponse" blob not null
              )
              """
      dbConf.db.run(createVersion)
      dbConf.db.run(createHistory)

  }

}

object DBCreatorRepositoryImpl extends DBCreatorRepositoryImpl