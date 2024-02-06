@file:Suppress("UnstableApiUsage")

import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import java.net.URL

plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kover)
    alias(libs.plugins.spotless)

    `java-library`
}

dependencies {
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin.get())
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
        languageVersion = KotlinVersion.KOTLIN_2_0
        freeCompilerArgs.addAll("-Xjvm-default=all")
    }

    explicitApi()
}

spotless {
    kotlin {
        ktlint(libs.versions.ktlint.get())
    }

    kotlinGradle {
        ktlint(libs.versions.ktlint.get())
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            jdkVersion = 17

            moduleName = "Mabo"
            includes.from("Module.md")

            sourceLink {
                localDirectory = file("src/main/kotlin")
                remoteUrl = URL("https://github.com/dnaka91/mabo-kt/tree/main/lib/src/main/kotlin")
                remoteLineSuffix = "#L"
            }
        }
    }
}
