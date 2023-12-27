package com.cious.learnhub.ui.detail.pagerfragment.viewitems

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import coil.load
import com.cious.learnhub.R
import com.cious.learnhub.databinding.BottomSheetPaymentBinding
import com.cious.learnhub.databinding.ItemChapterHeaderBinding
import com.cious.learnhub.databinding.ItemCourseChapterBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.model.Video
import com.cious.learnhub.ui.payment.detail.PaymentDetailActivity
import com.cious.learnhub.utils.toCurrencyFormat
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
    private val data: Video,
    private val context: Context,
    private val detailData: Course?,
    private val onItemClick: (Video) -> Unit
) : BindableItem<ItemCourseChapterBinding>() {

    val bottomSheetDialog = BottomSheetDialog(context)
    val binding = BottomSheetPaymentBinding.inflate(LayoutInflater.from(context))

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
            if (data.isLocked == false) {
                onItemClick.invoke(data)
            } else {
                showBottomSheet()
            }
        }
    }


    private fun showBottomSheet() {
        bindData()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        binding.btnContinueToDetail.setOnClickListener {
            PaymentDetailActivity.startActivity(context, detailData)
        }
    }

    private fun bindData() {
        bottomSheetDialog.setContentView(binding.root)
        binding.itemCourse.ivCourseImage.load(detailData?.imageUrl)
        binding.itemCourse.tvCourseCategory.text = detailData?.categoryName
        binding.itemCourse.tvCourseTitle.text = detailData?.title
        binding.itemCourse.tvCourseInstructor.text = detailData?.instructor
        binding.itemCourse.tvRating.text = detailData?.rating.toString()
        binding.itemCourse.tvLevel.text = detailData?.level
        binding.itemCourse.tvModuleCount.text = detailData?.moduleCount.toString()
        binding.itemCourse.tvTotalDuration.text = detailData?.totalDuration.toString()
        binding.itemCourse.tvPrice.text = detailData?.price?.toCurrencyFormat()
        bottomSheetDialog.show()
    }

    override fun getLayout(): Int = R.layout.item_course_chapter

    override fun initializeViewBinding(view: View): ItemCourseChapterBinding =
        ItemCourseChapterBinding.bind(view)
}