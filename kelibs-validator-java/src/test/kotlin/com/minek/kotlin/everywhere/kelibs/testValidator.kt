package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.ifBlank
import com.minek.kotlin.everywhere.kelibs.validator.ifInvalid
import com.minek.kotlin.everywhere.kelibs.validator.validator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TestValidator {
    @Test
    fun testValidate() {
        class Model(val name: String)

        val validate = validator(
                Model::name to ifBlank("required")
        )

        assertEquals(listOf("required"), validate(Model("")))
        assertEquals(listOf<String>(), validate(Model("john")))
    }

    @Test
    fun testIfInvalid() {
        val ifFalse = ifInvalid<Boolean, String>("false") { !it }
        assertNull(ifFalse(true))
        assertEquals("false", ifFalse(false))
    }
}
