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
import kotlinx.android.synthetic.main.activity_ajouter_exercice_admin.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.ArrayList

class AjouterAlimentAdmin : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null

    private val REQUEST_CODE=200
    private var permissionsToRequest: ArrayList<String>? = null
    private val permissions = ArrayList<String>()
    private val ALL_PERMISSIONS_RESULT = 107
    lateinit var fabCamera: Button
    lateinit var imageViewA: ImageView
    lateinit var mBitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_aliment_admin)
        /**-------------------------------------retrofit and myshared -------------------------------------------------- */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        var role =myshared?.getString("role","")
        var nameImg =myshared?.getString("nameImg2","")

        /**.........................................findViewById................................................*/
        imageViewA = findViewById(R.id.imageViewA)
        val nomAlimentET = findViewById<EditText>(R.id.nomAliment)
        val caloriesET = findViewById<EditText>(R.id.calories)
        val quantiteET = findViewById<EditText>(R.id.quantite)
        val proteinesET = findViewById<EditText>(R.id.proteines)
        val glucidesET = findViewById<EditText>(R.id.glucides)
        val lipidesET = findViewById<EditText>(R.id.lipides)

        askPermissions();

        /************************************  Upload d'image   ***********************************/
        fabCamera = findViewById(R.id.camera);
        fabCamera.setOnClickListener{
            // startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            openGalleryForImage()
        }
        val save_aliment= findViewById<Button>(R.id.save_aliment)
        save_aliment.setOnClickListener {
            mBitmap = (imageViewA.drawable as BitmapDrawable).bitmap
            multipartImageUpload()
            /**-------------------------------- creation de map de requet---------------------------------*/
            val nomAliment = nomAlimentET.text.toString()
            val calories = caloriesET.text.toString()
            val quantite=quantiteET.text.toString()
            val proteines = proteinesET.text.toString()
            val glucides = glucidesET.text.toString()
            val lipides = lipidesET.text.toString()

            val map = HashMap<String?, String?>()
            map["nomAliment"] = nomAliment
            map["calories"] = calories
            map["quantite"] = quantite
            map["proteines"] = proteines
            map["glucides"] = glucides
            map["lipides"] = lipides
            map["name"]=nameImg.toString()

            if(role=="1"){
                 AddNotreAliment(map)
            }else{
                 AddAliment(map,token!!)
            }
        }
    }
    /**-------------------------------------------fin onCreate----------------------------------------*/

    fun AddNotreAliment(map: java.util.HashMap<String?, String?>?){
        val call = retrofitInterface!!.executeAddNotreAliment(map)
        call!!.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.code() == 200) {
                    finish()
                } else if (response.code() == 401) {
                    Toast.makeText(this@AjouterAlimentAdmin, "aliment existe", Toast.LENGTH_LONG).show()
                } else if (response.code() == 400) {
                    Toast.makeText(this@AjouterAlimentAdmin, "an error occured while saving aliment", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Void?>?, t: Throwable) {
                Toast.makeText(this@AjouterAlimentAdmin, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    fun AddAliment(map: java.util.HashMap<String?, String?>?,token:String){
        val call = retrofitInterface!!.executeAddAliment(map, token)
        call!!.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.code() == 200) {
                    finish()
                } else if (response.code() == 401) {
                    Toast.makeText(this@AjouterAlimentAdmin, "aliment existe", Toast.LENGTH_LONG).show()
                } else if (response.code() == 400) {
                    Toast.makeText(this@AjouterAlimentAdmin, "an error occured while saving aliment", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Void?>?, t: Throwable) {
                Toast.makeText(this@AjouterAlimentAdmin, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }




    private fun multipartImageUpload() {
        try {

            val body=buildImageBodyPart("upload", mBitmap!!)
            val name = RequestBody.create("text/plain".toMediaTypeOrNull(), "upload")
            val req: Call<Image>? = retrofitInterface!!.postImage(body,name)
            req!!.enqueue(object : Callback<Image> {
                override fun onResponse(call: Call<Image>, response: Response<Image>) {
                    var nameImg=response.body()!!.name
                    if (response.code() == 200) {
                        //9ayed e nameImg fi  myshared
                        var editor: SharedPreferences.Editor=myshared!!.edit()
                        editor.putString("nameImg2",nameImg)
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


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageViewA.setImageURI(data?.data) // handle chosen image
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



}