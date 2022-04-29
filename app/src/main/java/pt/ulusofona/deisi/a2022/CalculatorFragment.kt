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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.a2022.databinding.FragmentCalculatorBinding

private const val ARG_OPERATIONS = "operations"

class CalculatorFragment : Fragment() {

    private val TAG2 = CalculatorFragment::class.java.simpleName
    private var adapter = HistoryAdapter(
    ::onOperationClick,
    ::onLongOperationClick)

    private lateinit var binding: FragmentCalculatorBinding
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
        //operations = viewModel.getHistory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Calculadora"
        val view = inflater.inflate(
            R.layout.fragment_calculator, container, false
        )
        binding = FragmentCalculatorBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistoric?.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoric?.adapter = adapter
        viewModel.onGetHistory { updateHistory(it) }
        binding.textVisor.text = viewModel.getDisplayValue()

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
        val displayUpdated = viewModel.onClickSymbol(symbol)
        binding.textVisor.text = displayUpdated
    }

    private fun onClickEquals(){
        val displayUpdated = viewModel.onClickEquals {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context,getString(R.string.registry_saved),Toast.LENGTH_LONG).show()
                viewModel.onGetHistory { updateHistory(it) }
            }
        }
        binding.textVisor.text = displayUpdated
    }

    private fun onClickClear(){
        Log.i(TAG2,"Click no botão C")
        val displayUpdated = viewModel.onClickClear()
        binding.textVisor.text = displayUpdated
    }

    private fun onClickBackspace(){
        Log.i(TAG2, "Click no botão backspace")
        val displayUpdated = viewModel.onClickBackspace()
        binding.textVisor.text = displayUpdated
    }

    private fun onClickGetPreviousOperation() {
        viewModel.onClickGetLastOperation {
            binding.textVisor.text = it
        }
    }

    /*private fun saveResult(expression: String, result: String){
        operations.add(OperationUi(expression, result))
        adapter.updateItems(operations)
        Log.i(TAG2,"update items invocado")
    }*/

    private fun onOperationClick(operation: OperationUi) {
        NavigationManager.goToOperationDetailFragment(activity!!.supportFragmentManager,operation)
    }

    private fun onLongOperationClick(operation: OperationUi) : Boolean {
        Toast.makeText(context, getString(R.string.deleting), Toast.LENGTH_SHORT).show()
        viewModel.onDeleteOperation(operation.uuid) {viewModel.onGetHistory { updateHistory(it) }}
        return false
    }


    private fun updateHistory(operations: List<OperationUi>){
        val history = operations.map { OperationUi(it.uuid, it.expression, it.result, it.timestamp) }
        CoroutineScope(Dispatchers.Main).launch {
            adapter.updateItems(history)
        }
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