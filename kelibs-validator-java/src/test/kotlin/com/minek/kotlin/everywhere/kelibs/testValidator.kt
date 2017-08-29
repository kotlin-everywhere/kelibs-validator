package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.ifBlank
import com.minek.kotlin.everywhere.kelibs.validator.validator
import org.junit.Assert.assertEquals
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
}
