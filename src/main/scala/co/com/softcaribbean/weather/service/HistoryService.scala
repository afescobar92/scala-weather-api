package co.com.softcaribbean.weather.service

import scala.concurrent.{ExecutionContext, Future}

trait HistoryService[A, B, D] {
  def insertHistory(history: A)(implicit ec: ExecutionContext): Future[B]

  def getHistory(implicit ec: ExecutionContext): Future[Seq[D]]
}
