package com.salon.salon.extensions

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.View
import java.util.regex.Pattern

interface StringClickable {
    fun spanTextClicked()
}

fun String.getSpannableText(formattingString: String, clickable: StringClickable? = null): SpannableString {
    val spannableString = SpannableString(this)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) {
            clickable?.spanTextClicked()
        }
    }
    spannableString.setSpan(ForegroundColorSpan(Color.MAGENTA), this.length - formattingString.length - 1, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(clickableSpan, this.length - formattingString.length - 1, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}

fun String.isEmailValid(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPhoneNumber(): Boolean {
    val pattern = "[0-9]{10}";
    return Pattern.compile(pattern).matcher(this.trim()).matches()
}