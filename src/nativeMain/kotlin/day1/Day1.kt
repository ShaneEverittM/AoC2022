package day1

import utils.*

const val DAY1_INPUT = "src/nativeMain/resources/day1.txt"

fun chunkSums() = Path(DAY1_INPUT).text().split(SEP + SEP).map { chunk -> chunk.split(SEP).sumOf { it.toInt() } }

fun part1() = chunkSums().max()

fun part2() = chunkSums().sortedDescending().subList(0, 3).sum()

