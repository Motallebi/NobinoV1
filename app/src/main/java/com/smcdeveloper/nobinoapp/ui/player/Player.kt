package com.smcdeveloper.nobinoapp.ui.player

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.OptIn
import androidx.compose.foundation.AndroidExternalSurface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.DefaultTimeBar
import androidx.media3.ui.TimeBar
import com.smcdeveloper.nobinoapp.R
import com.smcdeveloper.nobinoapp.ui.theme.nobinoMedium
import com.smcdeveloper.nobinoapp.util.DigitHelper


@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel





)
{
    var isOverLayClicked by remember { mutableStateOf(false) }
    Log.d("VideoPlayer","created.......")
    Log.d("VideoPlayer","created.......$isOverLayClicked")
    val context = LocalContext.current
    val activity = context as? Activity
    val playerUiModel by playerViewModel.playerUiModel.collectAsState()

    val lifeCycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current






    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE->
                {





                   Log.d("VideoPlayer","created")
                   // playerViewModel.handleAction(Start())
                  //  playerViewModel.startPlayback()


                }

                Lifecycle.Event.ON_START->
                {
                    Log.d("VideoPlayer","Start")
                    Log.d("VideoPlayer","current link is:"+playerViewModel.currentVideoUri.value.toString())


                 //   playerViewModel.handleAction(action =Start())

                }

                //  Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                // Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> playerViewModel.stopPlayBack()
                Lifecycle.Event.ON_DESTROY -> {}
                else -> {}
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    LaunchedEffect(playerViewModel.currentVideoUri) {

        playerViewModel.startPlayback()




        val window = activity?.window
        val windowInsetsController =
            WindowCompat.getInsetsController(window!!, window.decorView)

        if(playerUiModel.isFullScreen) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        }










    }











     Box()
     {

         AndroidExternalSurface(modifier =
         modifier.aspectRatio(playerUiModel.videoAspectRatio)
             .clickable {
              /*   if(!isOverLayClicked){
                     playerViewModel.showPlayControls()
                     isOverLayClicked=true
                 }
                 else
                 {
                     playerViewModel.hidePlayControls()
                 }*/
               // playerViewModel.showPlayControls()
                 playerViewModel.makeLog()
               //  Log.d("VideoPlayer","clicked")

             }

         ) {
             onSurface { surface,_, _ ->
                 //playerViewModel.setSurface(surface)
                 playerViewModel.handleAction(AttachSurface(surface))


                 surface.onDestroyed {
                    // playerViewModel.clearSurface()
                     playerViewModel.handleAction(DetachSurface)





                 }




             }











         }

         VideoOverlay(
             modifier = Modifier.matchParentSize(),
             playerViewModel = playerViewModel,
             onCollapseClicked = {
                 //playerViewModel.exitFullScreen()
                                 },
             onExpandClicked = {
               //  playerViewModel.enterFullScreen()


             },
             onControlsClicked = {

                 playerViewModel.hidePlayControls()



             },
             onSettingsClicked = {},
             onAction = {
                 playerViewModel.handleAction(it)


             }






         )








     }













}



@Composable
fun VideoPlayer1(
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel





)
{
    val playerUiModel by playerViewModel.playerUiModel.collectAsState()
    var isOverLayClicked by remember { mutableStateOf(false) }
    Log.d("VideoPlayer","created.......")
    Log.d("VideoPlayer","created.......$isOverLayClicked")































    Box()
    {

        AndroidExternalSurface(modifier =
        modifier.aspectRatio(playerUiModel.videoAspectRatio)
            .clickable {
                /*   if(!isOverLayClicked){
                       playerViewModel.showPlayControls()
                       isOverLayClicked=true
                   }
                   else
                   {
                       playerViewModel.hidePlayControls()
                   }*/
                 playerViewModel.showPlayControls()
                playerViewModel.makeLog()
                //  Log.d("VideoPlayer","clicked")

            }

        ) {
            onSurface { surface,_, _ ->
                //playerViewModel.setSurface(surface)
                playerViewModel.handleAction(AttachSurface(surface))


                surface.onDestroyed {
                    // playerViewModel.clearSurface()
                    playerViewModel.handleAction(DetachSurface)





                }




            }











        }

        VideoOverlay(
            modifier = Modifier.matchParentSize(),
            playerViewModel = playerViewModel,
            onCollapseClicked = {
                //playerViewModel.exitFullScreen()
            },
            onExpandClicked = {
                //  playerViewModel.enterFullScreen()


            },
            onControlsClicked = {

                playerViewModel.hidePlayControls()



            },
            onSettingsClicked = {
                playerViewModel.openTrackSelector()



            },
            onAction = {
                playerViewModel.handleAction(it)


            }






        )








    }













}















