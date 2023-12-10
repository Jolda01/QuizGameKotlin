package com.example.proj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.RadioButton
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast

class Play : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        var currentQuestionIndex = 0
        var userScore=0
        val questions=arrayOf("How many bones are there in the human body?",
            "what is the capital of France?",
            "How many colors in a rainbow?",
            "What is the rarest blood type?",
            "What is the name of the actor who played Jack in the Titanic?",
            "What language is the most popularly spoken worldwide?",
            "Au is the chemical symbol for which element?",
            "Which planet is known as the red planet?",
            "Which is the most abundant element in the universe?",
            "What does DNA stand for?"
        )
        val answers= arrayOf(arrayOf("420","69","1337","206"),
            arrayOf("Paris","Berlin","London","Madrid"),
            arrayOf("6","7","8","9"),
            arrayOf("O-","A+","B-","AB-"),
            arrayOf("Rowan atckinson","Tom cruise","Morgan freeman","Leonardo Dicaproio"),
            arrayOf("chinese","Arabic","English","Rotokas"),
            arrayOf("Argon","Aluminium","Gold","Actinium"),
            arrayOf("Mars","Venus","Jupiter","Saturn"),
            arrayOf("Carbon","Oxygene","Hydrogene","Nitrogene"),
            arrayOf("Acid","Deoxynucleic Acid","Deoxyribonucleic Acid","Decarboxylic Acid")
        )
        val correctanswers= arrayOf(3,0,1,3,3,0,2,0,2,2)
        val questionTextView: TextView=findViewById(R.id.textViewQuestion)
        val radioButtonAnswer1: RadioButton = findViewById(R.id.radioButtonAnswer1)
        val radioButtonAnswer2: RadioButton = findViewById(R.id.radioButtonAnswer2)
        val radioButtonAnswer3: RadioButton = findViewById(R.id.radioButtonAnswer3)
        val radioButtonAnswer4: RadioButton = findViewById(R.id.radioButtonAnswer4)
        val nextButton: Button=findViewById(R.id.butonNext)
        val rg: RadioGroup =findViewById(R.id.rg)


        fun updateUI() {
            questionTextView.text = questions[currentQuestionIndex]
            radioButtonAnswer1.text = answers[currentQuestionIndex][0]
            radioButtonAnswer2.text = answers[currentQuestionIndex][1]
            radioButtonAnswer3.text = answers[currentQuestionIndex][2]
            radioButtonAnswer4.text = answers[currentQuestionIndex][3]
        }
        updateUI()
        nextButton.setOnClickListener {

            val selectedOption = when {
                radioButtonAnswer1.isChecked -> 0
                radioButtonAnswer2.isChecked -> 1
                radioButtonAnswer3.isChecked -> 2
                radioButtonAnswer4.isChecked -> 3
                else -> -1 // No option selected

            }

            if (selectedOption == -1) {
                // Show toast when no option is selected
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
            } else {
                rg.clearCheck()
                if (selectedOption == correctanswers[currentQuestionIndex]) {
                    userScore++
                }

                currentQuestionIndex++

                // Check if all questions have been answered
                if (currentQuestionIndex < questions.size) {
                    // Update UI for the next question
                    updateUI()
                } else {
                    val dbHelper = DatabaseHelper(this)
                    val username = intent.getStringExtra("username") ?: ""

                    val currentHighscore = dbHelper.getHighscore(username)

                    if (userScore > currentHighscore) {
                        dbHelper.updateHighscore(username, userScore)
                    }


                    val intent = Intent(this@Play, FinalActivity::class.java)
                    intent.putExtra("score", userScore)
                    intent.putExtra("username", username)

                    startActivity(intent)
                }
            }
        }
    }
}