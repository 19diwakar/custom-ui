package co.diwakar.customui.utils

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import co.diwakar.customui.constants.Extras

inline fun <reified T : Parcelable> Intent.getParcelableArrayList(key: String): ArrayList<T>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArrayListExtra(Extras.UI_DATA_LIST, T::class.java)
    } else {
        @Suppress("DEPRECATION") getParcelableArrayListExtra(Extras.UI_DATA_LIST)
    }
}