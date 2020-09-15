package com.example.loginproject.data.interfaces

import android.util.Log
import com.example.loginproject.data.network.*
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RepoI {
    fun getClientInfo(): Observable<ClientInfo>
    fun login(code:String?, type:String, username:String, password:String, refreshToken:String?): Observable<AccessToken>
    fun signUp(userrname:String, pwd:String?): Observable<AccessToken>
    fun signUpPhone(userrname:String): Observable<TempToken>
    fun verify(sid:String, code:String): Completable
    fun verify2(sid:String, code:String): Observable<TempToken>
    fun registerWithPwd(sid:String, pwd:String): Observable<AccessToken>
    //-------------------------------------------RESET--------------------------------------------------
    fun resetRequestOtp(reset_option:String, username:String): Observable<TempToken>
    fun resetVerifyCode(sid: String,code: String): Observable<TempToken>
    fun updatePassword(sid:String, newPassword:String): Observable<TempToken>
    //-------------------------------------------PROFILE------------------------------------------------
    fun getUserInfo(authorization:String): Observable<UserInfo>
    fun setUserInfo(multipart: MultipartBody.Part, authorization:String): Observable<AvatarInfo>
}