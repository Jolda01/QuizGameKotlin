package com.example.proj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val hbtn: Button = findViewById(R.id.hbtn)
        hbtn.setOnClickListener{
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
        }
        val loginButton: Button = findViewById(R.id.Pbtn)
        val usert: EditText =findViewById(R.id.usert)
        val pset: EditText =findViewById(R.id.pset)
        loginButton.setOnClickListener {
            val username =usert.text.toString()
            val password =pset.text.toString()
            val dbHelper = DatabaseHelper(this)
            if (dbHelper.checkLoginCredentials(username, password)) {
                val user=dbHelper.getUserByUsername(username)
                // TODO: Navigate to the play activity
                val intent = Intent(this@Login, Ui::class.java).apply{
                    putExtra("username", user?.username)
                    putExtra("highscore", user?.highscore)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show()
            }
        }
}
}