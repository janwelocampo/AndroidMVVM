package com.janwelcris.mvvp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.janwelcris.mvvp.base.BaseActivity
import com.janwelcris.mvvp.databinding.FragmentSlideshowBinding
import com.janwelcris.mvvp.databinding.ScreenSplashBinding
import com.janwelcris.mvvp.ui.main.ScreenMain
import com.janwelcris.mvvp.utils.gone
import com.janwelcris.mvvp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : BaseActivity() {

    private lateinit var screenBinding: ScreenSplashBinding

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenBinding = ScreenSplashBinding.inflate(layoutInflater)
        setContentView(screenBinding.root)

        initObservations()

        viewModel.initSplashScreen()
    }

    private fun initObservations() {
        viewModel.uiStateLiveData.observe(this){
            when(it){
                is LoadingState ->{
                    screenBinding.lottieSplash.visible()
                }
                is ContentState ->{
                    screenBinding.lottieSplash.gone()
                    val intent = Intent(this, ScreenMain::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

}