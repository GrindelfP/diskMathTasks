package hl.grindelf.diskTasks.second

import java.lang.Exception

fun main() {
    var answer = "YES"
    val n = getValueOfN()
    if (n == 0) answer = "NO"
    val matrix = MatrixTwo(n, n)
    try {
        val adjacencyMatrix = AdjacencyMatrixTwo(matrix)
        if (!adjacencyMatrix.isMatrixWithLoops()) answer = "NO"
    }
    catch (e: Exception){
        answer = "NO"
    }
    println(answer)
}

private fun getValueOfN(): Int {
    val number = readLine()
    if (number.isNullOrBlank()) return 0
    number.forEach {
        if (!it.isDigit()) return 0
    }
    val int = number.toString().toInt()
    if (int > 100 || int < 1) return 0

    return number.toInt()
}


private class AdjacencyMatrixTwo(
    private var matrix: MatrixTwo
) {

    init {
        checkMatrix()
    }

    fun isMatrixOfSimpleGraph(): Boolean {
        matrix.content.forEach { row ->
            row.forEach {
                if (!isValidElement(it)) return false
            }
        }
        if (!matrix.hasZerosOnMainDiagonal()) return false
        return true
    }

    fun isMatrixWithLoops(): Boolean {
        return !matrix.hasZerosOnMainDiagonal()
    }

    private fun checkMatrix() {
        if (!matrix.isSymmetric()) throw Exception("Not symmetric!")
    }

    private fun isValidElement(element: Int): Boolean {
        return element == 0 || element == 1
    }
}

private class MatrixTwo(
    private val numberOfRows: Int,
    private val numberOfColumns: Int
) {
    var content: MutableList<MutableList<Int>> = mutableListOf()

    init {
        createMatrix()
    }

    fun isSymmetric(): Boolean {
        if (!isSquare()) return false
        for (i in 0 until numberOfRows) {
            for (j in 0 until numberOfColumns){
                if (content[i][j] != content[j][i]) return false
            }
        }

        return true
    }

    fun isSquare(): Boolean {
        return numberOfRows == numberOfColumns
    }

    fun hasZerosOnMainDiagonal(): Boolean {
        for (i in 0 until numberOfRows){
            if (content[i][i] != 0) return false
        }

        return true
    }

    private fun createMatrix() {
        for (i in 0 until numberOfRows) {
            content.add(mutableListOf())
            getMatrixRow().forEach {
                content[i].add(it)
            }
        }
    }

    private fun getMatrixRow(): MutableList<Int> {
        val matrixLine = mutableListOf<Int>()
        val line = getLine()
        if (line.length != numberOfColumns) throw Exception("Incorrect row length!")
        line.forEach {
            if (!it.isDigit()) throw Exception("Non-digit input!")
            matrixLine.add(it.toString().toInt())
        }

        return matrixLine
    }

    private fun getLine(): String {
        val inputLine = readLine()
        val line = if (inputLine.isNullOrBlank()) throw Exception("No input!") else inputLine
        var checkedLine = ""
        line.forEach {
            if (it != ' ') checkedLine += it
        }

        return checkedLine
    }
}
