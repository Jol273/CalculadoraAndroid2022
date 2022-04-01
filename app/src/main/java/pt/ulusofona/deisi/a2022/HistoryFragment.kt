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
import pt.ulusofona.deisi.a2022.databinding.FragmentHistoryBinding

private const val ARG_OPERATIONS = "operations"

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val TAGHISTORY = HistoryFragment::class.java.simpleName
    private var operations: ArrayList<OperationUi>? = null
    private val adapter = HistoryAdapter(
            ::onOperationClick,
            ::onLongOperationClick,
            operations
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operations = it.getParcelableArrayList(ARG_OPERATIONS)!!
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Histórico"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAGHISTORY,"onCreateView invocado")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(operations: ArrayList<OperationUi>) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_OPERATIONS,operations)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAGHISTORY,"onStart invocado")
        binding.historic.layoutManager = LinearLayoutManager(activity as Context)
        binding.historic.adapter = adapter
    }

    private fun onOperationClick(operation: String) {
        //parentFragment.
        //Toast.makeText(context,operation, Toast.LENGTH_LONG).show()
    }

    private fun onLongOperationClick(timeStamp: String){
        Toast.makeText(context,timeStamp, Toast.LENGTH_LONG).show()
    }

}