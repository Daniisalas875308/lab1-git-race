package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.ExtendedModelMap
import org.springframework.ui.Model

class HelloControllerTimeTests {

    private lateinit var controller: HelloController
    private lateinit var model: Model

    @BeforeEach
    fun setup() {
        // El constructor puede variar según tu implementación
        controller = HelloController("Test Message")
        model = ExtendedModelMap()
    }

    @Test
    fun `should return greeting with name`() {
        val view = controller.welcome(model, "Alice")

        assertThat(view).isEqualTo("welcome")
        val message = model.getAttribute("message") as String
        assertThat(message).contains("Good")
        assertThat(message).contains("Alice")
    }

    @Test
    fun `should return greeting without name`() {
        val view = controller.welcome(model, "")

        assertThat(view).isEqualTo("welcome")
        val message = model.getAttribute("message") as String
        assertThat(message).contains("Good")
        // no debe contener un nombre
        assertThat(message).doesNotContain("Alice")
    }
}
