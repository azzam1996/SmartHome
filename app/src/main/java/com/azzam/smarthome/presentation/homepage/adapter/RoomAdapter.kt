package com.azzam.smarthome.presentation.homepage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.azzam.smarthome.R
import com.azzam.smarthome.databinding.ItemRoomBinding
import com.azzam.smarthome.domain.model.Room

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val items = mutableListOf<Room>()

    interface OnRoomClickListener {
        fun handleOnRoomClickListener(room: Room)
    }

    var onRoomClickListener: OnRoomClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_room, null))
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(list: List<Room>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class RoomViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(room: Room, position: Int) {
            val binding = ItemRoomBinding.bind(itemView)

            binding.tvTitle.text = room.title
            binding.ivImage.load(room.image)
            binding.tvDevices.text = "${room.devices?.size} devices"
            binding.root.setOnClickListener { onRoomClickListener?.handleOnRoomClickListener(room) }
        }
    }

}