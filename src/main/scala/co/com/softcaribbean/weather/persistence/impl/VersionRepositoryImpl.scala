  package co.com.softcaribbean.weather.persistence.impl

  import cats.data.Reader
  import co.com.softcaribbean.weather.VersionDTO
  import co.com.softcaribbean.weather.persistence.VersionRepository
  import co.com.softcaribbean.weather.persistence.bd.base.DataBaseConf
  import co.com.softcaribbean.weather.util.cache.CacheProvider
  import co.com.softcaribbean.weather.util.MarshallerCommon
  import play.api.libs.json.Json

  import scala.concurrent.{ExecutionContext, Future}

  sealed trait VersionRepositoryImpl extends VersionRepository[DataBaseConf,VersionDTO, Int]  with CacheProvider[String,VersionDTO] with MarshallerCommon {

    import co.com.softcaribbean.weather.persistence.bd.VersionTable._

    override def insertVersion(versionDto: VersionDTO)(implicit ec: ExecutionContext): Reader[DataBaseConf, Future[Int]] = Reader{
     dbConf: DataBaseConf =>
       import dbConf.profile.api._
       val CacheKey: String = (versionDto.version+"-bdcache")
       cache.getIfPresent(CacheKey) match {
           case Some(data) =>
             println("Get From Cache : "+Json.toJson(data))
             Future.successful(1)
           case None =>
             val findVersion = VersionDAO.filter(v => v.version ===versionDto.version).map(_.version)
             val query = for {
               seq <- dbConf.db.run(findVersion.result)
             } yield {
               seq.headOption match {
                 case Some(_) =>
                   val updateAction = for { c <- VersionDAO if c.version === versionDto.version } yield c
                   Some(updateAction.update(versionDto))
                 case _ => Some(VersionDAO += versionDto)
               }
             }
             query.flatMap {
               case Some(action) =>
                 println(s"Put Cache : $CacheKey")
                 cache.put(CacheKey,versionDto)
                 dbConf.db.run(action)
               case _ => Future.successful(-1)
             }
         }
    }

  }

  object VersionRepositoryImpl extends VersionRepositoryImpl

