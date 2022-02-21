val decomposeVersion by rootProject.extra { "0.4.0" }
val mviKotlinVersion by rootProject.extra { "3.0.0-alpha02" }
val coroutinesVersion by rootProject.extra { "1.5.2" }
val kodeinVersion by rootProject.extra { "7.9.0" }

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.5.31")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}