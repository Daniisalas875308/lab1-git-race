package es.unizar.webeng.hello.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

@Controller
class HelloController(
    @param:Value("\${app.message:World}")
    private val message: String
) {
    private val logger = LoggerFactory.getLogger(HelloController::class.java)

    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String
    ): String {
        val currentHour = LocalTime.now().hour
        val timeGreeting = when (currentHour) {
            in 5..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            in 18..22 -> "Good Evening"
            else -> "Good Night"
        }

        val greeting = if (name.isNotBlank()) "$timeGreeting, $name!" else "$timeGreeting $message!"

        logger.info("Parametro recibido name='{}', greeting='{}'", name, greeting)
        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        return "welcome"
    }
}

@RestController
class HelloApiController {

    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        val hour = java.time.LocalTime.now().hour
        val timeGreeting = when (hour) {
            in 6..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            in 18..21 -> "Good Evening"
            else -> "Hello"
        }

        val message = "$timeGreeting, $name!"

        return mapOf(
            "message" to message,
            "timestamp" to java.time.Instant.now().toString()
        )
    }
}
