package aoc.d1

import java.io.File
import java.nio.file.Paths
import java.util.stream.Stream
import kotlin.streams.toList


fun readFile(): MutableList<Int>? {
    println(Paths.get("").toAbsolutePath().toString())
    return File("src/main/java/aoc/d1/input.dat").readLines()
            .stream()
            .map{ e -> e.toInt() }.toList()

}

private fun getIncrementingMeasures1() =
        readFile()?.zipWithNext()?.count { it.first < it.second }

private fun getIncrementingMeasures2() =
        readFile()?.
        windowed(3, 1) { it.sum() }?.
        zipWithNext()?.count { it.first < it.second }


fun main() {
    println(getIncrementingMeasures2())

}

