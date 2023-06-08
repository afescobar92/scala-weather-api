package co.com.softcaribbean.weather.service

import scala.concurrent.{ExecutionContext, Future}

trait WeatherService[A, B] {
  def getWeatherLocation(location: A)(implicit ec: ExecutionContext): Future[B]
}
