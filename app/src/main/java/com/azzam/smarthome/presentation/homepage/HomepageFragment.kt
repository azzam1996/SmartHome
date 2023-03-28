package com.azzam.smarthome.presentation.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.azzam.smarthome.R
import com.azzam.smarthome.databinding.FragmentHomepageBinding
import com.azzam.smarthome.domain.model.Device
import com.azzam.smarthome.domain.model.Room
import com.azzam.smarthome.presentation.homepage.adapter.RoomAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomepageFragment : Fragment(), RoomAdapter.OnRoomClickListener {

    lateinit var binding: FragmentHomepageBinding
    private val roomAdapter = RoomAdapter()
    private val list = mutableListOf<Room>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomepageBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHardCodedList()
        initAdapter()
    }

    private fun initHardCodedList() {
        val devices1 = mutableListOf<Device>()
        devices1.add(
            Device(
                title = "Light",
                image = R.drawable.ic_light
            )
        )
        devices1.add(
            Device(
                title = "Smart TV",
                image = R.drawable.ic_tv
            )
        )
        devices1.add(
            Device(
                title = "Fridge",
                image = R.drawable.ic_kitchen
            )
        )


        val devices2 = mutableListOf<Device>()
        devices2.add(
            Device(
                title = "Couch",
                image = R.drawable.ic_living_room
            )
        )
        devices2.add(
            Device(
                title = "Light",
                image = R.drawable.ic_light
            )
        )
        devices2.add(
            Device(
                title = "Smart TV",
                image = R.drawable.ic_tv
            )
        )
        devices2.add(
            Device(
                title = "Fridge",
                image = R.drawable.ic_kitchen
            )
        )

        val devices3 = mutableListOf<Device>()
        devices3.add(
            Device(
                title = "Light",
                image = R.drawable.ic_light
            )
        )
        devices3.add(
            Device(
                title = "Smart TV",
                image = R.drawable.ic_tv
            )
        )

        list.clear()
        list.add(
            Room(
                title = "Living Room",
                image = R.drawable.ic_living_room,
                devices = devices2,
                temperature = 24,
                maxTemperature = 30,
                minTemperature = 10
            )
        )

        list.add(
            Room(
                title = "Kitchen",
                image = R.drawable.ic_kitchen,
                devices = devices1,
                temperature = 14,
                maxTemperature = 20,
                minTemperature = 10
            )
        )

        list.add(
            Room(
                title = "Study Room",
                image = R.drawable.ic_study_room,
                devices = devices3,
                temperature = 714,
                maxTemperature = 1000,
                minTemperature = 120
            )
        )

        list.add(
            Room(
                title = "Garage",
                image = R.drawable.ic_garage,
                devices = devices1,
                temperature = 33,
                maxTemperature = 50,
                minTemperature = 10
            )
        )

        list.add(
            Room(
                title = "Bathroom",
                image = R.drawable.ic_bathroom,
                devices = devices2,
                temperature = 240,
                maxTemperature = 250,
                minTemperature = 10
            )
        )

        list.add(
            Room(
                title = "Dining Room",
                image = R.drawable.ic_dining_room,
                devices = devices1,
                temperature = 4,
                maxTemperature = 30,
                minTemperature = 10
            )
        )
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        roomAdapter.onRoomClickListener = this
        binding?.rvRoom?.adapter = roomAdapter
        binding?.rvRoom?.layoutManager = layoutManager

        roomAdapter.updateItems(list)
    }

    override fun handleOnRoomClickListener(room: Room) {
        findNavController().navigate(R.id.roomFragment, Bundle().apply {
            putParcelable("Room", room)
        })
    }
}