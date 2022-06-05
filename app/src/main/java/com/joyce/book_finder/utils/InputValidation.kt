package com.joyce.book_finder.utils

import android.util.Patterns
import java.util.regex.Pattern

fun String.validEmailFormat(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()