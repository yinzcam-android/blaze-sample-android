package com.wscsports.android.blaze.sampleapp

import android.app.Application
import com.blaze.blazesdk.shared.BlazeSDK
import com.blaze.blazesdk.prefetch.models.BlazeCachingLevel
import com.blaze.gam.BlazeGAM
import com.blaze.ima.BlazeIMA
import com.wscsports.android.blaze.sampleapp.ads.gam.BannerAdsDelegate
import com.wscsports.android.blaze.sampleapp.ads.gam.CustomNativeAdsDelegate
import com.wscsports.android.blaze.sampleapp.ads.ima.IMADelegate
import com.wscsports.android.blaze.sampleapp.core.Delegates

/** Use the [Application] class to initialize the BlazeSDK.
 * Note - you won't be able to use BlazeSDK before calling BlazeSDK.init
 * */
class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        BlazeSDK.init(
            // Please provide valid API-KEY here
            apiKey = "739741c1465c4d0681b39e4b3e633730",
            cachingLevel = BlazeCachingLevel.DEFAULT,
            cachingSize = 512,
            sdkDelegate = Delegates.globalDelegate,
            playerEntryPointDelegate = Delegates.playerEntryPointDelegate,
            completionBlock = {
                logd("BlazeSDK.init completionBlock")
                enableAds()
            },
            errorBlock = { error ->
                logd("BlazeSDK.init errorBlock -> , Init Error = $error")
            }
        )
    }

    private fun enableAds() {
        BlazeIMA.enableAds(
            delegate = IMADelegate
        )
        BlazeGAM.enableCustomNativeAds(
            context = applicationContext,
            delegate = CustomNativeAdsDelegate
        )
        BlazeGAM.enableBannerAds(
            context = applicationContext,
            delegate = BannerAdsDelegate
        )
    }

}
