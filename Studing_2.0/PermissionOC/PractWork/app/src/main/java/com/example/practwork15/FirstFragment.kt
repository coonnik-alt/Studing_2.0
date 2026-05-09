package com.example.practwork15

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practwork15.databinding.FragmentFirstBinding
import com.example.practwork15.databinding.FragmentSecondBinding
import java.util.concurrent.Executor
import kotlin.getValue

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    private lateinit var db : DataBaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DataBaseHelper(requireContext())
        adapter = Adapter(emptyList())

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerView.adapter = adapter

        loadPhotos()

        binding.button.setOnClickListener {
            Toast.makeText(requireContext(), "переход", Toast.LENGTH_SHORT).show()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, SecondFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()

        if (::db.isInitialized && ::adapter.isInitialized) {
            loadPhotos()
        }
    }

    private fun loadPhotos () {
        val items = db.getAllItems()
        adapter.updateItems(items)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}