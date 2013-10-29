sbt-sonar
=========

SBT plugin to generate sonar properties for projects.

Work in progress without warranties.

It _should_ work with SBT 0.11.3, 0.12.x and 0.13.0.

## Usage

Add to project/plugins.sbt:

```scala
//release versions will be pushed to Maven Central
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addSbtPlugin("info.schleichardt" % "sbt-sonar" % "0.1.0-SNAPSHOT")
```

Run sonar: 

```bash
sbt test gen-sonar-prop && cd target && sonar-runner
```

Use the geb-sonar-prop task after the testing tasks, since the configuration checks for existing folders.

## Customization

### Override or Add settings in build.sbt

#### 0.13.0:

```scala
sonarProperties := sonarProperties.value ++ 
Map("sonar.projectName" -> "override-name", "new.key" -> "new.value")
```

#### Older SBT

```scala 
sonarProperties ~= { old => old ++ 
Map("sonar.projectName" -> "override-name", "new.key" -> "new.value") }
```



