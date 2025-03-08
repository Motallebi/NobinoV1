package com.smcdeveloper.nobinoapp.util

import com.smcdeveloper.nobinoapp.util.Constants.USER_TOKEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AppConfigManager {

    // Holds the user token as an observable state
    private val _userToken = MutableStateFlow<String?>(null)
    val userToken: StateFlow<String?> = _userToken

    // Call this method when you validate the user and get a new token.
    fun updateToken(newToken: String) {
        _userToken.value = newToken
        USER_TOKEN =newToken
        // If you have remote config, you can trigger a refresh here as well.
        // e.g., FirebaseRemoteConfig.getInstance().fetchAndActivate()
    }


















}