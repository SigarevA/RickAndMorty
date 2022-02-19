pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "RickAndMorty"
include(":androidApp")
include(":shared")
include(":data-remote")
