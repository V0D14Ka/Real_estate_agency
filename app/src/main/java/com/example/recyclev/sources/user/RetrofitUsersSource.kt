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

    override suspend fun signIn(username: String, password: String
    ): String = wrapRetrofitExceptions{
        val signInRequestEntity = SignInRequestEntity(
            username = username,
            password = password
        )
        usersApi.signIn(signInRequestEntity).auth_token
    }

    override suspend fun signUp(signUpData: SignUpData
    ) = wrapRetrofitExceptions {
        val signUpRequestEntity = SignUpRequestEntity(
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password
        )
        usersApi.signUp(signUpRequestEntity)
    }

    override suspend fun getUser(): User = wrapRetrofitExceptions {
        usersApi.getUser().toUser()
    }

    override suspend fun setUsername(username: String
    ) = wrapRetrofitExceptions {
        val updateUsernameRequestEntity = UpdateUsernameRequestEntity(
            username = username
        )
        usersApi.setUsername(updateUsernameRequestEntity)
    }
}