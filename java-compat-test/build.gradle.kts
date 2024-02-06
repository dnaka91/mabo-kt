@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.spotless)

    `java-library`
}

dependencies {
    implementation(projects.lib)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

spotless {
    java {
        eclipse()
        cleanthat()
    }

    kotlinGradle {
        ktlint(libs.versions.ktlint.get())
    }
}
