package com.example.premierepage

import android.Manifest.permission
import android.Manifest.permission.*
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import java.io.File

import okhttp3.MediaType.Companion.toMediaTypeOrNull



import okhttp3.MultipartBody.Part.Companion.createFormData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.graphics.Bitmap



import java.util.ArrayList

import android.net.Uri
import android.os.Build
import android.content.pm.PackageManager
import android.os.Parcelable

import android.content.Intent

import android.content.ComponentName
import android.content.SharedPreferences

import android.content.pm.ResolveInfo
import android.graphics.BitmapFactory
import android.graphics.Color

import android.provider.MediaStore
import android.widget.*
import java.io.IOException

import java.io.FileNotFoundException

import okhttp3.ResponseBody

import okhttp3.MediaType

import okhttp3.RequestBody

import okhttp3.MultipartBody

import java.io.FileOutputStream

import java.io.ByteArrayOutputStream
import android.database.Cursor
import android.database.Cursor.FIELD_TYPE_STRING
import android.graphics.drawable.BitmapDrawable
import com.example.premierepage.model.Image
import kotlinx.android.synthetic.main.activity_ajouter_exercice_admin.*


class AjouterExerciceAdmin : AppCompatActivity() {




    var picUri: Uri? = null
    private var permissionsToRequest: ArrayList<String>? = null
    private val permissionsRejected = ArrayList<String>()
    private val permissions = ArrayList<String>()
    private val ALL_PERMISSIONS_RESULT = 107
    private val IMAGE_RESULT = 200
    private val REQUEST_CODE=200
    lateinit var fabCamera: Button
    lateinit var fabUpload:Button
    lateinit var mBitmap: Bitmap
    var textView: TextView? = null

    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_exercice_admin)
        /**-------------------------------------retrofit and myshared-------------------------------------------------- */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var nameImg =myshared?.getString("nameImg","")

        /**.................................   .findViewById.   ...............................................*/
        val nomExerciceET = findViewById<EditText>(R.id.nomExercice)
        val caloriesET = findViewById<EditText>(R.id.calories)
        val imageViewG = findViewById<ImageView>(R.id.imageViewG)
        fabCamera = findViewById(R.id.addImage);
        fabUpload = findViewById(R.id.uploadImage);

        /************************************  Upload d'image   ***********************************/
        fabCamera.setOnClickListener{
            openGalleryForImage()
        }
        fabUpload.setOnClickListener{
             mBitmap = (imageViewG.drawable as BitmapDrawable).bitmap
            multipartImageUpload()
            val nomExercice = nomExerciceET.text.toString()
            val calories = caloriesET.text.toString()
            val map = HashMap<String?, String?>()
            map["nomExercice"] = nomExercice
            map["calories"] = calories
            map["name"]=nameImg.toString()

            val call = retrofitInterface!!.executeAddExercice(map)
            call!!.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code() == 200) {
                        //fase5 nameImg men myshared
                        finish()
                    } else if (response.code() == 401) {
                        Toast.makeText(this@AjouterExerciceAdmin, "aliment existe", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        Toast.makeText(this@AjouterExerciceAdmin, "an error occured while saving aliment", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Void?>?, t: Throwable) {
                    Toast.makeText(this@AjouterExerciceAdmin, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        askPermissions();

    }


/****                    5edma zeyda  5demtha w ena njareb wa7di                   */
    fun getImageFilePath(data: Intent?): String{

        return getImageFromFilePath(data)!!
    }
    private fun getImageFromFilePath(data: Intent?): String? {

        val isCamera = data == null || data.data == null

         if (isCamera) {

             return getCaptureImageOutputUri().path

         }
        else {

             return getPathFromURI(data!!.data!!)
         }
        }
    private fun getPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)

        val cursor = contentResolver.query(contentUri, proj, null, null, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
       // Toast.makeText(this,cursor.getString(column_index).toString(),Toast.LENGTH_LONG).show()
        return cursor.getString(column_index)


    }
    fun getPickImageChooserIntent(): Intent? {
        val outputFileUri: Uri = getCaptureImageOutputUri()
        val allIntents: MutableList<Intent> = ArrayList()
        val packageManager = packageManager
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)

        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            }
            allIntents.add(intent)
        }

        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            val intent = Intent(galleryIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            allIntents.add(intent)
        }


        var mainIntent = allIntents[allIntents.size-1]

        for (intent in allIntents) {
            if (intent.component!!.className == "com.android.documentsui.DocumentsActivity") {
                mainIntent = intent
                break
            }
        }
     //   allIntents.remove(mainIntent)

        val chooserIntent = Intent.createChooser(mainIntent, "Select source")

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>())
        return chooserIntent
    }
    private fun getCaptureImageOutputUri(): Uri{
        var outputFileUri: Uri? = null
        val getImage = getExternalFilesDir("")
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, "profile.png"))
        }
        return outputFileUri!!
    }

    /*******************************************************************************************************************/




    /** ******************************************************    5edmet l permission  ******************************************/
    private fun askPermissions() {
        permissions.add(CAMERA)
        permissions.add(WRITE_EXTERNAL_STORAGE)
        permissions.add(READ_EXTERNAL_STORAGE)
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
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        startActivityForResult(intent, REQUEST_CODE)
    }
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