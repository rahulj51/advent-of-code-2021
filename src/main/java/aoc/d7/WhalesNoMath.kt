package aoc.d7

import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import java.io.File
import kotlin.math.absoluteValue

//https://todd.ginsberg.com/post/advent-of-code/2021/day7/
fun main() {
    val crabs = readFile().first().split(",").map { it.toInt() }.groupingBy { it }.eachCount()


    val fuelCost : (Int) -> Int = { it }

    val cost = solve(crabs, fuelCost)

    println(cost)

    val fuelCost2 : (Int) -> Int = { it * (it+1)/2 }

    val cost2 = solve(crabs, fuelCost2)
    println(cost2)


}

private fun solve(crabs: Map<Int, Int>, fuelCost: (Int) -> Int): Int {
    val min = crabs.keys.minOf { it }
    val max = crabs.keys.maxOf { it }

    val cost = (min..max).minOf { targetPosition ->
        crabs.map { (crabPosition, crabCount) ->
            fuelCost((targetPosition - crabPosition).absoluteValue) * crabCount
        }.sum()
    }
    return cost
}

