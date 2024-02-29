package org.vinzlac.error.ex2

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.fold
import io.kotest.assertions.fail
import io.kotest.matchers.shouldBe
import org.vinzlac.error.ex1.User

data class UserNotFound(val message: String)

fun User.isValid(): Either<UserNotFound, Unit> = either {
    ensure(id > 0) { UserNotFound("User without a valid id: $id") }
}

fun Raise<UserNotFound>.isValid(user: User): User {
    ensure(user.id > 0) { UserNotFound("User without a valid id: ${user.id}") }
    return user
}

fun example() {
    val invalidUser: Either<UserNotFound, Unit> = User(-1).isValid()
    invalidUser shouldBe UserNotFound("User without a valid id: -1").left()

    fold(
        { isValid(User(1)) },
        { _ -> fail("No logical failure occurred!") },
        { user: User -> user.id shouldBe 1L }
    )
}


fun main() {
    println(User(2).isValid())
    example()
}

