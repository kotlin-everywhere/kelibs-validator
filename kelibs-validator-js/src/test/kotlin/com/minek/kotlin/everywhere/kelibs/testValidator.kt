package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.ifBlank
import com.minek.kotlin.everywhere.kelibs.validator.ifInvalid
import com.minek.kotlin.everywhere.kelibs.validator.validator
import org.junit.Test
import kotlin.test.assertEquals

class TestValidator {
    @Test
    fun testValidate() {
        class Model(val name: String)

        val validate = validator(Model::name to ifBlank("required"))

        assertEquals(listOf("required"), validate(Model("")))
        assertEquals(listOf(), validate(Model("john")))
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
}
