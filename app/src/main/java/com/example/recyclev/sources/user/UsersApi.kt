package com.example.recyclev.sources.user

import com.example.recyclev.sources.user.entities.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UsersApi {

    @POST("")
    suspend fun signIn(
        @Body signInRequestEntity: SignInRequestEntity
    ): SignInResponseEntity

    @POST("")
    suspend fun signUp(@Body body : SignUpRequestEntity)

    @GET("")
    suspend fun getUser(): GetUserResponseEntity

    @PUT("")
    suspend fun setUsername(@Body body: UpdateUsernameRequestEntity)

}