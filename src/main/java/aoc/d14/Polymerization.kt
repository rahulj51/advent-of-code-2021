package aoc.d14

import aoc.common.Util

fun readInputFile(): List<String> {
    return Util.readFile("src/main/java/aoc/d14/input.dat")
}

fun readTestFile(): List<String> {
    return Util.readFile("src/main/java/aoc/d14/input_test.dat")
}

fun main() {

    val readings = readInputFile()
    val template = readings.first()
    val ll = Element.parse(template)
    println(ll)

    val ruleReadings = readings.takeLastWhile { it.isNotEmpty() }
    println(ruleReadings)
    val rules = ruleReadings.parse()
    println(rules)

    val solvePart1 = solve(10, ll, rules)
    printAnswer(solvePart1)

    //this won't work due to oom
//    val solvePart2 = solve(40, ll, rules)
//    printAnswer(solvePart2)

}

private fun printAnswer(list: Element?) {
    val counts = list?.steps()?.groupingBy { it }?.eachCount()
    val max = counts?.values?.maxOf { it }
    val min = counts?.values?.minOf { it }
    if (max != null && min != null) {
        println(max - min)
    }
}

fun solve(steps: Int, template: Element, rules: MutableMap<String, String>): Element? {

    var node:Element? = template
    (1..steps).forEach {
        while (node != null) {
            var pair = node!!.toStr()
            val newE = rules.get(pair)?.first()
            //insert newE into the chain
            newE?.let { it1 ->
                val newElement = Element(e= it1, next = node!!.next)
                node?.next = newElement

            }
            node = node?.next?.next
        }
        node = template //point back to first node
    }

    return template
}

private fun List<String>.parse() =
    this.fold(mutableMapOf<String,String>()) { m, s ->
        val splits = s.split("->")
        m[splits.first().trim()] = splits.last().trim()
        return@fold m
    }


