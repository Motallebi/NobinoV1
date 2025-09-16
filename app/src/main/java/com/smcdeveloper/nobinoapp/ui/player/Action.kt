package com.smcdeveloper.nobinoapp.ui.player

import android.view.Surface
import com.smcdeveloper.nobinoapp.viewmodel.VideoState

sealed interface Action
data class Init(val streamUrls:List<String>) : Action

data object Pause : Action
data object Resume : Action
data object Stop : Action
data object SkipAdd : Action
data class Rewind(val amountInMs: Int) : Action
data class FastForward(val amountInMs: Int) : Action
data class Seek(val targetInMs: Long) : Action
data class AttachSurface(val surface: Surface) : Action
data object DetachSurface : Action
data class Start(val positionInMs: Long?=null) : Action
data class PlayingAd(val remainingTime: Int, val skippable: Boolean) : Action


data class SetVideoTrack(val track: VideoTrack) : Action
data class SetAudioTrack(val track: AudioTrack) : Action
data class SetSubtitleTrack(val track: SubtitleTrack) : Action
data class SetPlaybackSpeed(val speed: Float) : Action


