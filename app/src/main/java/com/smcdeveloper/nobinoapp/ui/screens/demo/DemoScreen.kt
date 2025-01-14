package com.smcdeveloper.nobinoapp.ui.screens.demo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun DemoScreen(
    navController: NavHostController,
    //videourl: String


)


{














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
fun HLSPlayerComposable(
    context: Context,
    hlsUrl: String,
    modifier: Modifier = Modifier
) {
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
fun VideoScreen(videourl:String) {

    if(videourl.isNotBlank())
    HLSPlayerComposable(
        context = LocalContext.current,
        hlsUrl = videourl,
        modifier = Modifier.fillMaxSize()
    )
    else{

        Toast.makeText(LocalContext.current,"Video is not valid", Toast.LENGTH_SHORT).show()


    }
}







