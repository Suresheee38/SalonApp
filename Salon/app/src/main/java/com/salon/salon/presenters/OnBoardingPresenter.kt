package com.salon.salon.presenters

import android.content.Intent
import android.preference.PreferenceManager
import com.salon.salon.activities.LogInActivity
import com.salon.salon.activities.onBoarding.OnBoardingActivity
import com.salon.salon.interfaces.onBoarding.OnBoardingInterface
import com.salon.salon.api.requests.OnBoarding
import com.salon.salon.api.requests.OnBoardingElement
import kotlin.jvm.java

class OnBoardingPresenter(private val activity: OnBoardingActivity): OnBoardingInterface {

    private val sampleOnboardingJson = """
{
  "OnBoarding": [
      {
        "buttonText": "Next",
        "content": "First Page",
        "imageSrc": "www.example.com"
     },{
        "buttonText": "Next",
        "content": "Second Page",
        "imageSrc": "www.example.com"
     },{
       "buttonText": "Finish",
        "content": "Last Page",
        "imageSrc": "www.example.com"
     }
  ]
}
"""
    override var onBoardingContentLists: ArrayList<OnBoarding>?
        get() = OnBoardingElement.fromJson(sampleOnboardingJson)?.onBoarding
        set(value) {
            onBoardingContentLists = value
        }

    var screenCounts: Int = 0
        get() = onBoardingContentLists?.count() ?: 0

    fun onBoardingButtonText(position: Int?): String {
        var pos = position ?: 0
        return onBoardingContentLists?.get(pos)?.buttonText ?: ""
    }

    fun finishActivity(position: Int?) {
        var pos = position ?: 0
        val text = onBoardingContentLists?.get(pos)?.buttonText ?: ""
        if(text == "Finish") {

            PreferenceManager.getDefaultSharedPreferences(activity).edit().apply {
                putBoolean("isFirstTimeUser", true)
                apply()
            }

            val loginIntent = Intent(activity,  LogInActivity:: class.java)
            activity.startActivity(loginIntent)
            activity.finish()
        }
    }
}