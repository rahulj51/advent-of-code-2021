package aoc.d13

//import aoc.common.Point
import aoc.common.Point
import aoc.common.Util

enum class AXIS {
    x,y
}

fun readInputFile(): List<String> {
    return Util.readFile("src/main/java/aoc/d13/input.dat")
}

fun readTestFile(): List<String> {
    return Util.readFile("src/main/java/aoc/d13/input_test.dat")
}

fun main() {
    val readings = readInputFile()
    val pointReadings = readings.takeWhile { it.isNotEmpty() }
    val points = parse(pointReadings)
    var grid = Grid.parse(points)

    val foldingInstructions = readings.reversed().takeWhile { it.isNotEmpty() }.reversed()
    val folds = parseFoldingInstructions(foldingInstructions)

    var pz = points
    val dots = folds.map { fold ->
        grid = grid.resize(fold)
        val (above, below) = pz.partition { p -> p.aboveFold(fold) }
        pz = above.union(below.foldAlong(fold))

        pz.count()
    }

    grid.plot(pz)

    println(dots)



}

private fun List<Point>.foldAlong(fold: Pair<AXIS, Int>) =
    this.map { p ->
        if (fold.first == AXIS.y) {
            Point(p.x, (2 * fold.second) - p.y)
        } else {
            Point((2 * fold.second) - p.x, p.y)
        }
}

private fun Point.aboveFold(fold: Pair<AXIS, Int>): Boolean =
    if (fold.first == AXIS.y) (this.y < fold.second) else (this.x < fold.second)

fun parseFoldingInstructions(foldingInstructions: List<String>): List<Pair<AXIS,Int>> =
    foldingInstructions.map { it.split("fold along")
        .last().split("=").let {
            AXIS.valueOf(it.first().trim()) to it.last().toInt()
        }
    }

fun parse(pointReadings: List<String>): Set<Point> =
    pointReadings.map { reading ->
        reading.split(",").let {
            Point(it.first().toInt(), it.last().toInt())
        }
    }.toSet()

