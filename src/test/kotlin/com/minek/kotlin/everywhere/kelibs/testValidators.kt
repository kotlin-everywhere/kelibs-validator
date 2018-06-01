package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.*
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

    @Test
    fun testIfNotMatched() {
        val validator = ifNotMatched("not matched", "^[a-z]+$".toRegex())
        assertEquals(listOf("not matched"), validator(""))
        assertEquals(listOf(), validator("a"))
        assertEquals(listOf(), validator("ab"))
        assertEquals(listOf("not matched"), validator("ab1"))

        val validator2 = ifNotMatched("^[a-z]+$".toRegex())
        assertEquals(listOf("Invalid input."), validator2(""))
        assertEquals(listOf(), validator2("a"))
        assertEquals(listOf(), validator2("ab"))
        assertEquals(listOf("Invalid input."), validator2("ab1"))
    }


    @Test
    fun testIfEmpty() {
        val validator = ifEmpty("empty")
        assertEquals(listOf("empty"), validator(listOf<String>()))
        assertEquals(listOf("empty"), validator(listOf<Int>()))
        assertEquals(listOf(), validator(listOf(1, 2, 3)))
        assertEquals(listOf(), validator(listOf("1", "2", "3")))

        val validator2 = ifEmpty
        assertEquals(listOf("This field is required."), validator2(listOf<String>()))
        assertEquals(listOf("This field is required."), validator2(listOf<Int>()))
        assertEquals(listOf(), validator2(listOf(1, 2, 3)))
        assertEquals(listOf(), validator2(listOf("1", "2", "3")))
    }
}