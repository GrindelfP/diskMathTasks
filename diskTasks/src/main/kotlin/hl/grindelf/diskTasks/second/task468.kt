package hl.grindelf.diskTasks.second

fun main() {
    if (hasParallels(getPairs(getCommonInput()[1].toInt()))) println("YES")
    else println("NO")
}

fun hasParallels(pairs: List<Pair>): Boolean {
    pairs.forEach{ pair ->
        pairs.forEach { anotherPair ->
            if (pair.isParallelTo(anotherPair)) return true
        }
    }

    return false
}

fun getPairs(m: Int): List<Pair> {
    val pairs = mutableListOf<Pair>()
    for (i in 0 until m){
        val pair = getInput()
        var temp = ""
        pair.forEach { if (it.isDigit()) temp += it.toString() }
        if (temp.length != 2) error()
        pairs.add(Pair(temp[0].toInt(), temp[1].toInt()))
    }

    return pairs
}

fun getCommonInput(): List<String> {
    val vertAndEdge = getInput()
    var verts = ""
    var edges = ""
    vertAndEdge.forEach {
        if (it.isDigit()) {
            if (vertAndEdge.indexOf(it) == 0) verts = it.toString()
            else edges = it.toString()
        }
    }

    return listOf(verts, edges)
}

fun getInput(): String {
    val input = readLine()
    if (input.isNullOrBlank()) error()
    return input!!
}

fun error() {
    throw Exception("wrong input")
}

data class Pair(
    val edgeOne: Int,
    val edgeTwo: Int
){
    fun isParallelTo(anotherPair: Pair): Boolean {
        return edgeOne == anotherPair.edgeTwo && edgeTwo == anotherPair.edgeOne
    }
}