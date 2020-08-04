package com.salon.salon.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import com.salon.salon.R
import com.salon.salon.api.responses.SignUpResponse
import com.salon.salon.databinding.ActivityLogInBinding
import com.salon.salon.fragments.SignUpFragment
import com.salon.salon.extensions.StringClickable
import com.salon.salon.extensions.getSpannableText
import com.salon.salon.presenters.SignUpPresenter

class LogInActivity : AppCompatActivity(), View.OnClickListener, StringClickable {

    private lateinit var logInbinding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         logInbinding = ActivityLogInBinding.inflate(layoutInflater)
         setContentView(logInbinding.root)
         bindViews()
    }

    fun bindViews() {
        logInbinding.logInGoogleButton.setOnClickListener(this)
        logInbinding.logInFaceBookButton.setOnClickListener(this)
        logInbinding.logInButton.setOnClickListener(this)
        logInbinding.signInTextView.movementMethod = LinkMovementMethod.getInstance()
        logInbinding.signInTextView.text = resources.getString(R.string.signUpText).getSpannableText(resources.getString(R.string.signUpSpanText),clickable = this)
    }

    override fun spanTextClicked() {
        var signUpFragment = SignUpFragment.newInstance(7)
        signUpFragment.show(supportFragmentManager, "dialog")
        signUpFragment.completionWithOutput = {
            if(it.second == null) {
                if(it.first?.code() == 409) {
                    //Existing user
                    Toast.makeText(this, "Phone number is already registered", Toast.LENGTH_LONG).show()
                } else {
                    //Success signed up
                    Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0) {
            logInbinding.logInButton -> {

            }
        }
    }
}
