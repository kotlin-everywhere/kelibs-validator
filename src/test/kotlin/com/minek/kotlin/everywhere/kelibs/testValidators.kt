package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.ifBlank
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

    @Test
    fun testIfBlank() {
        val validator = ifBlank("blank")
        assertEquals(listOf("blank"), validator(""))
        assertEquals(listOf("blank"), validator(" ")) // test white space
        assertEquals(listOf(), validator("not blank"))

        val validator2 = ifBlank
        assertEquals(listOf("This field is required."), validator2(""))
        assertEquals(listOf("This field is required."), validator2(" ")) // test white space
        assertEquals(listOf(), validator2("not blank"))
    }
}