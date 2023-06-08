package co.com.softcaribbean.weather.persistence.bd.base

import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcProfile

case class DataBaseConf(profile: JdbcProfile, db: JdbcBackend#DatabaseDef)
