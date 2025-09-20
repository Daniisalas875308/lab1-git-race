package es.unizar.webeng.hello.controller

import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(HelloController::class, HelloApiController::class)
class HelloControllerMVCTests {
    @Value("\${app.message:Welcome to the Modern Web App!}")
    private lateinit var message: String

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val validGreetings = listOf("Good Morning", "Good Afternoon", "Good Evening", "Good Night")

    @Test
    fun `should return home page with default message`() {

        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", allOf(
                startsWithIn(validGreetings) // cualquier saludo correcto
            )))
            .andExpect(model().attribute("name", equalTo("")))
    }


    @Test
    fun `should return home page with personalized message`() {
        val nameValue = "Developer"  // Cambié 'name' a 'nameValue' para evitar conflictos con imports
        mockMvc.perform(get("/").param("name", nameValue))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", allOf(
                startsWithIn(validGreetings),
                containsString(nameValue)
            )))
            .andExpect(model().attribute("name", equalTo(nameValue)))
    }

    @Test
    fun `should return API response as JSON`() {
        val nameValue = "Test"
        mockMvc.perform(get("/api/hello").param("name", nameValue))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", allOf(
                startsWithIn(validGreetings),
                containsString(nameValue)
            )))
            .andExpect(jsonPath("$.timestamp").exists())
    }


    // Helper matcher para verificar que el mensaje empieza con alguno de los saludos válidos
    private fun startsWithIn(list: List<String>) = object : org.hamcrest.BaseMatcher<String>() {
        override fun describeTo(description: org.hamcrest.Description?) {
            description?.appendText("starts with one of $list")
        }
        override fun matches(item: Any?): Boolean {
            if (item !is String) return false
            return list.any { item.startsWith(it) }
        }
    }

}

