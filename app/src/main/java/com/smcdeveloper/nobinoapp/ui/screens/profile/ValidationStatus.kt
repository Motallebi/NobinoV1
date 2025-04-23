package com.smcdeveloper.nobinoapp.ui.screens.profile

sealed class ValidationStatus {

    object Default : ValidationStatus()
    object Success : ValidationStatus()
    object Error : ValidationStatus()



}