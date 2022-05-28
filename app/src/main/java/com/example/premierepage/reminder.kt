package com.example.premierepage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_reminder.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Year

class reminder : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0


    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0


    private var retrofitInterface: RetrofitInterface? = null
    var myshared: SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        /**-------------------------------------retrofit and myshared -------------------------------------------------- */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        pickDate()

        val nomTask:EditText=findViewById(R.id.nomTask)
        val descTask:EditText=findViewById(R.id.descTask)
        val addTask:LinearLayout=findViewById(R.id.addTask)


        addTask.setOnClickListener {
            val map = HashMap<String?, String?>()
            map["nomTask"] = nomTask.text.toString()
            map["descTask"] = descTask.text.toString()
            map["year"] =savedYear.toString()
            map["month"] =savedMonth.toString()
            map["day"] =savedDay.toString()
            map["hour"] =savedHour.toString()
            map["minute"] =savedMinute.toString()
            Toast.makeText(this@reminder,savedYear.toString(),Toast.LENGTH_LONG).show()
            AddTask(token!!,map)
        }

    }
    @SuppressLint("NewApi")
    private fun getDateTimeCalendar(){
        val cal : Calendar = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        var hour = cal.get(Calendar.HOUR)
        var minute = cal.get(Calendar.MINUTE)

    }

    private fun pickDate(){
        btn_timePicker.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this,year,month,day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int,month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(this, this,hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
       savedHour = hourOfDay
        savedMinute = minute

        tv_textTime.text = "$savedDay-$savedMonth-$savedYear\n Heure: $savedHour Minute: $savedMinute"
    }

    fun AddTask(token:String,map: java.util.HashMap<String?, String?>?){
        val call = retrofitInterface!!.executeAddTask(token,map)
        call!!.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.code() == 200) {
                    finish()
                } else if (response.code() == 401) {
                    Toast.makeText(this@reminder, "Task existe", Toast.LENGTH_LONG).show()
                } else if (response.code() == 400) {
                    Toast.makeText(this@reminder, "an error occured while saving task", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Void?>?, t: Throwable) {
                Toast.makeText(this@reminder, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}