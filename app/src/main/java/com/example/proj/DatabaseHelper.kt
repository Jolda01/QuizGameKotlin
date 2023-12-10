package com.example.proj
import com.example.proj.ui.login.User
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Define table and column names, create table query, etc.
    // ...
    companion object {
        const val DATABASE_NAME = "user_database"
        const val DATABASE_VERSION = 2

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_HIGHSCORE = "highscore"

    }

    private val CREATE_USER_TABLE = """
    CREATE TABLE $TABLE_USERS (
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_USERNAME TEXT,
        $COLUMN_PASSWORD TEXT,
        $COLUMN_HIGHSCORE INTEGER DEFAULT NULL
    )
"""

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(context: Context, username: String, password: String, highscore: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_HIGHSCORE, highscore)
        }
        val userId = db.insert(TABLE_USERS, null, values)
        db.close()
        if (userId != -1L) {
            // User added successfully
            Log.d(
                "Database",
                "User added: id=$userId, username=$username, password=$password, highscore=$highscore"
            )
            Toast.makeText(context, "User added", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        } else {
            // Failed to add user
            Log.e(
                "Database",
                "Failed to add user: username=$username, password=$password, highscore=$highscore"
            )
            Toast.makeText(context, "Failed to add user", Toast.LENGTH_SHORT).show()
        }
    }

    fun getHighscore(username: String): Int {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_HIGHSCORE),
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var highscore = 0
        if (cursor.moveToFirst()) {
            highscore = cursor.getInt(cursor.getColumnIndex(COLUMN_HIGHSCORE))
        }

        cursor.close()
        db.close()

        return highscore
    }

    fun updateHighscore(username: String, newHighscore: Int) {
        val existingHighscore = getHighscore(username)

        if (newHighscore > existingHighscore) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_HIGHSCORE, newHighscore)
            }

            val rowsAffected =
                db.update(TABLE_USERS, values, "$COLUMN_USERNAME = ?", arrayOf(username))
            db.close()

            if (rowsAffected > 0) {
                Log.d("Database", "Highscore updated for user $username: $newHighscore")
            } else {
                Log.e("Database", "Failed to update highscore for user $username")
            }
        }
        }
        fun checkLoginCredentials(username: String, password: String): Boolean {
            val db = readableDatabase
            val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
            val selectionArgs = arrayOf(username, password)

            val cursor = db.query(
                TABLE_USERS,
                arrayOf(COLUMN_ID),  // You only need to select the ID for checking existence
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            val isValidLogin = cursor.count > 0

            cursor.close()
            db.close()

            return isValidLogin
        }

        fun getUserByUsername(username: String): User? {
            val db = readableDatabase
            val selection = "$COLUMN_USERNAME = ?"
            val selectionArgs = arrayOf(username)
            val cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null)

            if (cursor.moveToFirst()) {

                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
                val highscore = cursor.getInt(cursor.getColumnIndex(COLUMN_HIGHSCORE))

                cursor.close()
                db.close()

                return User(id, username, password, highscore)
            }

            cursor.close()
            db.close()

            return null
        }

}

