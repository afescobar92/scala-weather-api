package co.com.softcaribbean.weather.service.impl

import co.com.softcaribbean.weather.persistence.impl.DBCreatorRepositoryImpl
import co.com.softcaribbean.weather.service.DBCreatorService

import scala.concurrent.{ExecutionContext, Future}

class DBCreatorServiceImpl extends DBCreatorService[String,Int]{
  import co.com.softcaribbean.weather.util.bd.DatabaseConfigProvider._

  override def createModelDB()(implicit ec: ExecutionContext): Future[Int] = {
     DBCreatorRepositoryImpl.createModelDB().run(config)
  }
}

object DBCreatorServiceImpl extends DBCreatorServiceImpl
