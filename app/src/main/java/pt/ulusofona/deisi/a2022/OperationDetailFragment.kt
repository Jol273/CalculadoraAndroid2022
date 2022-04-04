package pt.ulusofona.deisi.a2022

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import pt.ulusofona.deisi.a2022.databinding.FragmentOperationDetailBinding

private const val ARG_PARAM1 = "param1"

class OperationDetailFragment : Fragment() {

    private var operation: OperationUi? = null
    private lateinit var binding: FragmentOperationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operation = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_operation_detail, container, false)
        binding = FragmentOperationDetailBinding.bind(view)
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: OperationUi) =
            OperationDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                    parentFragment
                }
            }
    }
}