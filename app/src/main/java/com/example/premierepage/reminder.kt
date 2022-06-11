package com.example.premierepage

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.premierepage.model.Aliments
import com.example.premierepage.model.Task
import com.example.premierepage.view.AlimentAdapter
import com.example.premierepage.view.TaskAdapter
import kotlinx.android.synthetic.main.activity_reminder.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*
import kotlin.collections.HashMap

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
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var recv: RecyclerView
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        /**-------------------------------------retrofit and myshared -------------------------------------------------- */
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
        myshared=getSharedPreferences("myshared",0)
        var token =myshared?.getString("token","")
        createNotificationChannel()
        pickDate()

        val nomTask:EditText=findViewById(R.id.nomTask)
        val descTask:EditText=findViewById(R.id.descTask)
        val addTask:LinearLayout=findViewById(R.id.addTask)

          recv= findViewById(R.id.tasksRecycler)

        addTask.setOnClickListener {
            val map = HashMap<String?, String?>()
            map["nomTask"] = nomTask.text.toString()
            map["descTask"] = descTask.text.toString()
            map["year"] =savedYear.toString()
            map["month"] =savedMonth.toString()
            map["day"] =savedDay.toString()
            map["hour"] =savedHour.toString()
            map["minute"] =savedMinute.toString()
            var editor: SharedPreferences.Editor=myshared!!.edit()
            editor.putString("nomTask",nomTask.text.toString())
            editor.putString("descTask",descTask.text.toString())
            editor.commit()

            AddTask(token!!,map)
            System.out.println("aaaaaaa")
            getTasks(token!!)
        }
        getTasks(token!!)

    }
    fun getTasks(t:String){
        //5edmet l affichage mte3 les aliments
        val call = retrofitInterface!!.executeAllTasks(t)
        call.enqueue(object : Callback<MutableList<Task>> {
            override fun onResponse(call: Call<MutableList<Task>>, response: Response<MutableList<Task>>) {
                System.out.println(response.code().toString())
                if (response.code()==200){
                    System.out.println("200")
                    recv.apply {
                        recv.layoutManager = LinearLayoutManager(this@reminder)
                        adapter= TaskAdapter(context,response.body()!!)
                    }
                }else if (response.code()==400){
System.out.println("400")
                }
            }
            override fun onFailure(call: Call<MutableList<Task>>, t: Throwable) {
                Toast.makeText(this@reminder, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    @SuppressLint("NewApi")
  /*  private fun getDateTimeCalendar(){
        val cal : Calendar = Calendar.getInstance()
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        var hour = cal.get(Calendar.HOUR)
        var minute = cal.get(Calendar.MINUTE)

    }*/

    private fun pickDate(){
        //Calendar
        val c = java.util.Calendar.getInstance()
        var year = c.get(java.util.Calendar.YEAR)
        var month = c.get(java.util.Calendar.MONTH)
        var day = c.get(java.util.Calendar.DAY_OF_MONTH)
        btn_timePicker.setOnClickListener {
           // getDateTimeCalendar()
            DatePickerDialog(this, this,year,month,day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int,month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month+1
        savedYear = year

       // getDateTimeCalendar()
        TimePickerDialog(this, this,hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
       savedHour = hourOfDay
        savedMinute = minute

        tv_textTime.text = "${savedDay.toString().padStart(2, '0')}-${savedMonth.toString().padStart(2, '0')}-$savedYear\n   ${savedHour.toString().padStart(2, '0')} : ${savedMinute.toString().padStart(2, '0')}"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun AddTask(token:String, map: java.util.HashMap<String?, String?>?){

        val sdf = SimpleDateFormat("dd-M-yyyy hh:mm:ss")
        val dateString = "$savedDay-$savedMonth-$savedYear $savedHour:$savedMinute:00"//"29-05-2022 12:00:00"
        val date: Date = sdf.parse(dateString)
        System.out.println("Given Time in milliseconds : " + date.getTime())
        Toast.makeText(this@reminder,date.getTime().toString(),Toast.LENGTH_LONG).show()

/*
        val i=Intent(this@reminder,App::class.java)
        intent!!.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent2=PendingIntent.getActivity(this@reminder,0,i,0)

        val builder= NotificationCompat.Builder(this@reminder,"foxandroid")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("focandroid Alarm Manager")
            .setContentText("Subscribe for more android relates content")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent2)

        val notificationManager= NotificationManagerCompat.from(this@reminder)
        notificationManager.notify(123,builder.build())
   */



        val cal : Calendar = Calendar.getInstance()
        //Setting the Calendar date and time to the given date and time
        //Setting the Calendar date and time to the given date and time
        cal.time = date

        alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this@reminder,AlarmReceiver::class.java)

        pendingIntent= PendingIntent.getBroadcast(this@reminder,0,intent,0)
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.timeInMillis, AlarmManager.INTERVAL_DAY,pendingIntent)
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal.timeInMillis,pendingIntent)
        Toast.makeText(this,"Alarm set Successfuly",Toast.LENGTH_LONG).show()

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
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name:CharSequence="foxandroidReminderChannel"
            val description="Channel For Alarm Manager"
            val importance= NotificationManager.IMPORTANCE_HIGH
            val channel= NotificationChannel("foxandroid",name,importance)
            channel.description=description
            val notificationManager=getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}