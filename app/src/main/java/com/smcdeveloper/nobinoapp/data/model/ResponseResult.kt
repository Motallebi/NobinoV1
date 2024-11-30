package com.smcdeveloper.nobinoapp.data.model

data class ResponseResult<T>
    (

    val userMessage : String ,
    val data : T ,
    val success: Boolean,
    val code:String,
    val developerMessage:String






            )
