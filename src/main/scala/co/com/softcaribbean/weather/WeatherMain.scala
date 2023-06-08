package co.com.softcaribbean.weather

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import co.com.softcaribbean.weather.api.WeatherApiRest
import co.com.softcaribbean.weather.model.BaseDomain
import co.com.softcaribbean.weather.service.impl._
import co.com.softcaribbean.weather.util.{ExceptionHandlerAdapter, ResponseFactory}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class VersionDTO (version: String,api: String) extends BaseDomain

object WeatherMain extends App with ExceptionHandlerAdapter with ResponseFactory{
  val Config: Config = ConfigFactory.load("application")

  implicit val system = ActorSystem("WeatherServer", Config)

  val Prefix  = Config.getString("http.prefix")
  val Version = Config.getString("version")
  val ApiName = Config.getString("nameApi")

  val ApiFindWeather: WeatherApiRest = new WeatherApiRest {
    def log: LoggingAdapter = Logging(system.eventStream, "api-weather")
    override val defaultMsgErrorResponse: String = ""
  }

  val ApiWeather = pathPrefix(Prefix) {
    pathPrefix("weather") {
        ApiFindWeather.routes() ~ version
    }
  }

  def version: Route = {
    path("version") {
      get {
        val version = VersionDTO(Version,ApiName)
        val insertVersion = VersionServiceImpl.insertVersion(version)
        manageResponse[VersionDTO](insertVersion)
      }
    }
  }

  def start(api: Route)(implicit system: ActorSystem): Unit = {
    implicit val ec = system.dispatcher
    val host = system.settings.config.getString("http.host")
    val port = system.settings.config.getInt("http.port")
    def akkaRoute: Route = handleExceptions(exceptionHandler) {
        api
    }

    val bindingFuture: Future[ServerBinding] =
      Http().newServerAt(host, port).bind(akkaRoute)
    def log: LoggingAdapter = Logging(system.eventStream, "api-weather")
    bindingFuture.map { serverBinding =>
      val msg = s"ApiWeather run to ${serverBinding.localAddress} "
      log.info(msg)
      println(msg)
      DBCreatorServiceImpl.createModelDB()
    }

  }

  start(ApiWeather)
  override val defaultMsgErrorResponse = "ERROR API START"
}
