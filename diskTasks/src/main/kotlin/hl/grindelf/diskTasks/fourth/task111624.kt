package hl.grindelf.diskTasks.fourth

import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val bestRoute = getBestRoute().nodes
    bestRoute.forEach {
        val realNumber = it + 1
        print("$realNumber ")
    }
}

data class Route(
    val nodes: MutableList<Int>,
    val weight: Double
)

data class City(
    val x: Int,
    val y: Int,
    val number: Int
)

const val LOWER_NUMBER_OF_CITIES = 2
const val UPPER_NUMBER_OF_CITIES = 5000

val cityList = getCityList(getNumOfCities())
val cityMatrix = run {
    val matrix = mutableListOf<MutableList<Double>>()
    for (i in 0 until cityList.size) {
        matrix.add(mutableListOf())
        for (j in 0 until cityList.size) {
            matrix[i].add(
                getDistance(i, j)
            )
        }
    }
    matrix
}

fun getBestRoute(): Route {
    var shortestRoute = Route(mutableListOf(), Double.MAX_VALUE)
    for (i in 0 until cityList.size) {
        val route = closestNeighbor(i)
        if (route.weight < shortestRoute.weight) shortestRoute = route
    }

    return shortestRoute
}

fun closestNeighbor(startCityNumber: Int): Route {
    val visitedCities = mutableListOf<Boolean>()
    for (i in cityList.indices) visitedCities.add(false) // declaring and filling list of visited cities
    var weight = 0.0 // setting initial weight to 0
    val route = mutableListOf<Int>() // declaring list representing route
    var currentCity = cityList[startCityNumber] // setting first current city
    route.add(currentCity.number) // adding first current city to the route
    visitedCities[currentCity.number] = true // marking the first city as a visited
    while (visitedCities.indexOf(false) != -1) {
        val closestCityParams = getClosestCityOf(currentCity, visitedCities)
        val closestCity = closestCityParams[0] as City
        route.add(closestCity.number)
        visitedCities[closestCity.number] = true
        weight += closestCityParams[1] as Double // ?????????
        currentCity = closestCity
    }
    return Route(route, weight)
}

fun getClosestCityOf(city: City, visitedCities: MutableList<Boolean>): List<Any> {
    var minimumDistance = Double.MAX_VALUE
    var minimumDistanceCityNumber = 0
    val distances = cityMatrix[city.number]
    for (i in distances.indices) {
        if (!visitedCities[i] && distances[i] != Double.MAX_VALUE && distances[i] < minimumDistance) {
            minimumDistance = distances[i]
            minimumDistanceCityNumber = i
        }
    }
    return listOf(cityList[minimumDistanceCityNumber], minimumDistance)
}

fun getDistance(i: Int, j: Int) = when {
    i != j -> sqrt(
        (cityList[j].x - cityList[i].x).toDouble().pow(2) +
                (cityList[j].y - cityList[i].y).toDouble().pow(2)
    )

    else -> Double.MAX_VALUE
}

fun getCityList(numOfCities: Int): MutableList<City> {
    val cityList = mutableListOf<City>()
    for (i in 0 until numOfCities) {
        val coordinates = getCoordinates()
        require(coordinates.size == 2) { "Incorrect coordinates count!" }
        cityList.add(City(coordinates[0], coordinates[1], i))
    }

    return cityList
}

fun getCoordinates(): MutableList<Int> {
    val coordinates = mutableListOf<Int>()
    val line = readLine()
    require(!line.isNullOrBlank()) { "Null or blank" }
    var buffer = ""
    for (i in line.indices) {
        buffer += if (line[i] != ' ' && i != line.length - 1) line[i] else {
            if (i == line.length - 1) buffer += line[i]
            coordinates.add(buffer.toInt())
            buffer = ""
            continue
        }
    }

    return coordinates
}

fun getNumOfCities(): Int {
    val number = readLine()
    require(!number.isNullOrBlank()) { "Null or blank" }
    var resultLine = ""
    for (i in number.indices) {
        if (number[i].isDigit()) resultLine += number[i]
    }
    val numOfCities = if (resultLine != "") resultLine.toInt() else 0
    require(numOfCities in LOWER_NUMBER_OF_CITIES..UPPER_NUMBER_OF_CITIES) {
        "Number of cities out of bounds!"
    }

    return numOfCities
}