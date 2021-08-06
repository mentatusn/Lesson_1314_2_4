package calculator.calulation.lesson2.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import calculator.calulation.lesson2.databinding.MainActivityWebviewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivityWebView : AppCompatActivity() {


    lateinit var binding: MainActivityWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ok.setOnClickListener(clickListener)
    }

    var clickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {

            Thread {
                var httpsURLConnection: HttpsURLConnection? = null
                try {
                    val url = URL(binding.url.text.toString())
                    httpsURLConnection = url.openConnection() as HttpsURLConnection
                    httpsURLConnection.requestMethod = "GET"
                    httpsURLConnection.connectTimeout = 5000

                    var reader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                    val result = reader.lines().collect(Collectors.joining("\n"))
                    Log.d("mylogs", result)
                    runOnUiThread {
                        //binding.webView.loadData(result,"text/html; charset = utf-8","utf-8")
                        binding.webView.loadDataWithBaseURL(
                            null,
                            result,
                            "text/html; charset = utf-8",
                            "utf-8",
                            null
                        )
                    }

                } catch (e: ExceptionInInitializerError) {
                    Log.d("mylogs", e.toString())
                    Log.d("mylogs", e.stackTraceToString())
                    Log.d("mylogs", e.localizedMessage)
                }
            }.start()
        }
    }


}