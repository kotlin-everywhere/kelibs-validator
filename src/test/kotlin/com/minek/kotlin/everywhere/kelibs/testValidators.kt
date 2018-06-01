package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.ifInvalid
import kotlin.test.Test
import kotlin.test.assertEquals

class TestValidators {
    @Test
    fun testIfInvalid() {
        val validator = ifInvalid<Boolean, String>("false") { !it }
        assertEquals(listOf(), validator(true))
        assertEquals(listOf("false"), validator(false))

        val validator2 = ifInvalid<Boolean> { !it }
        assertEquals(listOf(), validator2(true))
        assertEquals(listOf("Invalid"), validator2(false))
    }
}