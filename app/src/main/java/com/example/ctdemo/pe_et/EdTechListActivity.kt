package com.example.ctdemo.pe_et

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ctdemo.R
import com.example.ctdemo.databinding.ActivityEdTechListBinding
import com.segment.analytics.Analytics
import com.segment.analytics.Traits


class EdTechListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdTechListBinding
//    private lateinit var cleverTapAPI: CleverTapAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ed_tech_list)

        binding.cardRed.setOnClickListener {
            showProgress("Spanish")
        }

        binding.cardGray.setOnClickListener {
            showProgress("Italian")
        }

        binding.cardGreen.setOnClickListener {
            showProgress("German")
        }

        binding.cardBlue.setOnClickListener {
            showProgress("French")
        }

        val traits = Traits()

        val profile: HashMap<String, Any> = HashMap()

        profile["profile_value"] = "Chetan"
        traits.putAll(profile)
        Analytics.with(applicationContext).identify(null, traits, null)


//        MyApplication.getCleverTapDefaultInstance().pushProfile(profile)

//        binding.tvHeader.text =
//            "Hello " + MyApplication.getCleverTapDefaultInstance().getProperty("profile_value")

    }

    private fun showProgress(courseName: String) {
        val courseProperties =
            HashMap<String, Any>()
        courseProperties["course_name"] = courseName
//        MyApplication.getCleverTapDefaultInstance().pushEvent("course_selection", courseProperties)

        val intent = Intent(this, EdTechActivity::class.java)
        startActivity(intent)
    }
}
