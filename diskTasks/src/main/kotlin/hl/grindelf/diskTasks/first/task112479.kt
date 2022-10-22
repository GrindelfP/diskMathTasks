package hl.grindelf.diskTasks.first

// Напишите программу, которая находит все цифры, которых нет в переданной ей строке.
// На вход программа подаётся символьная строка.
// Программа должна вывести в одной строке все цифры, которые не встречаются в исходной строке,
// в порядке убывания. Если таких цифр нет, нужно вывести слово 'NO'.

private const val DIGITS = "0123456789"

fun main() {
    println(sortDigits(missingDigits(digits(input()))))
}

private fun input(): String {
    val input = readLine()
    if (input.isNullOrBlank()) return ""

    return input
}

private fun digits(line: String): String {
    var digitsOnly = ""
    line.forEach {
        if (it.isDigit()) digitsOnly += it
    }

    return digitsOnly
}

private fun missingDigits(digitsLine: String): MutableList<Int> {
    val missingDigits = mutableSetOf<Int>()
    val digits = mutableListOf<Int>()
    DIGITS.forEach {
        if (!digitsLine.contains(it)) missingDigits.add(it.toString().toInt())
    }
    digits.addAll(missingDigits)

    return digits
}

private fun sortDigits(digits: MutableList<Int>): String {
    if (digits.size == 0) return "NO"
    var result = ""
    for (i in 0 until digits.size) {
        for (j in 0 until digits.size - i - 1) {
            if (digits[j] < digits[j + 1]) {
                val tempor = digits[j]
                digits[j] = digits[j + 1]
                digits[j + 1] = tempor
            }
        }
    }
    digits.forEach { result += it }

    return result
}