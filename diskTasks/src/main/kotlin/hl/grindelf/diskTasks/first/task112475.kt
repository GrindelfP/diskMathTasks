package hl.grindelf.diskTasks.first

// Напишите программу, которая проверяет правильность имени переменной в языке Паскаль. Имя может содержать только
// латинские буквы (заглавные и строчные), цифры и знак подчёркивания, но не может начинаться с цифры.
// На вход программы подаётся символьная строка, содержащая имя переменной. Программа должна вывести ответ 'YES',
// если строка представляет собой правильное имя переменной в языке Паскаль, и 'NO', если имя ошибочно.

private const val AVAILABLE_CHARACTERS = "abcdefghigklmnopqrstuvdxywABCDEFGHIJKLMNOPQRSTUVDXYZ_"

fun main(){
    printResult()
}

fun checkNaming(line: String): Boolean{
    if (line.isBlank()) return false
    if (line[0].isDigit()) return false
    line.forEach { symbol ->
        if (!(symbol.isDigit() || AVAILABLE_CHARACTERS.contains(symbol))) return false
    }
    return true
}

private fun printResult(){
    val input = readLine()
    if (input.isNullOrBlank()) println("NO")
    else if (checkNaming(input)) println("YES")
    else println("NO")
}
