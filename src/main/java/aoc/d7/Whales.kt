package aoc.d7

import org.nield.kotlinstatistics.median
import java.io.File
import kotlin.math.absoluteValue

fun readFile(): List<String> {
    return File("src/main/java/aoc/d7/input.dat").readLines()
}

fun main() {
    val readings = readFile().first().split(",").map { it.toInt() }
    val median = readings.sorted().median().toInt()
    println(median)
    val cost = readings.sorted().map { (median - it).absoluteValue }
    println(cost.sum())

}

