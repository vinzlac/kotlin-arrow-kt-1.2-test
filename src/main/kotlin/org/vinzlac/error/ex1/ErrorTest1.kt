package org.vinzlac.error.ex1

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.right

object UserNotFound
data class User(val id: Long)

val user: Either<UserNotFound, User> = User(1).right()

fun Raise<UserNotFound>.user(): User = User(1)

val error: Either<UserNotFound, User> = UserNotFound.left()

fun Raise<UserNotFound>.error(): User = raise(UserNotFound)


// Raise<UserNotFound> is extension receiver
fun Raise<UserNotFound>.findUser(id: Long): User = User(id)
// Raise<UserNotFound> is context receiver
context(Raise<UserNotFound>) fun otherFindUser(id: Long): User = User(id)

fun main() {
    println(user)
    println(error)
}

