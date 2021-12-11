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

}

fun Array<IntArray>.get(p:Point): Int =
    this[p.y][p.x]  //each row is y, each col is x

fun Array<IntArray>.contains(p:Point): Boolean =
    p.y in this.indices &&
            p.x in this[p.y].indices


fun main() {

    val readings = readInputFile()
        .map { it ->
            it.map { d -> d.digitToInt() }.toIntArray() } //convert each row to an int array
        .toTypedArray() //add to a second array

    val lowPoints = readings.mapIndexed { yIndex, row ->
        row.mapIndexed { xIndex, col ->
            val p = Point(xIndex, yIndex)
            val value = col
            val neighborValues = p.adjacent().filter { readings.contains(it) }
                .map { readings[it.y][it.x] }
            if (value < neighborValues.minOf { it }) p else null
        }
    }.flatten().filterNotNull()

    println(lowPoints.map { readings.get(it)+1 }.sum())

}