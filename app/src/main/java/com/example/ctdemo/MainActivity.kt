package com.example.ctdemo

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.clevertap.android.sdk.*
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent
import com.example.ctdemo.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), CTInboxListener, DisplayUnitListener,
    CTExperimentsListener, InAppNotificationButtonListener, InboxMessageButtonListener {
    private lateinit var binding: ActivityMainBinding
    private val displayUnits: ArrayList<CleverTapDisplayUnitContent> = ArrayList()
    private lateinit var adapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonInApp.setOnClickListener {
            val intent = Intent(this, InAppActivity::class.java)
            startActivity(intent)
        }

        binding.buttonNativeDisplay.setOnClickListener {
            MyApplication.getCleverTapDefaultInstance().pushEvent("Native Display")
        }
        CleverTapAPI.createNotificationChannel(
            applicationContext, "channel_id",
            "channel_name", "channel_description",
            NotificationManager.IMPORTANCE_MAX, true
        )
//        MyApplication.getCleverTapDefaultInstance().setInAppNotificationButtonListener(this)

//        MyApplication.getCleverTapDefaultInstance().ctNotificationInboxListener = this
        Log.d(
            "Inbox message count",
            "" + MyApplication.getCleverTapDefaultInstance().allInboxMessages
        )

        
//
        MyApplication.getCleverTapDefaultInstance().initializeInbox()
//
        MyApplication.getCleverTapDefaultInstance().setDisplayUnitListener(this)
//
//        MyApplication.getCleverTapDefaultInstance().ctExperimentsListener = this
        MyApplication.getCleverTapDefaultInstance().setInboxMessageButtonListener(this)
    }

    override fun inboxDidInitialize() {
        binding.buttonAppInbox.setOnClickListener {
            MyApplication.getCleverTapDefaultInstance().pushEvent("App Inbox")

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
            MyApplication.getCleverTapDefaultInstance().showAppInbox(styleConfig)

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

    }

    override fun onInAppButtonClick(payload: HashMap<String, String>?) {
        Log.d("Clicked", "" + payload?.size)

    }

    override fun onInboxButtonClick(payload: HashMap<String, String>?) {
        if (payload != null) {
            Log.d("Test", "" + payload.keys.toString())
            abc("")
        }
    }

    fun abc(abc: CharSequence) {

    }
}
