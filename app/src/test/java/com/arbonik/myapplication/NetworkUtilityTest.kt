package com.arbonik.myapplication

import com.arbonik.myapplication.network.uriParseToUserActivation
import org.junit.Assert.*
import org.junit.Test

class NetworkUtilityTest{
    //Тестирование, правильно ли анализируется строка для активации аккаунта
    @Test
    fun uri_parse_test(){
        val uri = "https://ke22.ru/confirmation-email/MTE/5oc-81af424b959c5e84555c"
        val rightToken = "5oc-81af424b959c5e84555c"
        val rightUID = "MTE"

        val userActivation = uriParseToUserActivation(uri)
        println(userActivation)
        assertEquals(rightToken, userActivation.token)
        assertEquals(rightUID, userActivation.uid)
    }
}