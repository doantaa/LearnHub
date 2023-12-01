package com.cious.learnhub.ui.myclass.viewitems

import android.content.Context
import android.view.View
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ItemChapterHeaderBinding
import com.cious.learnhub.databinding.ItemCourseChapterBinding
import com.xwray.groupie.viewbinding.BindableItem

class HeaderItem(private val context: Context, private val title: String, private val onHeaderClick: (item: String) -> Unit) :
    BindableItem<ItemChapterHeaderBinding>() {

    override fun bind(viewBinding: ItemChapterHeaderBinding, position: Int) {
        viewBinding.tvTitleChapter.text = title
        viewBinding.root.setOnClickListener { onHeaderClick.invoke(title) }
    }

    override fun getLayout(): Int = R.layout.item_chapter_header

    override fun initializeViewBinding(view: View): ItemChapterHeaderBinding =
        ItemChapterHeaderBinding.bind(view)
}

class DataItem(private val context: Context, private val data: String, private val onItemClick: (item: String) -> Unit) :
    BindableItem<ItemCourseChapterBinding>() {

    override fun bind(viewBinding: ItemCourseChapterBinding, position: Int) {
        viewBinding.tvTitleCourse.text = data
        viewBinding.root.setOnClickListener { onItemClick.invoke(data) }
    }

    override fun getLayout(): Int = R.layout.item_course_chapter

    override fun initializeViewBinding(view: View): ItemCourseChapterBinding =
        ItemCourseChapterBinding.bind(view)
}