package com.azzam.smarthome.presentation.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.azzam.smarthome.R
import com.azzam.smarthome.databinding.FragmentRoomBinding
import com.azzam.smarthome.domain.model.Device
import com.azzam.smarthome.domain.model.Room
import com.azzam.smarthome.presentation.room.adapter.DeviceAdapter

class RoomFragment : Fragment(), DeviceAdapter.OnDeviceClickListener {

    var binding: FragmentRoomBinding? = null
    var room: Room? = null
    private val deviceAdapter = DeviceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            room = it?.getParcelable("Room")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        room?.let {
            val percentage = it.temperature.toFloat() / it.maxTemperature.toFloat()
            binding?.temperatureProgressBar?.animateProgress(percentage, it.temperature)

            binding?.tvTitle?.text = it.title
            binding?.tvMinTemperature?.text = it.minTemperature.toString() + "°"
            binding?.tvMaxTemperature?.text = it.maxTemperature.toString() + "°"
        }

        binding?.clSettings?.setOnClickListener {
            binding?.motionLayout?.transitionToState(R.id.settingsSelected)
        }

        binding?.clFan?.setOnClickListener {
            binding?.motionLayout?.transitionToState(R.id.fanSelected)
        }

        binding?.clWeather?.setOnClickListener {
            binding?.motionLayout?.transitionToState(R.id.weatherSelected)
        }
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

        binding?.rvDevices?.layoutManager = layoutManager
        deviceAdapter.onDeviceClickListener = this
        binding?.rvDevices?.adapter = deviceAdapter

        room?.devices?.let {
            deviceAdapter.updateItems(it)
        }
    }

    override fun handleOnDeviceClickListener(device: Device) {
        // TODO
    }
}