package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.ifBlank
import com.minek.kotlin.everywhere.kelibs.validator.ifInvalid
import com.minek.kotlin.everywhere.kelibs.validator.validator
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
}
