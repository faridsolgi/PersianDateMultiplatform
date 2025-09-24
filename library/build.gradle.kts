@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.vanniktech.maven.publish") version "0.34.0"
    id("maven-publish")
    signing
}

group = "com.faridsolgi.persiandatemultiplatform"
version = "0.0.1"

kotlin {
    jvm()
    wasmJs {
        browser()
        nodejs()
        d8()
    }
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.faridsolgi.persiandatemultiplatform"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// javadoc jar با dokka
tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
}

tasks.named("publishKotlinMultiplatformPublicationToMavenLocal") {
    dependsOn("signKotlinMultiplatformPublication")
    dependsOn("signJvmPublication")
}
tasks.withType<PublishToMavenLocal>().configureEach {
    dependsOn(tasks.withType<Sign>())
}
// مطمئن شو هر publication سورس + javadoc داره
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates(group.toString(), "persiandatemultiplatform", version.toString())


    pom {
        name = "Persian Date Multiplatform"
        description = "A Kotlin Multiplatform library for Persian (Jalali) dates."
        inceptionYear = "2025"
        url = "https://github.com/faridsolgi/PersianDateMultiplatform"
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("faridsolgi")
                name.set("Farid Solgi")
                email.set("you@example.com")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/faridsolgi/PersianDateMultiplatform.git")
            developerConnection.set("scm:git:ssh://github.com:faridsolgi/PersianDateMultiplatform.git")
            url.set("https://github.com/faridsolgi/PersianDateMultiplatform")
        }
    }
}
