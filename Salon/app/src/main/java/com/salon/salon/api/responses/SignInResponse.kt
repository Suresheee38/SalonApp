package com.salon.salon.api.responses

import com.salon.salon.api.Codable

data class SignInResponse (val id: String, val email: String, val ym: String, val name: String, val phone_number: String): Codable
