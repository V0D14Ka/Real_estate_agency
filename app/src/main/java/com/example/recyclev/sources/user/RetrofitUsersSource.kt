package com.example.recyclev.sources.user

import com.example.recyclev.model.user.SignUpData
import com.example.recyclev.model.user.User
import com.example.recyclev.model.user.UsersSource
import com.example.recyclev.sources.base.BaseRetrofitSource
import com.example.recyclev.sources.base.RetrofitConfig
import com.example.recyclev.sources.user.entities.SignInRequestEntity
import com.example.recyclev.sources.user.entities.SignUpRequestEntity
import com.example.recyclev.sources.user.entities.UpdateUsernameRequestEntity
import kotlinx.coroutines.delay

class RetrofitUsersSource (
    config: RetrofitConfig
): BaseRetrofitSource(config), UsersSource{

    private val usersApi =
        retrofit.create(UsersApi::class.java)

    override suspend fun signIn(email: String, password: String
    ): String = wrapRetrofitExceptions{
        delay(1000)
        val signInRequestEntity = SignInRequestEntity(
            email = email,
            password = password
        )
        usersApi.signIn(signInRequestEntity).token
    }

    override suspend fun signUp(signUpData: SignUpData
    ) = wrapRetrofitExceptions {
        delay(1000)
        val signUpRequestEntity = SignUpRequestEntity(
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password
        )
        usersApi.signUp(signUpRequestEntity)
    }

    override suspend fun getUser(): User = wrapRetrofitExceptions {
        delay(1000)
        usersApi.getUser().toUser()
    }

    override suspend fun setUsername(username: String
    ) = wrapRetrofitExceptions {
        delay(1000)
        val updateUsernameRequestEntity = UpdateUsernameRequestEntity(
            username = username
        )
        usersApi.setUsername(updateUsernameRequestEntity)
    }
}