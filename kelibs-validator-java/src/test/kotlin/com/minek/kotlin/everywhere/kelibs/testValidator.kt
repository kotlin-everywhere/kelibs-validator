package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.*
import org.junit.Assert.assertEquals
import org.junit.Test

class TestValidator {
    @Test
    fun testValidate() {
        class Model(val name: String)

        val validate = validator(Model::name to ifBlank("required"))

        assertEquals(listOf("required"), validate(Model("")))
        assertEquals(listOf<String>(), validate(Model("john")))
    }

    @Test
    fun testIfInvalid() {
        val validator = ifInvalid<Boolean, String>("false") { !it }
        assertEquals(listOf<String>(), validator(true))
        assertEquals(listOf("false"), validator(false))
    }

    @Test
    fun testIfBlank() {
        val validator = ifBlank("blank")
        assertEquals(listOf("blank"), validator(""))
        assertEquals(listOf("blank"), validator(" ")) // test white space
        assertEquals(listOf<String>(), validator("not blank"))
    }

    @Test
    fun testIfNotBetween() {
        val validator = ifNotBetween("not between", 3, 5)
        assertEquals(listOf("not between"), validator(""))
        assertEquals(listOf("not between"), validator("1"))
        assertEquals(listOf("not between"), validator("12"))
        assertEquals(listOf<String>(), validator("123"))
        assertEquals(listOf<String>(), validator("1234"))
        assertEquals(listOf<String>(), validator("12345"))
        assertEquals(listOf("not between"), validator("123456"))
    }

    @Test
    fun testIfNotMatched() {
        val validator = ifNotMatched("not matched", "^[a-z]+$".toRegex())
        assertEquals(listOf("not matched"), validator(""))
        assertEquals(listOf<String>(), validator("a"))
        assertEquals(listOf<String>(), validator("ab"))
        assertEquals(listOf("not matched"), validator("ab1"))
    }

    @Test
    fun testFirst() {
        class Model(val name: String)

        val validate = validator(
                Model::name to first(
                        ifBlank("required"),
                        ifInvalid("invalid chars") { !it.matches("^[a-z]+$".toRegex()) }
                )
        )

        assertEquals(listOf("required"), validate(Model("")))
        assertEquals(listOf("invalid chars"), validate(Model("존")))
        assertEquals(listOf<String>(), validate(Model("john")))
    }
}
