package com.salon.salon.api.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.salon.salon.api.Codable

data class User(@SerializedName("phone_number") @Expose val phone_number: String,@SerializedName("email") @Expose val email: String,
                @SerializedName("name")  @Expose val name: String, @SerializedName("city_id") @Expose val city_id: String,
                @SerializedName("state_id")  @Expose val state_id: String, @SerializedName("country_id") @Expose val country_id: String)
data class SignUp(@SerializedName("user") @Expose val user: User): Codable