package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
private var ShowPass = false

    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +  //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
               // "(?=.*[@#$%^&+=])" +  //at least 1 special character
              //  "(?=\\S+$)" +  //no white spaces
                ".{6,}" +  //at least 6 characters
                "$"
    )


    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnValidate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showHidePassword.setOnClickListener{
            ShowPass = !ShowPass
            showPassword(ShowPass)
        }
        showPassword(ShowPass)
       etEmail = findViewById(R.id.editTextTextEmailAddress)
        etPassword = findViewById(R.id.editTextPassword)
         btnValidate = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener{
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if(email.isEmpty()){
                etEmail.error="Email Obligatoire";
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Veuiller entrer un email valide")
            }
            else if(password.isEmpty()){
                etPassword.error="Mot de passe Obligatoire"
            }else if (!PASSWORD_PATTERN.matcher(password).matches()){
                etPassword.setError("Mot de passe trop faible .. au moins 6 characteres")
            }
            else{
                Toast.makeText(applicationContext, R.string.toast_msg, Toast.LENGTH_LONG).show()
                val intent = Intent(this,ThirdActivity::class.java)
                startActivity(intent)


            }
        }



        buttonSignUp.setOnClickListener{
            val intent = Intent(this,SecondDesign::class.java)
            startActivity(intent)
        }



        }
    private fun showPassword(isShow:Boolean){
        if(isShow){
            editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showHidePassword.setImageResource(R.drawable.ic_baseline_visibility_off)
        }else {
            editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            showHidePassword.setImageResource(R.drawable.ic_baseline_visibility)
        }
           editTextPassword.setSelection(editTextPassword.text.toString().length)
    }
}

