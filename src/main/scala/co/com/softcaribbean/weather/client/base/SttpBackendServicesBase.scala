package co.com.softcaribbean.weather.client.base

import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.asynchttpclient.future.AsyncHttpClientFutureBackend

import scala.concurrent.Future

object SttpBackendServicesBase {
  implicit val sttpBackendFuture: SttpBackend[Future, Nothing] = AsyncHttpClientFutureBackend()

}
