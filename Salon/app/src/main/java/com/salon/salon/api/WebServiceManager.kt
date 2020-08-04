package com.salon.salon.api

import com.google.gson.Gson
import com.salon.salon.api.interfaces.ApiInterface
import com.salon.salon.api.requests.SignUp
import com.salon.salon.api.responses.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface Codable { }

data class SError(val description: String?) : Codable

interface RequestModelMapper{
    fun  <RequestType> initWithParams(params: Map<*,*>, clazz: Class<RequestType>)
}

sealed class ResultType<T, U> {
    class Success<T, U: Codable>(val call: T?) : ResultType<T,U>()
    class Failure<T, U: Codable>(val error: SError?):  ResultType<T,U>()
}

class WebServiceManager<ResponseType: Codable, ErrorType: Codable>:  Callback<ResponseType> {

    var apiInterface = RetrofitWrapper().createService(ApiInterface:: class.java)
    var completion: ((ResultType<Response<ResponseType>,ErrorType>) -> (Unit))? = null
    
    fun execute(type: Call<ResponseType>?) {
        type?.enqueue(this)
    }

    override fun onFailure(call: Call<ResponseType>?, t: Throwable?) {
        var failure = ResultType.Failure<Response<ResponseType>, ErrorType>(SError(description = t?.localizedMessage))
        completion?.let { it(failure) }
    }

    override fun onResponse(call: Call<ResponseType>?, response: Response<ResponseType>?) {
        var success = ResultType.Success<Response<ResponseType>,ErrorType>(response)
        completion?.let { it(success) }
    }
}