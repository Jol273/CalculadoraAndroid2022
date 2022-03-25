package pt.ulusofona.deisi.a2022

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.a2022.databinding.ItemExpressionBinding

class HistoryAdapter(
        private val onOperationClick: (String) -> Unit,
        private val onLongOperationClick: (String) -> Unit,
        private var items: List<OperationUi> = listOf(OperationUi("1+1","2"))

) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemExpressionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
                ItemExpressionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,false
                )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            onOperationClick(items[position].toString())
        }
        holder.itemView.setOnLongClickListener{
            onLongOperationClick(items[position].callTimeStamp())
            true
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<OperationUi>){
        this.items = items
        notifyDataSetChanged()
    }
}