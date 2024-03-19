package com.example.digishop.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.graphics.Color
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

enum class AdPosition {
    SlideShow,
    LeftOfSlideShow,
    RightOfSlideShow,
    MiddleCenter,
    MiddleCenterLeft,
    MiddleCenterRight,
    BottomCenter,
    BottomCenterLeft,
    BottomCenterRight
}






fun formatErrorMessage(message: String?): String {
    Timber.e(" ${message ?: "Unknown error"}")
    return "message= ${message ?: "Unknown error"}"
}


fun priceFormat(price: Double): String {
    // Format the price with commas as thousands separator and no decimal places
    return "%,.0f".format(price / 10)
}


object Utils {

        fun bannerPositions(position: AdPosition): Int {
            return when (position) {
                AdPosition.SlideShow -> 1
                AdPosition.LeftOfSlideShow -> 2
                AdPosition.RightOfSlideShow -> 3
                AdPosition.MiddleCenter -> 4
                AdPosition.MiddleCenterLeft -> 5
                AdPosition.MiddleCenterRight -> 6
                AdPosition.BottomCenter -> 7
                AdPosition.BottomCenterLeft -> 8
                AdPosition.BottomCenterRight -> 9
            }
        }

}

fun parseColor(colorCode:String):Color {
    //convert color hash to android graphics color
    return Color(android.graphics.Color.parseColor(colorCode))
}
fun convertStringTimestampToLong(timestamp: String): Long {
    // Convert your string timestamp to Long (milliseconds since Unix epoch)
    // You may use a library like SimpleDateFormat or java.time for this conversion
    // Example using SimpleDateFormat:
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = dateFormat.parse(timestamp)
    return date?.time ?: 0L
}

fun extractErrorMessageFromJson(errorBody: String): String {
    return try {
        // Try to parse the errorBody as JSON
        val json = JSONObject(errorBody)
        json.getString("message")
    } catch (e: Exception) {
        // If parsing fails, return a default error message or handle it in another way
        Timber.e(" \"failed to get {message} key json variable from api exception = \"$e")
        "failed to get {message} key json variable from api"

    }
}

fun Context.isNetworkConnected(): Boolean {
    var isConnected = false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
    if (capabilities != null) {
        isConnected =
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
    }
    return isConnected
}