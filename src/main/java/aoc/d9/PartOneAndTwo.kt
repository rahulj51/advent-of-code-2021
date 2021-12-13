package aoc.d9

import java.io.File

fun readFile(f: String): List<String> {
    return File(f).readLines()
}
fun readInputFile(): List<String> {
    return readFile("src/main/java/aoc/d9/input.dat")
}

fun readTestFile(): List<String> {
    return readFile("src/main/java/aoc/d9/input_test.dat")
}

val readings = readInputFile()
    .map { it ->
        it.map { d -> d.digitToInt() }.toIntArray() } //convert each row to an int array
    .toTypedArray() //add to a second array


data class Point(
    val x : Int,
    val y : Int
) {

    fun adjacent(): List<Point> =
        listOf(
            Point(this.x+1, this.y),
            Point(this.x-1, this.y),
            Point(this.x, this.y+1),
            Point(this.x, this.y-1)
        )

    fun validNeighbors(readings: Array<IntArray>): List<Point> =
        this.adjacent().filter { readings.contains(it) }

}

fun Array<IntArray>.get(p:Point): Int =
    this[p.y][p.x]  //each row is y, each col is x

fun Array<IntArray>.contains(p:Point): Boolean =
    p.y in this.indices &&
            p.x in this[p.y].indices


fun main() {


    val lowPoints = lowPoints(readings)

    println(lowPoints.map { readings.get(it)+1 }.sum())

    //part two
    //traverse the map for each low point
    val basinSizes = lowPoints.map { basinSize(it, readings) }
    println(basinSizes)

    println(basinSizes.sortedDescending().take(3).reduce{ a, b -> a*b})

}

//https://todd.ginsberg.com/post/advent-of-code/2021/day9/
fun basinSize(point: Point, readings: Array<IntArray>): Int {

    val visited = mutableSetOf<Point>(point)
    val toCheck = mutableListOf<Point>(point)

    while (toCheck.isNotEmpty()) {

        val neighbors = toCheck.removeFirst()
            .validNeighbors(readings)
            .filter{ !visited.contains(it) }
            .filter{ readings.get(it) != 9 }

        visited.addAll(neighbors)
        toCheck.addAll(neighbors)

    }

    return visited.size

}

private fun lowPoints(readings: Array<IntArray>): List<Point> {
    val lowPoints = readings.mapIndexed { yIndex, row ->
        row.mapIndexed { xIndex, col ->
            val p = Point(xIndex, yIndex)
            val value = col
            val neighborValues = p.validNeighbors(readings)
                .map { readings[it.y][it.x] }
            if (value < neighborValues.minOf { it }) p else null
        }
    }.flatten().filterNotNull()
    return lowPoints
}