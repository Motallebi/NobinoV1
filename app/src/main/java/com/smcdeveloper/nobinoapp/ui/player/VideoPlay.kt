package com.smcdeveloper.nobinoapp.ui.player

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.ima.ImaAdsLoader
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smcdeveloper.nobinoapp.data.remote.NetworkResult
import com.smcdeveloper.nobinoapp.navigation.Screen
import com.smcdeveloper.nobinoapp.ui.screens.demo.exoPlayerSaver
import com.smcdeveloper.nobinoapp.viewmodel.ProductDetailsViewModel
import com.smcdeveloper.nobinoapp.viewmodel.VideoPlayerViewModel
import com.smcdeveloper.nobinoapp.viewmodel.VideoState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen1(viewModel: VideoPlayerViewModel,productId: String)
{


    val context = LocalContext.current
    val state by viewModel.videoState.collectAsState()
    val videoUri by viewModel.currentVideoUri.collectAsState()
    val lifeCycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().also { player ->
            viewModel.attachPlayer(player) // attach listener
        }
    }


    val systemUiController = rememberSystemUiController()
    val activity = context as? Activity


    LaunchedEffect(Unit) {

        systemUiController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        systemUiController.isSystemBarsVisible = false







        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }








    DisposableEffect(Unit) {
        viewModel.startAd(productId.toInt())
        onDispose {
            exoPlayer.release()
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED


        }



    }

    LaunchedEffect(videoUri) {
        videoUri?.let {
            exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(it.toString())))
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {




        AndroidView(
            modifier = Modifier.fillMaxSize(),

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



        Text(productId)













        // State-driven overlay
        when (state) {
            is VideoState.Loading -> CircularProgressIndicator(modifier = Modifier.align(
                Alignment.Center))
            is VideoState.Buffering -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is VideoState.PlayingAd -> {
                val adState = state as VideoState.PlayingAd

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 40.dp)
                        .padding(start = 20.dp)
                    ,

                    contentAlignment = Alignment.BottomStart




                )
                {
                    Row()
                    {
                        Button(
                            onClick = {

                                viewModel.skipAd(productId.toInt())


                            },
                           // modifier = Modifier.align(Alignment.TopEnd).padding(16.dp),
                            enabled = adState.skippable
                        ) {
                            Text(if (!adState.skippable) "تا نمایش ${adState.remainingTime}" else "رد کردن آکهی")
                        }


                    }


                }



            }
            is VideoState.Error -> {
                val errorMsg = (state as VideoState.Error).message
                Text("Error: $errorMsg", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
            }
            is VideoState.Completed -> {
                Text("Playback finished", modifier = Modifier.align(Alignment.Center))
               // viewModel.autoSkipAdd()
                viewModel.skipAd(productId.toInt())
            }
            else -> Unit
        }
    }
}




@Composable
fun VideoPlay2(videoUrl:String,productId:String)
{

    val viewModel1:VideoPlayerViewModel= hiltViewModel()
    VideoPlayerScreen1(
        viewModel = viewModel1,
        productId = productId)

}


@Composable
fun VideoPlay(videoUrl:String,productId:String)
{
     VideoPlayerScreen(productId)
     //  VideoScreen(productId)


}



@Composable
fun VideoScreen(productId: String) {
    val viewModel: PlayerViewModel = hiltViewModel()
    val videoUri by viewModel.currentVideoUri.collectAsState()
    val videoUri1 by viewModel.currentVideoUri1.collectAsState()

    val lifeCycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val context = LocalContext.current
    val activity = context as? Activity








    when (videoUri) {


        is NetworkResult.Error -> {
            Log.d("VideoPlayer1", "VideoPlay Error")

        }

        is NetworkResult.Loading -> {
            Log.d("VideoPlayer1", "VideoPlay current link is... Loading")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            )
            {
            }


        }

        is NetworkResult.Success -> {
            Log.d("VideoPlayer1", "VideoPlay current link is...Success :${videoUri.data}")


        }


    }
    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {

                    viewModel.startAd(productId.toInt())



                  //   viewModel.loadContent(productId.toInt())

                    //  viewModel.setStreamUrl(videoUri.toString())
                    Log.d("VideoPlayer", "created")
                    // playerViewModel.handleAction(Start())



                }

                Lifecycle.Event.ON_START -> {
                    //  viewModel.startPlayback()





                    Log.d("VideoPlayer", "Start")
                    Log.d(
                        "VideoPlayer",
                        "current link is:" + viewModel.currentVideoUri.value.toString()
                    )


                    //   playerViewModel.handleAction(action =Start())

                }

                //  Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                // Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> viewModel.stopPlayBack()
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






}




