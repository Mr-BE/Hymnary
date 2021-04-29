package dev.mrbe.hymnary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dev.mrbe.hymnary.databinding.ItemHymnBinding

class HymnAdapter(
    options: FirestoreRecyclerOptions<Hymn>,
    private val clickListener: HymnClickListener
) :
    FirestoreRecyclerAdapter<Hymn, HymnViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HymnViewHolder {
        return HymnViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HymnViewHolder, position: Int, model: Hymn) {
        val hymnItem = getItem(position)

        holder.itemView.setOnClickListener {
            clickListener.onClick(hymnItem)
        }
        holder.bind(hymnItem)
    }

}

class HymnViewHolder private constructor(private val binding: ItemHymnBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Hymn) {
        binding.hymn = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): HymnViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHymnBinding.inflate(layoutInflater, parent, false)
            return HymnViewHolder(binding)
        }
    }

}

//Click listener class for hymn items
class HymnClickListener(val click: (Hymn) -> Unit) {
    fun onClick(hymn: Hymn) = click(hymn)
}
