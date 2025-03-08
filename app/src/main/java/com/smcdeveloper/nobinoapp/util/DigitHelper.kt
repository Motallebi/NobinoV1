package com.smcdeveloper.nobinoapp.util

object DigitHelper {

    fun digitByLocate(englishStr: String): String {
        var result = ""
        var fa = '۰'


        for (ch in englishStr) {
            fa = ch
            when (ch) {
                '0' -> fa = '۰'
                '1' -> fa = '۱'
                '2' -> fa = '۲'
                '3' -> fa = '۳'
                '4' -> fa = '۴'
                '5' -> fa = '۵'
                '6' -> fa = '۶'
                '7' -> fa = '۷'
                '8' -> fa = '۸'
                '9' -> fa = '۹'
            }










            result = "${result}$fa"
        }
        return result
    }


    fun digitByLocateFaToEn(englishStr: String): String {
        var result = ""
        var en = '0'


        for (ch in englishStr) {
            en = ch
            when (ch) {
                '۰' -> en = '0'
                '۱' -> en = '1'
                '۲' -> en = '2'
                '۳' -> en = '3'
                '۴' -> en = '4'
                '۵' -> en = '5'
                '۶' -> en = '6'
                '۷' -> en = '7'
                '۸' -> en = '8'
                '۹' -> en = '9'
            }










            result = "${result}$en"
        }
        return result
    }


    fun showPartialPhone(phoneNumber:String):String
    {
       val length= phoneNumber.length

     return   phoneNumber.replaceRange(4,length-2,"*****")





    }






}