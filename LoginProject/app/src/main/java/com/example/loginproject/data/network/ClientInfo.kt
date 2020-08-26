package com.example.loginproject.data.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class AccessToken(
    @SerializedName("token_type") val tokenType: String = "",
    @SerializedName("access_token") val accessToken: String = "",
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    @SerializedName("expiration_date") val expirationDate: Calendar? = null
) : Parcelable {
    fun isExpired(): Boolean =
        expirationDate != null &&
                Calendar.getInstance().after(expirationDate)
}

@Parcelize
data class ClientInfo (
    @SerializedName("id") val id: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("bucket") val bucket: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("name") val name: String?,
    @SerializedName("background_color") val backgroundColor: String?,
    @SerializedName("button_color") val buttonColor: ButtonColor?,
    @SerializedName("logo_image") val logoImage: String?,
    @SerializedName("terms_condition_url") val termsConditionUrl: String?,
    @SerializedName("sign_up_type") val signUpType: ArrayList<SignUpType>?
) : Parcelable

@Parcelize
data class ButtonColor(
    @SerializedName("type") val type: String,
    @SerializedName("gradient_type") val gradientType: String?,
    @SerializedName("color") val colors: List<String>,
    @SerializedName("animation") val animation: Boolean
) : Parcelable

@Parcelize
data class RevokeAccessTokenBody(
    @field:SerializedName("access_token") var accessToken: String
) : Parcelable

@Parcelize
data class NewUserBody(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSecret: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String? = null
) : Parcelable

@Parcelize
data class SmsCodeRequestBody(
    val code: String
) : Parcelable

@Parcelize
data class PasswordRequestBody(
    val password: String
) : Parcelable

@Parcelize
enum class SignUpType : Parcelable {
    @SerializedName("both") BOTH,
    @SerializedName("phone_number") PHONE,
    @SerializedName("email") EMAIL
}