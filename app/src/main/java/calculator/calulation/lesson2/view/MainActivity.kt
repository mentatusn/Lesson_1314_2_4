package calculator.calulation.lesson2.view

import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import calculator.calulation.lesson2.R
import calculator.calulation.lesson2.databinding.ActivityMainBinding
import calculator.calulation.lesson2.lesson6.MyBroadcastReceiver
import calculator.calulation.lesson2.lesson6.ThreadsFragment
import calculator.calulation.lesson2.lesson9.ContentProviderFragment
import calculator.calulation.lesson2.view.history.HistoryFragment
import calculator.calulation.lesson2.view.main.MainFragment


class MainActivity : AppCompatActivity() {

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
            else ->super.onOptionsItemSelected(item)
        }
    }
}