@file:Suppress("UnstableApiUsage")

plugins {
  id("com.android.application")
  id("kotlin-android")
}

android {
  compileSdk = 34

  namespace = "okhttp.android.testapp"

  testBuildType = "release"

  defaultConfig {
    minSdk = 21
    targetSdk = 34
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  buildFeatures {
    viewBinding = true
  }

  compileOptions {
    targetCompatibility(JavaVersion.VERSION_11)
    sourceCompatibility(JavaVersion.VERSION_11)
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
  }

  buildTypes {
    release {
      isShrinkResources = true
      isMinifyEnabled = true
      signingConfig = signingConfigs.getByName("debug")
      setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"))
      testProguardFiles("test-proguard-rules.pro")
    }
  }
}

dependencies {
  implementation(libs.playservices.safetynet)
  implementation(projects.okhttp)
  implementation(projects.okhttpAndroid)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.activity)
  implementation(libs.androidx.constraintlayout)

  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.assertk)
  implementation(libs.gson)

}
