@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.dokka") version "1.9.20" // برای javadoc
    `maven-publish`
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
publishing {
    publications.withType<MavenPublication>().configureEach {
        artifact(tasks["javadocJar"])
        pom {
            name.set("PersianDateMultiplatform")
            description.set("A Kotlin Multiplatform library for Persian (Jalali) dates.")
            url.set("https://github.com/faridsolgi/PersianDateMultiplatform")
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

    repositories {
        maven {
            val releasesRepoUrl =
                uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl =
                uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials {
                username =
                    findProperty("ossrhUsername") as String? ?: System.getenv("OSSRH_USERNAME")
                password =
                    findProperty("ossrhPassword") as String? ?: System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

// امضا با GPG
signing {
    useGpgCmd()
    publishing.publications.forEach { sign(it) }
}
