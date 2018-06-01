package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.ifBlank
import com.minek.kotlin.everywhere.kelibs.validator.ifInvalid
import com.minek.kotlin.everywhere.kelibs.validator.ifNotBetween
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

    @Test
    fun testIfNotBetween() {
        val validator = ifNotBetween("not between", 3, 5)
        assertEquals(listOf("not between"), validator(""))
        assertEquals(listOf("not between"), validator("1"))
        assertEquals(listOf("not between"), validator("12"))
        assertEquals(listOf(), validator("123"))
        assertEquals(listOf(), validator("1234"))
        assertEquals(listOf(), validator("12345"))
        assertEquals(listOf("not between"), validator("123456"))

        val validator2 = ifNotBetween(3, 5)
        assertEquals(listOf("Field must be between 3 and 5 characters long."), validator2(""))
        assertEquals(listOf("Field must be between 3 and 5 characters long."), validator2("1"))
        assertEquals(listOf("Field must be between 3 and 5 characters long."), validator2("12"))
        assertEquals(listOf(), validator2("123"))
        assertEquals(listOf(), validator2("1234"))
        assertEquals(listOf(), validator2("12345"))
        assertEquals(listOf("Field must be between 3 and 5 characters long."), validator2("123456"))
    }
}