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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.a2022.databinding.FragmentCalculatorBinding

private const val ARG_OPERATIONS = "operations"

class CalculatorFragment : Fragment() {

    private val TAG2 = CalculatorFragment::class.java.simpleName
    private var operations = ArrayList<OperationUi>()
    private lateinit var adapter : HistoryAdapter

    private lateinit var binding: FragmentCalculatorBinding
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            Log.i(TAG2,"getParcelableArrayList")
            operations = it.getParcelableArrayList(ARG_OPERATIONS)!!
        }*/
        adapter = HistoryAdapter(
                ::onOperationClick,
                ::onLongOperationClick,
                operations
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Calculadora"
        val view = inflater.inflate(
            R.layout.fragment_calculator, container, false
        )
        binding = FragmentCalculatorBinding.bind(view)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
        binding.textVisor.text = viewModel.getDisplayValue()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistoric?.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoric?.adapter = adapter

        binding.button0.setOnClickListener { onClickSymbol("0") }
        binding.button1.setOnClickListener { onClickSymbol("1") }
        binding.button2.setOnClickListener { onClickSymbol("2") }
        binding.button3.setOnClickListener { onClickSymbol("3") }
        binding.button4.setOnClickListener { onClickSymbol("4") }
        binding.button5.setOnClickListener { onClickSymbol("5") }

        binding.button00?.setOnClickListener { onClickSymbol("00") }
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
        binding.buttonBackspace.setOnClickListener { onClickBackspace() }
        binding.buttonClear.setOnClickListener { onClickClear() }
        binding.buttonEquals.setOnClickListener { onClickEquals() }
    }

    private fun onClickSymbol(symbol: String){
        Log.i(TAG2,"Click no botão $symbol")
        binding.textVisor.text = viewModel.onClickSymbol(symbol)
    }

    private fun onClickEquals(){
        Log.i(TAG2, "Click no botão =")
        binding.textVisor.text = viewModel.onClickEquals()
        Log.i(TAG2,"O resultado da expressão é ${binding.textVisor.text}")
    }

    private fun onClickClear(){
        Log.i(TAG2,"Click no botão C")
        binding.textVisor.text = viewModel.onClickClear()
    }

    private fun onClickBackspace(){
        Log.i(TAG2, "Click no botão backspace")
        binding.textVisor.text = viewModel.onClickBackSpace()
    }

    private fun saveResult(expression: String, result: String){
        operations.add(OperationUi(expression, result))
        adapter.updateItems(operations)
        Log.i(TAG2,"update items invocado")
    }

    private fun onOperationClick(operation: OperationUi) {
        NavigationManager.goToOperationDetailFragment(activity!!.supportFragmentManager,operation)
    }

    private fun onLongOperationClick(timeStamp: String){
        Toast.makeText(context,timeStamp, Toast.LENGTH_LONG).show()
    }

    /*companion object {
        @JvmStatic
        fun newInstance(operations: ArrayList<OperationUi>) =
            CalculatorFragment().apply {
                arguments = Bundle().apply {
                    Log.i(TAG2,"newInstance invocado")
                    putParcelableArrayList(ARG_OPERATIONS,operations)
                }
            }
    }*/

}