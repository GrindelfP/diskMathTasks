package hl.grindelf.diskTasks.fourth

import hl.grindelf.diskTasks.fourth.Route.Companion.WORST_ROUTE
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    SalesmanProgram().run()
}

class SalesmanProgram {

    companion object {
        const val LOWER_NUMBER_OF_CITIES = 2
        const val UPPER_NUMBER_OF_CITIES = 5000
    }

    fun run() {
        val numberOfCitiesUserInput = getNumOfCities()
        val cityList = getCityList(numberOfCitiesUserInput)
        val logic = SalesmanLogic(cityList)

        val bestRoute = logic.getBestRoute(cityList).nodes
        bestRoute.forEach {
            print("${it + 1} ")
        }
    }

    private fun getNumOfCities(): Int {
        val number = readLine()
        require(!number.isNullOrBlank()) { "Received a blank line instead of the number of cities" }
        return when (val numOfCities = number.toIntOrNull()) {
            null -> 0

            else -> {
                require(numOfCities in LOWER_NUMBER_OF_CITIES..UPPER_NUMBER_OF_CITIES) {
                    "Number of cities must be between $LOWER_NUMBER_OF_CITIES and $UPPER_NUMBER_OF_CITIES"
                }
                numOfCities
            }
        }
    }

    private fun getCityList(numOfCities: Int): Map<Int, City> = mutableMapOf<Int, City>().apply {
        for (i in 0 until numOfCities) {
            val coordinates = getCoordinates()
            //require(coordinates.size == 2) { "Incorrect coordinates count!" }
            put(i, City(x = coordinates.first, y = coordinates.second, id = i))
        }
    }

    private fun getCoordinates(): Pair<Int, Int> {
        val line = readLine()
        require(!line.isNullOrBlank()) { "Null or blank" }

        line.trim().apply {
            val firstNumber = substringBefore(" ").toIntOrNull()
            val secondNumber = substringAfter(" ").trim().toIntOrNull()
            require(firstNumber != null && secondNumber != null) { "CARRAMBA!!!!" }

            return firstNumber to secondNumber
        }
    }
}

class SalesmanLogic(private val cityList: Map<Int, City>) {

    private val cityMatrix = cityMatrix()

    private fun cityMatrix(): List<List<Double>> {
        val matrix = mutableListOf<MutableList<Double>>()
        for (i in cityList.values.indices) {
            matrix.add(mutableListOf())
            for (j in cityList.values.indices) {
                if (i != 0)
                matrix[i].add(getDistance(cityList, i, j))
            }
        }
        return matrix
    }

    fun getBestRoute(cityList: Map<Int, City>): Route {
        // var weight = MAX_VALUE
        // var nodes = emptyList()
        var shortestRoute = WORST_ROUTE

        cityList.keys.forEach { cityId ->
            val route = closestNeighbor(cityList, cityId)
            if (route.weight < shortestRoute.weight) shortestRoute = route
        }

        return shortestRoute
    }

    private fun closestNeighbor(cityList: Map<Int, City>, cityId: /*vertex:*/ Int): Route {
        val visitedCities = mutableSetOf<Int>()
        // for (i in cityList.indices) visitedCities.add(false) // declaring and filling list of visited cities
        var weight = 0.0 // setting initial weight to 0
        val route = mutableSetOf<Int>() // declaring list representing route
        var currentCity = cityList[cityId]!! // setting first current city

        route.add(currentCity.id) // adding first current city to the route
        visitedCities.add(currentCity.id) // marking the first city as a visited

        cityList.entries.forEach { cityEntry ->
            if (currentCity.id != cityEntry.key && !visitedCities.contains(cityEntry.key)) {
                val closestCityParams = getClosestCityOf(currentCity, visitedCities, cityList)
                val closestCity = closestCityParams.first
                route.add(closestCity.id)
                visitedCities.add(closestCity.id)
                weight += closestCityParams.second
                currentCity = closestCity
            }
        }

        return Route(weight, route)
    }

    private fun getClosestCityOf(city: City, visitedCities: Set<Int>, cityList: Map<Int, City>): Pair<City, Double> {
        var minimumDistance = Double.MAX_VALUE
        var minimumDistanceCityId = 0
        val distances = cityMatrix[city.id]
        for (i in distances.indices) {
            if (!visitedCities.contains(i) && distances[i] != Double.MAX_VALUE && distances[i] < minimumDistance) {
                minimumDistance = distances[i]
                minimumDistanceCityId = i
            }
        }
        return cityList[minimumDistanceCityId]!! to minimumDistance
    }

    private fun getDistance(cityList: Map<Int, City>, i: Int, j: Int) = when {
        i != j -> sqrt(
            (cityList[j]!!.x - cityList[i]!!.x).toDouble().pow(2) +
                    (cityList[j]!!.y - cityList[i]!!.y).toDouble().pow(2)
        )

        else -> Double.MAX_VALUE
    }
}

data class Route(
    val weight: Double,
    val nodes: Set<Int>
) {

    companion object {
        val WORST_ROUTE = Route(Double.MAX_VALUE, emptySet())
    }
}

data class City(
    val x: Int,
    val y: Int,
    val id: Int
)
