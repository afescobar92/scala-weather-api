package co.com.softcaribbean.weather.util

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCode}
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.http.scaladsl.server.Directives._
import co.com.softcaribbean.weather.model.BaseDomain
import co.com.softcaribbean.weather.service.WeatherError
import play.api.libs.json.{JsValue, Json, Writes}

import scala.beans.BeanProperty
import scala.concurrent.Future

trait Response
object TypeMessageError extends Enumeration {
  val error = Value("error")
  val warning = Value("warning")
}


class ResponseServiceError(@BeanProperty val typeMessage: String, @BeanProperty val message: String) extends Response{
  override def toString: String = message
}




trait ResponseFactory extends MarshallerCommon {

  import akka.http.scaladsl.model.StatusCodes._
  val defaultMsgErrorResponse: String
  private def constructMessageResponse(errorCodeMsg: String, message: String) = s"$message - $errorCodeMsg."

  def createInconsistencyResponse(code: String, message: String = defaultMsgErrorResponse): Response = {
    new ResponseServiceError(TypeMessageError.error.toString, constructMessageResponse(code, message))
  }

  def createHttpResponse(statusCode: StatusCode, entity: JsValue): HttpResponse = {
    HttpResponse(statusCode, entity = HttpEntity(`application/json`, entity.toString))
  }

  def createOk(entity: JsValue): StandardRoute = {
    complete(
      createHttpResponse(OK, entity)
    )
  }

  def manageResponse[T <: BaseDomain](request: Future[BaseDomain], f: T => T = identity[T] _)(implicit write: Writes[T]): Route = {
    onSuccess(request) {
      case error: ErrorException =>
        complete(createHttpResponse(error.code, Json.toJson(error)))
      case success: T @unchecked =>
        createOk(Json.toJson(f(success)))
    }
  }

  def manageEitherResponse[Error <: WeatherError, T](
                                                 request: Future[Either[Error, T]],
                                                 f: T => T = identity[T] _)(
                                                 implicit
                                                 writeLeft: Writes[Error], writeRight: Writes[T]): Route = {
    onSuccess(request) {
      case Left(error) =>
        println(s"Respuesta fallida.")
        complete(createHttpResponse(error.httpCode, Json.toJson(error)))
      case Right(resp) =>
        println(s"Respuesta exitosa."+resp)
        createOk(Json.toJson(f(resp)))
    }
  }

}
