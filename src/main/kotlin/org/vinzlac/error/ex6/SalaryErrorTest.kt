package org.vinzlac.error.ex6

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.fold
import arrow.core.raise.zipOrAccumulate

data class Salary private constructor(val amount: Double, val currency: String) {
    companion object {
        operator fun invoke(amount: Double, currency: String): Either<NonEmptyList<SalaryError>, Salary> =
            either {
                zipOrAccumulate(
                    { ensure(amount >= 0.0) { NegativeAmount } },
                    {
                        ensure(currency.isNotEmpty() && currency.matches("[A-Z]{3}".toRegex())) {
                            InvalidCurrency("Currency must be not empty and valid")
                        }
                    },
                ) { _, _ ->
                    Salary(amount, currency)
                }
            }

    }
}

val wrongSalary: Either<NonEmptyList<SalaryError>, Salary> = Salary(-1000.0, "eu")
val googSalary: Either<NonEmptyList<SalaryError>, Salary> = Salary(500.0, "EUR")

sealed interface SalaryError
data object NegativeAmount : SalaryError
data class InvalidCurrency(val message: String) : SalaryError


fun main() {
    println(wrongSalary)
    fold(
        { wrongSalary.bind() },
        { errors -> println("The risen errors are: $errors") },
        { salary -> println("The valid salary is $salary") }
    )

    println(googSalary)
    fold(
        { googSalary.bind() },
        { errors -> println("The risen errors are: $errors") },
        { salary -> println("The valid salary is $salary") }
    )
}
