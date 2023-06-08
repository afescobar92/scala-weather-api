package co.com.softcaribbean.weather.util.bd

import co.com.softcaribbean.weather.model.DatabaseException
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import slick.basic.BasicProfile
import slick.basic.DatabaseConfig
import slick.jdbc._
import slick.util.ClassLoaderUtil

import scala.reflect.ClassTag

object DatabaseConfigFactory {

  val basePath: String          = "databases"
  val defaultConfigPath: String = "default"

  def forConfig[P <: BasicProfile: ClassTag](
      name: String,
      config: Config = ConfigFactory.load(),
      classLoader: ClassLoader = ClassLoaderUtil.defaultClassLoader): DatabaseConfig[P] = {

    if (defaultConfigPath.equals(name))
      throw new DatabaseException("Configuracion de base de datos 'default' esta reservado")

    val databaseConfig: String = s"$basePath.$name"
    if (!config.hasPath(databaseConfig))
      throw new DatabaseException(
        s"configuracion de base de datos no encontrada para '$databaseConfig'")

    val mergedb = config.getConfig(databaseConfig)

    val merge = config.withFallback(mergedb).atPath(databaseConfig)

    DatabaseConfig.forConfig[P](databaseConfig, config.withFallback(merge), classLoader)
  }

  def obtenerJdbcProfile(profile: String): JdbcProfile = {
    profile match {
      case "slick.driver.DerbyDriver$"    => DerbyProfile
      case "slick.driver.H2Driver$"       => H2Profile
      case "slick.driver.HsqldbDriver$"   => HsqldbProfile
      case "slick.driver.MySQLDriver$"    => MySQLProfile
      case "slick.driver.PostgresDriver$" => PostgresProfile
      case _                              => throw new DatabaseException("No encontro un profile de Base de datos conocido")
    }
  }

}
