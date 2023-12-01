package com.cious.learnhub.ui.myclass.detail.pagerfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentClassMaterialBinding
import com.cious.learnhub.databinding.FragmentCourseDetailBinding
import com.cious.learnhub.ui.myclass.viewitems.DataItem
import com.cious.learnhub.ui.myclass.viewitems.HeaderItem
import com.cious.model.SectionedData
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

class ClassMaterialFragment : Fragment() {
    private val adapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    private lateinit var binding: FragmentClassMaterialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClassMaterialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        binding.rvData.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ClassMaterialFragment.adapter
        }

        val section = getListData().map {
            val section = Section()
            section.setHeader(HeaderItem(requireContext(), it.name) { data ->
                Toast.makeText(requireContext(), "Header Clicked : $data", Toast.LENGTH_SHORT).show()
            })
            val dataSection = it.data.map { data ->
                DataItem(requireContext(), data) { data ->
                    Toast.makeText(requireContext(), "Item Clicked : $data", Toast.LENGTH_SHORT).show()
                }
            }
            section.addAll(dataSection)
            section
        }
        adapter.addAll(section)
    }

    private fun getListData(): List<SectionedData> = listOf(
        SectionedData("Chapter 1", listOf("Pendahuluan 1", "Pendahuluan 2", "Pendahuluan 3")),
        SectionedData("Chapter 2", listOf("Isian 1", "Isian 2", "Isian 3")),
        SectionedData("Chapter 3", listOf("Final Chap 1", "Final Chap 1", "Final Chap 1")),
    )
}