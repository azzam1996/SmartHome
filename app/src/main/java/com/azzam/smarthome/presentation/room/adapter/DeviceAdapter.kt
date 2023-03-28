package com.azzam.smarthome.presentation.room.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.azzam.smarthome.R
import com.azzam.smarthome.databinding.ItemDeviceBinding
import com.azzam.smarthome.databinding.ItemRoomBinding
import com.azzam.smarthome.domain.model.Device
import com.azzam.smarthome.domain.model.Room


class DeviceAdapter(): RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private  val items = mutableListOf<Device>()


    interface OnDeviceClickListener {
        fun handleOnDeviceClickListener(device: Device)
    }

    var onDeviceClickListener: OnDeviceClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_device, null))
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(items[position],position)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(list: List<Device>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class DeviceViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(device: Device, position: Int) {
            val binding = ItemDeviceBinding.bind(itemView)

            binding.tvTitle.text = device.title
            binding.ivImage.load(device.image)

            binding.root.setOnClickListener { onDeviceClickListener?.handleOnDeviceClickListener(device) }
        }
    }

}