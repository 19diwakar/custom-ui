package co.diwakar.customui.utils

import co.diwakar.customui.domain.model.UiData

fun List<UiData>.isUserInputAdded(): Boolean {
    return any { it.userInput.isNullOrBlank().not() }
}

fun List<UiData>.mappedWithLabel(): List<UiData> {
    val toSet = mutableListOf<UiData>()
    for (i in 1 until size) {
        val it = this[i]
        if (it.getUIType() == UIType.EDITTEXT && it.userInput.isNullOrBlank().not()) {
            toSet.add(it.copy(label = this[i - 1].value))
        }
    }

    return toSet
}