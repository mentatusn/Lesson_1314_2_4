package calculator.calulation.lesson2.lesson6

import android.app.IntentService
import android.content.Intent
import android.util.Log


const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"
const val MAIN_SERVICE_INT_EXTRA = "MainServiceExtraInt"
class MainService(name: String="def"): IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        toast("onHandleIntent")
        intent?.let{

            val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
            val str = it.getIntExtra(MAIN_SERVICE_INT_EXTRA,-1)
            broadcastIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA,str.toString())
            sendBroadcast(broadcastIntent)
            //toast(intent.getStringExtra(MAIN_SERVICE_STRING_EXTRA)?:"")
        }

    }
    fun toast(m:String){
        Log.d("mylogs",m)
    }

    override fun onCreate() {
        super.onCreate()
        toast("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        toast("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        toast("onDestroy")
    }


}