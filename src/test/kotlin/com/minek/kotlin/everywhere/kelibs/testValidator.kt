package com.minek.kotlin.everywhere.kelibs

import com.minek.kotlin.everywhere.kelibs.validator.by
import com.minek.kotlin.everywhere.kelibs.validator.by2
import com.minek.kotlin.everywhere.kelibs.validator.validator
import kotlin.test.Test
import kotlin.test.assertEquals


class TestValidator {
    @Test
    fun testValidator() {
        data class Person(val name: String, val age: Int)

        val validNothing = { _: Person -> listOf<String>() }
        val validNothing2: (Person) -> String? = { _: Person -> null }

        val validate = validator(
                Person::name by { if (it.isBlank()) listOf("bad name") else listOf() },
                Person::age by2 { if (it < 18) "minor" else null },
                by(validNothing),
                by2(validNothing2)
        )

        assertEquals(
                mapOf<(Person) -> Any?, List<String>>(
                        Person::name to listOf("bad name"),
                        Person::age to listOf("minor")
                ),
                validate(Person("", 17))
        )

        // check key access
        val errors = validate(Person("", 17))
        assertEquals(listOf("bad name"), errors[Person::name])
        assertEquals(listOf("minor"), errors[Person::age])
        assertEquals(null, errors[validNothing])
        assertEquals(null, errors[validNothing2])
    }
}