package com.salon.salon.api.interfaces

import com.salon.salon.api.requests.Otp
import com.salon.salon.api.requests.SignIn
import com.salon.salon.api.responses.SignUpResponse
import com.salon.salon.api.requests.SignUp
import com.salon.salon.api.responses.OtpResponse
import com.salon.salon.api.responses.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @POST("create_user_info")
    fun getSignUpDetails(@Body postBody: SignUp?): Call<SignUpResponse>

    @POST("otp_temp")
    fun getOtp(@Body postBody: Otp?): Call<OtpResponse>

    @POST("verify_otp_info")
    fun getSignInDetails(@Body postBody: SignIn?): Call<SignInResponse>
}

interface RequestParams<K,V> {
    var params: Map<K,V>?
}