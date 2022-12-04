package utils

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import kotlin.math.max
import kotlin.math.min

val SEP = lineSeparator()

fun fileSize(file: Path) = FileSystem.SYSTEM.metadata(file).size!!.toInt()

fun Path(string: String) = string.toPath()

fun Path.lines() = sequence {
    FileSystem.SYSTEM.read(this@lines) {
        while (true) {
            yield(readUtf8Line() ?: break)
        }
    }
}

fun Path.text(): String {
    val size = fileSize(this)
    val buf = ByteArray(size)
    FileSystem.SYSTEM.read(this) { readFully(buf) }
    return buf.decodeToString()
}

infix fun <T : Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>) =
    endInclusive <= other.endInclusive && start >= other.start

infix fun <T> ClosedRange<T>.overlaps(other: ClosedRange<T>): Boolean
        where T : Comparable<T>,
              T : Number {
    return (min(endInclusive.toLong(), other.endInclusive.toLong())) - max(start.toLong(), other.start.toLong()) >= 0
}

fun lineSeparator() = when (Platform.osFamily) {
    OsFamily.UNKNOWN -> "\n"
    OsFamily.MACOSX -> "\r"
    OsFamily.IOS -> "\r"
    OsFamily.LINUX -> "\n"
    OsFamily.WINDOWS -> "\r\n"
    OsFamily.ANDROID -> "\n"
    OsFamily.WASM -> "\n"
    OsFamily.TVOS -> "\n"
    OsFamily.WATCHOS -> "\r"
}