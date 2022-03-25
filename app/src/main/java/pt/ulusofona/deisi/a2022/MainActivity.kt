package pt.ulusofona.deisi.a2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import net.objecthunter.exp4j.ExpressionBuilder
import pt.ulusofona.deisi.a2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    //private val operations = mutableListOf("1+1=2")
    private val operations = mutableListOf(OperationUi("1+1","2"))
    private val adapter = HistoryAdapter(
            ::onOperationClick,
            ::onLongOperationClick
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "o método onCreate foi invocado")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        Log.i(TAG,"o método onDestroy foi invocado")
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistoric?.layoutManager = LinearLayoutManager(this)
        binding.rvHistoric?.adapter = adapter
        adapter.updateItems(operations)

        binding.button0.setOnClickListener { onClickSymbol("0") }
        binding.button00?.setOnClickListener { onClickSymbol("00") }
        binding.button1.setOnClickListener { onClickSymbol("1") }
        binding.button2.setOnClickListener { onClickSymbol("2") }
        binding.button3.setOnClickListener { onClickSymbol("3") }
        binding.button4.setOnClickListener { onClickSymbol("4") }
        binding.button5.setOnClickListener { onClickSymbol("5") }
        binding.button6.setOnClickListener { onClickSymbol("6") }
        binding.button7?.setOnClickListener { onClickSymbol("7") }
        binding.button8?.setOnClickListener { onClickSymbol("8") }
        binding.button9?.setOnClickListener { onClickSymbol("9") }
        binding.button2Duplicate?.setOnClickListener { onClickSymbol("2") }

        binding.buttonAdition.setOnClickListener { onClickSymbol("+") }
        binding.buttonSubtract.setOnClickListener { onClickSymbol("-") }
        binding.buttonMultiplication.setOnClickListener { onClickSymbol("*") }
        binding.buttonDivision.setOnClickListener { onClickSymbol("/") }
        binding.buttonDot.setOnClickListener { onClickSymbol(".") }

        binding.buttonEquals.setOnClickListener { onClickEquals() }
        binding.buttonBackspace.setOnClickListener { onClickBackspace() }
        binding.buttonClear.setOnClickListener { onClickClear() }
    }

    private fun onClickSymbol(symbol: String){
        Log.i(TAG,"Click no botão $symbol")
        if (binding.textVisor.text == "0") {
            binding.textVisor.text = symbol
        } else if (symbol == "." || symbol == "+"|| symbol == "-"|| symbol == "/"|| symbol == "*") {
            binding.textVisor.append(symbol)
        } else {
            binding.textVisor.append(symbol)
        }
    }

    private fun onClickEquals(){
        Log.i(TAG, "Click no botão =")
        val text_visor_data = binding.textVisor.text.toString()
        val expression = ExpressionBuilder(text_visor_data).build()
        val result = expression.evaluate().toString()
        saveResult(text_visor_data,result)
        binding.textVisor.text = result
        Log.i(TAG,"O resultado da expressão é ${binding.textVisor.text}")
    }

    private fun onClickClear(){
        Log.i(TAG,"Click no botão C")
        binding.textVisor.text = "0"
        Log.i(TAG,"texto no visor: ${binding.textVisor.text}")
    }

    private fun onClickBackspace(){
        Log.i(TAG, "Click no botão backspace")
        if (binding.textVisor.text.length == 1){
            binding.textVisor.text = ""
            Log.i(TAG,"${binding.textVisor.text}")
        } else{
            binding.textVisor.text = binding.textVisor.text.dropLast(1)
            Log.i(TAG,"${binding.textVisor.text}")
        }
    }

    private fun saveResult(expression: String, result: String){
        /*operations.add("$expression=$result")
        adapter.updateItems(operations)*/
        operations.add(OperationUi(expression, result))
        adapter.updateItems(operations)
    }

    private fun onOperationClick(operation: String) {
        Toast.makeText(this,operation, Toast.LENGTH_LONG).show()
    }

    private fun onLongOperationClick(timeStamp: String){
        Toast.makeText(this,timeStamp,Toast.LENGTH_LONG).show()
    }

}