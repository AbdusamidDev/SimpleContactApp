package developer.abdusamid.app.databases

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import developer.abdusamid.app.models.Contact
import developer.abdusamid.app.utils.Constant
import developer.abdusamid.app.utils.DBServiceInterface

class MyDbHelper(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION),
    DBServiceInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "create table ${Constant.TABLE_NAME} (${Constant.ID} integer not null primary key autoincrement unique, ${Constant.NAME} text not null, ${Constant.PHONE_NUMBER} text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists ${Constant.TABLE_NAME}")
        onCreate(db)
    }

    override fun addContact(contact: Contact) {
        val dataBase = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(Constant.NAME, contact.name)
        contentValue.put(Constant.PHONE_NUMBER, contact.phoneNumber)
        dataBase.insert(Constant.TABLE_NAME, null, contentValue)
        dataBase.close()
    }

    override fun deleteContact(contact: Contact) {
        val database = this.writableDatabase
        database.delete(Constant.TABLE_NAME, "${Constant.ID} = ?", arrayOf("${contact.id}"))
        database.close()
    }

    override fun upDataContact(contact: Contact): Int {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(Constant.ID, contact.id)
        contentValue.put(Constant.NAME, contact.name)
        contentValue.put(Constant.PHONE_NUMBER, contact.phoneNumber)

        return database.update(
            Constant.TABLE_NAME,
            contentValue,
            "${Constant.ID} = ?",
            arrayOf(contact.id.toString())
        )
    }

    @SuppressLint("Recycle")
    override fun getAllContact(): ArrayList<Contact> {
        val list = ArrayList<Contact>()
        val query = "select * from ${Constant.TABLE_NAME}"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val phone = cursor.getString(2)
                val contact = Contact(id, name, phone)
                list.add(contact)
            } while (cursor.moveToNext())
        }
        return list
    }
}