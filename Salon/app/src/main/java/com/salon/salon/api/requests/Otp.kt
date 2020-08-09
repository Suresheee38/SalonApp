package com.salon.salon.api.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.salon.salon.api.Codable

data class Otp(@SerializedName("phone_number") @Expose val phone_number: String): Codable
