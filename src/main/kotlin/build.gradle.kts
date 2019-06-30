buildscript {
  dependencies {
    classpath("pl.droidsonroids.gradle:gradle-pitest-plugin:${Versions.gradlePitestPlugin}")
  }
}

plugins {
  id("pl.droidsonroids.pitest")
}

pitest {
    excludeMockableAndroidJar = false
    targetClasses = setOf("jp.co.panpanini.mypackage.*")
    outputFormats = setOf("XML", "HTML")
}
