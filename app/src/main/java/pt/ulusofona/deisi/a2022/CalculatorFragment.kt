package pt.ulusofona.deisi.a2022

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import net.objecthunter.exp4j.ExpressionBuilder
import pt.ulusofona.deisi.a2022.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private val TAG2 = CalculatorFragment::class.java.simpleName
    private val operations = ArrayList<OperationUi>()
    private val adapter = HistoryAdapter(
            ::onOperationClick,
            ::onLongOperationClick,
            operations
    )
    private lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Calculadora"
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        binding = FragmentCalculatorBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistoric?.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoric?.adapter = adapter

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
        Log.i(TAG2,"Click no botão $symbol")
        if (binding.textVisor.text == "0") {
            binding.textVisor.text = symbol
        } 
        else if (symbol == "." || symbol == "+"|| symbol == "-"|| symbol == "/"|| symbol == "*") {
            binding.textVisor.append(symbol)
        } 
        else {
            binding.textVisor.append(symbol)
        }
    }

    private fun onClickEquals(){
        Log.i(TAG2, "Click no botão =")
        val text_visor_data = binding.textVisor.text.toString()
        val expression = ExpressionBuilder(text_visor_data).build()
        val result = expression.evaluate().toString()
        saveResult(text_visor_data,result)
        binding.textVisor.text = result
        Log.i(TAG2,"O resultado da expressão é ${binding.textVisor.text}")
    }

    private fun onClickClear(){
        Log.i(TAG2,"Click no botão C")
        binding.textVisor.text = "0"
        Log.i(TAG2,"texto no visor: ${binding.textVisor.text}")
    }

    private fun onClickBackspace(){
        Log.i(TAG2, "Click no botão backspace")
        if (binding.textVisor.text.length == 1){
            binding.textVisor.text = ""
            Log.i(TAG2,"${binding.textVisor.text}")
        } else{
            binding.textVisor.text = binding.textVisor.text.dropLast(1)
            Log.i(TAG2,"${binding.textVisor.text}")
        }
    }

    private fun saveResult(expression: String, result: String){
        operations.add(OperationUi(expression, result))
        adapter.updateItems(operations)
        Log.i(TAG2,"update items invocado")
    }

    private fun onOperationClick(operation: String) {
        Toast.makeText(context,operation, Toast.LENGTH_LONG).show()
    }

    private fun onLongOperationClick(timeStamp: String){
        Toast.makeText(context,timeStamp, Toast.LENGTH_LONG).show()
    }

}