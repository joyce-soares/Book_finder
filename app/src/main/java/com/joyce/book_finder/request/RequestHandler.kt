package com.joyce.book_finder.request

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException

typealias SuspendCall<T> = suspend () -> Response<T>?

object RequestHandler {

    private val successRange = 200..299
    private val errorRange = 400..400 //400..499

    suspend fun <T: Any?> doRequest(
        handleSession: Boolean = true, call: SuspendCall<T>
    ): ResponseWrapper<T?> {
        return try {
            val response = withContext(Dispatchers.IO) { call.invoke() }
            return when (response?.code()) {
                in successRange -> ResponseWrapper.fromSuccess(response)
                in errorRange -> ResponseWrapper.fromError(response)
                else -> ResponseWrapper.unexpected(response?.code())
            }
        } catch (e: SSLPeerUnverifiedException) {
            Log.e("RequestHandler", ">>> UnknownHostException ${e.message}")
            ResponseWrapper.unexpected()
        } catch (e: UnknownHostException) {
            Log.e("RequestHandler", ">>> UnknownHostException ${e.message}")
            ResponseWrapper.unexpected()
        } catch (e: IOException) {
            Log.e("RequestHandler", ">>> IOException ${e.message}")
            ResponseWrapper.unexpected()
        } catch (e: Exception) {
            Log.e("RequestHandler", ">>> Exception ${e.message}")
            ResponseWrapper.unexpected()
        }
    }

    fun <T> ViewModel.doRequestAsync(handleSession: Boolean = true, call: SuspendCall<T>) =
        viewModelScope.doRequestAsync(handleSession, call)

    fun <T> CoroutineScope.doRequestAsync(handleSession: Boolean = true, call: SuspendCall<T>) =
        async { doRequest(handleSession, call) }
}
