plugins {
  id("pl.droidsonroids.pitest")
}

pitest {
    excludeMockableAndroidJar = false
    targetClasses = setOf("jp.co.panpanini.mypackage.*")
    outputFormats = setOf("XML", "HTML")
}
