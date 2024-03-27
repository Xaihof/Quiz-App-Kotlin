package com.xoksis.kotlinquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.xoksis.kotlinquizapp.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding // Binding.

    // For Questions.
    private var name: String? = null
    private var avatar: String? = null
    private var score: Int = 0
    private var currentPosition: Int = 1
    private var questionList: ArrayList<QuestionData>? = null
    private var selectedOption: Int = 0

    // For Timer.
    private var timeCountDown: CountDownTimer? = null
    private var timeProgress = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra(setData.name)
        avatar = intent.getStringExtra(setData.avatar)

        setNameAndAvatar(name.toString(),avatar!!.toInt())

        questionList = setData.getQuestion()

        setQuestion()

        binding.opt1.setOnClickListener {

            selectedOptionStyle(binding.opt1, 1)
        }

        binding.opt2.setOnClickListener {

            selectedOptionStyle(binding.opt2, 2)
        }

        binding.opt3.setOnClickListener {

            selectedOptionStyle(binding.opt3, 3)
        }

        binding.opt4.setOnClickListener {

            selectedOptionStyle(binding.opt4, 4)
        }

        binding.submit.setOnClickListener {

            if (selectedOption != 0) {

                binding.submit.setText("Next")

                val question = questionList!![currentPosition - 1]

                if (selectedOption != question.correct_ans)

                    setColor(selectedOption, R.drawable.wrong_question_option)
                else
                    score++

                setColor(question.correct_ans, R.drawable.correct_question_option)

                if (currentPosition == questionList!!.size)
                    binding.submit.text = "Finish"
                else
                    binding.submit.text = "Next"

            } else {

                nextQuestion()

            }
            selectedOption = 0

        }

    }

    fun nextQuestion() {
        currentPosition++
        binding.submit.setText("Submit")
        when {
            currentPosition <= questionList!!.size -> setQuestion()
            else -> {

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(setData.name, name.toString())
                intent.putExtra(setData.score, score.toString())
                intent.putExtra(setData.avatar, avatar.toString())
                intent.putExtra("total size", questionList!!.size.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    fun setColor(opt: Int, color: Int) {

        when (opt) {
            1 -> binding.opt1.background = ContextCompat.getDrawable(this, color)
            2 -> binding.opt2.background = ContextCompat.getDrawable(this, color)
            3 -> binding.opt3.background = ContextCompat.getDrawable(this, color)
            4 -> binding.opt4.background = ContextCompat.getDrawable(this, color)
        }
    }

    private fun setQuestion() {

        val question = questionList!![currentPosition - 1]
        setOptionStyle()

        binding.progressBar.progress = currentPosition
        binding.progressBar.max = questionList!!.size

        binding.progressText.text = "${currentPosition} / ${questionList!!.size}"
        binding.questionText.text = question.question
        binding.opt1.text = question.option_one
        binding.opt2.text = question.option_two
        binding.opt3.text = question.option_three
        binding.opt4.text = question.option_four
        resetTimer()
    }

    fun setOptionStyle() {

        val optionList: ArrayList<TextView> = arrayListOf()
        optionList.add(0, binding.opt1)
        optionList.add(1, binding.opt2)
        optionList.add(2, binding.opt3)
        optionList.add(3, binding.opt4)

        for (op in optionList) {
            op.setTextColor(Color.parseColor("#555155"))
            op.background = ContextCompat.getDrawable(this, R.drawable.question_option)
            op.typeface = Typeface.DEFAULT
        }
    }

    fun selectedOptionStyle(view: TextView, opt: Int) {

        setOptionStyle()
        selectedOption = opt
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_question_option)
        view.typeface = Typeface.DEFAULT_BOLD
        view.setTextColor(Color.parseColor("#000000"))

    }

    fun startTimer() {
        timeCountDown = object : CountDownTimer(21000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeProgress++
                binding.progressTextTimer.text = (21 - timeProgress).toString()
                binding.progressBarTimer.progress = 21 - timeProgress
            }

            override fun onFinish() {
                Toast.makeText(this@QuestionActivity, "Time Up!", Toast.LENGTH_SHORT).show()
                nextQuestion()

            }
        }.start()
    }

    fun resetTimer() {

        if (timeCountDown != null) {

            timeCountDown!!.cancel()
            timeProgress = 0
            timeCountDown = null
            binding.progressBarTimer.progress = 0
            binding.progressTextTimer.text = "21"
        }
        startTimer()
    }

    fun setNameAndAvatar(name: String, avatar: Int) {

        binding.textViewName.text = "Hi, ${name}"
        if (avatar == 1) {
            binding.imageViewAvatar.setImageResource(R.drawable.avatar_boy)

        } else if (avatar == 2) {
            binding.imageViewAvatar.setImageResource(R.drawable.avatar_girl)

        } else if (avatar == 3) {
            binding.imageViewAvatar.setImageResource(R.drawable.avatar_man)

        } else {
            binding.imageViewAvatar.setImageResource(R.drawable.avatar_woman)
        }
    }
}