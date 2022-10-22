package hl.grindelf.diskTasks.first

// Напишите программу, которая определяет количество различных символов, встречающихся в символьной строке.
// На вход программы подаётся символьная строка.
// Программа должна вывести количество различных символов в этой строке.

fun main(){
    println(deleteRepeats(input()).size)
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

