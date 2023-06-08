package co.com.softcaribbean.weather.util.bd

import co.com.softcaribbean.weather.VersionDTO
import co.com.softcaribbean.weather.model.HistoryDTO
import co.com.softcaribbean.weather.util.bd.VersionTable.api
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

 object VersionTable extends JdbcProfile {

    import api._

   class Version(tag: Tag) extends Table[VersionDTO](tag, "VERSION") {
     def version: Rep[String] = column[String]("cdversion")
     def api: Rep[String] = column[String]("dsapi")

     def * : ProvenShape[VersionDTO] = (version, api) <> (VersionDTO.tupled, VersionDTO.unapply)
   }

   val VersionDAO: TableQuery[Version] = TableQuery[Version]

  }

object HistoryTable extends JdbcProfile {
  import api._

  class History(tag: Tag) extends Table[HistoryDTO](tag, "HISTORY") {
    def location: Rep[String] = column[String]("cdubicacion")
    def response: Rep[String] = column[String]("dsresponse")

    def * : ProvenShape[HistoryDTO] = (location , response) <> (HistoryDTO.tupled, HistoryDTO.unapply)
  }

  val HistoryDAO: TableQuery[History] = TableQuery[History]

}
