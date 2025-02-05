pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

rootProject.name = "Valorant"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":data:datastore")
include(":data:local")
include(":data:network")
include(":data:source")

include(":core:common")
include(":core:uikit")
include(":core:testing")

include(":lint")
include(":domain")

include(":feature:explore")
include(":feature:agent")
include(":feature:map")
include(":feature:weapon")
