package co.com.softcaribbean.weather.util

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, IllegalUriException}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import co.com.softcaribbean.weather.model.BaseDomain

case class ErrorException(code: Int, msg: String, msgTech: String) extends BaseDomain

trait ExceptionHandlerAdapter {
  import akka.http.scaladsl.model.StatusCodes._

  val exceptionHandler = ExceptionHandler {
    case e: IllegalArgumentException =>
      println("Parametros de construccion incorrectos", e)
      complete(HttpResponse(BadRequest, entity = HttpEntity(`application/json`, ErrorException(BadRequest.intValue,s"Parametros de construcción no validos",s" ERROR: ${e.getMessage} $e").toString)))
    case e: IllegalUriException =>
      println("Parametros incorrectos en la URL", e)
      complete(HttpResponse(BadRequest, entity = HttpEntity(`application/json`, ErrorException(BadRequest.intValue,s"Parametros invalidos en la URL",s" ERROR: ${e.getMessage} $e").toString)))

    case e: Exception =>
      e.printStackTrace()
      println("Error no controlado", e)
      complete(HttpResponse(InternalServerError, entity = HttpEntity(`application/json`, ErrorException(InternalServerError.intValue,s"Error no manejado",s" ERROR: ${e.getMessage} $e").toString)))
  }


}
