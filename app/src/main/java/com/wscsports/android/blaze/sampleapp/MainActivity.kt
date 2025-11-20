package com.wscsports.android.blaze.sampleapp

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blaze.blazesdk.data_source.BlazeDataSourceType
import com.blaze.blazesdk.data_source.BlazeWidgetLabel
import com.blaze.blazesdk.features.moments.container.BlazeMomentsPlayerContainer
import com.wscsports.android.blaze.sampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainActivityNavigation) as? NavHostFragment)?.navController
    }
    private val volumeViewModel: VolumeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        lockOrientation()
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        prepareMomentsContainer()
    }

    private fun initUi() {
        binding.apply {
            navController?.let {
                mainActivityBottomNavigationView.setupWithNavController(it)
            }
        }
    }

    private fun prepareMomentsContainer() {
        BlazeMomentsPlayerContainer.prepareMoments(
            containerId = "blaze-moments-container-unique-id",
            dataSource = BlazeDataSourceType.Labels(BlazeWidgetLabel.singleLabel("all-access"))
        )
    }

    // Observing user increasing/decreasing volume, and updating the SDK
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            volumeViewModel.onVolumeChanged()
            true
        } else {
            super.onKeyUp(keyCode, event)
        }
    }


    /**
     * Locks the screen orientation based on the device type.
     *
     * For tablet devices, the function allows user-controlled orientation.
     * This means the orientation is not locked and can change based on how the user rotates the device.
     *
     * For non-tablet devices, the function locks the screen orientation to portrait mode.
     * In portrait mode, the orientation is fixed and does not change with device rotation.
     *
     * Note: This is an example implementation for client apps. Clients may decide to use or modify
     * this function based on their specific requirements. We recommend this approach for optimal
     * user experience on different device types.
     */
    private fun lockOrientation() {
        val isTabletDevice = ((resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)

        requestedOrientation = if (isTabletDevice) {
            ActivityInfo.SCREEN_ORIENTATION_USER
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

}