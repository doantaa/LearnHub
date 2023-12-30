package com.cious.learnhub.ui.notificationdetailpopup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentNotificationDetailBottomSheetBinding
import com.cious.learnhub.model.NotificationModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val ARG_PARAM_NOTIF = "ARG_PARAM_NOTIF"

class NotificationDetailBottomSheet : BottomSheetDialogFragment() {
    private var notif: NotificationModel? = null
    private lateinit var binding: FragmentNotificationDetailBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            notif = it.getParcelable(ARG_PARAM_NOTIF)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationDetailBottomSheetBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), notif.toString(), Toast.LENGTH_SHORT).show()
        //hit api untuk nandain kalo notif itu udah dibaca
    }

    companion object {
        @JvmStatic
        fun newInstance(notif: NotificationModel) =
            NotificationDetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_NOTIF, notif)
                }
            }
    }
}