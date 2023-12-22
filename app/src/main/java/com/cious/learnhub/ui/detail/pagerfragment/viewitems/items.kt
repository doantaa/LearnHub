package com.cious.learnhub.ui.detail.pagerfragment.viewitems

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.course.Video
import com.cious.learnhub.databinding.ItemChapterHeaderBinding
import com.cious.learnhub.databinding.ItemCourseChapterBinding
import com.cious.learnhub.databinding.SheetProcessPaymentBinding
import com.cious.learnhub.ui.detail.CourseDetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xwray.groupie.viewbinding.BindableItem

class HeaderItem(private val title: String?) : BindableItem<ItemChapterHeaderBinding>() {

    override fun bind(viewBinding: ItemChapterHeaderBinding, position: Int) {
        viewBinding.tvTitleChapter.text = title
    }

    override fun getLayout(): Int = R.layout.item_chapter_header

    override fun initializeViewBinding(view: View): ItemChapterHeaderBinding =
        ItemChapterHeaderBinding.bind(view)
}

class DataItem(
    private val data: Video?,
    private val context: Context,
    private val courseId: Int,
    private val onItemClick: (Video) -> Unit
) : BindableItem<ItemCourseChapterBinding>() {

    override fun bind(viewBinding: ItemCourseChapterBinding, position: Int) {
        viewBinding.tvTitleCourse.text = data?.title
        viewBinding.tvNumberCourse.text = data?.no.toString()
        viewBinding.ivButtonPlay.apply {
            if (data?.isLocked == false) {
                setImageResource(R.drawable.ic_play_course)
            } else {
                setImageResource(R.drawable.ic_lock)
            }
        }
        viewBinding.root.setOnClickListener {
            if (data?.isLocked == false) {
                onItemClick.invoke(data)
            } else {
                showBottomSheet(context)
            }
        }
    }

    private fun showBottomSheet(context:Context) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = View.inflate(context, R.layout.sheet_process_payment, null)
        bottomSheetDialog.setContentView(view)
        val processButton: Button = view.findViewById(R.id.btn_buy_now)
        processButton.setOnClickListener {
            Toast.makeText(context, courseId.toString(), Toast.LENGTH_SHORT).show()
        }
        bottomSheetDialog.show()
    }

    override fun getLayout(): Int = R.layout.item_course_chapter

    override fun initializeViewBinding(view: View): ItemCourseChapterBinding =
        ItemCourseChapterBinding.bind(view)
}