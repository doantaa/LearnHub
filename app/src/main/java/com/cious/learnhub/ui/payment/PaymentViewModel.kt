package com.cious.learnhub.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.PaymentRepository
import com.cious.learnhub.model.PaymentData
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val repository: PaymentRepository
) : ViewModel(){

    private val _paymentData = MutableLiveData<ResultWrapper<PaymentData>>()

    val paymentData: LiveData<ResultWrapper<PaymentData>>
        get() = _paymentData

    fun createPayment(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createPayment(id).collect(){
                _paymentData.postValue(it)
            }
        }
    }



}