package com.example.telegram.ui.fragments.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.R
import com.example.telegram.models.CommonModel
import com.example.telegram.database.CURRENT_UID
import com.example.telegram.utilits.DiffUtilCallback
import com.example.telegram.utilits.asTime
import kotlinx.android.synthetic.main.message_item.view.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mListMessagesCache = emptyList<CommonModel>()
    private lateinit var mDiffResult: DiffUtil.DiffResult

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blocUserMessage: ConstraintLayout = view.block_user_message
        val chatUserMessage: TextView = view.chat_user_massage
        val chatUserMessageTime: TextView = view.chat_user_massage_time

        val blocReceivedMessage: ConstraintLayout = view.block_received_message
        val chatReceivedMessage: TextView = view.chat_received_massage
        val chatReceivedMeessageTime: TextView = view.chat_received_massage_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun getItemCount(): Int = mListMessagesCache.size


    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (mListMessagesCache[position].from == CURRENT_UID) {
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = mListMessagesCache[position].text
            holder.chatUserMessageTime.text =
                mListMessagesCache[position].timeStamp.toString().asTime()
        } else {
            holder.blocUserMessage.visibility = View.GONE
            holder.blocReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = mListMessagesCache[position].text
            holder.chatReceivedMeessageTime.text =
                mListMessagesCache[position].timeStamp.toString().asTime()
        }
    }

    fun setList(list: List<CommonModel>) {

        //notifyDataSetChanged()
    }

    fun addItem(item: CommonModel) {
        val newList = mutableListOf<CommonModel>()
        newList.addAll(mListMessagesCache)
        newList.add(item)
        mDiffResult = DiffUtil.calculateDiff(DiffUtilCallback(mListMessagesCache, newList))
        mDiffResult.dispatchUpdatesTo(this)
        mListMessagesCache = newList

    }
}

