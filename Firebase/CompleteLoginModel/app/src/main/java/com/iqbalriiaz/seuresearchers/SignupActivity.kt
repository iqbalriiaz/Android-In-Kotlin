package com.iqbalriiaz.seuresearchers

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.iqbalriiaz.seuresearchers.databinding.ActivitySignupBinding
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivitySignupBinding

    //progressDialog
    private lateinit var progressDialog: ProgressDialog

    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        binding.alreadyUserId.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCanceledOnTouchOutside(false)


        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        //radiobutton bindings
        val isTeacher = binding.isTeacher
        val isStudent = binding.isStudent


        //handle click, begin signup
        binding.signupBtnId.setOnClickListener {

            //radio button selection check
            if(!(isTeacher.isChecked || isStudent.isChecked)){
                Toast.makeText(this, "Select the Account Type",
                    Toast.LENGTH_SHORT).show()
            }

            else{
                //validate data
                validateData()
            }

        }

    }



    //email validation for southeast university domain
    private val emailAddress: Pattern = Pattern.compile(
        "[a-zA-Z0-9+._%\\-]{1,256}" + "@seu.edu.bd"
    )

    private fun validateData() {
        //get data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if (!emailAddress.matcher(email).matches()) {
            Toast.makeText(this,"Please enter your Southeast University Email",
                Toast.LENGTH_SHORT).show()
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            //no password entered
            binding.passwordEt.error = "Please enter password"
        } else {
            //data is validated, begin signup
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with $email",
                    Toast.LENGTH_SHORT).show()


                //radiobutton bindings
                val isTeacher = binding.isTeacher
                val isStudent = binding.isStudent
                //writing into firebase realtime database

                val uid = FirebaseAuth.getInstance().currentUser!!.uid
                val rootRef = FirebaseDatabase.getInstance().reference


                if(isTeacher.isChecked){

                        rootRef.child("User").child(uid).child("userType")
                            .child("isTeacher").setValue("1")

                }

                if(isStudent.isChecked){

                    rootRef.child("User").child(uid).child("userType")
                        .child("isStudent").setValue("1")
                }



                //after signup redirect user to loginActivity
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->
                //if signup failed
                progressDialog.dismiss()
                Toast.makeText(this,"signup failed due to ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}