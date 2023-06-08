name := "api-weather"

version := "0.1"

scalaVersion := "2.13.10"
val SlickVersion = "3.4.1"
val H2Version = "2.1.214"
val AkkaVersion = "2.8.0"
val AkkaHttpVersion = "10.5.2"
val PlayJsonVersion = "2.9.4"
val CaffeineVersion = "5.2.1"
val LogBack = "1.4.7"
val CatsCoreVersion = "2.9.0"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "org.slf4j" % "slf4j-nop" % "2.0.5",
  "com.h2database" % "h2" % H2Version,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.play" %% "play-json" % PlayJsonVersion,
  "com.github.blemale" %% "scaffeine" % CaffeineVersion % "compile",
  "ch.qos.logback" % "logback-classic" % LogBack,
  "org.typelevel" %% "cats-core" % CatsCoreVersion,
  "com.softwaremill.sttp.client3" %% "core" % "3.8.15",
  "com.softwaremill.sttp"      %% "core"                   % "1.7.2",
  "com.softwaremill.sttp"      %% "async-http-client-backend-future" % "1.7.2",
  "com.softwaremill.sttp"      %% "play-json"              % "1.7.2",
  "com.softwaremill.sttp.client3"         %%  "async-http-client-backend-monix"         % "3.8.13",
  "io.monix"                              %%  "monix"                                   % "3.4.1",
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % "3.8.13",
  "com.softwaremill.sttp.client3" %% "play-json" % "3.8.13"


)
