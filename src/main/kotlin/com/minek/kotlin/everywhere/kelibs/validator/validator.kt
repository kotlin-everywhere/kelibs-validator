package com.minek.kotlin.everywhere.kelibs.validator

fun <T, E, K> validator(vararg validators: Pair<K, (T) -> List<E>>): (T) -> Map<K, List<E>> = {
    validators.map { (key, validate) -> key to validate(it) }
            .filter { it.second.isNotEmpty() }
            .toMap()
}

infix fun <T, R, E> ((T) -> R).by(validate: (R) -> List<E>): Pair<(T) -> R, (T) -> List<E>> =
        this to { it -> validate(invoke(it)) }

infix fun <T, R, E> ((T) -> R).by2(validate: (R) -> E?): Pair<((T) -> R), (T) -> List<E>> =
        this to { it -> validate(invoke(it))?.let(::listOf) ?: listOf() }

fun <T, E> by(validate: (T) -> List<E>): Pair<(T) -> List<E>, (T) -> List<E>> {
    return validate to validate
}

fun <T, E> by2(validate: (T) -> E?): Pair<(T) -> E?, (T) -> List<E>> {
    return validate to { it -> validate(it)?.let(::listOf) ?: listOf() }
}