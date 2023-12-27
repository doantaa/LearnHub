package com.cious.learnhub.ui.detail

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.OrientationEventListener
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityCourseDetailBinding
import com.cious.learnhub.ui.detail.adapter.MyPagerAdapter
import com.cious.learnhub.utils.proceedWhen
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CourseDetailActivity : AppCompatActivity() {

    private val binding: ActivityCourseDetailBinding by lazy {
        ActivityCourseDetailBinding.inflate(layoutInflater)
    }

    private val windowInsetsController: WindowInsetsControllerCompat by lazy {
        WindowCompat.getInsetsController(window, window.decorView)
    }

    private var youtubePlayer: YouTubePlayer? = null

    private var previousOrientation: Int = -1

    private var isFullScreen = false

    private val viewModel: CourseDetailViewModel by viewModel {
        parametersOf(intent?.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showLayout()
        youtubePlayer()
        observeData()
        invokeData()
    }

    private fun invokeData() {
        val id = viewModel.courseId ?: 0
        viewModel.getCourseById(id)
    }

    private fun observeData() {
        viewModel.detailCourse.observe(this){
            it.proceedWhen(
                doOnSuccess = {item ->
                    binding.shimmerDetailCourseActivity.isVisible = false
                    binding.cvHeaderClassInfo.isVisible = true
                    binding.youtubePlayerView.isVisible = true
                    binding.tlDetail.isVisible = true
                    binding.viewPager2.isVisible = true
                    item.payload?.let {data ->
                        binding.tvTitleClass.text = data.title
                        binding.tvModule.text = buildString {
                            append(data.moduleCount)
                            append(getString(R.string.txt_sps_module))
                        }
                        binding.tvLevel.text = data.level
                        binding.tvInstructor.text = data.instructor
                        binding.tvRating.text = data.rating.toString()
                        binding.tvDuration.text = buildString {
                            append(data.totalDuration)
                            append(getString(R.string.txt_sps_minutes))
                        }
                        binding.tvTitleCategoryClass.text = data.categoryName
                        Log.d("DATA COURSE ADALAH", data.toString())
                    }
                }, doOnLoading = {
                    binding.shimmerDetailCourseActivity.isVisible = true
                    binding.cvHeaderClassInfo.isVisible = false
                    binding.youtubePlayerView.isVisible = false
                    binding.tlDetail.isVisible = false
                    binding.viewPager2.isVisible = false
                },
                doOnError = {
                    Log.d("DATA COURSE", it.exception?.message.toString())
                }
            )
        }
    }


    private fun youtubePlayer() {
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        initYoutube()
        val orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                val newOrientation = when (orientation) {
                    in 0..44 -> 0
                    in 45..134 -> 1
                    in 135..224 -> 2
                    in 225..314 -> 3
                    in 315..359 -> 0
                    else -> ORIENTATION_UNKNOWN
                }
                if (newOrientation != previousOrientation && !isFullScreen) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                }
                previousOrientation = newOrientation
            }
        }
        val autoRotationOn = Settings.System.getInt(
            contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0
        ) == 1
        if (autoRotationOn) {
            orientationEventListener.enable()
        } else {
            orientationEventListener.disable()
        }
    }

    private fun initYoutube() {
        val iFramePlayerOptions =
            IFramePlayerOptions.Builder().controls(1).fullscreen(1) // enable full screen button
                .build()
        binding.youtubePlayerView.apply {
            enableAutomaticInitialization = false
            addFullscreenListener(object : FullscreenListener {
                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                    enterFullScreen(fullscreenView)
                }

                override fun onExitFullscreen() {
                    exitFullscreen()
                }

            })
            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    this@CourseDetailActivity.youtubePlayer = youTubePlayer
                    observeDataVideo()
                }
            }, iFramePlayerOptions)
        }
        lifecycle.addObserver(binding.youtubePlayerView)
    }

    private fun observeDataVideo() {
        viewModel.detailCourse.observe(this) { detailCourseResult ->
            if (detailCourseResult != null ) {
                val modules = detailCourseResult.payload?.module
                val firstVideoUrl: String? = modules?.get(0)?.videos?.get(0)?.videoUrl
                playVideo(firstVideoUrl)
            }
        }
        viewModel.videoUrl.observe(this) { result ->
                playVideo(result)
        }
    }

    private fun playVideo(videoUrl: String?) {
        if (youtubePlayer != null && !videoUrl.isNullOrBlank()) {
            youtubePlayer?.loadVideo(videoUrl, 0f)
        }
    }


    private fun showLayout() {
        val sectionsPagerAdapter = MyPagerAdapter(this)
        binding.viewPager2.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tlDetail, binding.viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val oldOrientation = requestedOrientation
        val newOrientation = newConfig.orientation
        Log.d("MainActivity", "onConfigurationChanged Old Orientation: $oldOrientation")
        Log.d("MainActivity", "onConfigurationChanged New Orientation: $newOrientation")
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!isFullScreen) {
                youtubePlayer?.toggleFullscreen()
            }
        } else {
            if (isFullScreen) {
                youtubePlayer?.toggleFullscreen()
            }
        }
        super.onConfigurationChanged(newConfig)
    }

    private fun exitFullscreen() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        isFullScreen = false
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        binding.tvDuration.isVisible = true
        binding.tvLevel.isVisible = true
        binding.tvRating.isVisible = true
        binding.tvInstructor.isVisible = true
        binding.tvModule.isVisible = true
        binding.tvTitleCategoryClass.isVisible = true
        binding.tvTitleClass.isVisible = true
        binding.flFullscreen.apply {
            isVisible = false
            removeAllViews()
        }
    }

    private fun enterFullScreen(view: View) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        isFullScreen = true
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        binding.tvDuration.isVisible = false
        binding.tvLevel.isVisible = false
        binding.tvRating.isVisible = false
        binding.tvInstructor.isVisible = false
        binding.tvModule.isVisible = false
        binding.tvTitleCategoryClass.isVisible = false
        binding.tvTitleClass.isVisible = false
        binding.flFullscreen.apply {
            isVisible = true
            addView(view)
        }
    }

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        fun startActivity(context: Context, id: Int) {
            val intent = Intent(context, CourseDetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            context.startActivity(intent)
        }

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}