package co.com.softcaribbean.weather.model

import co.com.softcaribbean.weather.util.Response
import play.api.libs.json.{Json, OFormat}

case class ConditionWeatherDTO(text: String,icon: String) extends BaseDomain
case class CurrentWeatherDTO(temp_c: Double, temp_f: Double,wind_dir: String,humidity: Double) extends BaseDomain
case class LocationWeatherDTO(name: String,region: String, country: String, lat: Double, lon: Double, tz_id: String) extends BaseDomain
case class WeatherDTO(location: LocationWeatherDTO, current: CurrentWeatherDTO) extends BaseDomain with Response

object WeatherDTO{
  implicit val ConditionWeatherDTOFormat : OFormat[ConditionWeatherDTO] = Json.format[ConditionWeatherDTO]
  implicit val CurrentWeatherDTOFormat : OFormat[CurrentWeatherDTO] = Json.format[CurrentWeatherDTO]
  implicit val LocationWeatherDTOFormat: OFormat[LocationWeatherDTO] = Json.format[LocationWeatherDTO]
  implicit val WeatherDTOFormat: OFormat[WeatherDTO] = Json.format[WeatherDTO]
}
