package aoc.d2

import java.io.File
import java.nio.file.Paths

fun readFile(): List<String> {
    return File("src/main/java/aoc/d2/input.dat").readLines()
}

fun main() {
    val (forward, rest) = readFile().partition { it.startsWith("forward") }
    val (up, down) = rest.partition { it.startsWith("up") }

    fun trimToValue(command: String, prefix: String) = command.removePrefix(prefix).trim().toInt()

    val final_x = forward.map { trimToValue(it, "forward") }.sum()

    val final_y = down.map { trimToValue(it, "down") }.sum() -
            up.map { trimToValue(it, "up") }.sum()

    println(final_x * final_y)
}