package co.com.softcaribbean.weather.service

import scala.concurrent.{ExecutionContext, Future}

trait DBCreatorService[A,B] {

  def createModelDB()(implicit ec: ExecutionContext): Future[B]
}
