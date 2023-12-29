package com.cious.learnhub.ui.detail.pagerfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cious.learnhub.databinding.FragmentClassMaterialBinding
import com.cious.learnhub.ui.detail.CourseDetailViewModel
import com.cious.learnhub.ui.detail.pagerfragment.viewitems.DataItem
import com.cious.learnhub.ui.detail.pagerfragment.viewitems.HeaderItem
import com.cious.learnhub.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.activityViewModel

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

        viewModel.enrollment.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { course ->
                    groupieAdapter.clear()
                    val modules = course.payload?.module
                    val courseData = course.payload
                    modules?.forEach { module ->
                        val section = Section().apply {
                            setHeader(HeaderItem(module.title))
                            val contentList = module.videos.map { video ->
                                DataItem(video, context, courseData) { videoUrl ->
                                    viewModel.getVideoUrl(videoUrl.videoUrl)
                                    viewModel.postProgress(videoUrl.id)
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