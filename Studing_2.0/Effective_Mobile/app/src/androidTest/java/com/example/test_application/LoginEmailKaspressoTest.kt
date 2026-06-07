package com.example.test_application

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class LoginEmailKaspressoTest : TestCase() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun invalidEmail_showsErrorText() = run {
        step("Вводим email без @") {
            composeTestRule
                .onNodeWithTag("email_field")
                .performTextInput("testgmail.com")
        }

        step("Проверяем текст ошибки") {
            composeTestRule.waitForIdle()

            composeTestRule
                .onNodeWithTag("email_error", useUnmergedTree = true)
                .assertIsDisplayed()
        }
    }
}