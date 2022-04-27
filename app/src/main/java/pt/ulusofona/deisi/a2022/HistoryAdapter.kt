package pt.ulusofona.deisi.a2022

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.a2022.databinding.ItemExpressionBinding
import kotlin.reflect.KFunction1

class HistoryAdapter(
    private val onOperationClick: (OperationUi) -> Unit,
    private val onLongOperationClick: (OperationUi) -> Boolean,
    private var items: List<OperationUi> = listOf(),
        ) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemExpressionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(ItemExpressionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,false
                )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.textExpression.text = items[position].expression
        holder.binding.textResult.text = items[position].result.toString()

        holder.itemView.setOnClickListener{ onOperationClick(items[position]) }
        holder.itemView.setOnLongClickListener{ onLongOperationClick(items[position]) }
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<OperationUi>){
        this.items = items
        notifyDataSetChanged()
    }

}