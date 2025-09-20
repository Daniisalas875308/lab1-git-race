# Lab 1 Git Race -- Project Report

## Description of Changes
In this project, I implemented several modifications and enhancements to the base "Hello Web App". I choose to enhance the greeting functionality by making it time-dependent and personalized. The main changes include:

1. **Enhanced Greeting Functionality**:
    - The greeting in both the web page and API now changes depending on the current time of day.
    - Time-based greetings include: `Good Morning`, `Good Afternoon`, `Good Evening`, and `Good Night`. The ranges are:
        - Morning: 5 AM to 11:59 AM
        - Afternoon: 12 PM to 5:59 PM
        - Evening: 6 PM to 10:59 PM
        - Night: 11 PM to 4:59 AM
    - Personalized greetings now include the user’s name along with the appropriate time-based salutation.


2. **Controller Updates**:

    The controllers were updated to implement **time-based greetings**. Changes include:

   - Greetings now depend on the current hour:
       - `5..11` → `"Good Morning"`
       - `12..17` → `"Good Afternoon"`
       - `18..22` → `"Good Evening"`
       - `23..4` → `"Good Night"` (`HelloController`) / `"Hello"` (`HelloApiController`)  
         User names are included if provided; otherwise, the greeting uses the default app message.

   - The **default greeting message** was removed from `application.properties` so that all greetings are now generated dynamically based on the time of day.

   - **HelloController**: Computes greeting with `LocalTime.now(clock).hour` and logs the received `name` and generated greeting.

   - **HelloApiController**: Returns JSON with time-based greeting and `timestamp`. Clock injection allows deterministic testing.

   - **Testability**: Added `Clock` parameter to replace direct calls to `LocalTime.now()`, enabling predictable unit and integration tests.

   - Default behavior and API structure (`message` and `timestamp`) remain unchanged.


3. **Unit and Integration Tests Updates**:
    - Existing tests were updated to match the new greeting behavior. Changing the check messages to the new messages defined based on time
    - In the Unit tests, I defined a fixed 'Clock' at 8:30 AM to check the results based on Good Morning

4.  **New tests**:
    - `HelloControllerTimeZoneTests.kt`: These tests check that `HelloController` returns the correct greeting depending on the **hour of the day**:
      - Uses a **fixed Clock** to simulate different times for deterministic testing. 
      - Ensures the greeting message includes the correct salutation and user name (if provided). 
    - `HelloControllerTimeTests.kt`: These tests validate the **general behavior of `HelloController` greetings**:
        - Checks that the greeting message contains `"Good"` and the correct user name when a name is provided.
        - Confirms that when no name is provided, the greeting still starts with a time-appropriate `"Good"` but does not include a name.
      - `HelloApiControllerTimeTests.kt`: These tests validate **time-based greetings in the API** (`HelloApiController`):
        - Ensures that the JSON response contains a `"message"` with the correct salutation and user name (if provided). 
        - Confirms the presence of the `"timestamp"` field in all responses. 
        - Checks behavior both when a name is provided and when it is omitted.
        
## Technical Decisions
1. **Time-based Greetings**:
    - Used `LocalTime.now(clock).hour` to determine the correct salutation range.
    - Decided to separate ranges: `5-11` → Morning, `12-17` → Afternoon, `18-22` → Evening, `23-4` → Night.
    - For API consistency, slightly adjusted ranges to match daytime periods.

2. **Testing with Time Dependency**:
    - Introduced the `Clock` dependency in unit tests to inject a fixed time, ensuring deterministic test results.


## Learning Outcomes
- Learned to adapt legacy code to add new functionality without breaking existing behavior.
- Gained experience with Spring Boot controller.
- Learned how to inject `Clock` into controllers/tests for deterministic behavior.
- Improved understanding of REST APIs, Thymeleaf templating, and time-dependent logic in web applications.

## AI Disclosure

### AI Tools Used
- ChatGPT
- GitHub Copilot

### AI-Assisted Work
- Troubleshooting code structure and Gradle usage.
- Generated suggestions for test structures and integration test adaptation for time-based greetings.
- Generated explanations for technical errors and the differents options to solve these problems.
- Checking and correcting documentation.

### Original Work
- All controller logic, core functionality, and integration with Spring Boot were implemented manually.
- Tests were adapted and written manually to match the actual behavior of the application.
- Learned how to inject `Clock` for test determinism and integrated it into the application.
- Documentation, reasoning, and technical decisions were written entirely by me.
