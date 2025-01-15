package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController


@Composable
fun DemoScreen(
    navController: NavHostController,
    //videourl: String


)


{

    Text("DEMO SCEREEN")

    val hlsUrl = "https://vod.nobino.ir/content/vod/FOMtvjsOeOIwfPXsta1st5UpuGDrYoYEHSywZdRbpfX90BecIhLRypvVuTt71uaFnhQZwZmrPFy2ibzCWql0GHOe7_hpzTpmScxSBdBdtbXulu8Jz5j37OI2cZ2i_BtrFKm6qCtnX7IdytEyNDQVUg_RghY8gxMUulDR5vP8_iT7kWFet7bEgnn7TcKO64HFWEdvJIn8Ke3kacGresswi6t61Kx4y-6iCh7w6cHpUh_CkFKBgmNz6ZNZleiazgBk_IiYPpOCOKxqyr6KI4YAE2K6XOyDu9-3Y2QpJC9-E26tx704hz0AIDRIbEypkLalyGBwWKO15yWvGdSlk1QN7eU8JUBSTPagxUNI6wfe80Ld-cF33WxORlbzi1a4UnTissmc29U1tVbW8uXmy0twqDzP0T-5p_OCDuXJtvylKlVPtFOQvPHBHZGhKh0CDNOs9L7UJ3qiCV-Xj7PeIpirw1cCKgCR0Z01CCXjK5_Xb02R6L5NS0MuNQynbC6GeTRNhu0Scg8aRN2LXglTSe9h1R3_K28SORO9Fbm93_qpzYT0UuJD39e_2CapIkFj2WihgdSgc40RCDnLnITItOk96vxcxUwlayqElN_Gp6PfjHHd9NOchCVLegXhE5hfTQlkELtIDvP_EM9CsEfLDJ4IEDd65qVewFHz_5qj9f-3KH5hf2V23eRbAk-NYPf7cg5arRZvqdTQIMOr5tBAiUJLHlF3WvYXHnLfolCMlZSlg71pR7ynifkImkzlpujjZe49/master.m3u8?x=dKMZV1t0dtoqIXFS1_Dr8g&e=1737004991800"




    HlsPlayer2(

       hlsUrl = hlsUrl
    )














}


fun createHlsExoPlayer(context: Context, hlsUrl: String): ExoPlayer {
    return ExoPlayer.Builder(context).build().apply {
        val mediaItem = MediaItem.fromUri(hlsUrl)
        setMediaItem(mediaItem) // Automatically determines the correct media source (HLS in this case)
        prepare()
        playWhenReady = true
    }
}





















@Composable
fun VideoPlayScreen(
    navController: NavHostController,
    videourl: String

)
{
    Log.d("video","VideoPlayScreen......")


    Text("DEMO SCEREEN")
    VideoScreen(videourl)


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







