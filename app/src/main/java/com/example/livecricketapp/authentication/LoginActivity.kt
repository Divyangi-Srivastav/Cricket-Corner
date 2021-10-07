package com.example.livecricketapp.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.livecricketapp.R
import com.example.livecricketapp.activities.HomeActivity
import com.example.livecricketapp.user.activities.HomeActivityUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

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
    private val reference = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txt_new_user.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
        auth = FirebaseAuth.getInstance()
        btn_submit.setOnClickListener {
            if (et_loginid.text!!.trim().isNotEmpty() && et_password.text!!.trim().isNotEmpty() && isValidString(et_loginid.text.toString())) {
                auth.signInWithEmailAndPassword(et_loginid.text.toString(), et_password.text.toString()).addOnCompleteListener(this, OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        check_user()
                    }else {
                        Toast.makeText(this, "Login Failed"+task.exception, Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                Toast.makeText(this@LoginActivity, "Please fill all detail correctly", Toast.LENGTH_SHORT).show()
            }
        }
        txt_forgot_password.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
    }

    fun isValidString(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    fun check_user ()
    {
        reference.child(auth.currentUser!!.uid)
            .child("status")
            .get()
            .addOnSuccessListener {
                val a = it.getValue().toString()
                next_activity(a)
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }

    }

    fun next_activity ( a : String)
    {
        if ( a.equals("user") )
        {
            val intent = Intent(this, HomeActivityUser::class.java)
            startActivity(intent)
            finish()
        }
        else if (a.equals("admin"))
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}