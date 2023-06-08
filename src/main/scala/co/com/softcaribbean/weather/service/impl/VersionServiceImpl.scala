package co.com.softcaribbean.weather.service.impl

import co.com.softcaribbean.weather.VersionDTO
import co.com.softcaribbean.weather.model.BaseDomain
import co.com.softcaribbean.weather.persistence.impl.VersionRepositoryImpl
import co.com.softcaribbean.weather.service.VersionService
import co.com.softcaribbean.weather.util.ErrorException


import scala.concurrent.{ExecutionContext, Future}

class VersionServiceImpl extends VersionService[VersionDTO,BaseDomain]{

  import co.com.softcaribbean.weather.util.bd.DatabaseConfigProvider._

  override def insertVersion(version: VersionDTO)(implicit ec: ExecutionContext): Future[BaseDomain] = {

    VersionRepositoryImpl.insertVersion(version).run(config).map { rows =>
      if (rows == 1) {
        VersionDTO(version.version, version.api)
      } else if (rows == 0) {
        ErrorException(999, "No se inserto la version", "")
      }else{
        VersionDTO(version.version, version.api)
      }
    }

  }
}

object VersionServiceImpl extends VersionServiceImpl
