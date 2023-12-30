package com.cious.learnhub.ui.notificationdetailpopup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.databinding.FragmentNotificationDetailBottomSheetBinding
import com.cious.learnhub.model.NotificationModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


private const val ARG_PARAM_NOTIF = "ARG_PARAM_NOTIF"

class NotificationDetailBottomSheet : AppCompatActivity() {
    private var notif: NotificationModel? = null
    private val binding: FragmentNotificationDetailBottomSheetBinding by lazy {
        FragmentNotificationDetailBottomSheetBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel:NotificationDetailViewModel by viewModel { parametersOf(intent?.extras)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        arguments?.let {
//            notif = it.getParcelable(ARG_PARAM_NOTIF)
//        }
        bindData(viewModel.notification)
    }

    private fun bindData(notification: NotificationModel?) {
        notification?.let { item->
            binding.tvTitle.text=item.title
            binding.tvDescription.text=item.description
            binding.tvDatetime.text=item.datetime
            binding.tvCategory.text=item.category
        }
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentNotificationDetailBottomSheetBinding.inflate(
//            inflater, container, false
//        )
//        return binding.root
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Toast.makeText(requireContext(), notif.toString(), Toast.LENGTH_SHORT).show()
//        //hit api untuk nandain kalo notif itu udah dibaca
//    }

    companion object {
        const val EXTRA_NOTIFICATION = "EXTRA_NOTIFICATION"
        fun startActivity(context: Context, notification : NotificationModel) {
            val intent = Intent(context, NotificationDetailBottomSheet::class.java)
            intent.putExtra(EXTRA_NOTIFICATION, notification)
            context.startActivity(intent)
        }

//        @JvmStatic
//        fun newInstance(notif: NotificationModel) =
//            NotificationDetailBottomSheet().apply {
//                arguments = Bundle().apply {
//                    putParcelable(ARG_PARAM_NOTIF, notif)
//                }
//            }
    }
}