pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Education"
include(":app")
include(":domain")
include(":data")
include(":data:remote")
include(":data:local")
include(":presentation")
include(":presentation:core")
include(":presentation:ui")
include(":data:core")
include(":navigation")
include(":presentation:logic")
