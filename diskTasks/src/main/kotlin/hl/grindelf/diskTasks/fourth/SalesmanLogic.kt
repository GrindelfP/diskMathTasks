package hl.grindelf.diskTasks.fourth

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

        val bestRoute = SalesmanLogic.getBestRoute(cityList).nodes
        bestRoute.forEach {
            val realNumber = it + 1
            print("$realNumber ")
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

    private fun getCityList(numOfCities: Int): List<City> = mutableListOf<City>().apply {
        for (i in 0 until numOfCities) {
            val coordinates = getCoordinates()
            //require(coordinates.size == 2) { "Incorrect coordinates count!" }
            add(City(x = coordinates.first, y = coordinates.second, number = i))
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

object SalesmanLogic {

    fun getBestRoute(cityList: List<City>): Route {
        var shortestRoute = Route(mutableListOf(), Double.MAX_VALUE)
        for (i in cityList.indices) {
            val route = closestNeighbor(cityList, i)
            if (route.weight < shortestRoute.weight) shortestRoute = route
        }

        return shortestRoute
    }

    private fun closestNeighbor(cityList: List<City>, startCityNumber: Int): Route {
        val visitedCities = mutableListOf<Boolean>()
        for (i in cityList.indices) visitedCities.add(false) // declaring and filling list of visited cities
        var weight = 0.0 // setting initial weight to 0
        val route = mutableListOf<Int>() // declaring list representing route
        var currentCity = cityList[startCityNumber] // setting first current city
        route.add(currentCity.number) // adding first current city to the route
        visitedCities[currentCity.number] = true // marking the first city as a visited
        while (visitedCities.indexOf(false) != -1) {
            val closestCityParams = getClosestCityOf(currentCity, visitedCities, cityList)
            val closestCity = closestCityParams[0] as City
            route.add(closestCity.number)
            visitedCities[closestCity.number] = true
            weight += closestCityParams[1] as Double // ?????????
            currentCity = closestCity
        }
        return Route(route, weight)
    }

    private fun getClosestCityOf(city: City, visitedCities: List<Boolean>, cityList: List<City>): List<Any> {
        var minimumDistance = Double.MAX_VALUE
        var minimumDistanceCityNumber = 0
        val distances = cityMatrix(cityList)[city.number]
        for (i in distances.indices) {
            if (!visitedCities[i] && distances[i] != Double.MAX_VALUE && distances[i] < minimumDistance) {
                minimumDistance = distances[i]
                minimumDistanceCityNumber = i
            }
        }
        return listOf(cityList[minimumDistanceCityNumber], minimumDistance)
    }

    private fun cityMatrix(cityList: List<City>): List<List<Double>> {
        val matrix = mutableListOf<MutableList<Double>>()
        for (i in cityList.indices) {
            matrix.add(mutableListOf())
            for (j in cityList.indices) {
                matrix[i].add(getDistance(cityList, i, j))
            }
        }
        return matrix
    }

    private fun getDistance(cityList: List<City>, i: Int, j: Int) = when {
        i != j -> sqrt(
            (cityList[j].x - cityList[i].x).toDouble().pow(2) +
                    (cityList[j].y - cityList[i].y).toDouble().pow(2)
        )

        else -> Double.MAX_VALUE
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
