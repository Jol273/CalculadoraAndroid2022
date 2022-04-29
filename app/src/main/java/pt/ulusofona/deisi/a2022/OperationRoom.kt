package pt.ulusofona.deisi.a2022

import androidx.room.*
import java.util.*

@Entity(tableName = "operation")
data class OperationRoom(
        @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
        val expression: String,
        val result: Double,
        val timestamp: Long) {


}