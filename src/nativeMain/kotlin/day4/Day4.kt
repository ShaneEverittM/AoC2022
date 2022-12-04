package day4

import utils.*

const val DAY4_INPUT = "src/nativeMain/resources/day4.txt"

private fun rangePairs() = Path(DAY4_INPUT).lines().map { line ->
    val (range1, range2) = line.split(",").map { range ->
        val (lower, upper) = range.split("-")
        (lower.toInt()..upper.toInt())
    }
    range1 to range2
}

fun part1() = rangePairs().count { (range1, range2) ->
    (range1 contains range2) or (range2 contains range1)
}

fun part2() = rangePairs().count { (range1, range2) ->
    (range1 overlaps range2) or (range2 overlaps range1)
}
