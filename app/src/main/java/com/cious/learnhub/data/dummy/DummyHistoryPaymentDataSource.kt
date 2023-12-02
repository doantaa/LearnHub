package com.cious.learnhub.data.dummy

import com.cious.learnhub.model.HistoryPayment


interface DummyHistoryPaymentDataSource {
    fun getListPayment(): List<HistoryPayment>
}

class DummyHistoryPaymentDataSourceImpl() : DummyHistoryPaymentDataSource {
    override fun getListPayment(): List<HistoryPayment> = listOf(
        HistoryPayment(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            author = "Maman",
            rating = 4.8,
            level = "Intermediate Level",
            modul = "11 Modul",
            duration = "120 Menit"
        ),
        HistoryPayment(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            author = "Jack",
            rating = 4.3,
            level = "Advanced Level",
            modul = "8 Modul",
            duration = "60 Menit"
        ),
        HistoryPayment(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            author = "Maman",
            rating = 4.5,
            level = "Intermediate Level",
            modul = "11 Modul",
            duration = "120 Menit"
        )
    )
}