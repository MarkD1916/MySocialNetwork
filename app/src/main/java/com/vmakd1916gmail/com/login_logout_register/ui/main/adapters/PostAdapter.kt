package com.vmakd1916gmail.com.login_logout_register.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vmakd1916gmail.com.login_logout_register.R
import com.vmakd1916gmail.com.login_logout_register.databinding.ItemPostBinding
import com.vmakd1916gmail.com.login_logout_register.models.local.Post
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import javax.inject.Inject

interface AdapterActionListener {
    fun expandItem(item: PostResponse) {

    }

    fun collapseItem(item: PostResponse) {

    }

    fun itemClick(item: PostResponse) {

    }
}

class PostAdapter(private val actionListener: AdapterActionListener) :
    PagingDataAdapter<PostResponse, PostAdapter.PostHolder>(PostComparator), View.OnClickListener {

    override fun onBindViewHolder(holder: PostAdapter.PostHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostAdapter.PostHolder {
        val binding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostHolder(binding)
    }

    inner class PostHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostResponse) = with(this.binding) {
            postTitleId.text = item.postTitle
            postDateId.text = item.postDate
            postAuthorId.text = item.author
            postCountLikeId.text = item.likes_count.toString()
        }

    }

    override fun onClick(v: View) {

        val item = v.tag as PostResponse
        when (v.id) {
            R.id.expandButton -> {
                actionListener.expandItem(item)
            }
//            R.id.collapsButton -> {
//                actionListener.collapseItem(item)
//            }
//            R.id.visible_item_id -> {
//                actionListener.itemClick(item)
//            }
        }

    }
}

object PostComparator : DiffUtil.ItemCallback<PostResponse>() {
    override fun areItemsTheSame(oldItem: PostResponse, newItem: PostResponse) =
        oldItem.postId == newItem.postId

    override fun areContentsTheSame(oldItem: PostResponse, newItem: PostResponse) =
        oldItem == newItem
}

