package com.smcdeveloper.nobinoapp.ui.player

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
    val isAddPlaying:Boolean=false,














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
                else -> "$width $height"
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


