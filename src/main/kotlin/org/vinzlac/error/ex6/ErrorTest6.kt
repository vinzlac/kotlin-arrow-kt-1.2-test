package org.vinzlac.error.ex6

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.nonEmptyListOf
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import io.kotest.matchers.shouldBe


sealed interface UserProblem {
    object EmptyName : UserProblem
    data class NegativeAge(val age: Int) : UserProblem
}

data class User private constructor (val name: String, val age: Int) {
    companion object {
        operator fun invoke(name: String, age: Int): Either<NonEmptyList<UserProblem>, Unit> =
            either {
                zipOrAccumulate(
                    { ensure(name.isNotEmpty()) { UserProblem.EmptyName } },
                    { ensure(age >= 0) { UserProblem.NegativeAge(age) } }
                ) { _, _ -> org.vinzlac.error.ex5.User(name, age) }
            }
    }
}

fun example() {
    org.vinzlac.error.ex5.User("", -1) shouldBe Left(nonEmptyListOf(UserProblem.EmptyName, UserProblem.NegativeAge(-1)))

}

fun main() {
    val user = org.vinzlac.error.ex5.User("", -1)
    either<NonEmptyList<String>, Int> {
        zipOrAccumulate(
            { ensure(false) { "false" } },
            { mapOrAccumulate(1..2) { ensure(false) { "$it: IsFalse" } } }
        ) { _, _ -> 1 }
    } shouldBe nonEmptyListOf("false", "1: IsFalse", "2: IsFalse").left()

    println(org.vinzlac.error.ex5.User("", -1))
    org.vinzlac.error.ex2.example()
}

