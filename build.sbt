name := """testingmo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJpa,
  "mysql" % "mysql-connector-java" % "5.1.35",
  "org.hibernate" % "hibernate-core" % "5.1.0.Final",
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  cache,
  javaWs
)

EclipseKeys.preTasks := Seq(compile in Compile)

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes
libraryDependencies += evolutions