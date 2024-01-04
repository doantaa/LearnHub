package com.cious.learnhub.ui.historypayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.UserTransaction
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryPaymentViewModel (
    private val historyPayment: ProfileRepository
): ViewModel(){
    private val _transaction= MutableLiveData<ResultWrapper<List<UserTransaction>>>()
    val transaction:LiveData<ResultWrapper<List<UserTransaction>>>
        get() {
           return _transaction
        }
    fun getTransaction()
    {
        viewModelScope.launch(Dispatchers.IO) {
            historyPayment.getUserTransaction().collect{
                _transaction.postValue(it)
            }
        }

    }

}




