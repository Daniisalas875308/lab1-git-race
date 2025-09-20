package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HelloApiControllerTimeTests {

    private lateinit var controller: HelloApiController

    @BeforeEach
    fun setup() {
        controller = HelloApiController()
    }

    @Test
    fun `should return greeting with name`() {
        val response = controller.helloApi("Daniel")

        assertThat(response["message"]).isInstanceOf(String::class.java)
        val message = response["message"] as String
        assertThat(message).contains("Good")
        assertThat(message).contains("Daniel")
        assertThat(response).containsKey("timestamp")
    }

    @Test
    fun `should return greeting without name`() {
        val response = controller.helloApi("")

        val message = response["message"] as String
        assertThat(message).contains("Good")
        assertThat(message).doesNotContain("Daniel")
        assertThat(response).containsKey("timestamp")
    }
}
