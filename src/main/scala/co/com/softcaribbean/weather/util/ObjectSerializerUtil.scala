package co.com.softcaribbean.weather.util

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module

object ObjectSerializerUtil {
  val nonNullMapper: ObjectMapper = new ObjectMapper()
    .registerModule(new Jdk8Module)
    .setSerializationInclusion(Include.NON_NULL)
}
