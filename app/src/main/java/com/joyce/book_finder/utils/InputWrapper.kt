package com.joyce.book_finder.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputWrapper(
    var value: String = "",
    var inputError: InputError = InputError(false),
    val onValueChange: (value: String) -> Unit
) : Parcelable

@Parcelize
data class InputError(
    var hasError: Boolean,
    var errorMessage: String? = null
) : Parcelable