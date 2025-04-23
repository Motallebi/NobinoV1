package com.smcdeveloper.nobinoapp.data.remote

import com.smcdeveloper.nobinoapp.data.model.prducts.Delimiter
import com.smcdeveloper.nobinoapp.data.model.prducts.KidsBanner
import com.smcdeveloper.nobinoapp.data.model.prducts.KidsBannerPics
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieCat
import com.smcdeveloper.nobinoapp.data.model.prducts.MovieResult
import com.smcdeveloper.nobinoapp.data.model.prducts.SpecialBanner

import com.smcdeveloper.nobinoapp.data.model.search.Countries
import com.smcdeveloper.nobinoapp.data.model.search.CountryInfo
import com.smcdeveloper.nobinoapp.data.model.search.Genre
import com.smcdeveloper.nobinoapp.data.model.search.Person
import com.smcdeveloper.nobinoapp.data.model.search.PersonInfo
import com.smcdeveloper.nobinoapp.data.model.sliders.Slider
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApiInterface {

    @GET("api/products")
    suspend fun getMovies(): Response<MovieResult>


    @GET("api/products")
    suspend fun getMoviesByQueryParams(
        @Query("size") size: Int = 10,
        @Query("category") category: String = "SERIES",
        @Query("tags") tags: String = "",
        @Query("offset") offset: Int = 0,
        @Header("Authorization") token: String? = null, // Optional header
        @Header("Profile-Id") profileId: String? = null // Optional header

        ): Response<MovieResult>


    @GET("api/products")
    suspend fun getMoviesWithPages(
        @Query("size") size: Int = 20,
        @Query("category") category: String = "",
        @Query("tags") tags: String = "",
        @Query("offset") offset: Int = 18,
        @Query("countries") countries: String = "",
        @Query("name") name: String = "",
       // @Header("Authorization") token: String? = null, // Optional header
       // @Header("Profile-Id") profileId: String? = null // Optional header





    ): Response<MovieResult>




    @GET("api/products")
    suspend fun getMoviesForKids(
        @Query("size") size: Int = 20,
        @Query("category") category: String = "",
        @Query("tags") tags: List<String?> = emptyList(),
        @Query("offset") offset: Int = 18,
        @Query("countries") countries: String = "",
        @Query("name") name: String = ""

    ): Response<MovieResult>








    @GET("api/products")
    suspend fun getMoviesWithPagesTest(
        @Query("size") size: Int = 20,
        @Query("category") category: String = "",
        @Query("tags") tags: List<String> = emptyList(),
        @Query("offset") offset: Int = 0,
    ): Response<MovieResult>


    @GET("api/settings/mainPage/sliders")
    suspend fun getSlider(): Response<Slider>


    @GET("api/settings/customPage/sliders/{id}")
    suspend fun getSliderById(
        @Path("id") id:String


    ): Response<Slider>















    @GET("movies/{id}")
    suspend fun fetchMovieDetails(
        @Path("id") id: Int
    ): Response<MovieResult>

    @GET("api/settings/mainPage/queries/{id}")
    suspend fun fetchMovieTags(
        @Path("id") id: Int
    ): Response<MovieCat>


    @GET("/api/v2/persons")
    suspend fun fetchActors(
        @Query("name") name: String = ""
    ): Response<Person>

    @GET("/api/v2/persons/{id}")
    suspend fun fetchActor(
        @Path("id") id: Int
    ): Response<PersonInfo>

    @GET("/api/countries")
    suspend fun fetchContries(

    ): Response<Countries>

    @GET("/api/countries/{id}")
    suspend fun fetchContry(
        @Path("id") id: Int
    ): Response<CountryInfo>


    @GET("/api/settings/mainPage/bigDelimiters/{id}")
    suspend fun getKidsBanners(
      @Path("id") id :Int


    ):Response<KidsBanner>




    @GET("/api/settings/mainPage/picMenus")
    suspend fun getKidsPics(



    ):Response<KidsBannerPics>











    @GET("api/settings/mainPage/normalDelimiters/{id}")
    suspend fun getDelimiter(
        @Path("id") id :Int


    ):Response<Delimiter>








    @GET("api/settings/mainPage/specials")
    suspend fun getSpecialBanner(
        @Query("type") type :String


    ):Response<SpecialBanner>




    @GET("api/products/relatedProducts/{id}")
    suspend fun getSeriesEpisodes(
        @Path("id") id: Int) :Response<MovieResult>











    @GET("/api/tags")
    suspend fun gerGenres(): Response<Genre>


}






















