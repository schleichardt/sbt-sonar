package info.schleichardt.sbt.sonar

import sbt._
import Keys._
import scala.Predef._

object SbtSonarPlugin extends Plugin {

  val generateSonarPropertiesFile = TaskKey[Unit]("gen-sonar-prop", "Generates sonar property files.")

  val sonarProperties = SettingKey[Map[String, String]]("sonar-properties", "The used properties to configure sonar, see http://docs.codehaus.org/display/SONAR/Analysis+Parameters.")

  override lazy val settings = Seq(generateSonarPropertiesFile <<= (sonarProperties, target) map { (p, targetFolder) =>
    val propertiesAsString = p.toSeq.map { case (k, v) => "%s=%s".format(k, v) }.mkString("\n")
    val propertiesFile = targetFolder / "sonar-project.properties"
    IO.write(propertiesFile, propertiesAsString)
  }, sonarProperties <<= (version, organization, name, unmanagedSourceDirectories in Compile) { (v, org, n, sourceDirs) =>
    Map(
      "sonar.host.url" -> "http://localhost:9000",
      "sonar.projectKey" -> "%s:%s".format(org, n),
      "sonar.projectName" -> n,
      "sonar.projectVersion" -> v,
      "sonar.sources" -> sourceDirs.filter(_.exists).map(_.getAbsolutePath).mkString(",")
    )
  }
  )
}