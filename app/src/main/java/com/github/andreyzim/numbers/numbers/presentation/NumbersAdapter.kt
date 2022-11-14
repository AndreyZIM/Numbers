package com.github.andreyzim.numbers.numbers.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.andreyzim.numbers.R
import com.github.andreyzim.numbers.databinding.ItemNumberLayoutBinding

class NumbersAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<NumbersViewHolder>(), Mapper.Unit<List<NumberUi>> {

    private val list = mutableListOf<NumberUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder =
        NumbersViewHolder(
            ItemNumberLayoutBinding.inflate(
                LayoutInflater.from(parent.context)
            ), clickListener
        )

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    override fun map(source: List<NumberUi>) {
        val diff = DiffUtilCallback(list, source)
        val result = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}

class NumbersViewHolder(
    binding: ItemNumberLayoutBinding,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.titleTextView
    private val subTitle = binding.subTitleTextView
    private val mapper = ListItemUi(title, subTitle)

    fun bind(model: NumberUi) {
        model.map(mapper)
        itemView.setOnClickListener { clickListener.click(model) }
    }
}

interface ClickListener {
    fun click(item: NumberUi)
}

class DiffUtilCallback(
    private val oldList: List<NumberUi>,
    private val newList: List<NumberUi>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].map(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].equals(newList[newItemPosition])

}