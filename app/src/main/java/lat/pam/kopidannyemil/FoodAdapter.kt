package lat.pam.kopidannyemil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(
    private var items: List<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit,
    private val onItemLongClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEmoji: TextView = view.findViewById(R.id.tv_emoji)
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvDesc: TextView = view.findViewById(R.id.tv_desc)
        val tvPrice: TextView = view.findViewById(R.id.tv_price)
    }

    fun setData(newItems: List<FoodItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = items[position]
        holder.tvEmoji.text = item.emoji
        holder.tvName.text = item.name
        holder.tvDesc.text = item.description
        holder.tvPrice.text = rupiah(item.price)
        holder.itemView.setOnClickListener { onItemClick(item) }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(item)
            true
        }
    }

    override fun getItemCount(): Int = items.size
}