package aoc.d8

import java.io.File

fun readFile(f: String): List<String> {
    return File(f).readLines()
}
fun readInputFile(): List<String> {
    return readFile("src/main/java/aoc/d8/input.dat")
}

fun readTestFile(): List<String> {
    return readFile("src/main/java/aoc/d8/input_test.dat")
}

//lot of hardcoding but it works
fun main() {
    val outputLines = readInputFile().map { it.split("|").last().trim() }
    println(outputLines)

    val segmentCounts = outputLines.flatMap { it.split(" ").map { p -> p.length } }

    val segmentCountsForUniques = listOf<Int>(2,4,3,7)

    println(segmentCounts.filter { segmentCountsForUniques.contains(it) }.count())


}
