plugins {
  kotlin("jvm")
}

dependencies {
  implementation(projects.okhttp)
  implementation(libs.squareup.moshi)
  implementation(libs.gson)
}
