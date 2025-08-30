package com.smcdeveloper.nobinoapp.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.smcdeveloper.nobinoapp.data.repository.ProductDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(val repository: ProductDetailsRepository):ViewModel() {


    private val _currentVideoUri = MutableStateFlow<Uri?>(null)
    val currentVideoUri: StateFlow<Uri?> = _currentVideoUri.asStateFlow()


    private val _videoState = MutableStateFlow<VideoState>(VideoState.Loading)
    val videoState: StateFlow<VideoState> = _videoState




    // Attach listener to ExoPlayer
    fun attachPlayer(exoPlayer: ExoPlayer) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> _videoState.value = VideoState.Buffering
                    Player.STATE_READY -> {
                        if (exoPlayer.isPlaying) {
                            // If already in ad → keep ad state
                            if (_videoState.value !is VideoState.PlayingAd) {
                                _videoState.value = VideoState.PlayingMain
                            }
                        } else {
                            _videoState.value = VideoState.Paused
                        }
                    }
                    Player.STATE_ENDED -> {
                        _videoState.value = VideoState.Completed
                    }


                    Player.STATE_IDLE -> {
                       // TODO()
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                _videoState.value = VideoState.Error(error.message ?: "Unknown error")
            }
        })
    }







































    // Example Media Items (replace with your actual ad and content)
    private val adUri =
        Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4") // Example ad URL
    private val contentUri =
        Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") // Example content URL


    // **Ad State Management**
    private val _isAdPlaying = mutableStateOf(false)
    val isAdPlaying: State<Boolean> = _isAdPlaying

    private val _isAdSkippable = mutableStateOf(false)
    val isAdSkippable: State<Boolean> = _isAdSkippable

    private val _isAdSkipped= mutableStateOf(false)
    val isAdSkipped: State<Boolean> = _isAdSkipped

    private val _autoSkipAdd= mutableStateOf(false)
    val autoSkipAdd: State<Boolean> = _autoSkipAdd



    suspend fun getAdsLink(productId: Int): Uri {

        val result = repository.getProductAdv(productId)
        val link=result.data?.advertie?.fileUrl.toString().toUri()
        Log.d("VideoPlayer", "Playing Ad → url=$link")
        return link



    }

    private suspend fun getVideoLink(productId: Int): Uri {

        val result = repository.getProductDetails(productId)

        val link= result.data?.data?.videoLink.toString().toUri()


        Log.d("VideoPlayer", "Video Link→ url=$link")
        Log.d("VideoPlayer", "product id → url=$productId")

        return link


    }

    fun loadContent(productId: Int) {

        viewModelScope.launch {
            _currentVideoUri.value = getVideoLink(productId)
            _videoState.value = VideoState.Loading

        }


    }

    fun startAd(productId: Int) {
        viewModelScope.launch {
            _currentVideoUri.value = getAdsLink(productId)
            Log.d("VideoPlayer", "Playing Ad → url=$_currentVideoUri.value ")

            var totalAddTime=12
            var timeLeft = 12
            var timePass=0
            while (totalAddTime-timePass > 7) {
                _videoState.value = VideoState.PlayingAd(
                    remainingTime = 5-timePass,
                    skippable = false
                )
                delay(1000)
                timePass++
            }

            // Skippable state
            _videoState.value = VideoState.PlayingAd(
                remainingTime = totalAddTime-timePass,
                skippable = true
            )

            // Auto skip after countdown
            if(isAdSkipped.value)
            loadContent(productId)
            else if(autoSkipAdd.value)
                loadContent(productId)
        }
    }








    fun onAdStarted() {
        _isAdPlaying.value = true
        _isAdSkippable.value = false
    }

    fun onContentStarted() {
        _isAdPlaying.value = false
        _isAdSkippable.value = false
    }


    fun skipCurrentAd() {
        if (_isAdSkippable.value) {
            // In a real implementation with a playlist (ConcatenatingMediaSource),
            // you would advance to the next item or remove the ad.
            // For this basic setup, you would typically load the main content again
            // if an ad was playing before it.
            // However, since `loadMainContent` requires a productId, you'd need
            // to store the productId if you want to jump back to it after an ad.
            // For now, let's just clear the ad state.
            // You should replace this with actual ExoPlayer playlist control.
            _isAdPlaying.value = false
            _isAdSkippable.value = false
        }


    }

    fun autoSkipAdd()
    {
        _autoSkipAdd.value=true



    }




    fun skipAd(productId: Int) {
        _isAdSkipped.value=true
        loadContent(productId)
    }


}





sealed class VideoState {
    object Loading : VideoState()
    data class PlayingAd(val remainingTime: Int, val skippable: Boolean) : VideoState()
    object PlayingMain : VideoState()
    object Paused : VideoState()
    object Buffering : VideoState()
    data class Error(val message: String) : VideoState()
    object Completed : VideoState()
}
