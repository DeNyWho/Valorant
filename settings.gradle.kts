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
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "Valorant"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":lint")
include(":domain")

include(":data:datastore")
include(":data:local")
include(":data:network")
include(":data:source")

include(":core:common")
include(":core:uikit")
include(":core:testing")

include(":feature:agent")
include(":feature:map")
include(":feature:weapon")
include(":feature:agents")
include(":feature:maps")
include(":feature:weapons")
include(":feature:settings")
