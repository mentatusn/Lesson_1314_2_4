package calculator.calulation.lesson2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import calculator.calulation.lesson2.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance()= MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.main_fragment, container, false)
    }

}