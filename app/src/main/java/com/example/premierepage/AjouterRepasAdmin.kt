package com.example.premierepage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.premierepage.model.Image
import com.example.premierepage.model.Recette
import kotlinx.android.synthetic.main.activity_ajouter_exercice_admin.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.ArrayList

class AjouterRepasAdmin : AppCompatActivity() {
    lateinit var addIngredient: Button
    var myshared: SharedPreferences?=null
    private var retrofitInterface: RetrofitInterface? = null
    private var permissionsToRequest: ArrayList<String>? = null
    private val permissionsRejected = ArrayList<String>()
    private val permissions = ArrayList<String>()
    private val ALL_PERMISSIONS_RESULT = 107
    private val REQUEST_CODE=200
    lateinit var mBitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_repas_admin)

        /********************************** Retrofit and myshared************************** */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        var role =myshared?.getString("role","")
        val imageViewG = findViewById<ImageView>(R.id.imageViewG)
        val typeRepas=intent.getStringExtra("typeRepas")
       val fabCamera:Button = findViewById(R.id.camera);
        fabCamera.setOnClickListener{
            // startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            openGalleryForImage()
        }
        askPermissions();


        /**.................................   .findViewById.   ...............................................*/
        val nomRepasET:EditText = findViewById(R.id.nomRepas)
        val  tempsPreparationET:EditText = findViewById(R.id. tempsPreparation)
        val saveRecette:Button=findViewById(R.id.saveRecette)
        val cancelRecette:Button=findViewById(R.id.cancelRecette)
        addIngredient = findViewById(R.id.addIngredient)
        addIngredient.setOnClickListener {
            val i=Intent(this@AjouterRepasAdmin,ListeAliments::class.java)
            if (myshared?.getString("idrecette","")==""){
                /**5edmet l back*/
                val map = HashMap<String?, String?>()
                map["nomRecette"] = nomRepasET.text.toString()
                map["temps"] = tempsPreparationET.text.toString()
                map["typeRepas"]=typeRepas.toString()
                if(role!="1") {
                    val call = retrofitInterface!!.executeAddRecette(map, token)
                    call.enqueue(object : Callback<Recette> {
                        override fun onResponse(call: Call<Recette>, response: Response<Recette>) {
                            if (response.code() == 200) {
                                var editor: SharedPreferences.Editor = myshared!!.edit()
                                editor.putString("idrecette", response.body()!!.recette._id.toString())
                                editor.putString("nomRecette", response.body()!!.recette.nomRecette)
                                editor.commit()
                                startActivity(i)
                            } else if (response.code() == 400) {
                                Toast.makeText(this@AjouterRepasAdmin, "400", Toast.LENGTH_LONG).show()
                            } else if (response.code() == 401) {
                                Toast.makeText(this@AjouterRepasAdmin, "401", Toast.LENGTH_LONG).show()
                            }
                        }
                        override fun onFailure(call: Call<Recette>, t: Throwable) {
                            Toast.makeText(this@AjouterRepasAdmin, t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }else{
                    val call = retrofitInterface!!.executeAddRepas(map)
                    call.enqueue(object : Callback<Recette> {
                        override fun onResponse(call: Call<Recette>, response: Response<Recette>) {
                            if (response.code() == 200) {
                                var editor: SharedPreferences.Editor = myshared!!.edit()
                                editor.putString("idrecette", response.body()!!.recette._id.toString())
                                editor.putString("nomRecette", response.body()!!.recette.nomRecette)
                                editor.commit()
                                startActivity(i)
                            } else if (response.code() == 400) {
                                Toast.makeText(this@AjouterRepasAdmin, "400", Toast.LENGTH_LONG)
                                    .show()
                            } else if (response.code() == 401) {
                                Toast.makeText(this@AjouterRepasAdmin, "401", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<Recette>, t: Throwable) {
                            Toast.makeText(this@AjouterRepasAdmin, t.message, Toast.LENGTH_LONG)
                                .show()
                        }

                    })
                }



            }else{
                startActivity(i)
            }
        }




        saveRecette.setOnClickListener {
            mBitmap = (imageViewG.drawable as BitmapDrawable).bitmap
            multipartImageUpload()
            val map = HashMap<String?, String?>()
            map["nomRecette"] = nomRepasET.text.toString()
            map["temps"] = tempsPreparationET.text.toString()
            val call = retrofitInterface!!.executeSaveRecette(myshared?.getString("idrecette",""),map)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@AjouterRepasAdmin,response.errorBody().toString(), Toast.LENGTH_LONG).show()
                    if (response.code()==200){
                        Toast.makeText(this@AjouterRepasAdmin,"recette saved",Toast.LENGTH_LONG).show()
                        var editor: SharedPreferences.Editor=myshared!!.edit()
                        editor.remove("idrecette")
                        editor.commit()
                        finish()
                    }else if (response.code()==400){
                        Toast.makeText(this@AjouterRepasAdmin,"400",Toast.LENGTH_LONG).show()
                    }else if (response.code()==401){
                        Toast.makeText(this@AjouterRepasAdmin,"401",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@AjouterRepasAdmin, t.message, Toast.LENGTH_LONG).show()
                }

            })
        }


        cancelRecette.setOnClickListener {
            var editor: SharedPreferences.Editor=myshared!!.edit()
            editor.remove("idrecette")
            editor.commit()

            finish()
        }
    }

    fun AddRecette(map: java.util.HashMap<String?, String?>?,token:String){

    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, REQUEST_CODE)
    }


    /** ******************************************************    5edmet l permission  ******************************************/
    private fun askPermissions() {
        permissions.add(Manifest.permission.CAMERA)
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionsToRequest = findUnAskedPermissions(permissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest!!.size > 0) requestPermissions(
                permissionsToRequest!!.toTypedArray(),
                ALL_PERMISSIONS_RESULT
            )
        }
    }

    fun findUnAskedPermissions(wanted: ArrayList<String>): ArrayList<String> {
        val result = ArrayList<String>()
        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }
        return result
    }
    private fun hasPermission(permission: String): Boolean {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }
    private fun canMakeSmores(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    /*******************************************************************************************************************/

    /**-------------------------------------Button1---------------------------------------*/
    private fun multipartImageUpload() {
        try {
            val body=buildImageBodyPart("upload.jpg", mBitmap!!)
            val name = RequestBody.create("text/plain".toMediaTypeOrNull(), "upload")
            val req: Call<Image>? = retrofitInterface!!.postImage(body,name)
            req!!.enqueue(object : Callback<Image> {
                override fun onResponse(call: Call<Image>, response: Response<Image>) {
                    var nameImg=response.body()!!.name
                    if (response.code() == 200) {
                        //9ayed e nameImg fi  myshared
                        var editor: SharedPreferences.Editor=myshared!!.edit()
                        editor.putString("nameImg",nameImg)
                        editor.commit()
                    }
                    Toast.makeText(applicationContext, response.code().toString() + " ", Toast.LENGTH_SHORT).show()
                    Toast.makeText(applicationContext, response.body()!!.name, Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<Image>, t: Throwable) {
                    Toast.makeText(applicationContext, "Request failed", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    /**-------------------------------------Button2---------------------------------------*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageViewG.setImageURI(data?.data) // handle chosen image
        }
    }
    private fun buildImageBodyPart(fileName: String, bitmap: Bitmap):  MultipartBody.Part {
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), leftImageFile)
        return MultipartBody.Part.createFormData(fileName,leftImageFile.name, reqFile)
    }
    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(this.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}