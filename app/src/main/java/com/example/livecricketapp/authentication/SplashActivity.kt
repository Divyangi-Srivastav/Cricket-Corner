package com.example.livecricketapp.authentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.livecricketapp.R
import com.example.livecricketapp.activities.HomeActivity
import com.example.livecricketapp.model.UserHelper
import com.example.livecricketapp.user.activities.HomeActivityUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SplashActivity : AppCompatActivity() {

    private val mWaitHandler = Handler()
    private lateinit var auth: FirebaseAuth
    private val reference = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            check_user()
        } else {
            mWaitHandler.postDelayed({
                try {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (ignored: Exception) {
                    ignored.printStackTrace()
                }
            }, 2000)
        }
    }

    fun check_user() {
        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val users = snapshot.getValue(UserHelper::class.java)!!
                if (users.userKey.equals(auth.currentUser!!.uid))
                    next_activity(users.status)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    fun next_activity(a: String) {
        if (a.equals("user")) {
            val intent = Intent(this, HomeActivityUser::class.java)
            startActivity(intent)
            finish()
        } else if (a.equals("admin")) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}