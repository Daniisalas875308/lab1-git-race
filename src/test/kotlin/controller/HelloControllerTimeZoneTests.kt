package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.ExtendedModelMap
import org.springframework.ui.Model
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class HelloControllerTimeZoneTests {

    private lateinit var controller: HelloController
    private lateinit var model: Model

    @BeforeEach
    fun setup() {
        model = ExtendedModelMap()
    }

    private fun createControllerAt(hour: Int): HelloController {
        val instant = Instant.parse("2025-09-20T${hour.toString().padStart(2,'0')}:00:00Z")
        val clock = Clock.fixed(instant, ZoneId.of("UTC"))
        return HelloController("World", clock)
    }

    @Test
    fun `should return Good Morning`() {
        controller = createControllerAt(6)
        val view = controller.welcome(model, "Alice")
        assertThat(model.getAttribute("message")).isEqualTo("Good Morning, Alice!")
    }

    @Test
    fun `should return Good Afternoon`() {
        controller = createControllerAt(13)
        val view = controller.welcome(model, "Bob")
        assertThat(model.getAttribute("message")).isEqualTo("Good Afternoon, Bob!")
    }

    @Test
    fun `should return Good Evening`() {
        controller = createControllerAt(19)
        val view = controller.welcome(model, "Charlie")
        assertThat(model.getAttribute("message")).isEqualTo("Good Evening, Charlie!")
    }

    @Test
    fun `should return Good Night`() {
        controller = createControllerAt(23)
        val view = controller.welcome(model, "Dana")
        assertThat(model.getAttribute("message")).isEqualTo("Good Night, Dana!")
    }

    @Test
    fun `should return greeting without name`() {
        controller = createControllerAt(9)
        val view = controller.welcome(model, "")
        assertThat(model.getAttribute("message")).isEqualTo("Good Morning World!")
    }
}
