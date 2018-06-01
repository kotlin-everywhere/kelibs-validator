package com.minek.kotlin.everywhere.kelibs.validator

import kotlin.reflect.KProperty1

fun <T, E, K> validator(vararg validators: Pair<K, (T) -> List<E>>): (T) -> Map<K, List<E>> = {
    validators.map { (key, validate) -> key to validate(it) }.toMap()
}

infix fun <T, R, E> KProperty1<T, R>.valid(validate: (R) -> List<E>): Pair<KProperty1<T, R>, (T) -> List<E>> =
        this to { it -> validate(get(it)) }

operator fun <T, R, E> KProperty1<T, R>.invoke(validate: (R) -> E?): Pair<KProperty1<T, R>, (T) -> List<E>> =
        this to { it -> validate(get(it))?.let(::listOf) ?: listOf() }

fun <T, E> valid(validate: (T) -> List<E>): Pair<(T) -> List<E>, (T) -> List<E>> {
    return validate to validate
}