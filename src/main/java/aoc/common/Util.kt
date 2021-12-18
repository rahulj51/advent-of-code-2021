package aoc.common

import java.io.File

object Util {


    fun readFile(f: String): List<String> {
        return File(f).readLines()
    }

}