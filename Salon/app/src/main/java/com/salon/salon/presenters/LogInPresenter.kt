package com.salon.salon.presenters

import com.google.gson.Gson
import com.salon.salon.api.ResultType
import com.salon.salon.api.SError
import com.salon.salon.api.WebServiceManager
import com.salon.salon.api.requests.Otp
import com.salon.salon.api.requests.SignIn
import com.salon.salon.api.responses.OtpResponse
import com.salon.salon.api.responses.SignInResponse
import com.salon.salon.extensions.isValidPhoneNumber
import retrofit2.Response

class LogInPresenter  {

    var otpResponse: Response<OtpResponse>? = null
    var signInResponse: Response<SignInResponse>? = null
    var failureResponse: SError? = null
    lateinit var lastSavedOtpValue: String

    fun shouldGetOtp(phoneNumber: String): Boolean {
        return phoneNumber.isNotBlank() && phoneNumber.isValidPhoneNumber()
    }

    fun canProceedToSubmit(phoneNumber: String, otp: String): Boolean {
        return phoneNumber.isNotBlank() && phoneNumber.isValidPhoneNumber() && otp.isNotBlank()
    }


    fun getOtp(phoneNumber: String, completion: (Response<OtpResponse>?, SError?) -> (Unit)) {
        val parameters = HashMap<String, String>()
        parameters.put("phone_number", phoneNumber)

        var manager = WebServiceManager<OtpResponse, SError>()
        val gSon = Gson()
        var jsonString = gSon.toJson(parameters)
        var mType = gSon.fromJson<Otp>(jsonString, Otp::class.java)

        manager.completion = { (it)
            when (it) {
                is ResultType.Success<Response<OtpResponse>, SError> -> otpResponse = it.call
                is ResultType.Failure<Response<OtpResponse>, SError> -> failureResponse = it.error
            }
            completion(otpResponse, failureResponse)
        }
        manager.apply {
            execute(apiInterface.getOtp(mType))
        }
    }


    fun signIn(phoneNumber: String, otp: String, completion: (Response<SignInResponse>?, SError?) -> (Unit)) {
        val parameters = HashMap<String, String>()
        parameters.put("phone_number", phoneNumber)
        parameters.put("otp_value", otp)

        var manager = WebServiceManager<SignInResponse, SError>()
        val gSon = Gson()
        var jsonString = gSon.toJson(parameters)
        var mType = gSon.fromJson<SignIn>(jsonString, SignIn::class.java)

        manager.completion = { (it)
            when (it) {
                is ResultType.Success<Response<SignInResponse>, SError> -> signInResponse = it.call
                is ResultType.Failure<Response<SignInResponse>, SError> -> failureResponse = it.error
            }
            completion(signInResponse, failureResponse)
        }
        manager.apply {
            execute(apiInterface.getSignInDetails(mType))
        }
    }
}