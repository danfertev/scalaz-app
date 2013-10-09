import sbt._
import sbt.Keys._

object AppBuild extends Build {
  val appName = "scalaz-app"
  val appOrganization = "ru.anfdenis"
  val appVersion = "0.1-SNAPSHOT"
  val appScalaVersion = "2.10.2"

  val appDependencies = Seq(
    "org.scalaz" %% "scalaz-core" % "7.0.3"
  )

  val appSettings = Defaults.defaultSettings ++ Seq(
    organization := appOrganization,
    version := appVersion,
    scalaVersion := appScalaVersion,
    libraryDependencies ++= appDependencies,
    initialCommands in console := "import scalaz._"
  )

  lazy val app = Project(
    appName, 
    file ("."), 
    settings = Defaults.defaultSettings ++ appSettings
  )
  
}