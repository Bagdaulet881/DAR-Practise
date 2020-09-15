package com.example.loginproject.data.interfaces

import com.example.loginproject.data.network.*
import okhttp3.MultipartBody

interface Contract {
    interface LoginPresenter {
        fun getClientInfo() {}
        fun login(code: String?, type: String, username: String,
            password: String, refreshToken: String?) {}
        fun destroy(){}
    }

    interface ProfilePresenter {
        fun userInfo(auth:String){}
        fun userAva(multipartBody: MultipartBody.Part, auth:String){}
        fun destroy(){}
    }

    interface RegPresenter {
        fun signUp(u:String,p:String?){}
        fun signUpPhone(u:String){}
        fun verifyCode(sid:String, code:String){}
        fun registerWithPwd(sid:String, pwd:String){}
        fun destroy(){}
    }

    interface ResetPresenter {
        fun resetRequest(resetOption:String,username:String){}
        fun resetVerifyCode(sid:String, code:String){}
        fun updatePassword(sid:String, newPassword:String){}
        fun destroy(){}
    }
//    ----------------------------------------------------------------------------------------------
    interface LoginView {
        fun clientInfo(clt: ClientInfo)
        fun login(token: AccessToken)
        fun handleError(type: String)
    }
    interface ProfileView {
        fun userResponse(userInfo: UserInfo)
        fun avaResponse(avatarInfo: AvatarInfo)
        fun dataFlowWait()
        fun handleError(type: String)
    }
    interface RegView {
        fun signUp(token:AccessToken)
        fun phoneVerify(temp: TempToken)
        fun registerWithPassword(token:AccessToken)
        fun dataFlowWait()
        fun handleError(type: String)
    }
    interface ResetView {
        fun response(str: String)
        fun dataFlowWait()
        fun handleError(type: String)
    }
}