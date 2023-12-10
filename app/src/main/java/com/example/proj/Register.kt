package com.example.proj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val hbtn: Button = findViewById(R.id.hbtn)
            hbtn.setOnClickListener{
                val intent = Intent(this@Register, MainActivity::class.java)
                startActivity(intent)
            }
        val sbtbtn: Button = findViewById(R.id.Pbtn)
        val usert: EditText=findViewById(R.id.usert)
        val pset: EditText=findViewById(R.id.pset)
        val dbHelper = DatabaseHelper(this)
        sbtbtn.setOnClickListener {

            val username = usert.text.toString()
            val password = pset.text.toString()

            dbHelper.insertUser(this, username,password,0)

            // You may also want to add additional logic here, such as showing a success message.
        }
    }

}