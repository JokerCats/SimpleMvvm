package net.jkcats.simplemvvm.features.splash

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import net.jkcats.simplemvvm.R
import net.jkcats.simplemvvm.basics.StandardActivity
import net.jkcats.simplemvvm.features.main.MainActivity
import net.jkcats.simplemvvm.simple.upgrade.UpgradeModel

class SplashActivity  : StandardActivity<SplashModel>(){

    override fun setPageResID() = R.layout.activity_splash

    override fun initViews() = initAnimation()

    private fun initAnimation() {
        val animation = AlphaAnimation(0f, 1f).apply {
            duration = 1000
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) = Unit
                override fun onAnimationRepeat(animation: Animation) = Unit
                override fun onAnimationEnd(animation: Animation) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
            })
        }
        findViewById<LinearLayout>(R.id.splash_container_rl).startAnimation(animation)
    }
}