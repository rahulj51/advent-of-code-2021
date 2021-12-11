package aoc.d8

//taking hints from https://todd.ginsberg.com/post/advent-of-code/2021/day8/
//so bascially, we need to put some branching logic to solve this

typealias Reading = Pair<List<String>, List<String>>

fun parse(str:String): Reading {
    val signalsAndOutput = str.split("|")
    val signals = signalsAndOutput.first().trim().split(" ")
    val output  = signalsAndOutput.last().trim().split(" ")
    return Reading(signals, output)
}

fun main() {
    val readings = readInputFile().map { parse(it) }

    println(readings.sumOf { it -> decodeSignals(it) })
}

fun decodeSignals(reading: Reading): Int {
    //sort for comparison
    var signals = reading.first.map { it.toCharArray().sorted().toList() }

    val digitToSignal = decodeSignals(signals)

    //now decipher output
    val output = reading.second.map { it.toCharArray().sorted().toList() }
    return output.map { digitToSignal.indexOf(it) }.joinToString("").toInt()
}

private fun decodeSignals(signals: List<List<Char>>): Array<List<Char>> {
    val digitToSignal = Array<List<Char>>(10) { emptyList() }

    val one = signals.filter { it.size == 2 }.first() //two segments -> digit 'one'

    val four = signals.filter { it.size == 4 }.first() //four segments -> 'four'

    val seven = signals.filter { it.size == 3 }.first() //three segments -> 'seven'

    val eight = signals.filter { it.size == 7 }.first() //seven segments -> 'eight'

    val three = signals.filter { it.size == 5 && it.containsAll(one) }.first() // five segments and contains 'one'

    val zero = signals.filter {
        it.size == 6 && it.containsAll(one)
                && it.containsAll(seven) && !it.containsAll(three)
    }.first()

    val nine = signals.filter { it.size == 6 && it.containsAll(seven) && it.containsAll(three) }
        .first() // six segments and contains 'seven'

    val six = signals.filter { it.size == 6 && !it.containsAll(nine) && !it.containsAll(zero) }.first()

    val five = signals.filter { it.size == 5 && six.containsAll(it) }.first()

    val two = signals.filter { it.size == 5 && !it.containsAll(five) && !it.containsAll(three) }.first()

    digitToSignal[0] = zero
    digitToSignal[1] = one
    digitToSignal[2] = two
    digitToSignal[3] = three
    digitToSignal[4] = four
    digitToSignal[5] = five
    digitToSignal[6] = six
    digitToSignal[7] = seven
    digitToSignal[8] = eight
    digitToSignal[9] = nine

    return digitToSignal
}

