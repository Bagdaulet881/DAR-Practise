package com.example.loginproject.data.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.android.parcel.Parcelize
import retrofit2.http.*
import java.util.*

interface ApiService {

    @GET("/api/v1/mgmt/client-info")
    fun getClientInfo(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    ): Observable<ClientInfo>

    @FormUrlEncoded
    @POST("/api/v1/oauth/token")
    @Headers("Content-type: application/x-www-form-urlencoded")
    fun grantNewAccessToken(
        @Field("code") code: String? = null,
        @Field("grant_type") type: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("username") username: String? = null,
        @Field("password") password: String? = null,
        @Field("refresh_token") refreshToken: String? = null
    ): Observable<AccessToken>

    @POST("/api/v1/oauth/revoke")
    fun revokeAccessToken(
        @Body body: RevokeAccessTokenBody?
    ): Completable
//TODO
    @POST("api/v1/oauth/signup")
    fun signUp(
        @Header("Content-Type") contentType: String,
        @Body body: NewUserBody
    ) : Observable<AccessToken>
    @POST("api/v1/oauth/signup")
    fun signUpPhone(
        @Header("Content-Type") contentType: String,
        @Body body: NewUserBody
    ) : Observable<TempToken>
//TODO
    @POST("api/v1/oauth/signup/phone/verify")
    fun phoneVerify(
        @Query("sid") sid: String,
        @Body smsCodeBody: SmsCodeRequestBody
    ): Completable
    @POST("api/v1/oauth/signup/phone/verify")
    fun phoneVerify2(
        @Query("sid") sid: String,
        @Body smsCodeBody: SmsCodeRequestBody
    ):Observable<TempToken>
//TODO
    @POST("api/v1/oauth/register")
    fun registerWithPassword(
        @Query("sid") sid: String,
        @Body passwordBody: PasswordRequestBody
    ): Observable<AccessToken>

    @POST("api/v1/oauth/signup/phone/resend")
    fun resendOtp(
        @Query("sid") sid: String
    ): Completable
}

