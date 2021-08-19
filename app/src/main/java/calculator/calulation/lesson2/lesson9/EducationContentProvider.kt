package calculator.calulation.lesson2.lesson9

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import calculator.calulation.lesson2.R
import calculator.calulation.lesson2.app.App
import calculator.calulation.lesson2.room.*


private const val URI_ALL = 1 // URI для всех записей
private const val URI_ID = 2 // URI для конкретной записи
private const val ENTITY_PATH = "HistoryEntity"

class EducationContentProvider : ContentProvider() {

    private var authorities: String? = null
    private lateinit var uriMatcher: UriMatcher
    private var entityContentType: String? = null
    private var entityContentItemType: String? = null
    private lateinit var contentUri: Uri

    override fun onCreate(): Boolean {
        authorities = context?.resources?.getString(
            R.string.authorities
        )
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(authorities, ENTITY_PATH, URI_ALL)
        uriMatcher.addURI(authorities, "$ENTITY_PATH/#", URI_ID)
        entityContentType = "vnd.android.cursor.dir/vnd.$authorities.$ENTITY_PATH"
        // Тип содержимого — один объект
        entityContentItemType = "vnd.android.cursor.item/vnd.$authorities.$ENTITY_PATH"

        contentUri = Uri.parse("content://$authorities/$ENTITY_PATH")

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val historyDao: HistoryDao = App.getHistoryDao()
        val cursor =
            when (uriMatcher.match(uri)) {
                URI_ALL -> historyDao.getHistoryCursor()
                URI_ID -> {
                    val id = ContentUris.parseId(uri)
                    historyDao.getHistoryCursor(id)
                }
                else -> throw IllegalStateException(" bad uri $uri")
            }
        cursor.setNotificationUri(context!!.contentResolver,contentUri)
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val historyDao: HistoryDao = App.getHistoryDao()
        val id = ContentUris.parseId(uri)
        historyDao.deleteById(id)
        context?.contentResolver?.notifyChange(uri,null)
        return 1
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            URI_ALL -> entityContentType
            URI_ID -> entityContentItemType
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val historyDao: HistoryDao = App.getHistoryDao()
        val entity =map(values)
        val id = entity.id
        historyDao.insert(entity)
        val resultUri = ContentUris.withAppendedId(contentUri,id)
        context?.contentResolver?.notifyChange(uri,null)
        return resultUri
    }

    // Переводим ContentValues в HistoryEntity
    private fun map(values: ContentValues?): HistoryEntity {
        return if (values == null) {
            HistoryEntity()
        } else {
            val id = if (values.containsKey(ID)) values[ID] as Long else 0
            val name = values[NAME] as String
            val temperature = values[TEMPERATURE] as Int
            HistoryEntity(id, name, temperature)
        }
    }




    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val historyDao: HistoryDao = App.getHistoryDao()
        val entity =map(values)
        historyDao.update(entity)
        return 1
    }
}