package com.iqbalriiaz.seuresearchers

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.iqbalriiaz.seuresearchers.databinding.ActivityLoginBinding
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityLoginBinding

    //progressDialog
    private lateinit var progressDialog:ProgressDialog

    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password =  ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        /*requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)*/

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)


        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loggin in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, begin login
        binding.loginBtnId.setOnClickListener {
            //before logging in, validate date
            validateData()
        }

    }

    private fun validateData() {
        //get data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //no password enterd
            binding.passwordEt.error = "Please enter password"
        }
        else{
            //data is validated, begin login
            firebaseLogin()
        }

    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged in as  $email",
                    Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this, StudentDashboardActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}",
                Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in go to profile activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser !=null){
            //user is already logged in
            startActivity(Intent(this, StudentDashboardActivity::class.java))
            finish()
        }
    }
}