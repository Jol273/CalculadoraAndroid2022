package pt.ulusofona.deisi.a2022

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

/*
@Parcelize
class OperationUi(private val expression: String, private val result: String,
                  private val currentTime: String = SimpleDateFormat("dd/MM/yyyy - hh:mm:ss",Locale.UK).format(Date())
): Parcelable {


    override fun toString(): String {
        return "$expression = $result"
    }

    fun callTimeStamp(): String{
        return currentTime
    }
}*/

@Parcelize
data class OperationUi(
        val uuid: String, val expression: String, val result: Double, val timestamp: Long
) : Parcelable