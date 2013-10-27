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

