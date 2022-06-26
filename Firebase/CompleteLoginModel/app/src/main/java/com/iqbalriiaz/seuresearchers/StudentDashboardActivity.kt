package com.iqbalriiaz.seuresearchers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import com.iqbalriiaz.seuresearchers.databinding.ActivityStudentDashboardBinding

class StudentDashboardActivity : AppCompatActivity() {
    //viewBinding
    private lateinit var binding: ActivityStudentDashboardBinding
    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //int firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        binding.logoutBtnId.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser !=null){
            //user not null, user is logged in, get user info
        }
        else{
            //user is null,  user is not logged in, so goto login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}