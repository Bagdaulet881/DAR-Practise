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
    @SerializedName("expiration_date") val expirationDate: Calendar? = null,
    @SerializedName("id_token") val id_token: String = "",
    @SerializedName("redirect_uri") val redirect_uri: String = "",
    @SerializedName("sid") val sid: String = ""


) : Parcelable {
    fun isExpired(): Boolean =
        expirationDate != null &&
                Calendar.getInstance().after(expirationDate)
}
@Parcelize
data class TempToken(
    @SerializedName("redirect_uri") val uri: String = "",
    @SerializedName("sid") val sid: String = "",
    @SerializedName("add_info") val add_info: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: String = ""
    ) : Parcelable

@Parcelize
data class TempToken2(
    @SerializedName("redirect_uri") val uri: String = "",
    @SerializedName("sid") val sid: String = ""
) : Parcelable
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
data class UserInfo (
    @SerializedName("id") val id: String,
    @SerializedName("bucket_id") val bucket_id: String,
    @SerializedName("brand_id") val brand_id: String,
    @SerializedName("email") val email: String,
    @SerializedName("email_verified") val email_verified: String?,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("phone_number_verified") val phone_number_verified: String,
    @SerializedName("pin") val pin: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("mfa_type") val mfa_type: String?,
    @SerializedName("avatar") var avatar: AvatarInfo?
) : Parcelable
@Parcelize
data class AvatarInfo (
    @SerializedName("original") val original: String,
    @SerializedName("normal") val normal: String,
    @SerializedName("thumbnail") val thumbnail: String
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
data class NewPasswordBody(
    @SerializedName("client_id") val client_id: String,
    @SerializedName("new_password") val new_password: String
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
@Parcelize
data class resetBody(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("reset_option") val clientSecret: String,
    @SerializedName("username") val username: String
) : Parcelable