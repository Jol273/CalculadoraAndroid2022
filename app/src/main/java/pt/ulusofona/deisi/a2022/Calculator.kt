package pt.ulusofona.deisi.a2022

import net.objecthunter.exp4j.ExpressionBuilder

class Calculator {

    var display: String = "0"
    private val history = mutableListOf<Operation>()

    fun insertSymbol(symbol: String) : String{
        if (display == "0") {
            display = symbol
        }
        else if (symbol == "." || symbol == "+"|| symbol == "-"|| symbol == "/"|| symbol == "*") {
            display += symbol
        }
        else {
            display += symbol
        }
        return display
    }

    fun performOperation(): Double {
        val expressionBuilder = ExpressionBuilder(display).build()
        val result = expressionBuilder.evaluate()
        history.add(Operation(display,result))
        display = result.toString()
        print(display)
        return result
    }

    fun clearDisplay(): String{
        display = "0"
        return display
    }

    fun backSpace(): String{
        display = if (display.length == 1) {
            "0"
        } else {
            display.dropLast(1)
        }
        return display
    }
}