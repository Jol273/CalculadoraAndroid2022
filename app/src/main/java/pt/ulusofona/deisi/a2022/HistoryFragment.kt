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
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import pt.ulusofona.deisi.a2022.databinding.FragmentHistoryBinding

private const val ARG_OPERATIONS = "operations"

class HistoryFragment : Fragment() {

    //private val model = Calculator()
    private val TAG = HistoryFragment::class.java.simpleName
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
        getAllOperationsWs { operations -> Log.i(TAG,operations.toString())}
       // model.getHistory { updateHistory(it) }
        
    }

    private fun onOperationClick(operation: OperationUi) {
        NavigationManager.goToOperationDetailFragment(activity!!.supportFragmentManager,operation)
    }

    private fun onLongOperationClick(operation: OperationUi): Boolean {
        Toast.makeText(context, getString(R.string.deleting), Toast.LENGTH_SHORT).show()
       // model.deleteOperation(operation.uuid) { model.getHistory { updateHistory(it) } }
        return false
    }

    private fun updateHistory(operations: List<OperationUi>) {
        val history = operations.map { OperationUi(it.uuid, it.expression, it.result, it.timestamp) }
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

    private fun getAllOperationsWs(callback: (List<OperationUi>) -> Unit) {
        data class GetAllOperationsResponse(val uuid: String, val expression: String, val result: Double, val timstamp: Long)

        CoroutineScope(Dispatchers.IO).launch {
            val request: Request = Request.Builder()
                .url("https://cm-calculatora.herokuapp.com/api/operations")
                .addHeader("apikey",
                "8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")
                .build()

            val response = OkHttpClient().newCall(request).execute().body
            if(response != null) {
                val responseObj = Gson().fromJson(response.string(),
                Array<GetAllOperationsResponse>::class.java).toList()

                callback(
                    responseObj.map {
                        OperationUi(it.uuid,it.expression,it.result,it.timstamp)
                    }
                )
            }
        }
    }

}