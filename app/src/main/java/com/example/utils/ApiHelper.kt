package  com.example.utils

import com.example.network.Response
import org.json.JSONObject
import retrofit2.Response as RetrofitResponse


class ApiHelper {

    companion object {

        fun <T> parseResponse(response: RetrofitResponse<Response<T>>): Response<T> {
            return if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                val errorResponse = Response<T>()
                try {
                    val errorBody = response.errorBody()?.string()
                    val error = JSONObject(errorBody ?: "")
                } catch (e: Exception) {
                }
                errorResponse
            }
        }
    }
}




