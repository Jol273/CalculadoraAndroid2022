package pt.ulusofona.deisi.a2022

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*

class Calculator(private val dao: OperationDao) {

    var expression: String = "0"
    private val history = mutableListOf<OperationRoom>()

    fun insertSymbol(symbol: String) : String{
        expression = if (expression == "0") symbol else "$expression$symbol"
        return expression
    }

    fun clear(): String{
        expression = "0"
        return expression
    }

    fun deleteLastSymbol(): String{
        expression = if(expression.length > 1) expression.dropLast(1) else "0"
        return expression
    }

    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(1 * 1000)
            val operation = history.find { it.uuid == uuid }
            history.remove(operation)
            onSuccess()
        }
    }

    fun getLastOperation(onFinished: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(1 * 1000)
            expression = if (history.size > 0) history[history.size - 1].expression else expression
            onFinished(expression)
        }
    }

    fun performOperation(onFinished: () -> Unit) {
        val expressionBuilder = ExpressionBuilder(expression).build()
        val result = expressionBuilder.evaluate()

        val operation = OperationRoom(expression = expression, result = result, timestamp = Date().time)
        history.add(operation)
        expression = result.toString()

        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(operation)
            addToHistory(operation)
            onFinished()
        }
    }

    fun getHistory(onFinished: (List<OperationUi>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val operations = dao.getAll()
            onFinished(operations.map {
                OperationUi(it.uuid,it.expression, it.result, it.timestamp)
            })
        }
    }

    private fun addToHistory(operationRoom: OperationRoom) {
        Thread.sleep(1 * 1000)
        history.add(operationRoom)
    }


}