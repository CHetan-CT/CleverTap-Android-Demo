package com.example.ctdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.clevertap.android.sdk.CTExperimentsListener
import com.clevertap.android.sdk.CTInboxStyleConfig
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent
import com.clevertap.android.sdk.featureFlags.CTFeatureFlagsController
import com.clevertap.android.sdk.product_config.CTProductConfigController
import com.clevertap.android.sdk.product_config.CTProductConfigListener
import com.example.ctdemo.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


class MainActivity : AppCompatActivity(), /*CTInboxListener,*/ DisplayUnitListener,
    CTExperimentsListener {
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

        setUpProductExperiences()
        setUpFeatureFlag()

        binding.buttonNativeDisplay.setOnClickListener {

            val props = HashMap<String, String>()
            props["test_prop"] = "worked"
            MyApplication.getCleverTapDefaultInstance().pushEvent(
                "Native Display",
                props as Map<String, Any>?
            )
        }

//        MyApplication.getCleverTapDefaultInstance().ctNotificationInboxListener = this
        //Initialize the inbox and wait for callbacks on overridden methods
        MyApplication.getCleverTapDefaultInstance().initializeInbox()

        MyApplication.getCleverTapDefaultInstance().setDisplayUnitListener(this)
        MyApplication.getCleverTapDefaultInstance().ctExperimentsListener = this

        MyApplication.getCleverTapDefaultInstance().registerIntegerVariable("testVariable1")
        MyApplication.getCleverTapDefaultInstance().registerBooleanVariable("testVariable2")


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

            MyApplication.getCleverTapDefaultInstance()
                .showAppInbox(styleConfig) //Opens activity with Tabs
        }

    }

    private fun setUpFeatureFlag() {
        val featureFlagInstance: CTFeatureFlagsController =
            MyApplication.getCleverTapDefaultInstance().featureFlag()

        MyApplication.getCleverTapDefaultInstance()
            .setCTFeatureFlagsListener {
                val boolean = featureFlagInstance.get("prime", false)
                Log.d("FEATURE_FLAG", "prime = $boolean")
            }
    }

    private fun setUpProductExperiences() {
        val productConfigInstance: CTProductConfigController =
            MyApplication.getCleverTapDefaultInstance().productConfig()
        val map: HashMap<String, Any> = HashMap()
        map["Button Color"] = "624"
        productConfigInstance.setDefaults(map)

        MyApplication.getCleverTapDefaultInstance()
            .setCTProductConfigListener(object : CTProductConfigListener {
                override fun onInit() {
                    productConfigInstance.fetch()
                    productConfigInstance.activate()
                }

                override fun onFetched() {
                    productConfigInstance.activate()
                }

                override fun onActivated() {
                    val color = productConfigInstance.getString("Button Color")
                    val buttonText = productConfigInstance.getString("Button text")

                    Log.d("PROD_EXP", color)
                    Log.d("AB_TEST", buttonText)
                }
            })
    }


//    override fun inboxDidInitialize() {
//
//        val inboxMessages: java.util.ArrayList<CTInboxMessage> =
//            MyApplication.getCleverTapDefaultInstance()
//                .allInboxMessages;
//
//        Log.d("inboxMessages", "Value : ${inboxMessages.size}")
//
//        binding.buttonAppInbox.setOnClickListener {
//
//            val tabs: ArrayList<String> = ArrayList()
//            tabs.add("Promotions")
//            tabs.add("Offers")
//            tabs.add("Others") //We support upto 2 tabs only. Additional tabs will be ignored
//
//            val styleConfig = CTInboxStyleConfig()
//            styleConfig.tabs = tabs //Do not use this if you don't want to use tabs
//
//            styleConfig.tabBackgroundColor = "#FF0000" //provide Hex code in string ONLY
//
//            styleConfig.selectedTabIndicatorColor = "#0000FF"
//            styleConfig.selectedTabColor = "#000000"
//            styleConfig.unselectedTabColor = "#FFFFFF"
//            styleConfig.backButtonColor = "#FF0000"
//            styleConfig.navBarTitleColor = "#FF0000"
//            styleConfig.navBarTitle = "MY INBOX"
//            styleConfig.navBarColor = "#FFFFFF"
//            styleConfig.inboxBackgroundColor = "#00FF00"
//
//            MyApplication.getCleverTapDefaultInstance()
//                .showAppInbox(null) //Opens activity tith Tabs
//        }
//    }
//
//
//    override fun inboxMessagesDidUpdate() {
//
//    }

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

        val boolVar =
            MyApplication.getCleverTapDefaultInstance().getBooleanVariable("testVariable2", null)

        val intVar =
            MyApplication.getCleverTapDefaultInstance().getIntegerVariable("testVariable1", -1)

        Log.d("Boolean Variable", "Value : $boolVar")
        Log.d("Integer Variable", "Value : $intVar")

    }
}
