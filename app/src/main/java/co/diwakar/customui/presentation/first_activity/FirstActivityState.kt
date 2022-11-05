package co.diwakar.customui.presentation.first_activity

import co.diwakar.customui.domain.model.CustomUI

data class FirstActivityState(
    val customUI: CustomUI? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)