package com.salon.salon.presenters
import com.google.gson.Gson
import com.salon.salon.api.ResultType
import com.salon.salon.api.SError
import com.salon.salon.api.WebServiceManager
import com.salon.salon.api.requests.SignUp
import com.salon.salon.api.responses.SignUpResponse
import com.salon.salon.custom.ValueType
import com.salon.salon.extensions.isEmailValid
import com.salon.salon.extensions.isValidPhoneNumber
import retrofit2.Response

class SignUpPresenter() {

    var signUpResponse: Response<SignUpResponse>? = null
    var failureResponse: SError? = null

    enum class TextFieldType {
        PHONE_NUMBER,
        EMAIL,
        NAME,
        CITY,
        STATE,
        COUNTRY
    }

    private fun composeParams(parameters: HashMap<String, String>): HashMap<String, HashMap<String, String>> {
        val hashMap = HashMap<String, String>()
        hashMap.put("name", parameters.get(ValueType.NAME.params) ?: "")
        hashMap.put("city_id", parameters.get(ValueType.CITY.params) ?: "")
        hashMap.put("country_id", parameters.get(ValueType.COUNTRY.params) ?: "")
        hashMap.put("email", parameters.get(ValueType.EMAIL.params) ?: "")
        hashMap.put("phone_number", parameters.get(ValueType.PHONE_NUMBER.params) ?: "")
        hashMap.put("state_id", parameters.get(ValueType.STATE.params) ?: "")

        val hashMapFinal = HashMap<String, HashMap<String, String>>()
        hashMapFinal["user"] = hashMap
        return hashMapFinal
    }

    fun getSignUpWebservices(parameters: HashMap<String, String>, completion: (Response<SignUpResponse>?, SError?) -> (Unit)) {

        var manager = WebServiceManager<SignUpResponse, SError>()
        val gSon = Gson()
        var jsonString = gSon.toJson(composeParams(parameters))
        var mType = gSon.fromJson<SignUp>(jsonString, SignUp:: class.java)

        manager.completion = { (it)
            when (it) {
                is ResultType.Success<Response<SignUpResponse>, SError> -> signUpResponse = it.call
                is ResultType.Failure<Response<SignUpResponse>, SError> -> failureResponse = it.error
            }
            completion(signUpResponse, failureResponse)
        }
        manager.apply {
            execute(apiInterface.getSignUpDetails(mType))
        }
    }

    fun isAllValid(consolidatedValues: HashMap<String,String>): String {

        if (consolidatedValues.values.filter { it.isNotBlank() }.count() != ValueType.values().count()) {
            return "Please Enter all the fields"
        }

        for (invalidKey in consolidatedValues.keys) {

            var invalidValue = consolidatedValues[invalidKey] ?: ""

            if (invalidKey == ValueType.EMAIL.params && !invalidValue.isEmailValid()) {
                return "Please Enter Valid Email"
            } else if (invalidKey == ValueType.PHONE_NUMBER.params && !invalidValue.isValidPhoneNumber()) {
                return "Please Enter Valid Mobile Number"
            }
        }
        return  ""
    }
 }