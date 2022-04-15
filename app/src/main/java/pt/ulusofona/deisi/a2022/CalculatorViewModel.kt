package pt.ulusofona.deisi.a2022

import android.util.Log
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val model = Calculator()
    private val TAG = CalculatorViewModel::class.java.simpleName

    fun getDisplayValue(): String {
        Log.i(TAG,model.display)
        return model.display
    }

    fun onClickSymbol(symbol: String) :String{
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(): String {
        val result = model.performOperation()
        return result.toString()
    }

    fun onClickClear() = model.clearDisplay()

    fun onClickBackSpace() = model.backSpace()
}