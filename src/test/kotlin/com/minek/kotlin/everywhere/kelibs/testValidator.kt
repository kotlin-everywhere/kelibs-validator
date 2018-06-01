package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.invoke
import com.minek.kotlin.everywhere.kelibs.validator.valid
import com.minek.kotlin.everywhere.kelibs.validator.validator
import kotlin.test.Test
import kotlin.test.assertEquals


class TestValidator {
    @Test
    fun testValidator() {
        data class Person(val name: String, val age: Int)

        val validNothing = { _: Person -> listOf<String>() }

        val validate = validator(
                Person::name { if (it.isBlank()) "bad name" else null },
                Person::age valid { if (it < 18) listOf("minor") else listOf() },
                valid(validNothing)
        )

        assertEquals(
                mapOf(
                        Person::name to listOf("bad name"),
                        Person::age to listOf("minor"),
                        validNothing to listOf()
                ),
                validate(Person("", 17))
        )

        // check key access
        val errors = validate(Person("", 17))
        assertEquals(listOf("bad name"), errors[Person::name])
        assertEquals(listOf("minor"), errors[Person::age])
        assertEquals(listOf(), errors[validNothing])
    }
}