package com.example.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.LatestRates
import com.example.models.SymbolsResponseData
import com.example.repositories.HomeRepository
import com.example.utils.Constants.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _ratesData: MutableLiveData<LatestRates?> = MutableLiveData()
    val ratesData: LiveData<LatestRates?> = _ratesData

    private val _symbolsData: MutableLiveData<SymbolsResponseData?> = MutableLiveData()
    val symbolsData: LiveData<SymbolsResponseData?> = _symbolsData

    private val _success: MutableLiveData<String?> = MutableLiveData()
    val success: LiveData<String?> = _success

    fun getSymbols(){
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = homeRepository.getSymbols(API_KEY)
                if (response.isSuccessful) {
                    val symbolsData = response.body()
                    if (symbolsData != null) {
                        _symbolsData.postValue(symbolsData)
                        _isLoading.postValue(false)
                    }
                    withContext(Dispatchers.Main) {
                        _success.postValue(null)
                        _isLoading.postValue(false)
                    }
                } else {
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.postValue(false)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getLatestRate(base: String, symbol: String){
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = homeRepository.getLatestRates(API_KEY, base = base, symbol= symbol)
                if (response.isSuccessful) {
                    val rates = response.body()
                    _ratesData.postValue(rates)
                    _isLoading.postValue(false)
                    withContext(Dispatchers.Main) {
                        _success.postValue(null)
                        _isLoading.postValue(false)
                    }
                } else {
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.postValue(false)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
