package com.example.proj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class FinalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)
        val hbtn: Button = findViewById(R.id.hbtn)
        hbtn.setOnClickListener{
            val intent = Intent(this@FinalActivity, MainActivity::class.java)
            startActivity(intent)
        }
        val username=intent.getStringExtra("username")
        val v1: TextView=findViewById(R.id.ustv)
        val Score=intent.getIntExtra("score",0)
        val v2: TextView=findViewById(R.id.uscore)
        v1.text="$username"
        v2.text="$Score"
    }
}