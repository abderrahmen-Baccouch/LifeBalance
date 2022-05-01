package com.example.premierepage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared:SharedPreferences?=null
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

        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        val i = Intent(this,App::class.java)
        val i2 = Intent(this,PageAdmin::class.java)

        showHidePassword.setOnClickListener{
            ShowPass = !ShowPass
            showPassword(ShowPass)
        }
        showPassword(ShowPass)

        etEmail = findViewById(R.id.editTextTextEmailAddress)
        etPassword = findViewById(R.id.editTextPassword)
        btnValidate = findViewById(R.id.buttonLogin)


        btnValidate.setOnClickListener{

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
                /**5edmet l backend */
                val map = HashMap<String?, String?>()
                map["email"] = email
                map["password"] = password
                val call = retrofitInterface!!.executeLogin(map)
                call!!.enqueue(object : Callback<LoginResult?> {
                    override fun onResponse(call: Call<LoginResult?>, response: Response<LoginResult?>) {
                        if (response.code() == 200) {
                            val result = response.body()
                            myshared=getSharedPreferences("myshared",0)
                            var editor: SharedPreferences.Editor=myshared!!.edit()
                            editor.putString("token",result!!.getToken())
                            editor.putString("username",result!!.getUsername())
                            editor.putString("role",result!!.getRole().toString())
                            editor.commit()
                          /*  if(result!!.getRole()==0) {
                                startActivity(i)
                            }else if(result!!.getRole()==1){
                                startActivity(i2)
                            }*/
                            startActivity(i)

                        } else if (response.code() == 400) {
                            Toast.makeText(this@MainActivity, "Wrong Credentials", Toast.LENGTH_LONG).show()
                        //si le compte n'est pas verifié
                        }else if(response.code() == 401){
                            Toast.makeText(this@MainActivity,"verifier ton compte ", Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<LoginResult?>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }/**wfet 5edmet l backend*/

        }

        buttonSignUp.setOnClickListener{
            Toast.makeText(this,"button signup clicked",Toast.LENGTH_LONG).show()
            val intent = Intent(this,SignupActivity::class.java)
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

