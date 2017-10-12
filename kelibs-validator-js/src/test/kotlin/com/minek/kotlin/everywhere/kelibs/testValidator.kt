package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.*
import org.junit.Test
import kotlin.test.assertEquals

class TestValidator {
    @Test
    fun testValidate() {
        class Model(val name: String, val age: Int)

        val validate = validator(
                Model::name to ifBlank("required"),
                Model::age to { if (it < 18) listOf("minor") else listOf() }
        )

        assertEquals(listOf("required"), validate(Model("", 18)))
        assertEquals(listOf(), validate(Model("john", 18)))
        assertEquals(listOf("minor"), validate(Model("john", 17)))
    }

    @Test
    fun testIfInvalid() {
        val validator = ifInvalid<Boolean, String>("false") { !it }
        assertEquals(listOf(), validator(true))
        assertEquals(listOf("false"), validator(false))
    }

    @Test
    fun testIfBlank() {
        val validator = ifBlank("blank")
        assertEquals(listOf("blank"), validator(""))
        assertEquals(listOf("blank"), validator(" ")) // test white space
        assertEquals(listOf(), validator("not blank"))
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
    }

    @Test
    fun testIfNotMatched() {
        val validator = ifNotMatched("not matched", "^[a-z]+$".toRegex())
        assertEquals(listOf("not matched"), validator(""))
        assertEquals(listOf(), validator("a"))
        assertEquals(listOf(), validator("ab"))
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
        assertEquals(listOf(), validate(Model("john")))
    }
}
