package com.example.proj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button

class Ui : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)
        val hbtn: Button = findViewById(R.id.hbtn)
        hbtn.setOnClickListener{
            val intent = Intent(this@Ui, MainActivity::class.java)
            startActivity(intent)
        }
        val pbtn: Button=findViewById(R.id.Pbtn)
        pbtn.setOnClickListener{
            val username = intent.getStringExtra("username") ?: ""
            val intent=Intent(this@Ui,Play::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }
        val usernameTextView:TextView = findViewById(R.id.username)
        val highscoreTextView:TextView = findViewById(R.id.userhighscore)
        val username = intent.getStringExtra("username") ?: ""
        val highscore = intent.getIntExtra("highscore", 0)
        usernameTextView.text = "$username"
        highscoreTextView.text = "$highscore"
    }
}