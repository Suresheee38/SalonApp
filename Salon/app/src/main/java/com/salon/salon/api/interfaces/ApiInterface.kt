package com.salon.salon.api.interfaces

import com.salon.salon.api.responses.SignUpResponse
import com.salon.salon.api.requests.SignUp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @POST("create_user_info")
    fun getSignUpDetails(@Body postBody: SignUp?): Call<SignUpResponse>
}

interface RequestParams<K,V> {
    var params: Map<K,V>?
}