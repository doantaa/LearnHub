package com.cious.learnhub.ui.detail.pagerfragment.viewitems

import android.content.Context
import android.view.View
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.course.Video
import com.cious.learnhub.databinding.ItemChapterHeaderBinding
import com.cious.learnhub.databinding.ItemCourseChapterBinding
import com.xwray.groupie.viewbinding.BindableItem

class HeaderItem(private val title: String?) : BindableItem<ItemChapterHeaderBinding>() {

    override fun bind(viewBinding: ItemChapterHeaderBinding, position: Int) {
        viewBinding.tvTitleChapter.text = title
    }

    override fun getLayout(): Int = R.layout.item_chapter_header

    override fun initializeViewBinding(view: View): ItemChapterHeaderBinding =
        ItemChapterHeaderBinding.bind(view)
}

class DataItem(private val data: Video?, private val onItemClick: (Video) -> Unit) :
    BindableItem<ItemCourseChapterBinding>() {

    override fun bind(viewBinding: ItemCourseChapterBinding, position: Int) {
        viewBinding.tvTitleCourse.text = data?.title
        viewBinding.tvNumberCourse.text = data?.no.toString()
        viewBinding.root.setOnClickListener { onItemClick.invoke(data!!) }
    }

    override fun getLayout(): Int = R.layout.item_course_chapter

    override fun initializeViewBinding(view: View): ItemCourseChapterBinding =
        ItemCourseChapterBinding.bind(view)
}