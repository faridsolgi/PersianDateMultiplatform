rootProject.name = "PersianDateMultiplatform"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven(uri("https://en-mirror.ir/"))
            google {
             mavenContent {
                  includeGroupAndSubgroups("androidx")
                  includeGroupAndSubgroups("com.android")
                  includeGroupAndSubgroups("com.google")
              }
          }

        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven(uri("https://en-mirror.ir/"))
         google {
             mavenContent {
                 includeGroupAndSubgroups("androidx")
                 includeGroupAndSubgroups("com.android")
                 includeGroupAndSubgroups("com.google")
             }
         }
         mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}


include(":library")