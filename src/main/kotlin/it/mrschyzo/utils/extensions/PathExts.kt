package it.mrschyzo.utils.extensions

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

/***
 * Returns a new Path that appends the `other` Path to `this` Path
 */
infix fun Path.and(other: Path): Path =
    Path.of(this.toString(), other.toString())

/***
 * Returns a new Path that appends the `other` String to `this` Path
 */
infix fun Path.and(other: String): Path =
    Path.of(this.toString(), other)

/***
 * Recursively deletes the files in `this` path
 */
fun Path.deleteRecursively() =
    Files.walk(this)
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete)
