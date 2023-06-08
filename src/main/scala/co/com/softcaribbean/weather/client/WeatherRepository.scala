package co.com.softcaribbean.weather.client

import scala.concurrent.{ExecutionContext, Future}

trait WeatherRepository[A, B] {

  def getWeatherLocation(location: B)(implicit ec: ExecutionContext): Future[A]

}
