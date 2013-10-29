package info.schleichardt.sbt.sonar

import sbt._
import Keys._
import scala.Predef._

object SbtSonarPlugin extends Plugin {

  val generateSonarPropertiesFile = TaskKey[Unit]("gen-sonar-prop", "Generates sonar property files.")

  val sonarProperties = SettingKey[Map[String, String]]("sonar-properties", "The used properties to configure sonar, see http://docs.codehaus.org/display/SONAR/Analysis+Parameters.")

  private def filePathsToString(files: Seq[File]) = files.filter(_.exists).map(_.getAbsolutePath).toSet.mkString(",")

  lazy val sonarSettings = Seq(generateSonarPropertiesFile <<= (sonarProperties, target) map { (p, targetDir) =>
    val resultingMap = p ++ hackForWrongSurefireTestResultNames(targetDir, p)
    val propertiesAsString = resultingMap.toSeq.map { case (k, v) => "%s=%s".format(k, v) }.mkString("\n")
    val propertiesFile = targetDir / "sonar-project.properties"
    IO.write(propertiesFile, propertiesAsString)
  }, sonarProperties <<= (version, organization, name, unmanagedSourceDirectories in Compile, unmanagedSourceDirectories in Test, classDirectory in Test, target) { (v, org, n, sourceDirs, testDirs, classDir, targetDir) =>
    Map(
      "sonar.host.url" -> "http://localhost:9000",
      "sonar.projectKey" -> "%s:%s".format(org, n),
      "sonar.projectName" -> n,
      "sonar.projectVersion" -> v,
      "sonar.dynamicAnalysis" -> "reuseReports",
      "sonar.tests" -> filePathsToString(testDirs),
      "sonar.binaries" -> filePathsToString(Seq(classDir)),
      "sonar.sourceEncoding" -> "UTF-8",
      "sonar.sources" -> filePathsToString(sourceDirs)
    )
  }
  )

  /**
   * Play hack, since surefire only accepts tests if they start with TEST-
   */
  private def hackForWrongSurefireTestResultNames(targetDir: File, p: Map[String, String]): Map[String, String] = {
    val guessedTestReportsFolder = targetDir / "test-reports"
    if (guessedTestReportsFolder.exists && !p.contains("sonar.surefire.reportsPath")) {
      val surefireTestReportsFolder = targetDir / "test-reports-sonar-copy"
      IO.copyDirectory(guessedTestReportsFolder, surefireTestReportsFolder, true)
      IO.listFiles(surefireTestReportsFolder).filter(_.isFile) foreach {
        testReportFile =>
          val Prefix = "TEST-"
          if (!testReportFile.getName.startsWith(Prefix)) {
            testReportFile.renameTo(new File(testReportFile.getParentFile, Prefix + testReportFile.getName))
          }
      }
      Map("sonar.surefire.reportsPath" -> filePathsToString(Seq(surefireTestReportsFolder)))
    } else {
      Map()
    }
  }
}