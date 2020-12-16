package com.savenews.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.savenews.BR
import com.savenews.NewsApplication
import com.savenews.R
import com.savenews.databinding.PostListItemBinding
import com.savenews.model.Characters
import com.savenews.model.Characters_
import io.objectbox.Box

@BindingAdapter("imageUrl", "placeholder")
fun ImageView.setImageUrl(
    url: String?,
    placeHolder: Drawable?
) {
    if (url == null) {
        this.setImageDrawable(placeHolder)
    } else {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(this)
    }
}

class PostListAdapter(var context: Context) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private var list: List<Characters> = emptyList()
    private lateinit var charactersBox: Box<Characters>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PostListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.post_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position))
    }

    fun setAdapterList(
        list: List<Characters>
    ){
        this.list = list
        notifyDataSetChanged()
    }
    fun saveDataList(
        charactersList: List<Characters>
    ){
        charactersBox = NewsApplication.ObjectBox.boxStore.boxFor(Characters::class.java)
        charactersBox.put(charactersList)
    }
    fun queryForText(query: String?) {
        charactersBox = NewsApplication.ObjectBox.boxStore.boxFor(Characters::class.java)
        val items:List<Characters>
        if(query!!.length>0) {
            items = charactersBox.query()
                .startsWith(Characters_.name, query)
                .build()
                .find()
        }else{
            items = charactersBox.query()
                .build()
                .find()
        }
        this.list = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val binding: PostListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(BR.postmodel, data)
            binding.executePendingBindings()
        }
    }
}