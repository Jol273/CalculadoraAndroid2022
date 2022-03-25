package pt.ulusofona.deisi.a2022

import java.text.SimpleDateFormat
import java.util.*

class OperationUi(private val expression: String, private val result: String) {

    private val currentTime = SimpleDateFormat("dd/MM/yyyy - hh:mm:ss",Locale.UK).format(Date())

    override fun toString(): String {
        return "$expression = $result"
    }

    fun callTimeStamp(): String{
        return currentTime
    }
}