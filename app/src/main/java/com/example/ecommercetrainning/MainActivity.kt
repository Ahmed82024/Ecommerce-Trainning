package com.example.ecommercetrainning

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ecommercetrainning.databinding.ActivityMainBinding
import com.example.ecommercetrainning.utils.AddToCartException
import com.example.ecommercetrainning.utils.CrashlyticsUtils


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)


        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            CrashlyticsUtils.sendLogToCrashlytics("crash btn clicked","btn","clicked","crash")// Force a crash

            val msg="Crash btn click"
            CrashlyticsUtils.sendCustomLogToCrashlytics<AddToCartException>(
                msg, Pair(CrashlyticsUtils.ADD_TO_CART,"adding to cart ex")
            )
            throw AddToCartException(msg)
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

    }

    private fun initSplashScreen(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            installSplashScreen()
            // Add a callback that's called when the splash screen is animating to the
            // app content.
            splashScreen.setOnExitAnimationListener {splashScreenView->
                //create your custom animation
                val slideUp=ObjectAnimator.ofFloat(
                    splashScreenView, View.TRANSLATION_Y,
                    0f,
                    splashScreenView.height.toFloat()
                )
                slideUp.interpolator=AnticipateInterpolator()
                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        }else{
            setTheme(R.style.Theme_EcommerceTrainning)
        }
    }
}