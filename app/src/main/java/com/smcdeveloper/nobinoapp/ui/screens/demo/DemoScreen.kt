package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.media.MediaCodec
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import com.smcdeveloper.nobinoapp.ui.screens.search.CountryPage
import com.smcdeveloper.nobinoapp.ui.screens.search.GenrePage
import com.smcdeveloper.nobinoapp.ui.screens.search.YearPage
import com.smcdeveloper.nobinoapp.viewmodel.FilterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun DemoScreen(navController: NavHostController) {
    // State to track selected genres
    val selectedGenres = remember { mutableStateOf(mutableSetOf<String>()) }
    val viewModel: FilterViewModel = hiltViewModel()


    // Main Column Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Demo Screen Title
        Text(
            text = "DEMO SCREEN",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        // Genre Page Composable

    }

    YearPage  (
        viewModel = viewModel,

        {}
    )



}

























   /* val hlsUrl = "https://vod.nobino.ir/content/vod/FOMtvjsOeOIwfPXsta1st5UpuGDrYoYEHSywZdRbpfX90BecIhLRypvVuTt71uaFnhQZwZmrPFy2ibzCWql0GHOe7_hpzTpmScxSBdBdtbXulu8Jz5j37OI2cZ2i_BtrFKm6qCtnX7IdytEyNDQVUg_RghY8gxMUulDR5vP8_iT7kWFet7bEgnn7TcKO64HFWEdvJIn8Ke3kacGresswi6t61Kx4y-6iCh7w6cHpUh_CkFKBgmNz6ZNZleiazgBk_IiYPpOCOKxqyr6KI4YAE2K6XOyDu9-3Y2QpJC9-E26tx704hz0AIDRIbEypkLalyGBwWKO15yWvGdSlk1QN7eU8JUBSTPagxUNI6wfe80Ld-cF33WxORlbzi1a4UnTissmc29U1tVbW8uXmy0twqDzP0T-5p_OCDuXJtvylKlVPtFOQvPHBHZGhKh0CDNOs9L7UJ3qiCV-Xj7PeIpirw1cCKgCR0Z01CCXjK5_Xb02R6L5NS0MuNQynbC6GeTRNhu0Scg8aRN2LXglTSe9h1R3_K28SORO9Fbm93_qpzYT0UuJD39e_2CapIkFj2WihgdSgc40RCDnLnITItOk96vxcxUwlayqElN_Gp6PfjHHd9NOchCVLegXhE5hfTQlkELtIDvP_EM9CsEfLDJ4IEDd65qVewFHz_5qj9f-3KH5hf2V23eRbAk-NYPf7cg5arRZvqdTQIMOr5tBAiUJLHlF3WvYXHnLfolCMlZSlg71pR7ynifkImkzlpujjZe49/master.m3u8?x=dKMZV1t0dtoqIXFS1_Dr8g&e=1737004991800"




    HlsPlayer2(

       hlsUrl = hlsUrl
    )

*/




















fun createHlsExoPlayer(context: Context, hlsUrl: String): ExoPlayer {
    return ExoPlayer.Builder(context).build().apply {
        val mediaItem = MediaItem.fromUri(hlsUrl)
        setMediaItem(mediaItem) // Automatically determines the correct media source (HLS in this case)
        prepare()
        playWhenReady = true
    }
}





















@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VideoPlayScreen(
    navController: NavHostController,
    videourl: String

)









{
    val url ="https://caspian18.asset.aparat.com/aparat-video/4459748e6dcfb160e367e752c714e24063339394-720p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjMzODRlMmQwZDkwZGZjMzgzYzAwZjdjMzZhNDU4MDFlIiwiZXhwIjoxNzQwNTcwMzA0LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.2BaRbZvzsc9NJJ7pj6LXY6MjiYYZbVKCG9ZA48Sxa5Y"


    Scaffold() {
        VideoView(url)





  }













    Log.d("video","VideoPlayScreen......")



   // VideoScreen(videourl)



Box(
    modifier = Modifier.fillMaxSize()

)



{





}




}


