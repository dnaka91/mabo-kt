package rocks.dnaka91.mabo.plugin.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class MaboPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("greeting") { task ->
            task.doLast {
                println("Hello from plugin 'rocks.dnaka91.mabo'")
            }
        }
    }
}
