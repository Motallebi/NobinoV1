package com.smcdeveloper.nobinoapp.data.repository

import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.model.search.Genre
import com.smcdeveloper.nobinoapp.data.model.search.Person
import com.smcdeveloper.nobinoapp.data.model.search.PersonInfo
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import com.smcdeveloper.nobinoapp.data.remote.BaseApiResponse2
import com.smcdeveloper.nobinoapp.data.remote.HomeApiInterface
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class FilterRepository @Inject constructor(private val api:HomeApiInterface):BaseApiResponse2() {



   suspend fun fetchActors(name:String):NetworkResult<Person> =

       safeApiCall {

          api.fetchActors(name=name) // 🔴 API returns `Person`



         //  response.personInfo // 🔴 Extract only the list of `PersonInfo`



       }



    suspend fun fetchGenres() :NetworkResult<Genre> =
        safeApiCall {

            api.gerGenres()

        }


    suspend fun getCountries():NetworkResult<Countries> =

        safeApiCall {

            api.fetchContries()


        }
















    suspend fun getSlider(): NetworkResult<Slider> =

        safeApiCall {

            api.getSlider()


        }











    }







