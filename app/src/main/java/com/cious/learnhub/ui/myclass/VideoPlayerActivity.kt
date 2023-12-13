package com.cious.learnhub.ui.myclass

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityVideoPlayerBinding


class VideoPlayerActivity : AppCompatActivity() {

    private val binding: ActivityVideoPlayerBinding by lazy {
        ActivityVideoPlayerBinding.inflate(layoutInflater)
    }


    private val fullScreenButtonClickListener =
        PlayerView.FullscreenButtonClickListener { isFullScreen ->
            requestedOrientation = if (isFullScreen) {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val player = ExoPlayer.Builder(this).build()
        val videoUri = getString(R.string.link_video)
        val playerView = binding.playerView

        playerView.setFullscreenButtonClickListener(fullScreenButtonClickListener)

        playerView.player = player
        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }


}