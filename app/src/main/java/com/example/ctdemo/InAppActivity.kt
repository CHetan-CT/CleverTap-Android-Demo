package com.example.ctdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.clevertap.android.sdk.InAppNotificationButtonListener
import com.example.ctdemo.databinding.ActivityInAppBinding
import com.segment.analytics.Analytics
import java.util.*

class InAppActivity : AppCompatActivity(), InAppNotificationButtonListener {

    private lateinit var binding: ActivityInAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_in_app)

        MyApplication.getCleverTapDefaultInstance().setInAppNotificationButtonListener(this)

        binding.buttonAlert.setOnClickListener {
            Analytics.with(applicationContext).track("In App Alert")
        }

        binding.buttonCover.setOnClickListener {
            Analytics.with(applicationContext).track("In App Cover")
        }

        binding.buttonCustomHtml.setOnClickListener {
            Analytics.with(applicationContext).track("In App Custom HTML-1")
        }

        binding.buttonFooter.setOnClickListener {
            Analytics.with(applicationContext).track("In App Footer")
        }

        binding.buttonHalfInterstitial.setOnClickListener {
            Analytics.with(applicationContext).track("In App Half Interstitial")
        }

        binding.buttonInterstitial.setOnClickListener {
            Analytics.with(applicationContext).track("In App Interstitial")
        }

        binding.buttonHeader.setOnClickListener {

            Analytics.with(applicationContext).track("In App Header")
        }
    }

    override fun onInAppButtonClick(payload: HashMap<String, String>?) {

    }
}
