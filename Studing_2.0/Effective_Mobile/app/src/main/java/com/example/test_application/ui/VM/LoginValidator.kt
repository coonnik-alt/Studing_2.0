package com.example.test_application.ui.VM

class LoginValidator {

    fun hasCyrillic (value : String) : Boolean {

        return value.any { char ->
            char in 'А'..'я' || char == 'ё' || char == 'Ё'
        }
    }

    fun isEmailValid (email : String) : Boolean{
        return email.contains("@")  && email.contains(".")
    }
}