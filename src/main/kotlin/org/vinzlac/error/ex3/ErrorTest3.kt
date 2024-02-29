package org.vinzlac.error.ex3

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.fold
import arrow.core.right
import io.kotest.assertions.fail
import io.kotest.matchers.shouldBe
import org.vinzlac.error.ex1.User
import org.vinzlac.error.ex1.UserNotFound


object UserNotFound
object Problem
data class User(val id: Long)

val user: Either<UserNotFound, User> = User(1).right()
fun Raise<UserNotFound>.user(): User = User(1)

val error: Either<UserNotFound, User> = UserNotFound.left()
fun Raise<UserNotFound>.error(): User = raise(UserNotFound)

fun example() {
    when (error) {
        is Either.Left -> error.value shouldBe UserNotFound
        is Either.Right -> fail("A logical failure occurred!")
    }

    fold(
        block = { error() },
        recover = { e: UserNotFound -> e shouldBe UserNotFound },
        transform = { _: User -> fail("A logical failure occurred!") }
    )
}

fun example2() {
    either { error() } shouldBe UserNotFound.left()
}

fun Raise<UserNotFound>.res(): User = org.vinzlac.error.ex1.user.bind()


val maybeTwo: Either<Problem, Int> = either { 2 }
val maybeFive: Either<Problem, Int> = either { raise(Problem) }
val maybeFour: Either<Problem, Int> = either {
    maybeTwo.bind() + maybeTwo.bind()
}

val maybeSeven: Either<Problem, Int> = either {
    maybeTwo.bind() + maybeFive.bind()
}

fun main() {
    org.vinzlac.error.ex2.example()
    example2()
    println(maybeFour)
    println(maybeSeven)

}

