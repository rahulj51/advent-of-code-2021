package aoc.d10

import org.nield.kotlinstatistics.median
import java.io.File

fun readFile(f: String): List<String> {
    return File(f).readLines()
}
fun readInputFile(): List<String> {
    return readFile("src/main/java/aoc/d10/input.dat")
}

fun readTestFile(): List<String> {
    return readFile("src/main/java/aoc/d10/input_test.dat")
}


val PAIRS  = mapOf<Char,Char>('{' to '}', '[' to ']', '<' to '>', '(' to ')')
val CORRUPTION_POINTS = mapOf<Char,Int>('}' to 1197, ']' to 57, '>' to 25137, ')' to 3)
val INCOMPLETE_POINTS = mapOf<Char,Int>('}' to 3, ']' to 2, '>' to 4, ')' to 1)
val OPENERS = PAIRS.map { it.key }.toCharArray()
val CLOSERS = PAIRS.map { it.value }.toCharArray()

fun main() {
    val readings = readInputFile().map { it.trim().toCharArray() }

    val sum = part1(readings)
    println(sum)

    val sum2 = part2(readings)
    println(sum2)


}

fun part2(lines: List<CharArray>): Long {
    val stack: ArrayDeque<Char> = ArrayDeque<Char>()
    val scores = lines.map { line ->
        stack.clear()
        var score:Long = 0
        run loop@{
            line.forEach { e ->
                if (e in OPENERS)
                    stack.addFirst(e)
                else if (stack.isNotEmpty() && e in CLOSERS)
                    if (e == PAIRS[stack.first()])
                        stack.removeFirst()
                    else {
                        //brackets don't match
                        //discard
                        return@loop
                    }
            }

            if (stack.isNotEmpty()) {
                //invert stack
                score = stack.map { PAIRS[it] }.fold(0, { s, e -> s*5 + INCOMPLETE_POINTS.getOrDefault(e,0)})
            }
        }
        score
    }.sorted().filterNot { it == 0L }
    return scores.median().toLong()

}

private fun part1(lines: List<CharArray>): Int {
    val stack: ArrayDeque<Char> = ArrayDeque<Char>()
    val sum = lines.map { line ->
        stack.clear()
        var score = 0
        run loop@{
            line.forEach { e ->
                if (e in OPENERS)
                    stack.addFirst(e)
                else if (stack.isNotEmpty() && e in CLOSERS)
                    if (e == PAIRS[stack.first()])
                        stack.removeFirst()
                    else {
                        //brackets don't match
                        score = CORRUPTION_POINTS.getOrDefault(e, 0)
                        return@loop
                    }
            }
        }
        score
    }.sum()
    return sum
}