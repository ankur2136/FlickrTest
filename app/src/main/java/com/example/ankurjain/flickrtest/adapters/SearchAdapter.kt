package com.example.ankurjain.flickrtest.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.ankurjain.flickrtest.R
import com.example.ankurjain.flickrtest.activities.SearchActivity
import com.example.ankurjain.flickrtest.databinding.ListItemImageViewBinding
import com.example.ankurjain.flickrtest.dto.GalleryItem
import com.example.ankurjain.flickrtest.viewmodels.GalleryItemViewModel

class SearchAdapter (private val interactionListener: SearchActivity.IItemCLickListenerInteraction, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<GalleryItem> = mutableListOf()

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getItems(): List<GalleryItem> {
        return items
    }

    fun appendItems(newItems: List<GalleryItem>?) {
        if (newItems != null && newItems.isNotEmpty()) {
            items.addAll(newItems)
            notifyItemRangeInserted(items.size, newItems.size)
        }

        if (items.size > 0) {
            interactionListener.hideProgressBar()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemImageViewBinding>(layoutInflater,
                R.layout.list_item_image_view, parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ImageViewHolder
        holder.bind(items[position])
    }

    inner class ImageViewHolder(private val binding: ListItemImageViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewModel = GalleryItemViewModel()
        }

        fun bind(item: GalleryItem) {
            binding.viewModel?.setUrl(item.url)
            Glide.with(context).load(item.url).placeholder(R.drawable.ic_launcher_background).into(binding.photoView)
            binding.photoContainer.setOnClickListener { interactionListener.onItemClick(item.url) }
        }
    }
}