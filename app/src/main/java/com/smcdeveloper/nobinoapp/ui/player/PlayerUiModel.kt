package com.smcdeveloper.nobinoapp.ui.player

import com.smcdeveloper.nobinoapp.util.DigitHelper

data class PlayerUiModel(
    val videoTitle: String="",
    val streamUrl: String="",
    val videoAspectRatio: Float = 16.0f / 9.0f,
    val isFullScreen: Boolean = true,
    val playerControlsVisible: Boolean = false,
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val timelineUiModel:TimelineUiModel?=null,
    val trackSelectionUiModel: TrackSelectionUiModel?=null,
    val isTrackSelectorVisible: Boolean = false,
    val isAddPlaying:Boolean=true,
    val isAddSkippable:Boolean=false,














)
data class TrackSelectionUiModel(
    val selectedVideoTrack: VideoTrack,
    val videoTracks: List<VideoTrack>,
    val selectedAudioTrack: AudioTrack,
    val audioTracks: List<AudioTrack>,
    val selectedSubtitleTrack: SubtitleTrack,
    val subtitleTracks: List<SubtitleTrack>,
    val selectedSpeedTrack: SpeedTrack,
    val speedTracks: List<SpeedTrack>










)









enum class PlaybackState {
   // Idle nothng
  //Completed PlayBack Completed



    IDLE, PLAYING, PAUSED, BUFFERING, COMPLETED, ERROR,ADDPLAYING
}



data class VideoTrack(
    val width: Int,
    val height: Int
) {

    val displayName: String
        get() {
            return when (this) {
                AUTO -> "Auto"



                else -> showTrackQuality(width)
            }
        }

    companion object {
        val AUTO = VideoTrack(0 ,0)
    }
}

data class SpeedTrack(
    val videoPlayBackSpeed:Float,

) {

    val displayName: String
        get() {
            return when (this) {
                AUTO -> "1.0 "
                else -> "$videoPlayBackSpeed"
            }
        }

    companion object {
        val AUTO = SpeedTrack(1.0f)
    }
}


fun showTrackQuality(width:Int):String
{
  var result=""
  result = when(width)
    {
        1920->"FULL HD"
        1280->"HD"
        854->"480P"
        640->"SD"
        426->"240P"
        720->"SD"
        else->"Auto"





    }
    return DigitHelper.digitByLocate(result)





}













data class SubtitleTrack(
    val language: String
) {
    val displayName: String
        get() {
            return when (this) {
                AUTO -> "Auto"
                NONE -> "None"
                else -> language
            }
        }

    companion object {
        val AUTO = SubtitleTrack("Auto")
        val NONE = SubtitleTrack("None")
    }
}

data class AudioTrack(
    val language: String
) {
    val displayName: String
        get() {
            return when (this) {
                AUTO -> "Auto"
                NONE -> "None"
                else -> language
            }
        }

    companion object {
        val AUTO = AudioTrack("Auto")
        val NONE = AudioTrack("None")
    }
}














data class TimelineUiModel(

    val durationInMs: Long,
    val currentPositionInMs: Long,
    val bufferedPositionInMs: Long




)







fun PlaybackState.isReady(): Boolean {
    return this == PlaybackState.PAUSED || this == PlaybackState.PLAYING
}


