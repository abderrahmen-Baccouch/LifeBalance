package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.varunest.sparkbutton.SparkButton
import kotlinx.android.synthetic.main.activity_second_design.*
import retrofit2.Call
import java.util.regex.Pattern
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null




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


        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        val  intent = Intent(this,ThirdActivity::class.java)

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

                //Service Signup
                val map = HashMap<String?, String?>()
                map["username"] = user
                map["email"] = email
                map["password"] = password

                val call = retrofitInterface!!.executeSignup(map)
                call!!.enqueue(object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if (response.code() == 200) {
                            //zid code t7el inflater bech yaamel verification
                            Toast.makeText(
                                this@SignupActivity,
                                "Signed up successfully", Toast.LENGTH_LONG
                            ).show()
                            //intent.putExtra("username",user)
                            startActivity(intent)

                        } else if (response.code() == 400) {
                            Toast.makeText(
                                this@SignupActivity,
                                "Already registered", Toast.LENGTH_LONG
                            ).show()
                        }
                    }


                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(
                            this@SignupActivity, t.message,
                            Toast.LENGTH_LONG
                        ).show()

                    }
                })

            }
            startActivity(intent)
    }

    }//fin OnCreate
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
