package com.minek.kotlin.everywhere.kelibs.validator

typealias Getter<M, T> = (M) -> T
typealias Validate<M, E> = (M) -> List<E>
typealias Validator<T, E> = (T) -> E?

fun <M, T, E : Any> validator(vararg validators: Pair<Getter<M, T>, Validator<T, E>>): Validate<M, E> {
    return { model ->
        validators.mapNotNull { (getter, validator) ->
            validator(getter(model))
        }
    }
}

fun <E> ifBlank(error: E): Validator<String, E> {
    return { if (it.isBlank()) error else null }
}

fun <T, E> ifInvalid(error: E, test: (T) -> Boolean): Validator<T, E> {
    return { if (test(it)) error else null }
}