package day2

import utils.*

const val DAY2_INPUT = "src/nativeMain/resources/day2.txt"

enum class Outcome(val score: Int) {
    Win(6),
    Tie(3),
    Loss(0);

    companion object {
        fun fromString(letter: String): Outcome {
            require(letter.length == 1) { "Move cannot be created from multiple characters" }
            return when (letter) {
                "X" -> Loss
                "Y" -> Tie
                "Z" -> Win
                else -> throw IllegalArgumentException("Move cannot be created from character $letter.")
            }
        }
    }
}

enum class Move(val score: Int) {
    Rock(1),
    Paper(2),
    Scissors(3);

    infix fun beats(other: Move): Outcome {
        return when (this to other) {
            Rock to Scissors -> Outcome.Win
            Rock to Paper -> Outcome.Loss
            Rock to Rock -> Outcome.Tie
            Paper to Rock -> Outcome.Win
            Paper to Scissors -> Outcome.Loss
            Paper to Paper -> Outcome.Tie
            Scissors to Paper -> Outcome.Win
            Scissors to Rock -> Outcome.Loss
            Scissors to Scissors -> Outcome.Tie
            else -> throw NotImplementedError()
        }
    }

    companion object {
        fun fromString(letter: String): Move {
            require(letter.length == 1) { "Move cannot be created from multiple characters" }
            return when (letter) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                "C", "Z" -> Scissors
                else -> throw IllegalArgumentException("Move cannot be created from character $letter.")
            }
        }

        fun fromOutcome(outcome: Outcome, move: Move): Move {
            return when (outcome to move) {
                Outcome.Tie to Rock -> Rock
                Outcome.Win to Rock -> Paper
                Outcome.Loss to Rock -> Scissors
                Outcome.Tie to Paper -> Paper
                Outcome.Win to Paper -> Scissors
                Outcome.Loss to Paper -> Rock
                Outcome.Tie to Scissors -> Scissors
                Outcome.Win to Scissors -> Rock
                Outcome.Loss to Scissors -> Paper
                else -> throw NotImplementedError("Impossible")
            }
        }
    }
}

enum class Mode {
    Guide,
    Plan,
}

fun computeScore(mode: Mode) = Path(DAY2_INPUT).lines().sumOf {
    val (left, right) = it.split(" ")
    val move = Move.fromString(left)
    val response = when (mode) {
        Mode.Guide -> Move.fromString(right)
        Mode.Plan -> Move.fromOutcome(Outcome.fromString(right), move)
    }
    response.score + response.beats(move).score
}

fun part1() = computeScore(Mode.Guide)

fun part2() = computeScore(Mode.Plan)