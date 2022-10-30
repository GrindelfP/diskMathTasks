package hl.grindelf.diskTasks.third

fun main() {
    val dbp = DeepBypass()
    println(dbp.getResultsOfBypass())
}

class ConnectedComponents(
    private val vertexCount: Int,
    private val matrix: Matrix
){
    val components: MutableList<Int> = mutableListOf()
    private var numerator: Int

    init {
        for (vertex in 0 until vertexCount) components.add(0)
        numerator = 0
        quantity()
    }

    private fun quantity() {
        for (vertex in 0 until vertexCount) {
            if (components[vertex] == 0) {
                numerator++
                dfs(vertex)
            }
        }
    }

    private fun connectedVertexes(vertex: Int): MutableList<Int> {
        val connectedVertexes = mutableListOf<Int>()
        for (j in matrix.content[vertex].indices) {
            if (matrix.content[vertex][j] == 1) connectedVertexes.add(j)
        }

        return connectedVertexes
    }

    private fun dfs(vertex: Int) {
        components[vertex] = numerator
        for (uv in connectedVertexes(vertex)) {
            if (components[uv] == 0) dfs(uv)
        }
    }
}

class DeepBypass {
    private var vertexCount: Int
    private val chosenVertex: Int
    private val matrix: Matrix
    private val connectedComponents: ConnectedComponents

    init {
        val inputData = getData()
        val checkedData = if (checkValues(inputData)) inputData else throw Exception("wrong input")
        vertexCount = checkedData[0]
        chosenVertex = checkedData[1]
        val matrixCandidate = Matrix(vertexCount, vertexCount)
        matrix = if (matrixCandidate.hasZerosOnMainDiagonal()) matrixCandidate else
            throw Exception("wrong matrix")
        connectedComponents = ConnectedComponents(vertexCount, matrix)
    }

    fun getResultsOfBypass(): Int {
        var numerator = 0
        val chosenVertexComponent = connectedComponents.components[chosenVertex - 1]
        for (i in connectedComponents.components.indices) {
            if (connectedComponents.components[i] == chosenVertexComponent) numerator++
        }

        return numerator
    }

    private fun getData(): MutableList<Int> {
        val data = mutableListOf<Int>()
        val line = readLine()
        if (line.isNullOrBlank()) throw Exception("no input!")
        var number = ""
        for (i in line.indices) {
            if (line[i].isDigit()) number += line[i].toString()
            else {
                data.add(number.toInt())
                number = ""
            }
        }
        data.add(number.toInt())
        if (data.size > 2) throw Exception("more than 2 elements")

        return data
    }

    private fun checkValues(data: MutableList<Int>): Boolean {
        val firstValue = data[0]
        val secondValue = data[1]
        if (firstValue > 100 || firstValue < 1) return false
        if (secondValue > firstValue || secondValue < 1) return false

        return true
    }
}

class Matrix(
    private val numberOfRows: Int,
    private val numberOfColumns: Int
) {
    var content: MutableList<MutableList<Int>> = mutableListOf()

    init {
        createMatrix()
    }

    fun hasZerosOnMainDiagonal(): Boolean {
        for (i in 0 until numberOfRows) {
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

