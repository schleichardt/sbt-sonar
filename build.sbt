sbtPlugin := true

organization := "info.schleichardt"

name := "sbt-sonar"

//TODO extract and use sbt-release
version := "0.1-SNAPSHOT"

scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-Xlint"
)

scalariformSettings

CrossBuilding.scriptedSettings

crossBuildingSettings

CrossBuilding.crossSbtVersions := Seq("0.11.3", "0.12", "0.13")

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

publishMavenStyle := true

publishArtifact in Test := false

publishTo <<= version { (v: String) =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
    else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

pomExtra := (
    <url>https://github.com/schleichardt/sbt-sonar</url>
    <licenses>
        <license>
            <name>Apache 2</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0</url>
            <distribution>repo</distribution>
        </license>
    </licenses>
    <scm>
        <url>git@github.com:schleichardt/sbt-sonar.git</url>
        <connection>scm:git:git@github.com:schleichardt/sbt-sonar.git</connection>
    </scm>
    <developers>
        <developer>
            <id>schleichardt</id>
            <name>Michael Schleichardt</name>
            <url>http://michael.schleichardt.info</url>
        </developer>
    </developers>
)
