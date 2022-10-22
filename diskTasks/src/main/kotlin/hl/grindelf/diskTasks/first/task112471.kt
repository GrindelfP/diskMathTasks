package hl.grindelf.diskTasks.first

// Напишите программу, которая находит все различные цифры в символьной строке.
// На вход программе подаётся символьная строка. Программа должна вывести в одной строке все различные цифры,
// которые встречаются в исходной строке, в порядке возрастания. Если в строке нет цифр, нужно вывести слово 'NO'.

fun main() {
    printResult(getAllDigits(readLine()))
}

fun getAllDigits(line: String?): Set<Int> {
    val uniqueDigits = mutableSetOf<Int>()
    val allDigits = mutableListOf<Any>()
    if (line == null) {
        return uniqueDigits
    }

    for (i in line.indices){
        if (line[i].isDigit()){
            allDigits.add(line[i])
        }
    }

    allDigits.forEach{digit ->
        uniqueDigits.add(digit.toString().toInt())
    }

    return uniqueDigits
}

private fun printResult(uniqueDigits: Set<Int>) {
    var resultLine = ""
    if (uniqueDigits.isEmpty()) resultLine += "NO"
    else {
        val digits = mutableListOf<Int>()

        digits.addAll(uniqueDigits)
        for (i in 0 until digits.size) {
            for (j in 0 until digits.size - i - 1) {
                if (digits[j] > digits[j + 1]) {
                    val tempor = digits[j]
                    digits[j] = digits[j + 1]
                    digits[j + 1] = tempor
                }
            }
        }
        digits.forEach { digit ->
            resultLine += digit.toString()
        }
    }

    println(resultLine)
}