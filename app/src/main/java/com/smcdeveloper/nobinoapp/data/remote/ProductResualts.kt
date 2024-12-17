package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider

sealed class ProductResualts (


    val message: String? = null,
    val data: MovieResult? = null
    )

    {
        class Success(message: String, data:MovieResult ) : ProductResualts(message,data)
        class Error(message: String, data: MovieResult? = null) : ProductResualts(message, data)
        class Loading : ProductResualts()
    }






