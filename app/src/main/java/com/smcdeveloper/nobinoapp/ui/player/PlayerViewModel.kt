package com.smcdeveloper.nobinoapp.ui.player

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.Surface
import androidx.annotation.OptIn
import androidx.collection.emptyLongSet
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.TrackGroup
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.data.repository.ProductDetailsRepository
import com.smcdeveloper.nobinoapp.viewmodel.VideoState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val repository: ProductDetailsRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _playerUiModel = MutableStateFlow(PlayerUiModel())
    val playerUiModel: StateFlow<PlayerUiModel> = _playerUiModel.asStateFlow()


    private val _currentVideoUri = MutableStateFlow<NetworkResult<String>>(NetworkResult.Loading())
    val currentVideoUri: StateFlow<NetworkResult<String>> = _currentVideoUri.asStateFlow()

    private val _currentVideoUri1 = MutableStateFlow<String>("")
    val currentVideoUri1: StateFlow<String> = _currentVideoUri1.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val playerCoroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    private var positionTrackingJob: Job? = null

    private var selectedVideoTrack: VideoTrack = VideoTrack.AUTO
    private var selectedAudioTrack: AudioTrack = AudioTrack.AUTO
    private var selectedSubtitleTrack: SubtitleTrack = SubtitleTrack.AUTO
    private var selectedSpeedTrack: SpeedTrack = SpeedTrack.AUTO

    private var videoTracksMap: Map<VideoTrack, ExoPlayerTrack?> = emptyMap()
    private var audioTracksMap: Map<AudioTrack, ExoPlayerTrack?> = emptyMap()
    private var subtitleTracksMap: Map<SubtitleTrack, ExoPlayerTrack?> = emptyMap()


    private class ExoPlayerTrack(
        val trackGroup: TrackGroup,
        val trackIndexInGroup: Int,
    )


    private val playerEventListener: Player.Listener = object : Player.Listener {

        override fun onVideoSizeChanged(videoSize: VideoSize) {
            super.onVideoSizeChanged(videoSize)
            if (videoSize !== VideoSize.UNKNOWN) {
                val videoWidth = videoSize.width
                val videoHeight = videoSize.height / videoSize.pixelWidthHeightRatio
                val videoAspectRatio = videoWidth / videoHeight
                _playerUiModel.value = _playerUiModel.value.copy(
                    videoAspectRatio = videoAspectRatio
                )
            }


        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            val state = when (playbackState) {


                Player.STATE_BUFFERING -> PlaybackState.BUFFERING
                Player.STATE_READY -> {
                    if (exoPlayer.playWhenReady) {


                        PlaybackState.PLAYING
                    } else
                        PlaybackState.PAUSED


                }


                Player.STATE_ENDED -> PlaybackState.COMPLETED
                Player.STATE_IDLE -> {
                    if (exoPlayer.playerError != null) {
                        PlaybackState.ERROR

                    } else
                        PlaybackState.IDLE


                }

                else -> PlaybackState.IDLE


            }
            _playerUiModel.value = _playerUiModel.value.copy(playbackState = state)

            when (playbackState) {
                Player.STATE_READY -> {

                    startTrackingPlayBackPosition()


                }

                else -> {
                    stopTrackingPlayBackPosition()

                }


            }


        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) {
                _playerUiModel.value = _playerUiModel.value.copy(

                    playbackState = PlaybackState.PLAYING

                )


            } else if (exoPlayer.playbackState == Player.STATE_READY) {
                _playerUiModel.value = _playerUiModel.value.copy(
                    playbackState = PlaybackState.PAUSED
                )


            }


        }

        @OptIn(UnstableApi::class)
        override fun onTracksChanged(tracks: Tracks) {

            val newVideoTracks = mutableMapOf<VideoTrack, ExoPlayerTrack?>(
                VideoTrack.AUTO to null
            )
            val newAudioTracks = mutableMapOf<AudioTrack, ExoPlayerTrack?>(
                AudioTrack.AUTO to null,
                AudioTrack.NONE to null
            )
            val newSubtitleTracks = mutableMapOf<SubtitleTrack, ExoPlayerTrack?>(
                SubtitleTrack.AUTO to null,
                SubtitleTrack.NONE to null
            )















            tracks.groups.forEach {

                when (it.type) {
                    C.TRACK_TYPE_AUDIO -> {

                        newAudioTracks.putAll(extractAudioTracks(it))


                    }

                    C.TRACK_TYPE_VIDEO -> {

                        newVideoTracks.putAll(extractVideoTracks(it))


                    }


                    C.TRACK_TYPE_TEXT -> {

                        newSubtitleTracks.putAll(extractSubtitleTracks(it))


                    }


                    else -> {}


                }


            }


            videoTracksMap = newVideoTracks
            audioTracksMap = newAudioTracks
            subtitleTracksMap = newSubtitleTracks

            _playerUiModel.value = _playerUiModel.value.copy(
                trackSelectionUiModel = TrackSelectionUiModel(
                    selectedVideoTrack = selectedVideoTrack,
                    videoTracks = videoTracksMap.keys.toList(),
                    selectedAudioTrack = selectedAudioTrack,
                    audioTracks = audioTracksMap.keys.toList(),
                    selectedSubtitleTrack = selectedSubtitleTrack,
                    subtitleTracks = subtitleTracksMap.keys.toList(),
                    selectedSpeedTrack = selectedSpeedTrack,
                    speedTracks = listOf(
                        SpeedTrack(0.5f),
                        SpeedTrack(0.75f),
                        SpeedTrack(1.0f),
                        SpeedTrack(1.25f),
                        SpeedTrack(1.5f)


                    )
                )

            )


        }


    }


    @OptIn(UnstableApi::class)
    private fun extractAudioTracks(info: Tracks.Group): Map<AudioTrack, ExoPlayerTrack> {
        val result = mutableMapOf<AudioTrack, ExoPlayerTrack>()
        for (trackIndex in 0 until info.mediaTrackGroup.length) {
            if (info.isTrackSupported(trackIndex)) {
                val format = info.mediaTrackGroup.getFormat(trackIndex)
                val language = format.language
                if (language != null) {
                    val audioTrack = AudioTrack(language)
                    result[audioTrack] = ExoPlayerTrack(
                        trackGroup = info.mediaTrackGroup,
                        trackIndexInGroup = trackIndex
                    )
                }
            }
        }
        return result
    }


    @OptIn(UnstableApi::class)
    private fun extractVideoTracks(info: Tracks.Group): Map<VideoTrack, ExoPlayerTrack> {
        val result = mutableMapOf<VideoTrack, ExoPlayerTrack>()
        for (trackIndex in 0 until info.mediaTrackGroup.length) {
            if (info.isTrackSupported(trackIndex)) {
                val format = info.mediaTrackGroup.getFormat(trackIndex)
                val width = format.width
                val height = format.height
                val videoTrack = VideoTrack(width, height)
                result[videoTrack] = ExoPlayerTrack(
                    trackGroup = info.mediaTrackGroup,
                    trackIndexInGroup = trackIndex
                )
            }
        }
        return result
    }


    @OptIn(UnstableApi::class)
    private fun extractSubtitleTracks(info: Tracks.Group): Map<SubtitleTrack, ExoPlayerTrack> {
        val result = mutableMapOf<SubtitleTrack, ExoPlayerTrack>()
        for (trackIndex in 0 until info.mediaTrackGroup.length) {
            if (info.isTrackSupported(trackIndex)) {
                val format = info.mediaTrackGroup.getFormat(trackIndex)
                val language = format.language
                if (language != null) {
                    val subtitleTrack = SubtitleTrack(language)
                    result[subtitleTrack] = ExoPlayerTrack(
                        trackGroup = info.mediaTrackGroup,
                        trackIndexInGroup = trackIndex
                    )
                }
            }
        }
        return result
    }

    private fun setVideoTrack(track: VideoTrack) {
        val selectionBuilder = exoPlayer.trackSelectionParameters.buildUpon()
            .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
        when {
            track == VideoTrack.AUTO -> {
                selectedVideoTrack = track
            }

            else -> {
                val exoVideoTrack = videoTracksMap[track]
                if (exoVideoTrack != null) {
                    selectionBuilder.setOverrideForType(
                        TrackSelectionOverride(
                            exoVideoTrack.trackGroup,
                            listOf(exoVideoTrack.trackIndexInGroup)
                        )
                    )
                    selectedVideoTrack = track
                }
            }
        }
        exoPlayer.trackSelectionParameters = selectionBuilder.build()
        _playerUiModel.value = _playerUiModel.value.copy(
            trackSelectionUiModel = _playerUiModel.value.trackSelectionUiModel?.copy(
                selectedVideoTrack = track
            )
        )
    }

    private fun setPlaybackSpeed(speed: Float) {

        Log.d("VideoPlayer", "setPlaybackSpeed $speed")

        exoPlayer.playbackParameters = PlaybackParameters(speed)
        _playerUiModel.value = _playerUiModel.value.copy(
            trackSelectionUiModel = _playerUiModel.value.trackSelectionUiModel?.copy(
                selectedSpeedTrack = SpeedTrack(speed)
            )
        )


    }


    private fun setAudioTrack(track: AudioTrack) {
        val trackDisabled = track == AudioTrack.NONE
        val selectionBuilder = exoPlayer.trackSelectionParameters.buildUpon()
            .clearOverridesOfType(C.TRACK_TYPE_AUDIO)
            .setTrackTypeDisabled(C.TRACK_TYPE_AUDIO, trackDisabled)
        when {
            track === AudioTrack.AUTO || track === AudioTrack.NONE -> {
                selectedAudioTrack = track
            }

            else -> {
                val exoAudioTrack = audioTracksMap[track]
                if (exoAudioTrack != null) {
                    selectionBuilder.setOverrideForType(
                        TrackSelectionOverride(
                            exoAudioTrack.trackGroup,
                            listOf(exoAudioTrack.trackIndexInGroup)
                        )
                    )
                    selectedAudioTrack = track
                }
            }
        }
        exoPlayer.trackSelectionParameters = selectionBuilder.build()
        _playerUiModel.value = _playerUiModel.value.copy(
            trackSelectionUiModel = _playerUiModel.value.trackSelectionUiModel?.copy(
                selectedAudioTrack = track
            )
        )
    }

    private fun setSubtitleTrack(track: SubtitleTrack) {
        val trackDisabled = track == SubtitleTrack.NONE
        val selectionBuilder = exoPlayer.trackSelectionParameters.buildUpon()
            .clearOverridesOfType(C.TRACK_TYPE_TEXT)
            .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, trackDisabled)
        when {
            track === SubtitleTrack.AUTO || track === SubtitleTrack.NONE -> {
                selectedSubtitleTrack = track
            }

            else -> {
                val exoSubtitleTrack = subtitleTracksMap[track]
                if (exoSubtitleTrack != null) {
                    selectionBuilder.setOverrideForType(
                        TrackSelectionOverride(
                            exoSubtitleTrack.trackGroup,
                            listOf(exoSubtitleTrack.trackIndexInGroup)
                        )
                    )
                    selectedSubtitleTrack = track
                }
            }
        }
        exoPlayer.trackSelectionParameters = selectionBuilder.build()
        _playerUiModel.value = _playerUiModel.value.copy(
            trackSelectionUiModel = _playerUiModel.value.trackSelectionUiModel?.copy(
                selectedSubtitleTrack = track
            )
        )
    }


    private val exoPlayer = buildExoPlayer().apply {
        addListener(playerEventListener)


    }

    private fun buildExoPlayer(): ExoPlayer {

        return ExoPlayer.Builder(appContext).apply {


        }.build()


    }


    fun makeLog() {
        Log.d("VideoPlayer", "makeLog")

    }


    fun setStreamUrl(streamUrl: String) {
        val mediaItem = MediaItem.Builder().apply {

            setUri(Uri.parse(streamUrl))


        }
        exoPlayer.setMediaItem(mediaItem.build())


    }

    fun setSurface(surface: Surface) {

        exoPlayer.setVideoSurface(surface)


    }

    fun clearSurface() {
        exoPlayer.setVideoSurface(null)


    }


    fun startPlayback() {


        exoPlayer.prepare()
        exoPlayer.playWhenReady = true


    }


    private suspend fun getAdsLink1(productId: Int) {

        val result = repository.getProductAdv(productId)
        Log.d("VideoPlayer", "getsAdd → ${result.data?.advertie.toString()}")


        val link = result.data?.advertie?.fileUrl
        if (link == null) {

            _currentVideoUri.value = NetworkResult.Error("ERROR")
        }


        Log.d("VideoPlayer", "Playing Ad → url=$link")
        _currentVideoUri.value = NetworkResult.Success(link.toString())
        _currentVideoUri1.value = link.toString()


        // return link


    }

    private fun getAdsLink(productId: Int) {
        // Launch a new coroutine to handle the async work
        viewModelScope.launch {
            _currentVideoUri.value = NetworkResult.Loading() // Set to loading at the start

            val result = repository.getProductAdv(productId)

            // Update the state based on the result
            if (result is NetworkResult.Success) {
                val link = result.data?.advertie?.fileUrl
                if (link == null) {
                    _currentVideoUri.value = NetworkResult.Error("ERROR: Link is null")
                } else {
                    _currentVideoUri.value = NetworkResult.Success(link)
                }
            } else if (result is NetworkResult.Error) {
                _currentVideoUri.value = NetworkResult.Error(result.message!!)
            }
        }
    }


    private suspend fun getVideoLink(productId: Int) {


        val result = repository.getProductDetails(productId)


        val link = result.data?.data?.videoLink


        Log.d("VideoPlayer", "Video Link→ url=$link")
        Log.d("VideoPlayer", "product id → url=$productId")

        _currentVideoUri.value = NetworkResult.Success(link.toString())
        _playerUiModel.value = playerUiModel.value.copy(
            videoTitle = result.data?.data?.name.toString()
        )


    }


    fun loadContent(productId: Int) {
        //  _isLoading.value=true
        viewModelScope.launch {

            getVideoLink(productId)
            //  _videoState.value = VideoState.Loading

        }
        //   _isLoading.value=false


    }


    fun startAd(productId: Int) {
        getAdsLink(productId)
       /* viewModelScope.launch {
            //_currentVideoUri.value =
            getAdsLink(productId)
            Log.d("VideoPlayer", "Playing Ad → url=${_currentVideoUri.value}")

            var totalAddTime = 12
            var timeLeft = 12
            var timePass = 0
            while (totalAddTime - timePass > 7) {


            }

        }*/
    }


    fun stopPlayBack() {

        exoPlayer.stop()
        Log.d("VideoPlayer", "StopPlayBack")


    }


    fun showPlayControls() {
        _playerUiModel.value = playerUiModel.value.copy(playerControlsVisible = true)


    }


    fun hidePlayControls() {
        _playerUiModel.value = playerUiModel.value.copy(playerControlsVisible = false)


    }


    fun enterFullScreen() {
        _playerUiModel.value = playerUiModel.value.copy(isFullScreen = true)


    }


    fun exitFullScreen() {
        _playerUiModel.value = playerUiModel.value.copy(isFullScreen = false)


    }

    fun hideTrackSelector() {
        _playerUiModel.value = _playerUiModel.value.copy(
            isTrackSelectorVisible = false
        )
    }

    fun openTrackSelector() {
        _playerUiModel.value = _playerUiModel.value.copy(
            isTrackSelectorVisible = true
        )
    }


    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
        Log.d(
            "VideoPlayer", "Re" +
                    "leasePlayBack"
        )
    }

    fun handleAction(action: Action) {
        when (action) {
            is AttachSurface -> {
                exoPlayer.setVideoSurface(action.surface)


            }

            DetachSurface -> {
                exoPlayer.setVideoSurface(null)


            }

            is FastForward -> {

                exoPlayer.seekTo(exoPlayer.currentPosition + action.amountInMs)

            }


            is Init -> {
                _playerUiModel.value.copy(
                    isAddPlaying = true

                )
                Log.d("VideoPlayer", "Init ${action.streamUrl}")

                // setUri(Uri.parse(streamUrl))
                val mediaItem =
                    MediaItem.Builder().setUri(Uri.parse(action.streamUrl)).build()
                exoPlayer.setMediaItem(mediaItem)


            }







            Pause -> {
                exoPlayer.pause()


            }

            Resume -> {
                exoPlayer.play()


            }

            is Rewind -> {
                exoPlayer.seekTo(exoPlayer.currentPosition - action.amountInMs)


            }

            is Seek -> {
                exoPlayer.seekTo(action.targetInMs)


            }

            is Start -> {

                exoPlayer.prepare()
                exoPlayer.play()
                action.positionInMs?.let {
                    exoPlayer.seekTo(it)


                }


            }

            Stop -> {
                exoPlayer.stop()


            }

            is PlayingAd -> {

                // val mediaItem =MediaItem.Builder().setUri(Uri.parse(action.streamUrl)).build()
                // exoPlayer.setMediaItem(mediaItem)


            }

            is SetAudioTrack -> {
                setAudioTrack(action.track)

            }

            is SetSubtitleTrack -> {
                setSubtitleTrack(action.track)

            }

            is SetVideoTrack -> {
                setVideoTrack(action.track)


            }

            is SetPlaybackSpeed -> {

                setPlaybackSpeed(action.speed)


            }


        }


    }

    private fun startTrackingPlayBackPosition() {


        positionTrackingJob = playerCoroutineScope.launch {
            while (true) {
                val newTimelineUiModel = buildTimelineUiModel()
                _playerUiModel.value = _playerUiModel.value.copy(
                    timelineUiModel = newTimelineUiModel


                )
                delay(1000L)


            }


        }


    }

    private fun buildTimelineUiModel(): TimelineUiModel? {

        val duration = exoPlayer.contentDuration
        if (duration == C.TIME_UNSET) return null
        val currentPosition = exoPlayer.currentPosition
        val bufferedPosition = exoPlayer.contentBufferedPosition

        return TimelineUiModel(
            durationInMs = duration,
            currentPositionInMs = currentPosition,
            bufferedPositionInMs = bufferedPosition
        )


    }

    private fun stopTrackingPlayBackPosition() {
        buildTimelineUiModel()
        positionTrackingJob?.cancel()
        positionTrackingJob = null


    }


}