@Composable
fun PlaybackControls(
    modifier: Modifier,
    isFullScreen: Boolean,
    playerUiModel: PlayerUiModel,
    onCollapseClicked: () -> Unit,
    onExpandClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onAction: (Action) -> Unit,


)

{


    Box(

        modifier = modifier.background(Color(0xA0000000))
            .padding(8.dp)

    )
    {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(16.dp)


        )






        {

            PlaybackButton(
                R.drawable.settings,
                description = "Open track selector"
            ) {
                onSettingsClicked()
            }




            if(playerUiModel.playbackState.isReady()) {
                PlaybackButton(
                    R.drawable.rewind,
                    description = "rewind",
                    onClick = {
                        onAction(Rewind(15_000))



                    }
                )

                PlaybackButton(
                    R.drawable.fastforward,
                    description = "fastforward",
                    onClick = {
                        onAction(FastForward(15_000))





                    }


                )
            }


                when(playerUiModel.playbackState)
                {
                    PlaybackState.IDLE->{


                        PlaybackButton(
                            R.drawable.playx,
                            description = "play",
                            onClick = {
                                onAction(Start())

                            }












                        )


                    }
                    PlaybackState.PLAYING->{


                        PlaybackButton(
                            R.drawable.pause,
                            description = "pause",
                            onClick ={
                                onAction(Pause)



                            }


                        )





                    }
                    PlaybackState.PAUSED->{


                        PlaybackButton(
                            R.drawable.playx,
                            description = "play",
                            onClick = {
                                onAction(Start())



                            }




                        )





                    }
                    PlaybackState.BUFFERING->{

                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = Color.White
                        )


                    }
                    PlaybackState.COMPLETED->{
                        PlaybackButton(
                            R.drawable.replay,
                            description = "replay",
                            onClick = {}




                        )




                    }
                    PlaybackState.ERROR->{
                        PlaybackButton(
                            R.drawable.error,
                            description = "replay",
                            onClick = {}




                        )

                        PlaybackButton(
                            R.drawable.replay,
                            description = "retry",
                            onClick = {}




                        )





                    }
















                }

























        }





            playerUiModel.timelineUiModel?.let { timeBar->

                Column(
                    modifier = Modifier.align(Alignment.BottomEnd)



                )
                {
                    PlayBackPosition(timeBar.currentPositionInMs,timeBar.durationInMs)
                    TimeBar(
                        currentPositionInMs = timeBar.currentPositionInMs,
                        contentDurationInMs = timeBar.durationInMs,
                        bufferedPositionInMs = timeBar.bufferedPositionInMs,
                        onSeek = {

                            onAction(Seek(it.toLong()))


                        }






                    )

                }



            }


































    }













}

@Composable
fun PlayAdd()
{









}






@OptIn(UnstableApi::class)
@Composable
fun TimeBar(
    currentPositionInMs: Long,
    contentDurationInMs: Long,
    bufferedPositionInMs: Long,
    onSeek: (Float) -> Unit,




) {

    AndroidView(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        factory = { context ->
            DefaultTimeBar(context).apply {
                setScrubberColor(0xFFFF0000.toInt())
                setPlayedColor(0xCCFF0000.toInt())
                setBufferedColor(0x77FF0000.toInt())



            }

        },

        update = { timeBar ->
            with(timeBar) {
                addListener(object : TimeBar.OnScrubListener {
                    override fun onScrubStart(timeBar: TimeBar, position: Long) {

                    }

                    override fun onScrubMove(timeBar: TimeBar, position: Long) {

                    }

                    override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {

                        onSeek(position.toFloat())



                    }


                }



                )

                setDuration(contentDurationInMs)
                setPosition(currentPositionInMs)
                setBufferedPosition(bufferedPositionInMs)



            }






        }




    )








}

@Composable
fun PlayBackPosition(
    contentDurationInMs: Long ,
    currentPositionInMs: Long,






) {

    Text(
        "${DigitHelper.formatMsToString(currentPositionInMs)}|${DigitHelper.formatMsToString(contentDurationInMs)}",
        style = MaterialTheme.typography.nobinoMedium



    )







}

@Composable
fun VideoOverlay(
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel,
    onCollapseClicked: () -> Unit,
    onExpandClicked: () -> Unit,
    onControlsClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onAction: (Action) -> Unit,
)

