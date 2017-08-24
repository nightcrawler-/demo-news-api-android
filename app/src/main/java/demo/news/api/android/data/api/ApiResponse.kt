package demo.news.api.android.data.api

import retrofit2.Response
import java.io.IOException

/**
 * Common class used by API responses.

 * @param <T>
</T> */
class ApiResponse<T> {

    val code: Int
    val body: T?
    val errorMessage: String?

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string()
                } catch (ignored: IOException) {
                    ignored.printStackTrace()
                }

            }
            if (message == null || message.trim { it <= ' ' }.length == 0) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }
    }

    val isSuccessful: Boolean
        get() = code >= 200 && code < 300


}
