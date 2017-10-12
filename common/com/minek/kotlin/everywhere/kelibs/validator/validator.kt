package com.minek.kotlin.everywhere.kelibs.validator

typealias Getter<M, T> = (M) -> T
typealias Validate<T, E> = (T) -> List<E>
typealias Validator<T, E> = (T) -> List<E>

fun <M, E : Any> validator(vararg validates: Validate<M, E>): Validate<M, E> {
    return { model ->
        validates.flatMap {
            it(model)
        }
    }
}

infix fun <M, T, E> Getter<M, T>.to(validator: (T) -> List<E>): Validate<M, E> {
    return { validator(this.invoke(it)) }
}

fun <T, E> ifInvalid(error: E, test: (T) -> Boolean): Validator<T, E> {
    return { if (test(it)) listOf(error) else listOf() }
}

fun <E> ifBlank(error: E): Validator<String, E> {
    return ifInvalid(error, String::isBlank)
}

fun <E> ifNotBetween(error: E, min: Int, max: Int): Validator<String, E> {
    return ifInvalid(error) { it.length < min || it.length > max }
}

fun <E> ifNotMatched(error: E, regex: Regex): Validator<String, E> {
    return ifInvalid(error) { !it.matches(regex) }
}

fun <E> ifEmpty(error: E): Validator<Collection<*>, E> {
    return ifInvalid(error) { it.isEmpty() }
}

fun <T, E> first(vararg validators: Validator<T, E>): Validator<T, E> {
    return {
        validators
                .asSequence()
                .map { validator -> validator(it) }
                .filter { it.isNotEmpty() }
                .firstOrNull() ?: listOf()
    }
}