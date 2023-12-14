package com.cious.learnhub.ui.myclass.detail

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.cious.learnhub.databinding.FragmentCourseDetailBinding
import com.cious.learnhub.ui.myclass.detail.adapter.MyPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class CourseDetailFragment : Fragment() {

    private lateinit var binding: FragmentCourseDetailBinding
    private val tabLayout: TabLayout by lazy {
        binding.tlDetail
    }
    private val viewPager2: ViewPager2 by lazy {
        binding.viewPager2
    }

    private val adapter: MyPagerAdapter by lazy {
        MyPagerAdapter(childFragmentManager, lifecycle)
    }

    private val windowInsetsController: WindowInsetsControllerCompat by lazy {
        WindowCompat.getInsetsController(requireActivity().window, requireActivity().window.decorView)
    }

    private var youtubePlayer: YouTubePlayer? = null

    private var previousOrientation: Int = -1

    private var isFullScreen = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLayout()
        youtubePlayer()
    }

    private fun youtubePlayer() {
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        initYoutube()
        val orientationEventListener = object : OrientationEventListener(requireContext()) {
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
                    requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                }
                previousOrientation = newOrientation
            }
        }
        val autoRotationOn = Settings.System.getInt(
            requireActivity().contentResolver,
            Settings.System.ACCELEROMETER_ROTATION, 0
        ) == 1
        if (autoRotationOn) {
            orientationEventListener.enable()
        } else {
            orientationEventListener.disable()
        }
    }

    private fun initYoutube() {
        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1) // enable full screen button
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
                    this@CourseDetailFragment.youtubePlayer = youTubePlayer
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
        val oldOrientation = requireActivity().requestedOrientation
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
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        isFullScreen = false
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        binding.youtubePlayerView.isVisible = true
        binding.flFullscreen.apply {
            isVisible = false
            removeAllViews()
        }
    }

    private fun enterFullScreen(view: View) {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        isFullScreen = true
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        binding.youtubePlayerView.isVisible = false
        binding.flFullscreen.apply {
            isVisible = true
            addView(view)
        }
    }
}