@Composable
fun VideoPlayerScreen(productId: String) {
    val viewModel: PlayerViewModel = hiltViewModel()
    val playerUiModel by viewModel.playerUiModel.collectAsState()
    val videoUri by viewModel.currentVideoUri.collectAsState()
    val lifeCycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val context = LocalContext.current
    val activity = context as? Activity


    when(videoUri)
    {
        is NetworkResult.Error -> {
            Log.d("VideoPlayer1", "VideoPlay Error")

        }
        is NetworkResult.Loading ->{
            Log.d("VideoPlayer1", "VideoPlay current link is... Loading")
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator()
            }

        }
        is NetworkResult.Success -> {

            val link = (videoUri as NetworkResult.Success).data

            Log.d("VideoPlayer1", "VideoPlay current link is...Success :${videoUri.data}")









           // viewModel.setStreamUrl(videoUri.data.toString())
          //  viewModel.handleAction(Init(videoUri.data.toString()))
         //   viewModel.handleAction(Init(videoUri.data.toString()))
            viewModel.handleAction(Init(link.toString()))



            viewModel.handleAction(Start())

           // viewModel.startPlayback()
            Surface() {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center


                )
                {
                    VideoPlayer1(
                        modifier = Modifier.aspectRatio(playerUiModel.videoAspectRatio),
                        playerViewModel = viewModel


                    )

                    if(playerUiModel.isTrackSelectorVisible)
                    {
                        playerUiModel.trackSelectionUiModel?.let{trackUiModel->
                            TrackSelector(
                                trackSelectionUiModel = trackUiModel,
                                onVideoTrackSelected =
                                {
                                    viewModel.handleAction(SetVideoTrack(it))
                                },
                                onAudioTrackSelected =
                                {
                                    viewModel.handleAction(SetAudioTrack(it))
                                },
                                onSubtitleTrackSelected = {
                                    viewModel.handleAction(SetSubtitleTrack(it))
                                    Log.d("VideoPlayer","Playback Subtitle Selected")

                                },
                                onPlayBackSpeedSelected = {

                                    Log.d("VideoPlayer","Playback Speed Selected")
                                    viewModel.handleAction(SetPlaybackSpeed(it))


                                },
                                onDismiss = {

                                    viewModel.hideTrackSelector()



                                }








                            )














                        }





                    }







                }


            }





























        }
    }













    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    viewModel.startAd(productId.toInt())




                    // viewModel.loadContent(productId.toInt())

                   //  viewModel.setStreamUrl(videoUri.toString())
                    Log.d("VideoPlayer", "created")
                   // playerViewModel.handleAction(Start())



                }

                Lifecycle.Event.ON_START -> {
                  //  viewModel.startPlayback()





                    Log.d("VideoPlayer", "Start")
                    Log.d(
                        "VideoPlayer",
                        "current link is:" + viewModel.currentVideoUri.value.toString()
                    )


                    //   playerViewModel.handleAction(action =Start())

                }

                //  Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                // Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> viewModel.stopPlayBack()
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



    LaunchedEffect(viewModel.currentVideoUri) {




       // viewModel.startPlayback()


        val window = activity?.window
        val windowInsetsController =
            WindowCompat.getInsetsController(window!!, window.decorView)

        if (playerUiModel.isFullScreen) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        }


    }
}




    @Composable
    fun VideoPlay4(videoUrl: String, productId: String) {

        val viewModel1: VideoPlayerViewModel = hiltViewModel()
        val viewModel: PlayerViewModel = hiltViewModel()

        val context = LocalContext.current
        val videoUri by viewModel.currentVideoUri.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val playerUiModel by viewModel.playerUiModel.collectAsState()
        val videoUri1 =
            "https://vod.nobino.ir/content/vod/E8WVkz5-HHViAaMDriKHlgo3IH7cMWtZE7qntAXGk3z5LO0ZDl-VXdS3Com6PyBZOFyYOulGN6_DAAKqdpeVtlmYmTM-Q_yAVBpiKnMI8xZsGMzWHG63LHJ2xXtK-lCtTK7YxDFkz78CyFe3xEr3JhC0_BSyRu-cceTeB3p6rlFm5IJX_g7SGSbN-0XfG4LK4ujQxkOF0SffnJOvsPm-1cqX7RAAoWq7LIBW8MaPvbIZ0E3Oonhw-BpWCsRDFJbF1jBld-h0CNx9KdWnYgDbccqCWXALkm0W44nRBIB7PFx-ioZjOMUzyup8FmMUHZnsIBQ-aPlHRNp6MHgAkrrJEXahIVOb1glSM_RQZqWs0oTu0NqnsW59hoZdDtu4crCR/master.m3u8?x=UH4uqoq-nkk7bf1Wpie38A&e=1756641495656"

        // viewModel.loadContent(productId.toInt())
        //viewModel.startPlayback()

        LaunchedEffect(Unit) {
            viewModel.loadContent(productId.toInt())

            //viewModel.setStreamUrl(videoUri.toString())
            //viewModel.setStreamUrl(videoUri1.toString())


            Log.d("VideoPlayer", "VideoPlay current link is... :$videoUri")


        }


        /*
        VideoPlayerScreen1(
            viewModel = viewModel,
            productId = productId


        )*/



        Surface() {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center


            )
            {

                when (isLoading) {
                    true -> {
                        Box()
                        {

                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))


                        }


                    }

                    false -> {
                        // viewModel.setStreamUrl(videoUri.toString())
                        //  viewModel.handleAction(Init(videoUri.toString()))
                        VideoPlayer(
                            modifier = Modifier.aspectRatio(playerUiModel.videoAspectRatio),
                            playerViewModel = viewModel


                        )

                    }


                }


            }


        }


    }


    @Composable
    fun VideoPlay1(videoUrl: String, productId: String) {

        val productViewModel: ProductDetailsViewModel = hiltViewModel()

        val advertise by productViewModel.productAdv.collectAsState()







        getAdd(productId = productId, viewModel = productViewModel)


        val addLink: String


        when (advertise) {
            is NetworkResult.Loading -> {

                addLink = ""


            }

            is NetworkResult.Error -> {

                addLink = ""

            }

            is NetworkResult.Success -> {

                addLink = advertise.data?.advertie?.fileUrl.toString()


            }


        }










        Log.d("finished", "p id is:" + productId)

        val url =
            "https://caspian18.asset.aparat.com/aparat-video/4459748e6dcfb160e367e752c714e24063339394-720p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IjMzODRlMmQwZDkwZGZjMzgzYzAwZjdjMzZhNDU4MDFlIiwiZXhwIjoxNzQwNTcwMzA0LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.2BaRbZvzsc9NJJ7pj6LXY6MjiYYZbVKCG9ZA48Sxa5Y"
        val url2 =
            "https://vod.nobino.ir/content/vod/-Jmv7SR-2SAhaUSEyRuIEZyUGJ4tcXxFF7DAaFc7m7zemZEG4_ciigSHQRPgvhFki4PWRzyWa3DIJHy_zp-io3rtdh1Aqv3GOyAtfS6Fpb4/master.m3u8?x=7VR4YoTvaOejC3W4Auzz4g&e=1754893084070"

        VideoView1(videoUrl,

            onMovieFinished = {

                Log.d("finished", "finishedplay")


            }


        )


    }


    fun getAdd(productId: String, viewModel: ProductDetailsViewModel) {

        viewModel.getProductAdv(productId.toInt())


    }


    @OptIn(UnstableApi::class)
    @Composable
    fun VideoView1(
        videoUri: String, onMovieFinished: () -> Unit,
        endOffsetMillis: Long = 5000, // Trigger callback 1 second before the end (default)
        checkIntervalMillis: Long = 500 // Ch


    ) {

        val systemUiController = rememberSystemUiController()
        /* SideEffect {
        systemUiController.setStatusBarColor(
            Color.Transparent

        )

        systemUiController.setSystemBarsColor(Color.Transparent)
    }*/


        val lifeCycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()


        val activity = context as? Activity

        // Force landscape mode
        LaunchedEffect(Unit) {

            systemUiController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            systemUiController.isSystemBarsVisible = false







            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }


        val url2 =
            "https://vod.nobino.ir/content/vod/-Jmv7SR-2SAhaUSEyRuIEZyUGJ4tcXxFF7DAaFc7m7zemZEG4_ciigSHQRPgvhFki4PWRzyWa3DIJHy_zp-io3rtdh1Aqv3GOyAtfS6Fpb4/master.m3u8?x=7VR4YoTvaOejC3W4Auzz4g&e=1754893084070"
        val url3 =
            "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_ad_samples&sz=640x480&cust_params=sample_ct%3Dlinear&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&correlator="


        val adTagUri = Uri.parse(url3)
        val adsLoader = ImaAdsLoader.Builder(context).build()


        val mediaItem2 = MediaItem.fromUri(videoUri)
        val addContent = MediaItem.fromUri(url2)


        val mediaItem3 = MediaItem.Builder()
            .setUri(videoUri)
            .setAdsConfiguration(MediaItem.AdsConfiguration.Builder(adTagUri).build())
            .build()


        //   val player = ExoPlayer.Builder(context).setMediaSourceFactory(mediaSourceFactory).build()
        /*   val exoPlayer1 = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.Builder()
                .setUri(videoUri)
                .setAdsConfiguration(MediaItem.AdsConfiguration.Builder(adTagUri).build())
                .build()

            val mediaSource =
                DefaultMediaSourceFactory(context)
                    .setAdsLoaderProvider { adsLoader }
                    .createMediaSource(mediaItem)

            setMediaSource(mediaSource)
            prepare()
            playWhenReady = true
        }
    }*/


        val exoPlayer =
            rememberSaveable(context, videoUri, saver = exoPlayerSaver(context, mediaItem2)) {
                ExoPlayer.Builder(context).build().apply {

                    //  addMediaItem(addContent)
                    //  addMediaItem(mediaItem2)
                    addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(playbackState: Int) {
                            Log.d("finished", "statechanged $playbackState")

                            when (playbackState) {
                                Player.STATE_IDLE -> Log.d("ExoPlayerState", "STATE_IDLE")
                                Player.STATE_BUFFERING -> Log.d("ExoPlayerState", "STATE_BUFFERING")
                                Player.STATE_READY -> Log.d("ExoPlayerState", "STATE_READY")
                                Player.STATE_ENDED -> {
                                    Log.d("ExoPlayerState", "STATE_ENDED - Movie finished!")
                                    coroutineScope.launch {
                                        onMovieFinished()
                                    }
                                }

                                else -> Log.d("ExoPlayerState", "Unknown state: $playbackState")


                            }


                        }
                    })









                    prepare()
                    playWhenReady = true

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

        LaunchedEffect(exoPlayer) {
            while (true) {
                delay(checkIntervalMillis)
                val currentPlayer = exoPlayer ?: continue
                val duration = currentPlayer.duration
                val currentPosition = currentPlayer.currentPosition

                if (duration != C.TIME_UNSET && currentPosition >= (duration - endOffsetMillis) && currentPosition > 0) {
                    onMovieFinished()
                    break // Or continue if you want to trigger repeatedly near the end
                }

                Log.d("player", "current position $currentPosition")
                Log.d("player", "duration  $duration")
            }


        }










        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black))
        {


            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp),

                factory = {
                    PlayerView(it).apply {
                        player = exoPlayer




                        useController = true // Show playback controls

                        resizeMode =
                            AspectRatioFrameLayout.RESIZE_MODE_FILL // 🔥 This removes padding!
                        layoutParams = FrameLayout.LayoutParams(
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



            Text("Title")


        }


        /* factory = {
             CustomPlayerView(context, exoPlayer)
         },*/


    }




