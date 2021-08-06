package calculator.calulation.lesson2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import calculator.calulation.lesson2.R
import calculator.calulation.lesson2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commit()
        }
        //ExtFunctions.main()
        //mainD()
    }
}