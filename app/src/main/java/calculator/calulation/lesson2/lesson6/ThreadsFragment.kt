package calculator.calulation.lesson2.lesson6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import calculator.calulation.lesson2.databinding.FragmentMainBinding
import calculator.calulation.lesson2.databinding.FragmentThreadsBinding
import calculator.calulation.lesson2.view.MainFragment
import kotlinx.android.synthetic.main.fragment_threads.*
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.TimeUnit

const val TEST_BROADCAST_INTENT_FILTER = "TEST BROADCAST INTENT FILTER"
const val THREADS_FRAGMENT_BROADCAST_EXTRA = "THREADS_FRAGMENT_EXTRA"
class ThreadsFragment : Fragment() {



    private val testReceiver: BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

            val srt = intent?.getStringExtra(THREADS_FRAGMENT_BROADCAST_EXTRA)

            intent?.getStringExtra(THREADS_FRAGMENT_BROADCAST_EXTRA).let{
                binding.mainContainer.addView(TextView(context).apply {
                    text = it
                    textSize = 30f
                })
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.registerReceiver(testReceiver, IntentFilter(TEST_BROADCAST_INTENT_FILTER))
    }

    private var _binding: FragmentThreadsBinding? = null
    private val binding: FragmentThreadsBinding
        get() :FragmentThreadsBinding {
            return _binding!!
        }


    var counter = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {


            val seconds: Int = Integer.parseInt(binding.editText.text.toString())
            val result = startCalculations(seconds).toString()
            binding.textView.text = result
            binding.mainContainer.addView(TextView(it.context).apply {
                text = "из потока $counter"
            })
        }

        binding.buttonFromThread.setOnClickListener {


            Thread {
                val seconds: Int = Integer.parseInt(binding.editText.text.toString())

                val result = startCalculations(seconds).toString()
                val handler = Handler(Looper.getMainLooper())
                handler.post(Runnable {
                    counter++
                    binding.textView.text = result
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "из потока $counter"
                    })
                })
            }.start()
        }
        /*val myThread = MyThread()
        myThread.start()
        binding.buttonEndlessThread.setOnClickListener {
            val myHandler = myThread.mHabdler
            myHandler?.post(Runnable {
                val seconds: Int = Integer.parseInt(binding.editText.text.toString())
                //Looper.myLooper()?.quit()
                //Looper.myLooper()?.quitSafely()
                val result = startCalculations(seconds).toString()
                val handler = Handler(Looper.getMainLooper())
                handler.post(Runnable {
                    counter++
                    binding.textView.text = result
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "из потока $counter"
                    })
                })
            })
        } */

        val myHandlerThread = HandlerThread("HandlerThread")
        myHandlerThread.start()
        binding.buttonEndlessThread.setOnClickListener {
            val myHandler = Handler(myHandlerThread.looper)
            myHandler.post(Runnable {
                val myHandler = Handler()
                val seconds: Int = Integer.parseInt(binding.editText.text.toString())
                //Looper.myLooper()?.quit()
                //Looper.myLooper()?.quitSafely()
                val result = startCalculations(seconds).toString()
                val handler = Handler(Looper.getMainLooper())
                handler.post(Runnable {
                    counter++
                    binding.textView.text = result
                    binding.mainContainer.addView(TextView(it.context).apply {
                        text = "из потока ${myHandlerThread.name} $counter"
                    })
                })
            })
        }


        binding.serviceButton.setOnClickListener {
            val i = Intent(it.context,MainService::class.java)
            i.putExtra(MAIN_SERVICE_STRING_EXTRA ,"привет Степан")
            context?.startService(i)
        }
        binding.serviceReceiverButton.setOnClickListener {
            val i = Intent(it.context,MainService::class.java)
            i.putExtra(MAIN_SERVICE_INT_EXTRA ,binding.editText.text.toString().toInt())
            context?.startService(i)
        }

        binding.receiver.setOnClickListener {
            val i = Intent("my.action")
            context?.sendBroadcast(i)
        }
        return binding.root
    }

    class MyThread : Thread() {

        var mHabdler: Handler? = null

        override fun run() {
            Looper.prepare()
            mHabdler = Handler()
            Looper.loop()
        }
    }


    private fun startCalculations(seconds: Int): Long {
        /*val date = Date()
        var diffInSec: Long
        do {
            val currentDate = Date()
            val diffInMs: Long = currentDate.time - date.time
            diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs)
            diffInSec = diffInMs/1000
            Log.d("mylogs","$diffInMs")
            Log.d("mylogs","$diffInSec")
        } while (diffInSec < seconds)*/
        sleep((seconds * 1000).toLong())
        return seconds.toLong()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        context?.let{
            LocalBroadcastManager.getInstance(it).unregisterReceiver(testReceiver)
        }
    }

    companion object {
        fun newInstance() = ThreadsFragment()
    }

}