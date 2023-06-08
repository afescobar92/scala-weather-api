package co.com.softcaribbean.weather.util

import co.com.softcaribbean.weather.VersionDTO
import co.com.softcaribbean.weather.model.{ConditionWeatherDTO, CurrentWeatherDTO, HistoryDTO, HistoryResponse, LocationWeatherDTO, WeatherDTO}
import co.com.softcaribbean.weather.service.WeatherError
import play.api.libs.functional.syntax.unlift
import play.api.libs.json._
import play.api.libs.functional.syntax._

trait MarshallerCommon {

  implicit val errorExceptionMarshaller: Writes[ErrorException] = Json.writes[ErrorException]
  implicit val versionDTOMarshaller: Writes[VersionDTO] = Writes { version =>
    Json.obj(
      "version" -> version.version,
      "api" -> version.api
    )

  }
  implicit val locationWeatherDTOMarshaller: Writes[LocationWeatherDTO] = Json.writes[LocationWeatherDTO]
  implicit val currentWeatherDTOMarshaller: Writes[CurrentWeatherDTO] = Json.writes[CurrentWeatherDTO]
  implicit val conditionWeatherDTOMarshaller: Writes[ConditionWeatherDTO] = Json.writes[ConditionWeatherDTO]
  implicit val weatherDTOMarshaller: Writes[WeatherDTO] = Json.writes[WeatherDTO]
  implicit val historyDTOMarshaller: Writes[HistoryDTO] = Json.writes[HistoryDTO]
  implicit val historyReponseFormat: OFormat[HistoryResponse] = Json.format[HistoryResponse]


  implicit val smErrorWrites: Writes[WeatherError] = (
    (JsPath \ "msg").write[String] and
      (JsPath \ "code").write[String])(unlift(unapplyWeatherError))

  private def unapplyWeatherError(error: WeatherError) = {
    Option((error.msg, error.code))
  }
}