@OptIn(UnstableApi::class)
@Composable
fun VideoView(videoUri: String) {
    val lifeCycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val context = LocalContext.current



    val activity = context as? Activity

    // Force landscape mode
    LaunchedEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }










    val mediaItem2 = MediaItem.fromUri(videoUri)

    val exoPlayer =
        rememberSaveable(context, videoUri, saver = exoPlayerSaver(context, mediaItem2)) {
            ExoPlayer.Builder(context).build().apply {

                setMediaItem(mediaItem2)
                playWhenReady = true
                prepare()
                videoScalingMode = MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
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

    AndroidView(
        modifier = Modifier.fillMaxSize(),

       /* factory = {
            CustomPlayerView(context, exoPlayer)
        },*/

        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = true // Show playback controls

                layoutParams =
                    FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
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















@Composable
fun HlsPlayer(hlsUrl: String) {
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = rememberExoPlayer(context, hlsUrl)

    // Dispose ExoPlayer when the composable is removed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // AndroidView to host the PlayerView
    AndroidView(factory = {
        PlayerView(it).apply {
            player = exoPlayer
        }
    })
}











@Composable
fun HLSPlayerComposable(
    context: Context,
    hlsUrl: String,
    modifier: Modifier = Modifier
)
{
    // Create and remember the ExoPlayer instance
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            // Load the HLS media item
            val mediaItem = MediaItem.fromUri(hlsUrl)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true





        }









    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = true // Show playback controls
            }
        },
        modifier = modifier
    )
}



@Composable
private fun rememberExoPlayer(context: Context, hlsUrl: String): ExoPlayer {
    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        val mediaItem = MediaItem.fromUri(hlsUrl)
        setMediaItem(mediaItem)
        prepare()
        playWhenReady = true
    }
    return exoPlayer
}





@Composable
fun HlsPlayer2(hlsUrl: String) {
    val context = LocalContext.current

    // Create ExoPlayer instance
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(hlsUrl))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    // Dispose ExoPlayer when not needed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Show the PlayerView in Jetpack Compose
    AndroidView(factory = {
        PlayerView(it).apply {
            player = exoPlayer
        }
    })
}
















@Composable
fun exoPlayerSaver(context: Context, mediaItem: MediaItem): Saver<ExoPlayer, Long> {
    return Saver(
        save = { player -> player.currentPosition },
        restore = { position ->
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(mediaItem)
                seekTo(position)
                playWhenReady = true
                prepare()
            }
        }
    )
}

























@Composable
fun VideoScreen(videourl:String) {

    if(videourl.isNotBlank())
   HlsPlayer2(

       // context = LocalContext.current,
        hlsUrl = videourl,

    )
    else{

        Toast.makeText(LocalContext.current,"Video is not valid", Toast.LENGTH_SHORT).show()


    }
}



class CustomPlayerView(context: Context, exoPlayer: ExoPlayer) : PlayerView(context) {
    private val gestureDetector = GestureDetector(context, GestureListener(context, exoPlayer))

    init {
        player = exoPlayer
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event!!)
        return super.onTouchEvent(event)
    }
}

class GestureListener(private val context: Context, private val exoPlayer: ExoPlayer) :
    GestureDetector.SimpleOnGestureListener() {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var initialVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    private var initialBrightness =
        Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        e1 ?: return false
        e2 ?: return false

        val deltaY = e1.y - e2.y
        val deltaX = e1.x - e2.x

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Horizontal swipe - Seek
            exoPlayer.seekTo(exoPlayer.currentPosition + (deltaX * 1000).toLong())
        } else {
            // Vertical swipe - Adjust volume or brightness
            if (e1.x < context.resources.displayMetrics.widthPixels / 2) {
                // Left side - Adjust brightness
                val newBrightness = (initialBrightness + deltaY).toInt().coerceIn(0, 255)
                Settings.System.putInt(
                    context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    newBrightness
                )
            } else {
                // Right side - Adjust volume
                val newVolume = (initialVolume + deltaY / 10).toInt()
                    .coerceIn(0, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC))
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
            }
        }
        return true
    }
}




