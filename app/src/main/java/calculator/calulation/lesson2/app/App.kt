package calculator.calulation.lesson2.app

import android.app.Application
import androidx.room.Room
import calculator.calulation.lesson2.room.HistoryDao
import calculator.calulation.lesson2.room.HistoryDataBase
import calculator.calulation.lesson2.room.MIGRATION_1_2

class App :Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
    companion object{
        private var appInstance : App? = null
        private var db: HistoryDataBase? = null
        private var nameDB = "HistoryDB1"

        fun getHistoryDao():HistoryDao{ //FIXME
            if(db==null){
                val builder = Room.databaseBuilder(appInstance!!.applicationContext,
                    HistoryDataBase::class.java,
                    nameDB)
                db = builder
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build()
                //db = builder.build() // FIXME
            }
            return db!!.historyDao()
        }
    }
}