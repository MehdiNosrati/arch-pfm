package io.xdev.x_pfm.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.xdev.x_pfm.R
import io.xdev.x_pfm.databinding.ItemTagBinding
import io.xdev.x_pfm.repository.models.entities.Tag

class TagAdapter(private val tags: MutableList<Tag>,
                 private val tagClickHandler: TagClickHandler) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        for (i in 0..999) {
            if (i % 2 == 0) {
                this.tags.add(Tag("test", R.drawable.ic_add_fab, 0))
            } else {
                this.tags.add(Tag("test num 2", R.drawable.ic_add_fab, 0))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        R.layout.item_tag, parent, false),
                tagClickHandler
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val t = tags[position]
        if (holder is TagViewHolder) {
            holder.bind(t)
        }
    }

    override fun getItemCount(): Int {
        return this.tags.size
    }

    interface TagClickHandler {
        fun tagClicked(t: Tag)
    }

    internal inner class TagViewHolder(var binding: ItemTagBinding,
                                       private var tagClickHandler: TagClickHandler) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(t: Tag) {
            binding.tag = t
            binding.executePendingBindings()
            binding.root.setOnClickListener({ tagClickHandler.tagClicked(t) })
        }


    }
}
