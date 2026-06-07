package com.example.test_application

import com.example.test_application.ui.VM.LoginValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class LoginValidatorTest () {

    val validator = LoginValidator()


@Test
    fun textWithRussianLetters_ReturnTrue () {

        val result = validator.hasCyrillic("тест@gmail.ru")

        assertTrue(result)
    }

    @Test
    fun textWithoutRussianLetters () {

        val result = validator.hasCyrillic("test@gmail.ru")

        assertFalse(result)
    }

}