// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
//    id("org.jetbrains.kotlin.kapt") version "2.0.0-RC1" apply false
}
buildscript {
    val kotlin_version = "1.9.23"  // Kotlin 버전 업데이트
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}