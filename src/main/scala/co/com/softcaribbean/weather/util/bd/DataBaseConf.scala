package co.com.softcaribbean.weather.util.bd

import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcProfile

case class DataBaseConf(profile: JdbcProfile, db: JdbcBackend#DatabaseDef)
