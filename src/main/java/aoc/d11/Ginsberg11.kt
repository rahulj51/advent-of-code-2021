package aoc.d11

typealias OctopusCave = Map<Point, Int>

class Day11(input: List<String>) {

    private val startingCave: OctopusCave = parseInput(input)

    private fun parseInput(input: List<String>): OctopusCave =
        input.flatMapIndexed { y, row ->
            row.mapIndexed { x, energy -> Point(x, y) to energy.digitToInt() }
        }.toMap()

    private fun OctopusCave.steps(): Sequence<Int> = sequence {
        val cave = this@steps.toMutableMap()

        while (true) {
            cave.forEach { (point, energy) -> cave[point] = energy + 1 }
            do {
                val flashersThisRound = cave.filterValues { it > 9 }.keys
                flashersThisRound.forEach { cave[it] = 0 }

                flashersThisRound
                    .flatMap { it.adjacent() }
                    .filter { it in cave && cave[it] != 0 }
                    .forEach { cave[it] = cave.getValue(it) + 1 }
            } while (flashersThisRound.isNotEmpty())

            yield(cave.count { it.value == 0 })
        }
    }

    fun solvePart1(): Int =
        startingCave.steps().take(100).sum()

    fun solvePart2(): Int =
        startingCave.steps().indexOfFirst { it == startingCave.size } + 1
}

fun main() {

    val readings = readInputFile()
    println(Day11(readings).solvePart1())
    println(Day11(readings).solvePart2())
}


