package hl.grindelf.diskTasks.first

// Напишите программу, которая удаляет из строки все повторяющиеся символы.
// На вход программы подаётся строка, содержащая символы таблицы ASCII.
// Программа должна вывести исходную строку, из которой удалены все повторяющиеся символы.

fun main(){
    println(buildResultLine(deleteRepeats(input())))
}

private fun input(): String{
    val input = readLine()
    if (input.isNullOrBlank()) return ""

    return input
}

private fun deleteRepeats(line: String): MutableSet<Char>{
    val uniqueSymbols = mutableSetOf<Char>()
    line.forEach { symbol ->
        uniqueSymbols.add(symbol)
    }

    return uniqueSymbols
}

private fun buildResultLine(uniqueSymbols: MutableSet<Char>): String{
    var resultLine = ""
    uniqueSymbols.forEach{element ->
        resultLine += element
    }

    return resultLine
}
