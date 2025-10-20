// Always add scalafmt plugin
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")

// Conditionally add GitHub packages plugin only when GITHUB_TOKEN is available
// This is handled in build.sbt instead to avoid SBT loading issues
