package com.minek.kotlin.everywhere.kelibs.validator

fun <T, E> ifInvalid(error: E, test: (T) -> Boolean): (T) -> List<E> {
    return { if (test(it)) listOf(error) else listOf() }
}