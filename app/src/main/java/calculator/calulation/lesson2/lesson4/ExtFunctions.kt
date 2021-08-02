package calculator.calulation.lesson2.lesson4

import android.util.Log

object ExtFunctions {

    fun squareUsual(int: Int): Int {
        return int * int
    }

    fun Int.square(): Int {
        return this * this
    }

    fun letterCounterUsual(string: String, char: Char): Int {
        var counter = 0
        for (letter in string) {
            if (letter == char) {
                counter++
            }
        }
        return counter
    }


    data class Person(val name: String, var age: Int)

    fun Person.printExt() {
        Log.d("mylogs", "Имя: ${this.name} - ${this.age} годиков")
    }

    fun main() {
        //Log.d("mylogs", "work ${"aaabbbbccc".letterCounter('b')}")
        //Log.d("mylogs", "work ${2.square()}")


        //person.printExt()

        //val persons = listOf(person, Person("Оля", 18))


        val personV:Person? = Person("Вася", 20)

        personV?.let{
            with(personV){
                printExt()
            }
        }


        run {   }
        var f = ""
        val personO = personV.apply {  }

        var x=1
        var y=5

        Log.d("mylogs", "x = $x y = $y")
        x = y.also { y=x }
        Log.d("mylogs", "x = $x y = $y")



        /*val wi = with(persons){
            forEach({ p: Person -> p.printExt() })
            Log.d("mylogs", "________________")
            forEach{ p: Person -> p.printExt() }
            Log.d("mylogs", "________________")
            forEach{  it.age+=2 }
        }

        val app = persons.apply{
            forEach({ p: Person -> p.printExt() })
            Log.d("mylogs", "________________")
            forEach{ p: Person -> p.printExt() }
            Log.d("mylogs", "________________")
            forEach{  it.printExt() }
            forEach{  it.age+=2 }
        }

        persons.forEach({ p: Person -> p.printExt() })
        Log.d("mylogs", "________________")
        persons.forEach{ p: Person -> p.printExt() }
        Log.d("mylogs", "________________")
        persons.forEach{  it.printExt() }*/
    }
}

fun String.letterCounter(char: Char): Int {
    var counter = 0
    for (letter in this) {
        if (letter == char) {
            counter++
        }
    }
    return counter
}
