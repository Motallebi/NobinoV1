package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.plans.Plan
import com.smcdeveloper.nobinoapp.data.model.plans.Plans
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface SubsCriptionInterface {



    @GET("api/plans")
    suspend fun getSubscriptionPlans(

    ): Response<Plans>




    @GET("api/plans/{id}")
    suspend fun getSubscriptionPlan(
       @Path("id") planId : String


    ): Response<Plan>






}