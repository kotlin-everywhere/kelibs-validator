package com.minek.kotlin.everywhere.kelibs.validator

fun <T, E> ifInvalid(error: E, test: (T) -> Boolean): (T) -> List<E> = { if (test(it)) listOf(error) else listOf() }

fun <T> ifInvalid(test: (T) -> Boolean): (T) -> List<String> = ifInvalid("Invalid", test)

fun <E> ifBlank(error: E): (String) -> List<E> = ifInvalid(error, String::isBlank)

val ifBlank: (String) -> List<String> = { ifBlank("This field is required.")(it) }

fun <E> ifNotBetween(error: E, min: Int, max: Int): (String) -> List<E> =
        ifInvalid(error) { it.length < min || it.length > max }

fun ifNotBetween(min: Int, max: Int): (String) -> List<String> =
        ifNotBetween("Field must be between $min and $max characters long.", min, max)


fun <E> ifNotMatched(error: E, regex: Regex): (String) -> List<E> = ifInvalid(error) { !it.matches(regex) }

fun ifNotMatched(regex: Regex): (String) -> List<String> = ifNotMatched("Invalid input.", regex)

fun <E> ifEmpty(error: E): (Collection<*>) -> List<E> = ifInvalid(error) { it.isEmpty() }

val ifEmpty: (Collection<*>) -> List<String> = ifEmpty("This field is required.")
