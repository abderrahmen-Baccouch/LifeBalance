package com.example.premierepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.varunest.sparkbutton.SparkButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second_design.*
import kotlinx.android.synthetic.main.activity_second_design.showHide
import java.util.regex.Pattern
import android.widget.Button as Button

class SecondDesign : AppCompatActivity() {
    private var Show = false
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

    private lateinit var etUsername : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var etVerifyPassword : EditText
    private lateinit var btnValidate : SparkButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_design)

        showHide.setOnClickListener{
            Show = !Show
            showPassword(Show)
        }
        showPassword(Show)

        etUsername = findViewById(R.id.username)
        etEmail = findViewById(R.id.email)
        etPassword = findViewById(R.id.password)
        etVerifyPassword = findViewById(R.id.verifyPassword)
        btnValidate = findViewById(R.id.spark_button)




        spark_button.setOnClickListener{
            val email = etEmail.text.toString().trim()
            val user = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val verif_password = etVerifyPassword.text.toString().trim()



            if(user.isEmpty()) {
                etUsername.error = "Nom d'utilisateur est obligatoire";

            } else if(email.isEmpty()){
                etEmail.error="Email Obligatoire";

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Veuiller entrer un email valide")

            }else if(password.isEmpty()){
                etPassword.error="Mot de passe Obligatoire"

            }else if (!PASSWORD_PATTERN.matcher(password).matches()){
                etPassword.setError("Mot de passe trop faible .. au moins 6 characteres")

            }else if(verif_password.isEmpty()) {
                etVerifyPassword.error = "Ce champ est Obligatoire"

            }else if (password!=verif_password) {
                 etVerifyPassword.error ="Mot de passe incorrecte"}
            else {
                Toast.makeText(this, "Vous avez inscit !", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,ThirdActivity::class.java)
                intent.putExtra("username",user)
                startActivity(intent)
            }
    }

    }
    private fun showPassword(isShow:Boolean){
        if(isShow){
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showHide.setImageResource(R.drawable.ic_baseline_visibility_off)
        }else {
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            showHide.setImageResource(R.drawable.ic_baseline_visibility)
        }
        password.setSelection(password.text.toString().length)
    }
}
