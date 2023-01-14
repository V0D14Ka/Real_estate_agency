package com.example.recyclev.sources.base

import android.util.Log
import com.example.recyclev.BackendException
import com.example.recyclev.ConnectionException
import com.example.recyclev.ParseBackendResponseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

open class BaseRetrofitSource(
    retrofitConfig: RetrofitConfig
) {
    val retrofit = retrofitConfig.retrofit

    private val errorAdapter =
        retrofitConfig.moshi.adapter(ErrorResponseBody::class.java)

    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: JsonDataException) {
            throw ParseBackendResponseException(e)
        } catch (e: JsonEncodingException) {
            throw ParseBackendResponseException(e)
        } catch (e: HttpException) {
            throw createBackendException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        }catch (e: SocketTimeoutException) {
            throw ConnectionException(e)
        }
    }

    private fun createBackendException(e: HttpException): Exception {
        return try{
            val errorBody = errorAdapter.fromJson(
                e.response()?.errorBody()!!.string()
            )!!
            BackendException(e.code(), errorBody.toString())
        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }

    class ErrorResponseBody(
        val username: Array<String>,
        val email: Array<String>,
        val non_field_errors: Array<String>,
        val password: Array<String>
    ){
        override fun toString(): String {
            var answer : String = ""
            if (username != null) username.forEach { answer += it }
            if (email != null) email.forEach { answer += it }
            if (non_field_errors != null) non_field_errors.forEach { answer += it }
            if (password != null) password.forEach { answer += it }
            return answer
        }
    }
}