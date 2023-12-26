package com.cious.learnhub.ui.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.cious.learnhub.databinding.ActivityPaymentDetailBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.proceedWhen
import com.cious.learnhub.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentDetailActivity : AppCompatActivity() {

    private val binding: ActivityPaymentDetailBinding by lazy {
        ActivityPaymentDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: PaymentViewModel by viewModel {
        parametersOf(intent?.extras)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
        bindCourseData()
        observePaymentSendStatus()
    }

    private fun observePaymentSendStatus() {
        viewModel.paymentData.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    navigateToPaymentMidtrans(it.payload?.redirectUrl.orEmpty())
                }
            )
        }
    }

    private fun bindCourseData() {
        Log.d("INI EXTRA", viewModel.extraCourse.toString())
        viewModel.extraCourse.apply {
            binding.incPaymentDetail.ivCourseImage.load(this?.imageUrl)
            binding.incPaymentDetail.tvCourseCategory.text = this?.categoryName
            binding.incPaymentDetail.tvCourseTitle.text = this?.title
            binding.incPaymentDetail.tvCourseInstructor.text = this?.instructor
            binding.incPaymentDetail.tvCoursePrice.text = this?.price?.toCurrencyFormat()
            binding.incPaymentDetail.tvCourseTotalPrice.text = this?.price?.toCurrencyFormat()
        }
    }

    private fun setClickListeners() {
        binding.btnBuyNow.setOnClickListener {
            val courseId = viewModel.extraCourse?.id ?: 0
            viewModel.createPayment(courseId)
        }
    }

    private fun navigateToPaymentMidtrans(url: String) {
        val intent = Intent(this, PaymentMidtransActivity::class.java)
        intent.putExtra("URL", url)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_COURSE = "EXTRA_COURSE"
        const val COURSE = "COURSE"
        fun startActivity(context: Context, course: Course?) {
            val intent = Intent(context, PaymentDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(COURSE, course)
            intent.putExtra(EXTRA_COURSE, bundle)
            context.startActivity(intent)
        }
    }
}