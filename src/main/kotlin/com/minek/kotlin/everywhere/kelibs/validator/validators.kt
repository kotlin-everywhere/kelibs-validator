package com.minek.kotlin.everywhere.kelibs.validator

fun <T, E> ifInvalid(error: E, test: (T) -> Boolean): (T) -> List<E> = { if (test(it)) listOf(error) else listOf() }

fun <T> ifInvalid(test: (T) -> Boolean): (T) -> List<String> = ifInvalid("Invalid", test)
