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
val Slf4jVersion = "2.0.5"
val CatsCoreVersion = "2.9.0"
val Client3Version = "3.8.15"
val Client3PlayJsonVersion = "3.8.13"
val Client3CoreVersion = "1.7.2"
val MonixVersion = "3.4.1"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "org.slf4j" % "slf4j-nop" % Slf4jVersion,
  "com.h2database" % "h2" % H2Version,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.play" %% "play-json" % PlayJsonVersion,
  "com.github.blemale" %% "scaffeine" % CaffeineVersion % "compile",
  "ch.qos.logback" % "logback-classic" % LogBack,
  "org.typelevel" %% "cats-core" % CatsCoreVersion,
  "com.softwaremill.sttp.client3" %% "core" % Client3Version,
  "com.softwaremill.sttp"         %% "core"  % Client3CoreVersion,
  "com.softwaremill.sttp"         %% "async-http-client-backend-future" % Client3CoreVersion,
  "com.softwaremill.sttp"         %% "play-json" % Client3CoreVersion,
  "com.softwaremill.sttp.client3" %%  "async-http-client-backend-monix" % Client3Version,
  "io.monix"                      %%  "monix"  % MonixVersion,
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % Client3PlayJsonVersion,
  "com.softwaremill.sttp.client3" %% "play-json" % Client3PlayJsonVersion


)
