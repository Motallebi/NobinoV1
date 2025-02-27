package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.app.Activity
import android.content.pm.ActivityInfo
import android.media.MediaCodec
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun VideoPlay(videoUrl:String)
{
    val url ="https://caspian18.asset.aparat.com/aparat-video/4459748e6dcfb160e367e752c714e24063339394-720p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjMzODRlMmQwZDkwZGZjMzgzYzAwZjdjMzZhNDU4MDFlIiwiZXhwIjoxNzQwNTcwMzA0LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.2BaRbZvzsc9NJJ7pj6LXY6MjiYYZbVKCG9ZA48Sxa5Y"


    VideoView1(videoUrl)




}





@OptIn(UnstableApi::class)
@Composable
fun VideoView1(videoUri: String) {

    val systemUiController = rememberSystemUiController()
   /* SideEffect {
        systemUiController.setStatusBarColor(
            Color.Transparent

        )

        systemUiController.setSystemBarsColor(Color.Transparent)
    }*/



    val lifeCycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val context = LocalContext.current



    val activity = context as? Activity

    // Force landscape mode
    LaunchedEffect(Unit) {

        systemUiController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        systemUiController.isSystemBarsVisible = false







        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }










    val mediaItem2 = MediaItem.fromUri(videoUri)

    val exoPlayer =
        rememberSaveable(context, videoUri, saver = exoPlayerSaver(context, mediaItem2)) {
            ExoPlayer.Builder(context).build().apply {

                setMediaItem(mediaItem2)
                playWhenReady = true
                prepare()
               // videoScalingMode = MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT
            }
        }


    val lifeCycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(lifeCycleOwner, exoPlayer) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                Lifecycle.Event.ON_DESTROY -> exoPlayer.release()
                else -> {}
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }


        Box(modifier = Modifier.fillMaxSize().background(Color.Black))
        {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp),

            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    useController = true // Show playback controls

                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL // 🔥 This removes padding!
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                }









            },

                update = {
                    when (lifeCycle) {
                        Lifecycle.Event.ON_PAUSE -> {
                            it.onPause()
                            it.player?.pause()
                        }

                        Lifecycle.Event.ON_RESUME -> {
                            it.onResume()
                        }

                        else -> Unit
                    }
                }
            )



        }






        /* factory = {
             CustomPlayerView(context, exoPlayer)
         },*/































}


