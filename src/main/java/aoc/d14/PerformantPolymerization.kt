package aoc.d14

//store char and freqeuncy
//but we also need the pairs and frequency after each step
//for ex NNCB
//NN -1, NC-> 1, CB->1

//each step, update


private val readings = readInputFile()
//parsing logic
//map of xx->i
private val lastChar = readings.first().last()
private val template = parseTemplate(readings.first())
private val rules = parseRules(readings)

private fun parseTemplate(str: String): Map<String, Long> =
    str.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }

private fun parseRules(input: List<String>): Map<String, Char> =
    input.drop(2).associate { //great util function
        it.substring(0..1) to it[6]
    }


fun main() {
    println(solve(40))
}

private fun solve(iterations: Int): Long =
    (0 until iterations)
        .fold(template) { polymer, _ -> polymer.react() }
        .byCharFrequency()
        .values
        .sorted()
        .let { it.last() - it.first() }

private fun Map<String, Long>.react(): Map<String, Long> =
    buildMap {
        this@react.forEach{ (pair, count) ->
            val inserted = rules.get(pair)
            plus("${pair.first()}$inserted", count)
            plus("$inserted${pair.last()}", count)

        }

    }

fun <T> MutableMap<T, Long>.plus(key: T, count: Long) {
    this[key] = this.getOrDefault(key, 0L) + count
}

private fun Map<String, Long>.byCharFrequency(): Map<Char, Long> =
    this
        .map { it.key.first() to it.value }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.sum() + if (it.key == lastChar) 1 else 0 }

