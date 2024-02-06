plugins {
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.spotless)
}

spotless {
    kotlinGradle {
        ktlint(libs.versions.ktlint.get())
    }
}

tasks.getByName<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
