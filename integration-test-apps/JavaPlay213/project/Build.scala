import sbt._
import Keys._
import play.Project._
import de.johoop.jacoco4sbt._
import JacocoPlugin._
import info.schleichardt.sbt.sonar.SbtSonarPlugin._


object ApplicationBuild extends Build {

  val appName         = "JavaPlay213"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(jacoco.settings:_*).settings(
    jacoco.reportFormats in jacoco.Config := Seq(XMLReport())
  ).settings(sonarSettings:_*)
}
