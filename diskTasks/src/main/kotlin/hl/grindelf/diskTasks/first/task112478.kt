package hl.grindelf.diskTasks.first

// Напишите программу, которая находит все символы в символьной строке, которые встречаются только один раз.
// На вход программы подаётся символьная строка.
// Программа должна вывести все символы, которые встречаются в строке только один раз,
// в порядке возрастания их ASCII-кодов. Если таких символов нет, нужно вывести слово 'NO'.

fun main() {
    println(buildResultLine(sortUniqueCharacters(deleteRepeatedCharacters(input()))))
}

private fun input(): String {
    val input = readLine()
    if (input.isNullOrBlank()) return ""

    return input
}

private fun deleteRepeatedCharacters(line: String): MutableList<Char> {
    val uniqueSymbols = mutableListOf<Char>()
    line.forEach { symbol ->
        var flag = true
        for (i in line.indexOf(symbol) + 1 until line.length) {
            if (line[i] == symbol) flag = false
        }
        if (flag) uniqueSymbols.add(symbol)
    }

    return uniqueSymbols
}

private fun sortUniqueCharacters(characters: MutableList<Char>): MutableList<Char> {
    for (i in 0 until characters.size) {
        for (j in 0 until characters.size - i - 1) {
            val leftAscii = characters[j].toInt()
            val rightAscii = characters[j + 1].toInt()
            if (leftAscii > rightAscii) {
                val temp = characters[j]
                characters[j] = characters[j + 1]
                characters[j + 1] = temp
            }
        }
    }

    return characters
}

private fun buildResultLine(characters: MutableList<Char>): String {
    if (characters.size == 0) return "NO"
    var resultLine = ""
    characters.forEach{
        resultLine += it
    }

    return resultLine
}