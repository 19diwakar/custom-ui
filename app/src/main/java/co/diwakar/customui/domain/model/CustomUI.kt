package co.diwakar.customui.domain.model

import android.os.Parcelable
import co.diwakar.customui.utils.UIType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomUI(
    val logoUrl: String?,
    val headingText: String?,
    val uidata: List<UiData>?
): Parcelable

@Parcelize
data class UiData(
    val uitype: String?,
    val value: String?,
    val key: String?,
    val hint: String?,
    var userInput: String? = null,
    var label: String? = null
): Parcelable {
    fun getUIType(): UIType {
        return when (uitype) {
            "label" -> UIType.LABEL
            "edittext" -> UIType.EDITTEXT
            "button" -> UIType.BUTTON
            else -> UIType.NONE
        }
    }
}