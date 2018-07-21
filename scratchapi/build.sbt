import Dependencies._

val Http4sVersion = "0.18.12"
val Specs2Version = "4.2.0"
val LogbackVersion = "1.2.3"
val ScalazVersion = "7.2.24"

cancelable in Global := true
fork in run := true

scalacOptions ++= Seq(
    "-Ypartial-unification",
//    "-Xlog-implicits",
    )

lazy val root = (project in file(".")).
settings(
    inThisBuild(List(
        organization := "com.example",
        scalaVersion := "2.12.6"
        //version      := "0.1.0-SNAPSHOT"
        )),
    name := "scratchapi",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.scalaz"     %% "scalaz-core"          % ScalazVersion,
      "org.scalaz"     %% "scalaz-ioeffect"      % "2.3.0",
      "org.scalaz"     %% "scalaz-ioeffect-cats" % "2.3.0",
      "com.codecommit" %% "shims"                % "1.2.1",
      //      "org.http4s"     %% "http4s-core"          % Http4sVersion,
      "org.http4s"     %% "http4s-blaze-server"  % Http4sVersion,
      "org.http4s"     %% "http4s-circe"         % Http4sVersion,
      "org.http4s"     %% "http4s-dsl"           % Http4sVersion,
      "org.scalaj"     %% "scalaj-http"          % "2.4.0",
      "org.specs2"     %% "specs2-core"          % Specs2Version % "test",
      "ch.qos.logback" %  "logback-classic"      % LogbackVersion
      )
    )
