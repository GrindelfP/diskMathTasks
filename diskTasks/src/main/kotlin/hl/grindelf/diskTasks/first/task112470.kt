package hl.grindelf.diskTasks.first
// Напишите программу, которая считает знаки пунктуации в символьной строке. К знакам пунктуации относятся
// символы из набора ".,;:!?". Программа получает на вход символьную строку.
// Программа должна вывести общее количество знаков пунктуации во входной строке.

fun main(){
    println(countPunctMarks(readLine()))
}

const val PUNCTUATION_MARKS = ".,;:!?"

fun countPunctMarks(line: String?) = when (line) {
    null -> 0
    else -> {
        var counter = 0
        line.forEach {symbol ->
            if (isPunctMark(symbol)) counter++
        }
        counter
    }
}

fun isPunctMark(symbol: Char): Boolean = PUNCTUATION_MARKS.contains(symbol)
