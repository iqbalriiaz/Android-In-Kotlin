package com.iqbalriiaz.seuresearchers

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.iqbalriiaz.seuresearchers.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityLoginBinding

    //progressDialog
    private lateinit var progressDialog: ProgressDialog

    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)


        binding.newUserId.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }


        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        //checkUser()

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
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            //no password entered
            binding.passwordEt.error = "Please enter password"
        } else {
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
                Toast.makeText(
                    this, "Logged in as  $email",
                    Toast.LENGTH_SHORT
                ).show()


                //writing into firebase realtime database
                val uEmail = binding.emailEt.text.toString()
                val uPassword = binding.passwordEt.text.toString()
                val uid = FirebaseAuth.getInstance().currentUser!!.uid
                val rootRef = FirebaseDatabase.getInstance().reference
                val uidRef: DatabaseReference = rootRef.child("User").child(uid)


                rootRef.child("User").child(uid).child("credentials")
                    .setValue(User(uEmail,uPassword))



                val valueEventListener: ValueEventListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {


                        if (dataSnapshot.child("userType").child("isStudent")
                                .getValue(String:: class.java)!=null) {
                            startActivity(Intent(this@LoginActivity, StudentDashboardActivity::class.java))
                        }
                        if (dataSnapshot.child("userType").child("isTeacher")
                                .getValue(String:: class.java)!=null) {
                            startActivity(Intent(this@LoginActivity, TeacherDashboardActivity::class.java))
                        }

                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.d(TAG, databaseError.message)
                    }
                }
                uidRef.addListenerForSingleValueEvent(valueEventListener)




            }
            .addOnFailureListener { e ->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(
                    this, "Login failed. ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    /*private fun checkUser() {
        //if user is already logged in go to profile activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser !=null){
            //user is already logged in
            *//*startActivity(Intent(this, StudentDashboardActivity::class.java))
            Toast.makeText(this, "Welcome Back",
                Toast.LENGTH_SHORT).show()
            finish()*//*

            //writing into firebase realtime database
            val database = FirebaseDatabase.getInstance().reference
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val uidRef: DatabaseReference = database.child("User").child("Student").child(uid)

            val valueEventListener: ValueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if( dataSnapshot.child("User").child("Student").child(uid).child("uType").getValue(Int::class.java) == 1) {
                        startActivity(Intent(this@LoginActivity, StudentDashboardActivity::class.java))
                    }

                    else if( dataSnapshot.child("User").child("Student").child(uid).child("uType").getValue(Int::class.java) == 2)  {
                        startActivity(Intent(this@LoginActivity, TeacherDashboardActivity::class.java))
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d(TAG, databaseError.message)
                }
            }
            uidRef.addListenerForSingleValueEvent(valueEventListener)
        }
    }*/
}