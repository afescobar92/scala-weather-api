package co.com.softcaribbean.weather.service

import scala.concurrent.{ExecutionContext, Future}

trait VersionService[A, B] {
  def insertVersion(version: A)(implicit ec: ExecutionContext): Future[B]

}
