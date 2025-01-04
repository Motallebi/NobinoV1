package com.smcdeveloper.nobinoapp.util

import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult

data class MovieDisplayData(
    val movieCat: MovieCat.MovieCatData,
    val movieResult: MovieResult,
    val movieItems: List<MovieResult.DataMovie.Item?>


)
