package co.com.softcaribbean.weather.persistence

import cats.data.Reader

import scala.concurrent.{ExecutionContext, Future}

trait DBCreatorRepository[A, B, C]  {
  def createModelDB()(implicit ec: ExecutionContext): Reader[A, Future[C]]
}
