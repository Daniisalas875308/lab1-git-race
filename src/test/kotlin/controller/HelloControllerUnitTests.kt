package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import org.springframework.ui.ExtendedModelMap
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class HelloControllerUnitTests {
    private lateinit var controller: HelloController
    private lateinit var model: Model
    
    @BeforeEach
    fun setup() {
        val fixedClock = Clock.fixed(Instant.parse("2025-09-20T08:30:00Z"), ZoneId.of("UTC")) // Definimos horario a las 8:30
        controller = HelloController("Test Message", fixedClock)
        model = ExtendedModelMap()
    }
    
    @Test
    fun `should return welcome view with default message`() {
        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Good Morning Test Message!")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test
    fun `should return welcome view with personalized message`() {
        val view = controller.welcome(model, "Developer")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Good Morning, Developer!")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }
    
    @Test
    fun `should return API response with timestamp`() {
        val apiController = HelloApiController(Clock.fixed(Instant.parse("2025-09-20T08:30:00Z"), ZoneId.of("UTC")))
        val response = apiController.helloApi("Test")
        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Good Morning, Test!")
        assertThat(response["timestamp"]).isNotNull()
    }

}
