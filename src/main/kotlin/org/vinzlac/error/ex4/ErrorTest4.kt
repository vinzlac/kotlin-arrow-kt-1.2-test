package org.vinzlac.error.ex4

import arrow.core.Either
import arrow.core.left
import arrow.core.mapOrAccumulate
import arrow.core.nonEmptyListOf
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import io.kotest.matchers.shouldBe

data class NotEven(val i: Int)

fun Raise<NotEven>.isEven3(i: Int) = ensure(i % 2 == 0) { NotEven(i) }

fun Raise<NotEven>.isEven(i: Int): Int =
    i.also { ensure(i % 2 == 0) { NotEven(i) } }

fun isEven2(i: Int): Either<NotEven, Int> =
    either { isEven(i) }

val errors = nonEmptyListOf(NotEven(1), NotEven(3), NotEven(5), NotEven(7), NotEven(9)).left()

val ints = nonEmptyListOf(2, 4, 6, 8, 10).right()

fun example() {
    (1..10).mapOrAccumulate { isEven(it) } shouldBe errors
    (1..10).mapOrAccumulate { isEven2(it).bind() } shouldBe errors
    (2..10 step 2).mapOrAccumulate { isEven2(it).bind() } shouldBe ints
}

fun main() {
    println(isEven2(1))
    println(isEven2(2))
    org.vinzlac.error.ex2.example()
    println( (2..10 step 2).mapOrAccumulate { isEven2(it).bind() })
}

