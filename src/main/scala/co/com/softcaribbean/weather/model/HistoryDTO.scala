package co.com.softcaribbean.weather.model

import co.com.softcaribbean.weather.util.MarshallerCommon
import play.api.libs.json.Json

case class HistoryDTO (location: String, response: String) extends BaseDomain

case class HistoryResponse (location: String, response: WeatherDTO) extends BaseDomain


object HistoryResponse extends MarshallerCommon{

  def apply(dto: HistoryDTO): HistoryResponse = {
   new HistoryResponse(
      dto.location,
      Json.parse(dto.response).as[WeatherDTO]
   )

  }

}
