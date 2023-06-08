package co.com.softcaribbean.weather.util.cache

import com.github.blemale.scaffeine.{Cache, Scaffeine}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration._

trait CacheProvider[A,B] {

  val Config: Config = ConfigFactory.load("application")
  val CacheConfig : Config = Config.getConfig("cache")
  val Expire: Duration = Duration(CacheConfig.getString("expire"))
  val Size: Long = CacheConfig.getLong("size")

  val cache: Cache[A,B] =
    Scaffeine()
      .recordStats()
      .expireAfterWrite(toScala(Expire))
      .maximumSize(Size).build[A,B]()

  def toScala (javaDuration: Duration): FiniteDuration = {
    Duration.fromNanos (javaDuration.toNanos)
  }
}
