@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    var count = 0
    var number = n
    do {
        count++
        number /= 10
    } while (abs(number) > 0)
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int = if (n < 3) 1 else fib(n - 1) + fib(n - 2)

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var number1 = n
    var number2 = m
    var k = 1
    if (m == n) return m
    if (isCoPrime(m, n)) {
        return m * n
    }
    for (i in 2..max(m, n)) {
        if (isPrime(i)) {
            while (number1 % i == 0 || number2 % i == 0) {
                k *= i
                if (number1 % i == 0) number1 /= i
                if (number2 % i == 0) number2 /= i
            }
        }
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    for (i in 2..ceil(sqrt(n.toDouble())).toInt()) {
        if (n % i == 0) return i
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    for (i in n / 2 downTo 2) {
        if (n % i == 0) return i
    }
    return 1
}


/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    if (m == 1 && n == 1) return true
    if (m == n) return false
    for (i in 2..max(n, m) / 2) {
        if (m % i == 0 && n % i == 0) return false
    }
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val sqrtM = sqrt(m.toDouble())
    val sqrtN = sqrt(n.toDouble())
    if (sqrtM % 1 == 0.0 || sqrtN % 1 == 0.0) return true
    else {
        var count = -1
        for (i in ceil(sqrtM).toInt()..ceil(sqrtN).toInt()) {
            count++
        }
        return count > 0
    }
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var number = x
    while (abs(number) >= 2 * PI) {
        if (number > 0) number -= 2 * PI else number += 2 * PI

    }
    var result = 0.0
    var count = 0
    for (i in 1..Int.MAX_VALUE step 2) {
        val value = pow(number, i.toDouble()) / factorial(i)
        if (count % 2 == 0) result += value else result -= value
        count++
        if (abs(value) < eps) break
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var number = x
    while (abs(number) >= 2 * PI) {
        if (number > 0) number -= 2 * PI else number += 2 * PI
    }
    var result = 1.0
    var count = 1
    for (i in 2..Int.MAX_VALUE step 2) {
        val value = pow(number, i.toDouble()) / factorial(i)
        if (count % 2 == 0) result += value else result -= value
        count++
        if (abs(value) < eps) break
    }
    return result
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var startNumber = n
    var resultNumber = 0.0
    do {
        resultNumber *= 10
        resultNumber += startNumber % 10
        startNumber /= 10
    } while (startNumber > 0)
    return resultNumber.toInt()
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int) = revertDouble(n) == n.toDouble()

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var number = n
    val numeric = n % 10
    do {
        if (number % 10 != numeric) return true
        number /= 10
    } while (number > 0)
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
    var currentIntNumber = 0
    var count = 0
    var counter = 1.0
    while (count < n) {
        currentIntNumber = sqr(counter).toInt()
        count += digitNumber(sqr(counter).toInt())
        counter++
    }
    while (count != n) {
        count--
        currentIntNumber /= 10
    }
    return currentIntNumber % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var fib1 = 0
    var fib2 = 1
    var fib3 = 0
    var count = 0
    while (count < n) {
        fib1 = fib2
        fib2 = fib3
        fib3 = fib1 + fib2
        count += digitNumber(fib3)
    }
    while (count != n) {
        count--
        fib3 /= 10
    }
    return fib3 % 10
}

fun revertDouble(n: Int): Double {
    var startNumber = n
    var resultNumber = 0.0
    do {
        resultNumber *= 10
        resultNumber += startNumber % 10
        startNumber /= 10
    } while (startNumber > 0)
    return resultNumber
}
