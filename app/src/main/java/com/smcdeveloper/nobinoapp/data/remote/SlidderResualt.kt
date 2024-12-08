package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider

sealed class  SlidderResualt(
    val message: String? = null,
    val data: Slider? = null
) {
    class Success(message: String, data:Slider) : SlidderResualt(message,data)
    class Error(message: String, data: Slider? = null) : SlidderResualt(message, data)
    class Loading : SlidderResualt()
}
