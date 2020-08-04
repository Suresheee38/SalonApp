package com.salon.salon.api.requests
import com.beust.klaxon.*
import com.salon.salon.api.Codable

private val klaxon = Klaxon()

data class OnBoardingElement(@Json(name = "OnBoarding") val onBoarding: ArrayList<OnBoarding>) {
    public fun toJson() = klaxon.toJsonString(this)
    companion object {
        public fun fromJson(json: String) = klaxon.parse<OnBoardingElement>(json)
    }
}

data class OnBoarding(val buttonText: String, val content: String, val imageSrc: String): Codable


