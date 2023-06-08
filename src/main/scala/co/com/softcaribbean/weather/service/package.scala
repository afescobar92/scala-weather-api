package co.com.softcaribbean.weather.service

import play.api.libs.json.{JsError}
import sttp.client3._
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend


import scala.concurrent.Future

trait WeatherError extends Product with Serializable {
  val msg: String
  val code: String
  val httpCode: Int
}

final case class GenericError(
                               msg: String = "Error no controlado al consumir el servicio",
                               code: String = "0-999-999",
                               httpCode: Int = 502) extends WeatherError

package object service {
  implicit lazy val SttpBackend: SttpBackend[Future, Any] = AsyncHttpClientFutureBackend()
  type SttpComplexResponse[T] = Response[Either[ResponseException[String, JsError], T]]
  type ServiceResponse[Error <: WeatherError, T] = Either[Error, T]
}
