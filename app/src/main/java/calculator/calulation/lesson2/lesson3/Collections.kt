package calculator.calulation.lesson2.lesson3

import android.util.Log

object Collections:Interface {

    fun writeValueForString(input:String){
        Log.d("mylogs", "${(input.toString())}")
    }
    fun writeValueForInt(input:Int){
        Log.d("mylogs", "${(input.toString())}")
    }
    fun writeValueForDouble(input:Double){
        Log.d("mylogs", "${(input.toString())}")
    }

    fun writeValueForChar(input:Char){
        Log.d("mylogs", "${(input.toString())}")
    }

    fun <T> writeValue(input:T){
        Log.d("mylogs", "${(input.toString())}")
    }

    fun foo() {
        /*val array1:Array<Int> = arrayOf(1,2,3,4,5)
        array1[1] = 3*/
        /*writeValueForString("1")
        writeValueForDouble(1.0)
        writeValueForChar('1')
        writeValueForInt(1)*/

        writeValue("1")
        writeValue(1.0)
        writeValue('1')
        writeValue(1)


        var list:List<Int> = listOf(5,2,10,4,5)
        var listMutable:List<Age> = mutableListOf(Age(1),
            Age(2),Age(3),Age(4))

        //list = list.map { it*2 }
        //list = list.filter { it>=6 }
        list = list.sortedBy { selector(it) }

        //Log.d("mylogs", "${(list)} list")


        /*listMutable.add(listMutable.size,Age(6))
        val newAge = Age(6)
        val listHack:MutableList<Age?> = list.toMutableList()
        listHack.add(listHack.size,newAge)*/
        TODO(" нужно занулить список =      list = listHack")
    }

    override val interTest: Collections
        get() = TODO("Not yet implemented")

    override fun testFun() {
        TODO("Not yet implemented")
    }

    override fun testFunString(): String {
        TODO("Not yet implemented")
    }

}



interface Interface {
    val interTest:Collections
    val itertest2:String
    get() = "getter for itertest2"
    fun testFun():Unit
    fun testFunString():String

    fun testFunNonabstract(){
        interTest.foo()
    }
}

fun selector(p: Int): Int = p


class Age(var age:Int){

}

class Collections2 {
    val testInt: Int = 1
    fun main():Nothing {
        Log.d("mylogs", "main вызвалась")
        main()
    }
}