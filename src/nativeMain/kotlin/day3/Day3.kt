package day3

import utils.*

const val DAY3_INPUT = "src/nativeMain/resources/day3.txt"

fun Char.score() = if (isUpperCase()) {
    code - 38
} else {
    code - 96
}

fun part1() = Path(DAY3_INPUT).lines().sumOf { line ->
    val midpoint = line.length / 2
    val compartment1 = line.substring(0 until midpoint).toSet()
    val compartment2 = line.substring(midpoint until line.length).toSet()

    compartment1.intersect(compartment2).first().score()
}

fun part2() = Path(DAY3_INPUT).lines().chunked(3).sumOf { line ->
    val bag1 = line[0].toSet()
    val bag2 = line[1].toSet()
    val bag3 = line[2].toSet()

    bag1.intersect(bag2).intersect(bag3).first().score()
}