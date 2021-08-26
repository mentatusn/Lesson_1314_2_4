package calculator.calulation.lesson2.view

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import calculator.calulation.lesson2.R
import calculator.calulation.lesson2.databinding.ActivityMainBinding
import calculator.calulation.lesson2.lesson10.MapsFragment
import calculator.calulation.lesson2.lesson6.MyBroadcastReceiver
import calculator.calulation.lesson2.lesson6.ThreadsFragment
import calculator.calulation.lesson2.lesson9.ContentProviderFragment
import calculator.calulation.lesson2.view.history.HistoryFragment
import calculator.calulation.lesson2.view.main.MainFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult


class MainActivity : AppCompatActivity() {


    val CHANNEL_ID1="fisrt_channel"
    val CHANNEL_ID2="second_channel"
    val NOTIFICATION_ID=1

    var myBroadcastReceiver:MyBroadcastReceiver? = MyBroadcastReceiver()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commit()
        }
        //registerReceiver(myBroadcastReceiver, IntentFilter("android.intent.action.LOCALE_CHANGED"))
        //registerReceiver(myBroadcastReceiver, IntentFilter("my.action"))
        //push()


        /** Получаем ID клиента */
        FirebaseInstanceId.getInstance().getInstanceId()
            .addOnCompleteListener(OnCompleteListener<InstanceIdResult> { task ->
                if (!task.isSuccessful) {
                    Log.d("mylogs", task.exception.toString() + "")
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token: String = task.result!!.getToken()
                // Log and toast
                Log.d("mylogs", token)
            })

    }

    private fun push() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID2).apply {
            setContentTitle("Title1")
            setContentText("Message1")
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notificationManager)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createChannel(notificationManager: NotificationManager) {
        val name = "Важное новый2"
        val description1 = "От этого зависит судьба Земли"
        val description2 = "Не выключай, Христа ради"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID2, name, importance).apply {
            description = description1
        }
        notificationManager.createNotificationChannel(channel)
    }


    override fun onDestroy() {
        super.onDestroy()
        myBroadcastReceiver = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.threads->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ThreadsFragment.newInstance()).commit()
                true
            }
            R.id.action_history->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HistoryFragment.newInstance()).addToBackStack("").commit()
                true
            }

            R.id.action_content_provider->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ContentProviderFragment.newInstance()).addToBackStack("").commit()
                true
            }

            R.id.action_menu_google_maps->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MapsFragment.newInstance()).addToBackStack("").commit()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}