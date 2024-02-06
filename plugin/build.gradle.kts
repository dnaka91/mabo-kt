@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kover)
    alias(libs.plugins.spotless)

    `java-gradle-plugin`
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin.get())
        }

        val functionalTest by registering(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin.get())

            dependencies {
                implementation(project())
            }

            targets {
                all {
                    testTask.configure { shouldRunAfter(test) }
                }
            }
        }
    }
}

gradlePlugin {
    val mabo by plugins.creating {
        id = "rocks.dnaka91.mabo"
        implementationClass = "rocks.dnaka91.mabo.plugin.gradle.MaboPlugin"
    }
}

gradlePlugin.testSourceSet(sourceSets["functionalTest"])

tasks.named<Task>("check") {
    dependsOn(testing.suites.named("functionalTest"))
}

spotless {
    kotlin {
        ktlint(libs.versions.ktlint.get())
    }

    kotlinGradle {
        ktlint(libs.versions.ktlint.get())
    }
}

kover {
    excludeSourceSets {
        names(testing.suites.names)
    }
}
