package com.salon.salon.custom

import android.annotation.SuppressLint
import android.content.Context
import android.widget.*
import com.salon.salon.R

enum class ViewType {
    SPINNER,
    TEXT_VIEW,
    EDIT_TEXT,
    BUTTON
}

enum class ValueType(val params: String) {
    NAME("Name"),
    PHONE_NUMBER("Phone number"),
    EMAIL("Email"),
    CITY("City"),
    STATE ("State"),
    COUNTRY ("Country"),
    SUBMIT("Submit")
}

class Form(private val activityContext: Context?, private val viewType: ViewType? = null) : LinearLayout(activityContext) {

    val spinner = Spinner(activityContext)
    val editText = EditText(activityContext)
    val button = Button(activityContext)

    init {
        addSubView()
    }

    @SuppressLint("ResourceAsColor")

    fun addSubView() {
        if (viewType == null) {
            return
        }
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        setBackgroundColor(android.R.color.holo_orange_dark)
        layoutParams = params

        //Spinner
        when(viewType) {
            ViewType.SPINNER -> {
                var adapter = ArrayAdapter<String>(activityContext, android.R.layout.simple_dropdown_item_1line, arrayOf("Hi", "Hello"))
                spinner.adapter = adapter
                spinner.id = R.id.customSpinner
                spinner.layoutParams = layoutParams
                addView(spinner)
            }
            ViewType.EDIT_TEXT -> {
                editText.id = R.id.customEditText
                editText.layoutParams = layoutParams
                addView(editText)
            }
            ViewType.BUTTON -> {
                button.id =  R.id.customButton
                button.layoutParams = layoutParams
                button.text = "Submit"
                addView(button)
            }
        }
    }
}