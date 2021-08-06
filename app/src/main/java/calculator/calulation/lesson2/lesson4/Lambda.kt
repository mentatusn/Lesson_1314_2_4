package calculator.calulation.lesson2.lesson4

import android.util.Log

object Lambda {
    private val str = "outer"
    fun main(){

        val l= { int:Int->
            //Log.d("mylogs", "work inside")
            "result l $int "+str}

        val a = fun(int:Int):String{
            //Log.d("mylogs", "work inside")
            return "result a $int "+str
        }

        printHightOrder(l)
        printHightOrder(a)

        //Log.d("mylogs", l.toString())
        //Log.d("mylogs", l())
    }
    fun printHightOrder(block: (int:Int)->String){
        Log.d("mylogs", block(2))
    }
}