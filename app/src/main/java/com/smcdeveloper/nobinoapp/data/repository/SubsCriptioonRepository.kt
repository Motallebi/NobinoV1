package com.smcdeveloper.nobinoapp.data.repository

import android.util.Log
import com.smcdeveloper.nobinoapp.data.model.plans.Plan
import com.smcdeveloper.nobinoapp.data.model.plans.Plans
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.remote.SubsCriptionInterface
import javax.inject.Inject

class SubsCriptioonRepository @Inject constructor(private val api:SubsCriptionInterface  ):BaseApiResponse2() {



  suspend fun showPlans(): NetworkResult<Plans> =

      safeApiCall {


          api.getSubscriptionPlans()


      }



    suspend fun showPlanDetails1(id:String):NetworkResult<Plan> =

        safeApiCall {

            api.getSubscriptionPlan(id)


        }



    suspend fun showPlanDetails(id: String): NetworkResult<Plan> {
        Log.d("showPlanDetails", "Fetching plan details for ID: $id") // Log request start

        return safeApiCall {
            try {
                val response = api.getSubscriptionPlan(id)
                Log.d("showPlanDetails", "Response received: ${response.body()}") // Log successful response
                response
            }

            catch (e: Exception) {
                Log.e("showPlanDetails", "Error fetching plan details", e) // Log errors
                throw e
            }
        }
    }










}