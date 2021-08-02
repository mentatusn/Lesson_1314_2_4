package calculator.calulation.lesson2.lesson4

import android.util.Log

val lazyField:String by lazy {
    Log.d("mylogs", " обратились")
    "string1"
}

fun mainD(){
    /*val baseImpl= BaseImpl()
    val deleagate= Deleagate(baseImpl,baseImpl)
    deleagate.funBase1()
    deleagate.funBase2()*/
    Log.d("mylogs", " lazyField "+lazyField)
}

class Deleagate(base1:Base1,base2:Base2):Base1 by base1,Base2 by base2{


}
interface Base1{
    fun funBase1()
    val fieldBase1:String
}
interface Base2{
    fun funBase2()
    val fieldBase2:String
}

class BaseImpl() :Base1,Base2{
    override fun funBase1() {
        Log.d("mylogs", " BaseImpl funBase1() "+fieldBase1)
    }
    override fun funBase2() {
        Log.d("mylogs", " BaseImpl funBase2() "+fieldBase2)
    }

    override val fieldBase1: String = "fieldBase1 BaseImpl"
    override val fieldBase2: String = "fieldBase2 BaseImpl"
}