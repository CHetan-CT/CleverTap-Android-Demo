package com.example.ctdemo

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.clevertap.android.sdk.CTExperimentsListener
import com.clevertap.android.sdk.CTInboxListener
import com.clevertap.android.sdk.CTInboxStyleConfig
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent
import com.example.ctdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), CTInboxListener, DisplayUnitListener,
    CTExperimentsListener {
    private lateinit var binding: ActivityMainBinding
    private val displayUnits: ArrayList<CleverTapDisplayUnitContent> = ArrayList()
    private lateinit var adapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonInApp.setOnClickListener {
            //            MyApplication.getCleverTapDefaultInstance().removeValueForKey("is_test")


            val intent = Intent(this, InAppActivity::class.java)
            startActivity(intent)
            val cleverTapAPI =
                CleverTapAPI.getDefaultInstance(applicationContext)
// Creating a Notification Channel With Sound Support
// Creating a Notification Channel With Sound Support
            CleverTapAPI.createNotificationChannel(
                applicationContext,
                "got",
                "Game of Thrones",
                "Game Of Thrones",
                NotificationManager.IMPORTANCE_MAX,
                true,
                "gameofthrones.mp3"
            )


//            pushCreateProfileClevertap();
        }

//        binding.buttonNativeDisplay.setOnClickListener {
//            MyApplication.getCleverTapDefaultInstance().pushEvent("Native Display")
//        }
//
//        MyApplication.getCleverTapDefaultInstance().ctNotificationInboxListener = this
//
//        MyApplication.getCleverTapDefaultInstance().initializeInbox()
//
//        MyApplication.getCleverTapDefaultInstance().setDisplayUnitListener(this)
//
//        MyApplication.getCleverTapDefaultInstance().ctExperimentsListener = this
//
//        MyApplication.getCleverTapDefaultInstance().registerIntegerVariable("CTA")
//        MyApplication.getCleverTapDefaultInstance().registerBooleanVariable("imgUrl")

    }

//    val cleverTapAPI: CleverTapAPI? = CleverTapAPI.getDefaultInstance(this)

//    fun pushCreateProfileClevertap() {
//        val profileUpdate = HashMap<String, Any>()
//        profileUpdate.put("Name", "Rachel ")
//        profileUpdate.put("fullname", "Rachel ")
//        profileUpdate.put("Identity", 56670)
//        profileUpdate.put("Email", "rachelsugehaa@mail.com")
//        profileUpdate.put("Phone", "+6281319328629")
//        profileUpdate.put("Gender", "F")
//        profileUpdate.put("Available Cash", 675440.41)
//        profileUpdate.put("Available Koin", 0)
//        profileUpdate.put("Preference AP KOINP2P", 0)
//        profileUpdate.put("Auto Purchase KOINP2P", "")
//        cleverTapAPI?.onUserLogin(profileUpdate)
//    }

    override fun inboxDidInitialize() {
        binding.buttonAppInbox.setOnClickListener {

            val tabs: ArrayList<String> = ArrayList()
            tabs.add("Promotions")
            tabs.add("Offers")
            tabs.add("Others") //We support upto 2 tabs only. Additional tabs will be ignored

            val styleConfig = CTInboxStyleConfig()
            styleConfig.tabs = tabs //Do not use this if you don't want to use tabs

            styleConfig.tabBackgroundColor = "#FF0000" //provide Hex code in string ONLY

            styleConfig.selectedTabIndicatorColor = "#0000FF"
            styleConfig.selectedTabColor = "#000000"
            styleConfig.unselectedTabColor = "#FFFFFF"
            styleConfig.backButtonColor = "#FF0000"
            styleConfig.navBarTitleColor = "#FF0000"
            styleConfig.navBarTitle = "MY INBOX"
            styleConfig.navBarColor = "#FFFFFF"
            styleConfig.inboxBackgroundColor = "#00FF00"

//            MyApplication.getCleverTapDefaultInstance()
//                .showAppInbox(styleConfig) //Opens activity with Tabs

        }
    }

    override fun inboxMessagesDidUpdate() {

    }

    override fun onDisplayUnitsLoaded(units: java.util.ArrayList<CleverTapDisplayUnit>?) {
        for (i in 0 until units!!.size) {
            val unit = units[i]

            Log.d("Unit ID", unit.unitID)
            prepareDisplayView(unit)
        }
    }

    private fun prepareDisplayView(unit: CleverTapDisplayUnit) {
        displayUnits.clear()
        displayUnits.addAll(unit.contents)

        adapter = CarouselAdapter(displayUnits, this)
        binding.viewPager.adapter = adapter
//        binding.tvNativeDisplay.text = unit.customExtras["title"]
    }

    override fun CTExperimentsUpdated() {

//        val boolVar =
//            MyApplication.getCleverTapDefaultInstance().getBooleanVariable("testVariable2", null)
//
//        val intVar =
//            MyApplication.getCleverTapDefaultInstance().getIntegerVariable("testVariable1", -1)
//
//        val cta =
//            MyApplication.getCleverTapDefaultInstance().getStringVariable("CTA", null)
//
//        val url =
//            MyApplication.getCleverTapDefaultInstance().getStringVariable("imgUrl", null)
//
//        Log.d("CTA Variable", "Value : $cta")
//        Log.d("ImgUrl Variable", "Value : $url")

    }
}
