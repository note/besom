package besom.languageplugin.test.pulumiapp

import besom.*

@main def run = Pulumi.run {
  // Making sure the plugin is actually on the classpath
  val x = besom.languageplugin.test.resourceplugin.standard.customVal
  val y = besom.languageplugin.test.resourceplugin.external.customVal

  // Show that we were executed for tests to read
  for _ <- log.warn("scala executor test got executed")
  yield exports()
}
