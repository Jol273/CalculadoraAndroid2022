package pt.ulusofona.deisi.a2022

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.a2022.databinding.ItemExpressionBinding
import kotlin.reflect.KFunction1

class HistoryAdapter(
    private val onOperationClick: (OperationUi) -> Unit,
    private val onLongOperationClick: (String) -> Unit,
    private var items: ArrayList<OperationUi>,
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
        holder.itemView.setOnClickListener{
            onOperationClick(
                items.get(position)
            )
        }
        val parts = items.get(position).toString().split("=")
        holder.binding.textExpression.text = parts[0]
        holder.binding.textResult.text = parts[1]

        holder.itemView.setOnLongClickListener{
            onLongOperationClick(items.get(position).callTimeStamp())
            true
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(items: ArrayList<OperationUi>){
        this.items = items
        notifyDataSetChanged()
    }

}