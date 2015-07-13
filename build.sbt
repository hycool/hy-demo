name := "hy-demo"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.jvnet.hudson" % "xstream" % "1.3.1-hudson-8",
  "org.jvnet.hudson.dom4j" % "dom4j" % "1.6.1-hudson-3"
)     

play.Project.playJavaSettings
