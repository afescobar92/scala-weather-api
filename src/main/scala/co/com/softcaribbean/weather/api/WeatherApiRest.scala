package co.com.softcaribbean.weather.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import co.com.softcaribbean.weather.model.WeatherDTO
import co.com.softcaribbean.weather.service.WeatherError
import co.com.softcaribbean.weather.service.impl.{HistoryServiceImpl, WeatherServiceImpl}
import co.com.softcaribbean.weather.util.{MarshallerCommon, ResponseFactory}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

trait WeatherApiRest extends ResponseFactory with MarshallerCommon {


  def routes()(implicit system: ActorSystem, ec: ExecutionContext): Route = {
     get{
      parameters("location".as[String]) {
        location =>
         manageEitherResponse[WeatherError,WeatherDTO](WeatherServiceImpl.getWeatherLocation(location))
      }
    } ~ pathPrefix("history"){
        get {
          onComplete(HistoryServiceImpl.getHistory){
            case Success(res) => complete(createHttpResponse(200, entity = Json.toJson(res)))
            case Failure(err) =>
              err match {
                case _ =>
                  complete(createHttpResponse(500,Json.toJson(err.toString)))
              }

          }
      }

    }
  }


}
