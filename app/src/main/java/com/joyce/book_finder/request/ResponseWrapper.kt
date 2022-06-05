package com.joyce.book_finder.request
import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response

typealias OnSuccessNoContent = suspend () -> Unit
typealias OnSuccess<T> = suspend (response: T) -> Unit
typealias OnError = suspend (message: String) -> Unit
typealias OnFinish = suspend () -> Unit

private const val UNEXPECTED_ERROR = "Erro inesperado, tente novamente mais tarde"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

data class ErrorResponse(
    @Json(name = "message") val message: String?,
    @Json(name = "status") val status: Int?,
    @Json(name = "code") val code: String?,
)

sealed class ResponseWrapper<out T> (
    val hasError: Boolean = false,
    val isSuccess: Boolean = false,
    val isSessionExpired: Boolean = false,
    val status: Int? = null,
    open val content: T? = null,
    open val message: String? = null,
    open val code: String? = null,
) {
    class Success<T>(override val content: T, status: Int) : ResponseWrapper<T>(
        isSuccess = true,
        status = status,
        content = content)

    class Error<T>(
        override val message: String,
        override val code: String?,
        status: Int? = null
    ) : ResponseWrapper<T>(
        hasError = true,
        status = status,
        isSessionExpired = status == SESSION_EXPIRED_CODE,
        message = message
    )

    companion object {
        private const val SESSION_EXPIRED_CODE = 401

        fun <T> fromSuccess(response: Response<T>?) = Success(
            content = response!!.body(),
            status = response.code()
        )

        fun <T> fromError(response: Response<T>?): ResponseWrapper<T> {
            val message = try {
                val adapter = moshi.adapter(ErrorResponse::class.java)
                val stream = response?.errorBody()!!.source()
                adapter.fromJson(stream)?.message ?: UNEXPECTED_ERROR
            } catch (e: Exception) {
                Log.e("ResponseWrapper", ">>> Exception ${e.message}")
                UNEXPECTED_ERROR
            }
            return Error(message, status = response?.code(), code = null)
        }

        fun <T> unexpected(status: Int? = null) = Error<T>(
            message = UNEXPECTED_ERROR,
            code = null,
            status = status
        )
    }
}

suspend inline fun <T> ResponseWrapper<T?>.then(
    crossinline onSuccess: OnSuccess<T>,
    crossinline onError: OnError
) {
    when (this) {
        is ResponseWrapper.Error -> onError(this.message)
        is ResponseWrapper.Success -> this.content?.let { onSuccess(it) }
    }
}

suspend inline fun <T> ResponseWrapper<T?>.then(
    crossinline onSuccess: OnSuccess<T>,
    crossinline onError: OnError,
    crossinline onFinish: OnFinish,
) {
    onFinish()
    when (this) {
        is ResponseWrapper.Error -> onError(this.message)
        is ResponseWrapper.Success -> this.content?.let { onSuccess(it) }
    }
}


suspend inline fun <T> ResponseWrapper<T?>.then(
    crossinline onSuccessNoContent: OnSuccessNoContent,
    crossinline onError: OnError
) {
    when (this) {
        is ResponseWrapper.Error -> onError(message)
        is ResponseWrapper.Success -> onSuccessNoContent()
    }
}

suspend inline fun <T> ResponseWrapper<T?>.then(
    crossinline onSuccessNoContent: OnSuccessNoContent,
    crossinline onError: OnError,
    crossinline onFinish: OnFinish,
) {
    onFinish()
    when (this) {
        is ResponseWrapper.Error -> onError(this.message)
        is ResponseWrapper.Success -> onSuccessNoContent()
    }
}


suspend inline fun <T> ResponseWrapper<T?>.then(
    crossinline onError: OnError,
    crossinline onFinish: OnFinish,
) {
    onFinish()
    when (this) {
        is ResponseWrapper.Error -> onError(this.message)
        is ResponseWrapper.Success -> {}
    }
}
