name := """hotel-urbano"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := """<empty>;Reverse.*;"""

ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 80

ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := true

ScoverageSbtPlugin.ScoverageKeys.coverageHighlighting := false

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23",
  "org.mongodb" %% "casbah" % "2.8.0",
  "org.scalatestplus" %% "play" % "1.1.0" % "test"
)
