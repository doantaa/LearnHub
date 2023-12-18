package com.cious.learnhub.ui.detail

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.OrientationEventListener
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSource
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.data.repository.CourseRepositoryImpl
import com.cious.learnhub.databinding.ActivityCourseDetailBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.ui.detail.adapter.MyPagerAdapter
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.proceedWhen
import com.google.android.material.tabs.TabLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

class CourseDetailActivity : AppCompatActivity() {

    private val binding: ActivityCourseDetailBinding by lazy {
        ActivityCourseDetailBinding.inflate(layoutInflater)
    }

    private val tabLayout: TabLayout by lazy {
        binding.tlDetail
    }

    private val viewPager2: ViewPager2 by lazy {
        binding.viewPager2
    }

    private val adapter: MyPagerAdapter by lazy {
        MyPagerAdapter(supportFragmentManager, lifecycle)
    }

    private val windowInsetsController: WindowInsetsControllerCompat by lazy {
        WindowCompat.getInsetsController(window, window.decorView)
    }

    private var youtubePlayer: YouTubePlayer? = null

    private var previousOrientation: Int = -1

    private var isFullScreen = false

    private val viewModel: CourseDetailViewModel by viewModels {
        val service = CourseService.invoke(ChuckerInterceptor(this))
        val dataSource = CourseApiDataSource(service)
        val repository = CourseRepositoryImpl(dataSource)
        GenericViewModelFactory.create(CourseDetailViewModel(intent.extras, repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showLayout()
        youtubePlayer()
        observeData()
        invokeData()
//        bindCourse()
      Toast.makeText(this, viewModel.courseId.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun invokeData() {
        val id = viewModel.courseId ?: 0
        viewModel.getCourseById(id)
    }

    private fun observeData() {
        viewModel.courses.observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    it.payload.let {
                        binding.tvTitleClass.text = it?.title
                    }
                }
            )
        }
    }

    private fun bindCourse() {
//        viewModel.getCourseById(id)
//        Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT).show()
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
                    youTubePlayer.loadVideo("dQw4w9WgXcQ", 0f)
                }
            }, iFramePlayerOptions)
        }
        lifecycle.addObserver(binding.youtubePlayerView)
    }

    private fun showLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Tentang"))
        tabLayout.addTab(tabLayout.newTab().setText("Materi Kelas"))

        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        isFullScreen = false
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        binding.youtubePlayerView.isVisible = true
        binding.flFullscreen.apply {
            isVisible = false
            removeAllViews()
        }
    }

    private fun enterFullScreen(view: View) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        isFullScreen = true
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        binding.youtubePlayerView.isVisible = false
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
    }
}