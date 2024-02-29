package org.vinzlac.error.ex7

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.withError

val stringError: Either<String, Unit> = "problem".left()

val intError: Either<Int, Unit> = either {
    // transform error String -> Int
    withError({ it.length }) {
        stringError.bind()
    }
}

fun main() {
    println(intError)
}

