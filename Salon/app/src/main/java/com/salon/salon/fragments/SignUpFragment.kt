package com.salon.salon.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.salon.salon.R
import com.salon.salon.api.SError
import com.salon.salon.api.responses.SignUpResponse
import com.salon.salon.custom.Form
import com.salon.salon.custom.ValueType
import com.salon.salon.custom.ViewType
import com.salon.salon.presenters.SignUpPresenter
import retrofit2.Response

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    SignUpFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class SignUpFragment : BottomSheetDialogFragment() {

    var vArray = arrayOf<ViewType>(ViewType.EDIT_TEXT, ViewType.EDIT_TEXT,ViewType.EDIT_TEXT,ViewType.EDIT_TEXT,ViewType.EDIT_TEXT,ViewType.EDIT_TEXT, ViewType.BUTTON)
    var vKeys = arrayOf<ValueType>(ValueType.NAME, ValueType.PHONE_NUMBER, ValueType.EMAIL, ValueType.CITY, ValueType.STATE, ValueType.COUNTRY, ValueType.SUBMIT)
    var groupValues = vKeys.zip(vArray).toMap()

    var outPutKeyAndValues = HashMap<String,String>()
    var completionWithOutput: (Pair<Response<SignUpResponse>?, SError?>) -> (Unit) = { }

    private val signUpPresenter = SignUpPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = arguments?.getInt(ARG_ITEM_COUNT)?.let { ItemAdapter(it) }
    }

    private inner class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup, type: Int)
        : RecyclerView.ViewHolder(Form(context, vArray[type])) {

        var viewType: ViewType? = vArray[type]
        var text: EditText? = itemView.findViewById(R.id.customEditText)
        var button: Button? = itemView.findViewById(R.id.customButton)

        init {
            button?.setOnClickListener {
                val validationError = signUpPresenter.isAllValid(outPutKeyAndValues)
                if(validationError.isBlank()) {
                    signUpPresenter.getSignUpWebservices(outPutKeyAndValues) { response: Response<SignUpResponse>?, t: SError? ->
                        completionWithOutput(Pair(response, t))
                    }
                } else {
                    Toast.makeText(activity, validationError, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private inner class ItemAdapter internal constructor(private val mItemCount: Int) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent, viewType)
        }

        @SuppressLint("RecyclerView")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            var mapKeys = groupValues.keys
            var viewType: ViewType? = groupValues.getValue(mapKeys.elementAt(position))

            when(viewType) {
                ViewType.EDIT_TEXT -> {
                    holder.text?.hint = mapKeys.elementAt(position).params

                    holder?.text?.addTextChangedListener(object : TextWatcher {

                        override fun afterTextChanged(p0: Editable?) {
                            outPutKeyAndValues.put(mapKeys.elementAt(position).params, p0.toString())
                        }

                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        }
                    })
                }
                ViewType.BUTTON -> {
                    holder.button?.text = mapKeys.elementAt(position).params
                    outPutKeyAndValues.put(mapKeys.elementAt(position).params, "Submit")
                }
            }
        }

        override fun getItemCount(): Int {
            return mItemCount
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }
    }

    companion object {
        fun newInstance(itemCount: Int): SignUpFragment =
                SignUpFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_ITEM_COUNT, itemCount)
                    }
                }
        }
}