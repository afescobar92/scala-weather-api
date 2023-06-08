package co.com.softcaribbean.weather.util.bd

import com.typesafe.config.ConfigFactory
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object DatabaseConfigProvider {

  private val dbConfig: DatabaseConfig[JdbcProfile] =
    DatabaseConfigFactory.forConfig[JdbcProfile]("weather", ConfigFactory.load("application"))

  val config = DataBaseConf(dbConfig.profile, dbConfig.db)

}
