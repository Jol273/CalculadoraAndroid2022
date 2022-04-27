package pt.ulusofona.deisi.a2022

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import pt.ulusofona.deisi.a2022.databinding.FragmentHistoryBinding

private const val ARG_OPERATIONS = "operations"

class HistoryFragment : Fragment() {

    private val model = Calculator()
    private lateinit var binding: FragmentHistoryBinding
    private var adapter =
            HistoryAdapter(
                    ::onOperationClick,
                    ::onLongOperationClick,
            )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Histórico"
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    /*companion object {
        @JvmStatic
        fun newInstance(operations: ArrayList<OperationUi>) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    Log.i(TAGHISTORY, "newInstance invocado")
                    putParcelableArrayList(ARG_OPERATIONS, operations)
                }
            }
    }*/

    override fun onStart() {
        super.onStart()
        binding.historic.layoutManager = LinearLayoutManager(activity as Context)
        binding.historic.adapter = adapter
        model.getHistory { updateHistory(it) }
        
    }

    private fun onOperationClick(operation: OperationUi) {
        NavigationManager.goToOperationDetailFragment(activity!!.supportFragmentManager,operation)
    }

    private fun onLongOperationClick(operation: OperationUi): Boolean {
        Toast.makeText(context, getString(R.string.deleting), Toast.LENGTH_SHORT).show()
        model.deleteOperation(operation.uuid) { model.getHistory { updateHistory(it) } }
        return false
    }

    private fun updateHistory(operations: List<Operation>) {
        val history = operations.map { OperationUi(it.uuid, it.expression, it.result, it.timeStamped) }
        CoroutineScope(Dispatchers.Main).launch {
            showHistory(history.isNotEmpty())
            adapter.updateItems(history)
        }
    }

    private fun showHistory(show: Boolean) {
        if (show) {
            binding.historic.visibility = View.VISIBLE
            binding.textNoHistoryAvailable.visibility = View.GONE
        } else {
            binding.historic.visibility = View.GONE
            binding.textNoHistoryAvailable.visibility = View.VISIBLE
        }
    }

}