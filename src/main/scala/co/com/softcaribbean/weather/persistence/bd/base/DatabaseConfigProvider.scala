package co.com.softcaribbean.weather.persistence.bd.base

import com.typesafe.config.ConfigFactory
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object DatabaseConfigProvider {

  private val dbConfig: DatabaseConfig[JdbcProfile] =
    DatabaseConfigFactory.forConfig[JdbcProfile]("weather", ConfigFactory.load("application"))

  val config = DataBaseConf(dbConfig.profile, dbConfig.db)

}
