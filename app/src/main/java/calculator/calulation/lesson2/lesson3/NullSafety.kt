package calculator.calulation.lesson2.lesson3

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File

object NullSafety {
    var str1: String = ""
    val str2: String? = null

    fun foo() {
        //Log.d("mylogs",str2)
        if (str2 != null) {

        }
        str1 = str2 ?: ""
        str1 = if (str2 != null) str2 else ""
        val test: NullSafety2 =NullSafety2()



        test?.main()

       // Log.d("mylogs", "${(Object().equals(Any()))} очень интересно")
        var testAny:Any = Any()
        var testObject:Object = Object()
        Log.d("mylogs", "${(testAny is Any)} очень интересно 1")
        Log.d("mylogs", "${(testAny is Object)} очень интересно 2")
        Log.d("mylogs", "${(testObject is Any)} очень интересно 3")
        Log.d("mylogs", "${(testObject is Object)} очень интересно 4")
        //val str3: File = AppCompatActivity().cacheDir
    }
}

class NullSafety2 {
    val testInt: Int = 1
    fun main():Nothing {
        Log.d("mylogs", "main вызвалась")
        main()
    }
}