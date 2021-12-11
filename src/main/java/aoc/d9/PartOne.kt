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

typealias Point = Pair<Int,Int>

fun Array<IntArray>.get(p:Point): Int =
    this[p.second][p.first]

fun Array<IntArray>.contains(p:Point): Boolean =
    p.second in this.indices && p.first in this[p.second].indices

fun Point.adjacent(): List<Point> =
    listOf(
        Point(this.first+1, this.second),
        Point(this.first-1, this.second),
        Point(this.first, this.second+1),
        Point(this.first, this.second-1)
    )

fun main() {

    val readings = readInputFile().map { it -> it.map { d -> d.digitToInt() }.toIntArray() }.toTypedArray()

    val lowestPoints = readings.mapIndexed { yIndex, row ->
        row.mapIndexed { xIndex, col ->
            val p = Point(xIndex, yIndex)
            val value = col
            val neighborValues = p.adjacent().filter { readings.contains(it) }
                .map { readings[it.second][it.first] }
            if (value < neighborValues.minOf { it }) p else null
        }
    }.flatten().filterNotNull()

    println(lowestPoints.map { readings.get(it)+1 }.sum())

}