@file:Suppress("UNUSED_PARAMETER")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.isPrime
import java.lang.Math.pow
import java.lang.Math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.fold(0.0) { previousResult, element ->
    sqr(element) + previousResult
})

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0 else list.sum() / list.size

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val average = mean(list)
    for ((index, element) in list.withIndex()) {
        list[index] = element - average
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var c = 0.0
    if (a.isEmpty() && b.isEmpty()) return 0.0
    for (i in 0 until a.size) {
        c += a[i] * b[i]
    }
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var result = 0.0
    for (i in 0 until p.size) {
        result += p[i] * pow(x, i.toDouble())
    }
    return result
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    var currentSum = 0.0
    for (i in 0 until list.size) {
        currentSum += list[i]
        list[i] = currentSum
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var number = n
    val result = mutableListOf<Int>()
    if (isPrime(n)) return listOf(n)
    for (i in 2..n / 2) {
        if (isPrime(i)) {
            while (number % i == 0) {
                result.add(i)
                number /= i
            }
            if (result.fold(1.0) { previousResult, element -> element * previousResult } == n.toDouble()) return result
        }
    }
    return result
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var number = n
    val list = mutableListOf<Int>()
    while (number >= base) {
        list.add(0, number % base)
        number /= base
    }
    list.add(0, number)
    return list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val fromElementToLetterUsingUnicode = 87
    val fromElementToNumberUsingUnicode = 48
    val list = convert(n, base)
    var result = ""
    for (element in list) {
        result += if (element > 9) (element + fromElementToLetterUsingUnicode).toChar()
        else (element + fromElementToNumberUsingUnicode).toChar()
    }
    return result
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var result = 0
    val size = digits.size
    for ((index, element) in digits.withIndex()) {
        result += (element * pow(base.toDouble(), (size - index - 1).toDouble())).toInt()
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val fromUnicodeLetterToNumber = 87
    val fromUnicodeNumberToNumber = 48
    val lowercaseAInUnicode = 97
    val list = mutableListOf<Int>()
    for (element in str) {
        if (element.toInt() >= lowercaseAInUnicode) list.add(element.toInt() - fromUnicodeLetterToNumber)
        else list.add(element.toInt() - fromUnicodeNumberToNumber)
    }
    return decimal(list, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var result = ""
    var number = n
    while (number >= 1000) {
        result += "M"
        number -= 1000
    }
    if (number >= 900) {
        result += "CM"
        number -= 900
    }
    while (number >= 500) {
        result += "D"
        number -= 500
    }
    if (number >= 400) {
        result += "CD"
        number -= 400
    }
    while (number >= 100) {
        result += "C"
        number -= 100
    }
    if (number >= 90) {
        result += "XC"
        number -= 90
    }
    while (number >= 50) {
        result += "L"
        number -= 50
    }
    if (number >= 40) {
        result += "XL"
        number -= 40
    }
    while (number >= 10) {
        result += "X"
        number -= 10
    }
    if (number >= 9) {
        result += "IX"
        number -= 9
    }
    while (number >= 5) {
        result += "V"
        number -= 5
    }
    if (number >= 4) {
        result += "IV"
        number -= 4
    }
    while (number >= 1) {
        result += "I"
        number -= 1
    }
    return result
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
var trigger = 0

fun russian(n: Int): String {
    trigger = 0
    val leftPart = n / 1000
    val rightPart = n % 1000
    val strLeftPart = halfOfRussian(leftPart, true)
    val strRightPart = halfOfRussian(rightPart, false)
    return when {
        (trigger == 4) -> strRightPart
        (trigger == 1) -> (strLeftPart + " тысяча " + strRightPart).trim()
        (trigger == 2) -> (strLeftPart + " тысячи " + strRightPart).trim()
        else -> (strLeftPart + " тысяч " + strRightPart).trim()
    }

}


fun russianNumeral(n: Int) = when {
    (n == 1) -> "один"
    (n == 2) -> "два"
    (n == 3) -> "три"
    (n == 4) -> "четыре"
    (n == 5) -> "пять"
    (n == 6) -> "шесть"
    (n == 7) -> "семь"
    (n == 8) -> "восемь"
    (n == 9) -> "девять"
    (n == 10) -> "десять"
    (n == 11) -> "одиннадцать"
    (n == 12) -> "двенадцать"
    (n == 13) -> "тринадцать"
    (n == 14) -> "четырнадцать"
    (n == 15) -> "пятнадцать"
    (n == 16) -> "шестнадцать"
    (n == 17) -> "семнадцать"
    (n == 18) -> "восемнадцать"
    (n == 19) -> "девятнадцать"
    else -> ""

}

fun halfOfRussian(number: Int, isLeft: Boolean): String {
    var n = number
    var resString = ""
    if (number > 0) {
        if (n % 100 in 10..19) {
            resString += russianNumeral(n % 100)
            n /= 100
            when {
                (n == 1) -> resString = "сто " + resString
                (n == 2) -> resString = "двести " + resString
                (n in 3..4) -> resString = russianNumeral(n) + "ста " + resString
                (n in 5..9) -> resString = russianNumeral(n) + "сот " + resString
            }
        } else {
            if (isLeft && n % 10 == 1) trigger = 1 else if (isLeft && (n % 10 in 2..4)) trigger = 2
            when {
                (isLeft && n % 10 == 1) -> resString += "одна"
                (isLeft && n % 10 == 2) -> resString += "две"
                else -> if (n % 10 != 0) resString += russianNumeral(n % 10)
            }
            n /= 10

            when {
                (n % 10 in 2..3) -> resString = russianNumeral(n % 10) + "дцать " + resString
                (n % 10 == 4) -> resString = "сорок " + resString
                (n % 10 in 5..8) -> resString = russianNumeral(n % 10) + "десят " + resString
                (n % 10 == 9) -> resString = "девяносто " + resString
            }
            n /= 10
            when {
                (n == 1) -> resString = "сто " + resString
                (n == 2) -> resString = "двести " + resString
                (n in 3..4) -> resString = russianNumeral(n) + "ста " + resString
                (n in 5..9) -> resString = russianNumeral(n) + "сот " + resString
            }

        }
    } else if (isLeft) {
        trigger = 4
    }

    return resString.trim()
}