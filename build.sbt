name := """evaluadores"""
organization := "com.naranjalab"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  
// Use .class files instead of generated .scala files for views and routes



libraryDependencies ++= Seq(
filters,
javaJdbc,
"org.slf4j" % "slf4j-api" % "1.7.25",
"org.slf4j" % "slf4j-log4j12" % "1.7.25",
"org.xerial" % "sqlite-jdbc" % "3.8.6",
"commons-validator" % "commons-validator" % "1.6"
)
