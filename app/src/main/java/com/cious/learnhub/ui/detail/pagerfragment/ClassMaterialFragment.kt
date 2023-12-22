package com.cious.learnhub.ui.detail.pagerfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cious.learnhub.databinding.FragmentClassMaterialBinding
import com.cious.learnhub.databinding.SheetProcessPaymentBinding
import com.cious.learnhub.ui.detail.CourseDetailActivity
import com.cious.learnhub.ui.detail.CourseDetailViewModel
import com.cious.learnhub.ui.detail.pagerfragment.viewitems.DataItem
import com.cious.learnhub.ui.detail.pagerfragment.viewitems.HeaderItem
import com.cious.learnhub.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ClassMaterialFragment : Fragment() {
    private val groupieAdapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    private val viewModel: CourseDetailViewModel by activityViewModel()

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
        invokeData()
        setData(requireContext())
    }

    private fun invokeData() {
        val id = viewModel.courseId ?: 0
        viewModel.getCourseById(id)
    }

    private fun setData(context: Context) {
        binding.rvData.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupieAdapter
        }

        viewModel.detailCourse.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { success ->
                    groupieAdapter.clear()
                    val modules = success.payload?.dataDetailResponse?.modules
                    val id = success.payload?.dataDetailResponse?.id ?: 0
                    modules?.forEach { module ->
                        val section = Section().apply {
                            setHeader(HeaderItem(module.title))
                            val contentList = module.videos.map { video ->
                                DataItem(video, context, id) { videoUrl ->
                                    viewModel.getVideoUrl(videoUrl.videoUrl)
                                }
                            }
                            addAll(contentList)
                        }
                        groupieAdapter.add(section)
                    }
                }
            )
        }
    }
}