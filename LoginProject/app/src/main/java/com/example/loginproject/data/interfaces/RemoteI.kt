package com.example.loginproject.data.interfaces

import com.example.loginproject.data.network.*
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap

interface RemoteI {
    fun downloadClientInfo(): Observable<ClientInfo>
    fun login(code:String?, type:String, username:String, password:String, refreshToken:String?): Observable<AccessToken>
    fun signUp(username:String, pwd:String?): Observable<AccessToken>
    fun signUpPhone(username:String): Observable<TempToken>
    fun verifyPhoneNumber(sid:String, code:String): Completable
    fun verifyPhoneNumber2(sid:String, code:String): Observable<TempToken>
    fun registerWithPwd(sid:String, pwd:String): Observable<AccessToken>
//-------------------------------------------RESET--------------------------------------------------

    fun requestOTP(reset_option:String, username:String): Observable<TempToken>
    fun resetVerify(sid: String, code:String): Observable<TempToken>
    fun updatePassword(sid:String, newPassword:String): Observable<TempToken>
//-------------------------------------------PROFILE------------------------------------------------
    fun getUserInfo(authorization:String): Observable<UserInfo>
    fun setUserAva(multipart: MultipartBody.Part, authorization:String): Observable<AvatarInfo>
    fun setUserAva2(multipart: HashMap<String, RequestBody>, authorization:String): Observable<AvatarInfo>
}