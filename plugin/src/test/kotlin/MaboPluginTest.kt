package rocks.dnaka91.mabo.plugin.gradle

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

class MaboPluginTest {
    @Test fun `plugin registers task`() {
        // Create a test project and apply the plugin
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("rocks.dnaka91.mabo")

        // Verify the result
        assertNotNull(project.tasks.findByName("greeting"))
    }
}
