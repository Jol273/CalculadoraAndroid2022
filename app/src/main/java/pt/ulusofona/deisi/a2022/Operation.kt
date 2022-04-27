package pt.ulusofona.deisi.a2022

import java.util.*

class Operation(
        val uuid: String = UUID.randomUUID().toString(),
        val expression: String,
        val result: Double) {

    val timeStamped: Long = Date().time
}