package co.com.softcaribbean.weather.service.impl

import cats.data.EitherT
import cats.implicits._
import co.com.softcaribbean.weather.service.{GenericError, WeatherError, WeatherService}
import co.com.softcaribbean.weather.client.impl.WeatherRepositoryImpl
import co.com.softcaribbean.weather.model.{HistoryDTO, WeatherDTO}
import co.com.softcaribbean.weather.service.service.ServiceResponse
import co.com.softcaribbean.weather.util.cache.CacheProvider
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

class WeatherServiceImpl extends WeatherService[String,ServiceResponse[WeatherError,WeatherDTO]] with CacheProvider[String,WeatherDTO]{

  override def getWeatherLocation(location: String)(implicit ec: ExecutionContext): Future[ServiceResponse[WeatherError,WeatherDTO]] = {
    val CacheKey: String = s"$location-bdcache"
    cache.getIfPresent(CacheKey)match {
      case Some(data) =>
        println("Get From Cache : " + Json.toJson(data))
        Future.successful( Either.right[WeatherError, WeatherDTO](data))
      case None =>

        val result = for {
          sttpResult <- EitherT(WeatherRepositoryImpl.getWeatherLocation(location))
        } yield sttpResult
        result.map( value => {
              println(s"Put Cache :location '$location'")
              cache.put(CacheKey, value)
              val json = Json.toJson(value).toString()
              println(s"Create hisotry :location '$location' json: $json")
              HistoryServiceImpl.insertHistory(HistoryDTO(location,json))
          }
          )
        result.value.recover {
          case ex: Exception =>
            Either.left(GenericError(s"ERROR: $ex"))
        }

    }

  }

}

object WeatherServiceImpl extends WeatherServiceImpl
