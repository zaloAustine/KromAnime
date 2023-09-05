package com.zalo.kromanime.ui.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.zalo.kromanime.R
import com.zalo.kromanime.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val SPLASH_DELAY: Long = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            val mainIntent = Intent(this, HomeActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DELAY)

        animation()
    }

    private fun animation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.text_character_drop)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.dropTextView.visibility = View.VISIBLE
            }
            override fun onAnimationEnd(animation: Animation?) {
            }
            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        binding.dropTextView.startAnimation(animation)
    }
}