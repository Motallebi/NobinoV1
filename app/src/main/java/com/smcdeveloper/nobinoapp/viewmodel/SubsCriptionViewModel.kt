package com.smcdeveloper.nobinoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smcdeveloper.nobinoapp.data.model.plans.Plan
import com.smcdeveloper.nobinoapp.data.model.plans.Plans
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.SubsCriptioonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubsCriptionViewModel @Inject constructor(private val repository: SubsCriptioonRepository):ViewModel()


{


    private val _plans = MutableStateFlow<NetworkResult<Plans>>(NetworkResult.Loading())
    val plans: StateFlow<NetworkResult<Plans>> get() = _plans.asStateFlow()

    private val _isLoading = MutableStateFlow(false) // 🔴 Loading state
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _currentPlan = MutableStateFlow<NetworkResult<Plan>>(NetworkResult.Loading())
    val currentPlan: StateFlow<NetworkResult<Plan>> get() = _currentPlan.asStateFlow()









    fun fetchCurrentSubsCription() {
        viewModelScope.launch {
            _isLoading.value = true
            val data = repository.showPlans()
            _plans.value = data
            _isLoading.value = false
        }
    }


    fun fetchCurrentSubsCriptionPlan(id:String) {
        viewModelScope.launch {
            _isLoading.value = true
            val data = repository.showPlanDetails(id)
            Log.d("plan","Plan data ")
            _currentPlan.value = data
            _isLoading.value = false
        }
    }




























}