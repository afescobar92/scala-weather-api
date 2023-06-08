package co.com.softcaribbean.weather.persistence

import cats.data.Reader

import scala.concurrent.{ExecutionContext, Future}

trait HistoryRepository[A, B, C] {

  def insertHistory(location: B)(implicit ec: ExecutionContext): Reader[A, Future[C]]
  def getHistory(implicit ec: ExecutionContext): Reader[A, Future[Seq[B]]]


}
