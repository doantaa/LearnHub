package com.cious.learnhub.utils

import com.cious.learnhub.R
import com.cious.model.HistoryPayment

object DataDummy{
    private val nameCourse = arrayOf(
        "UI/UX Design",
        "UI/UX Design",
        "UI/UX Design"
    )

    private val rating = arrayOf(
        "4.7",
        "4.8",
        "4.4"
    )

    private val titleCourse = arrayOf(
        "Belajar Web Designer dengan Figma",
        "Membuat Wireframe Hingga ke Visual Design",
        "Pengalaman tentang Design System"
    )

    private val authorCourse = arrayOf(
        "Angela Doe",
        "Angela Doe",
        "Angela Doe"
    )

    private val levelCourse = arrayOf(
        "Intermediate Level",
        "Intermediate Level",
        "Advanced Level"
    )

    private val modulCourse = arrayOf(
        "10 Modul",
        "5 Modul",
        "10 Modul"
    )

    private val duration = arrayOf(
        "120 Menit",
        "60 Menit",
        "120 Menit"
    )

    private val imageCourse = arrayOf(
        R.drawable.ic_example_history_payment,
        R.drawable.ic_example_history_payment,
        R.drawable.ic_example_history_payment
    )

    private val statusPayment = arrayOf(
        R.drawable.iv_waiting_payment,
        R.drawable.iv_payment_success,
        R.drawable.iv_payment_success
    )

    val listData : ArrayList<HistoryPayment>
        get(){
            val list = arrayListOf<HistoryPayment>()
            for (position in nameCourse.indices) {
                list.add(
                    HistoryPayment(
                        nameCourse[position],
                        rating[position],
                        titleCourse[position],
                        authorCourse[position],
                        levelCourse[position],
                        modulCourse[position],
                        duration[position],
                        imageCourse[position],
                        statusPayment[position]
                    )
                )
            }
            return list
        }
}