package co.com.softcaribbean.weather.persistence

import scala.concurrent.{ExecutionContext, Future}
import cats.data.Reader

trait VersionRepository[A, B, C]  {

  def insertVersion(version: B)(implicit ec: ExecutionContext): Reader[A, Future[C]]


}
