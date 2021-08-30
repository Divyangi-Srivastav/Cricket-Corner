package com.example.livecricketapp.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.livecricketapp.activities.HomeActivity
import com.example.livecricketapp.R
import com.example.livecricketapp.model.Shared
import com.example.livecricketapp.model.UserHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()
        val refrence = FirebaseDatabase.getInstance().getReference("users")
        btn_submit.setOnClickListener {
            if (fieldValidation()) {
                if (et_signupid.text!!.trim().isNotEmpty() && et_password.text!!.trim().isNotEmpty() && isValidString(et_signupid.text.toString())) {
                    auth.createUserWithEmailAndPassword(et_signupid.text.toString(), et_password.text.toString()).addOnCompleteListener(this, OnCompleteListener{ task ->
                        if(task.isSuccessful){
                            val user_key = FirebaseAuth.getInstance().currentUser!!.uid
                            val shared = Shared(applicationContext)
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                            val userHelper =
                                UserHelper(et_first_name.text.toString(), et_mobile.text.toString(), et_signupid.text.toString(), et_password.text.toString(), et_address_line1.getText().toString() + " " + et_city_state.getText().toString() + " - " + et_pincode.getText().toString(), user_key)
                            refrence.child(user_key).setValue(userHelper)
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("userName", et_signupid.text.toString())
                            intent.putExtra("userNumber", et_mobile.text.toString())
                            intent.putExtra("userAddress", et_address_line1.getText().toString() + " " + et_city_state.getText().toString() + " - " + et_pincode.getText().toString())
                            intent.putExtra("userKey", user_key)
                            shared.setUserKeyShared(user_key)
                            shared.setUserNameShared(et_first_name.text.toString())
                            shared.setUserPhoneShared(et_mobile.text.toString())
                            shared.setFirstTimeLaunched(false)
                            shared.setUserAddressShared(et_address_line1.getText().toString() + " " + et_city_state.getText().toString() + " - " + et_pincode.getText().toString())
                            startActivity(intent)
                            finish()
                        }else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                        }
                    })
                } else {
                    Toast.makeText(this@SignupActivity, "Please fill all detail correctly", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun isValidString(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    private fun fieldValidation(): Boolean {
        var validate = false
        if (!et_first_name.getText().toString().isEmpty() && !et_mobile.getText().toString()
                .isEmpty() && !et_signupid.getText().toString().isEmpty() && !et_password.getText()
                .toString().isEmpty() && !et_confirm_password.getText()
                .toString().isEmpty() && !et_address_line1.getText().toString()
                .isEmpty() && !et_city_state.getText().toString().isEmpty() && !et_pincode.getText()
                .toString().isEmpty()
        ) {
            return true
        }
        if (et_first_name.getText().toString().isEmpty() && et_mobile.getText().toString()
                .isEmpty() && et_signupid.getText().toString().isEmpty() && et_password.getText()
                .toString().isEmpty() && et_address_line1.getText().toString()
                .isEmpty() && et_city_state.getText().toString().isEmpty() && et_pincode.getText()
                .toString().isEmpty()
        ) {
            Toast.makeText(this@SignupActivity, "All fields are mandatory", Toast.LENGTH_LONG)
                .show()
            validate = false
        }
        if (et_first_name.getText().toString().isEmpty()) {
            et_first_name.setError(getString(R.string.field_required))
            et_first_name.setFocusable(true)
            validate = false
        }
        if (et_mobile.getText().toString().isEmpty()) {
            et_mobile.setError(getString(R.string.field_required))
            et_mobile.setFocusable(true)
            validate = false
        }
        if (et_signupid.getText().toString().isEmpty()) {
            et_signupid.setError(getString(R.string.field_required))
            et_signupid.setFocusable(true)
            validate = false
        }
        if (et_password.getText().toString().isEmpty()) {
            et_password.setError(getString(R.string.field_required))
            et_password.setFocusable(true)
            validate = false
        } else if (!et_password.text.toString().equals(et_confirm_password.text.toString()))
        {
            et_password.setError("Password and confirm password should be same!")
            et_password.setFocusable(true)
            validate = false
        }
        if (et_address_line1.getText().toString().isEmpty()) {
            et_address_line1.setError(getString(R.string.field_required))
            et_address_line1.setFocusable(true)
            validate = false
        }
        if (et_city_state.getText().toString().isEmpty()) {
            et_city_state.setError(getString(R.string.field_required))
            et_city_state.setFocusable(true)
            validate = false
        }
        if (et_pincode.getText().toString().isEmpty()) {
            et_pincode.setError(getString(R.string.field_required))
            et_pincode.setFocusable(true)
            validate = false
        }
        return validate
    }
}