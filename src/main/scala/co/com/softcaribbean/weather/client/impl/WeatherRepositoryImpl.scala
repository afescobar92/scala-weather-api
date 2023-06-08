package co.com.softcaribbean.weather.client.impl

import sttp.client3._
import sttp.client3.playJson._
import cats.data.EitherT
import cats.implicits._
import co.com.softcaribbean.weather.service.{GenericError, WeatherError}
import co.com.softcaribbean.weather.client.WeatherRepository
import co.com.softcaribbean.weather.model.WeatherDTO
import com.softwaremill.sttp.{HeaderNames, sttp => client, _}
import com.typesafe.config.{Config, ConfigFactory}
import co.com.softcaribbean.weather.util.ResponseFactory
import co.com.softcaribbean.weather.service.service.{ServiceResponse, SttpBackend, SttpComplexResponse}
import play.api.libs.json.{JsError, Json}
import _root_.sttp.model.StatusCode
import _root_.sttp.client3.SttpClientException.ReadException
import _root_.sttp.model.Uri
import _root_.sttp.model.Uri.{PathSegment, QuerySegment}

import scala.concurrent.{ExecutionContext, Future}

class WeatherRepositoryImpl
  extends WeatherRepository[ServiceResponse[WeatherError,WeatherDTO], String] with ResponseFactory{

  val Config: Config = ConfigFactory.load("application")

  override def getWeatherLocation(location: String)
                                 (implicit ec: ExecutionContext): Future[ServiceResponse[WeatherError,WeatherDTO]] = {
    val result = for {
      response <- EitherT(send(location))
      managedResponse <- EitherT.fromEither[Future](manageResponse(location,response))
    } yield managedResponse

    result.value.recover {
      case ex: ReadException =>
        println("El servicio del clima ha superado el tiempo de espera máximo", ex)
        Either.left[WeatherError, WeatherDTO](GenericError(s"ERROR: $ex"))
      case ex: Exception =>
        println(s"Error no controlado al consumir el del clima", ex)
        Either.left[WeatherError, WeatherDTO](GenericError(s"ERROR: $ex"))
    }

  }

  private def send(location: String)
                  (implicit ec: ExecutionContext): Future[ServiceResponse[WeatherError, SttpComplexResponse[WeatherDTO]]] = {

    val headers: Map[String, String] = Map(
      HeaderNames.ContentType -> MediaTypes.Json
    )
    val Protocol = Config.getString("services.weather.protocol")
    val Host = Config.getString("services.weather.host")
    val Token = Config.getString("services.weather.token")
    val url: Uri = Uri(Protocol,Host)
      .addPathSegments(PathSegment("v1"),PathSegment("current.json"))
      .addQuerySegments(QuerySegment.KeyValue("q",location),QuerySegment.KeyValue("key",Token))

    basicRequest
      .headers(headers)
      .get(url)
      .response(asJson[WeatherDTO])
      .send(SttpBackend)
      .map(Either.right[WeatherError, SttpComplexResponse[WeatherDTO]])
  }


  private def manageResponse(request: String,response: SttpComplexResponse[WeatherDTO]): ServiceResponse[WeatherError, WeatherDTO] = {
    response.body.fold(
      err => {
        println(s"Se realizó el consumo del servicio del clima y ha ocurrido un error.| parameters=${Json.toJson(request)}")
        err match {
          case httpError: HttpError[String] =>
            httpError.statusCode match {
              case StatusCode.Unauthorized =>
                Either.left[WeatherError, WeatherDTO](GenericError("Unauthorized"))
              case StatusCode.RequestTimeout =>
                Either.left[WeatherError, WeatherDTO](GenericError("RequestTimeout"))
              case StatusCode.BadRequest =>
                Either.left[WeatherError, WeatherDTO](GenericError("BadRequest"))
              case StatusCode.InternalServerError =>
                Either.left[WeatherError, WeatherDTO](GenericError("InternalServerError"))
              case e @ _ =>
                Either.left[WeatherError, WeatherDTO](GenericError(s"ERROR: $e"))
            }
          case deserializationError: DeserializationException[JsError] =>
             Either.left[WeatherError, WeatherDTO](GenericError(s"ERROR: $deserializationError"))
        }
      },
      c => Either.right[WeatherError, WeatherDTO](c)
    )
  }

  override val defaultMsgErrorResponse: String = "Error invoke Weather Service"
}

object WeatherRepositoryImpl extends WeatherRepositoryImpl
