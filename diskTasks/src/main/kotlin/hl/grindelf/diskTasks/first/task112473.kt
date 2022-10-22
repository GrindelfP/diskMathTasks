package hl.grindelf.diskTasks.first

// Напишите программу, которая определяет правильность записи целого числа в восьмеричной системе счисления.
// На вход программы поступает символьная строка. Программа должна вывести ответ 'YES', если строка представляет собой
// правильную запись целого числа в восьмеричной системе счисления, и 'NO', если запись ошибочна.

private const val VALID_DIGITS = "01234567"


fun main() {
    if (octonaryNotation()) println("YES")
    else println("NO")
}

private fun octonaryNotation(): Boolean {
    val input = readLine()
    if (input.isNullOrBlank()) return false
    input.forEach { symbol ->
        if (!VALID_DIGITS.contains(symbol)) return false
    }
    if (!checkValidness(input)) return false
    return true
}

private fun checkValidness(notation: String): Boolean {
    if (notation.length != 1 && notation[0] == '0') return false
    return true
}