{
    val playerUiModel by playerViewModel.playerUiModel.collectAsState()

    Box(
        modifier = modifier
    )
    {
        Text(
            text = playerUiModel.videoTitle,
            //  modifier = Modifier.clickable {  }

        )

        if (playerUiModel.playerControlsVisible) {
            PlaybackControls(
                modifier = Modifier.matchParentSize().clickable(onClick = onControlsClicked),
                    /*onClick ={
                       // playerViewModel.makeLog()
                      //  playerViewModel.hidePlayControls()



                    }*/
                    //onControlsClicked




                isFullScreen = playerUiModel.isFullScreen,
                playerUiModel = playerUiModel,
                onCollapseClicked = onCollapseClicked,
                onExpandClicked = onExpandClicked,
                onSettingsClicked = onSettingsClicked,
                onAction = onAction,
            )
        }






    }
}

private enum class TrackState {
    VIDEO, AUDIO, SUBTITLE, LIST,SPEED
}

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackSelector(
    trackSelectionUiModel: TrackSelectionUiModel,
    onVideoTrackSelected: (VideoTrack) -> Unit,
    onAudioTrackSelected: (AudioTrack) -> Unit,
    onSubtitleTrackSelected: (SubtitleTrack) -> Unit,
    onPlayBackSpeedSelected: (Float) -> Unit,
    onDismiss: () -> Unit,
) {
    var currentTrackState by remember { mutableStateOf(TrackState.LIST) }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
    )
    {
        when (currentTrackState) {
            TrackState.LIST ->
            {

                Column {
                    Text(
                        text = "Video Tracks",
                        modifier = Modifier.clickable {
                            currentTrackState = TrackState.VIDEO
                        }.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Text(
                        text = "Audio Tracks",
                        modifier = Modifier.clickable {
                            currentTrackState = TrackState.AUDIO
                        }.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Text(
                        text = "Subtitle Tracks",
                        modifier = Modifier.clickable {
                            currentTrackState = TrackState.SUBTITLE
                        }.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Text(
                        text = "SPEED",
                        modifier = Modifier.clickable {
                            currentTrackState = TrackState.SPEED
                        }.padding(horizontal = 16.dp, vertical = 8.dp)
                    )


                }









            }
            TrackState.VIDEO -> {

                Column {
                    trackSelectionUiModel.videoTracks.forEach { videoTrack ->
                        Text(
                            modifier = Modifier.clickable {
                                onVideoTrackSelected(videoTrack)
                                onDismiss()
                            }.padding(horizontal = 16.dp, vertical = 8.dp),
                            text = videoTrack.displayName,
                            color = if (videoTrack === trackSelectionUiModel.selectedVideoTrack) {
                                Color.Yellow
                            } else {
                                Color.White
                            }
                        )
                    }
                }







            }
            TrackState.AUDIO -> {
                Column {
                    trackSelectionUiModel.audioTracks.forEach { audioTrack ->
                        Text(
                            modifier = Modifier.clickable {
                                onAudioTrackSelected(audioTrack)
                                onDismiss()
                            }.padding(horizontal = 16.dp, vertical = 8.dp),
                            text = audioTrack.displayName,
                            color = if (audioTrack === trackSelectionUiModel.selectedAudioTrack) {
                                Color.Yellow
                            } else {
                                Color.White
                            }
                        )
                    }
                }







            }
            TrackState.SUBTITLE ->
            {

                Column {
                    trackSelectionUiModel.subtitleTracks.forEach { subtitleTrack ->
                        Text(
                            modifier = Modifier.clickable {
                                onSubtitleTrackSelected(subtitleTrack)
                                onDismiss()
                            }.padding(horizontal = 16.dp, vertical = 8.dp),
                            text = subtitleTrack.displayName,
                            color = if (subtitleTrack === trackSelectionUiModel.selectedSubtitleTrack) {
                                Color.Yellow
                            } else {
                                Color.White
                            }
                        )
                    }
                }












            }







            TrackState.SPEED -> {
                Column {
                    trackSelectionUiModel.speedTracks.forEach { speedTrack ->
                        Text(
                            modifier = Modifier.clickable {
                                onPlayBackSpeedSelected(speedTrack.videoPlayBackSpeed)
                                onDismiss()
                            }.padding(horizontal = 16.dp, vertical = 8.dp),
                            text = speedTrack.displayName,
                            color = if (speedTrack === trackSelectionUiModel.selectedSpeedTrack) {
                                Color.Yellow
                            } else {
                                Color.White
                            }
                        )
                    }
                }















            }
        }


    }

}










@Composable
fun PlaybackButton(
    @DrawableRes resourceId: Int,
    description: String,
    onClick: () -> Unit = {},
) {
    Image(
        modifier = Modifier.size(32.dp).clickable(onClick = onClick),
        contentDescription = description,
        painter = painterResource(resourceId)
    )
}
