package com.xoksis.kotlinquizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.xoksis.kotlinquizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var selectedOption: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        // Hide the status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // send button.
        mainBinding.button.setOnClickListener {

            if (mainBinding.input.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter Your Name", Toast.LENGTH_SHORT).show()
            } else if (selectedOption == 0) {
                Toast.makeText(this, "Select Your Avatar", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra(setData.name, mainBinding.input.text.toString())
                intent.putExtra(setData.avatar, selectedOption.toString())
                startActivity(intent)
                finish()
            }
        }

        // Avatar.
        mainBinding.imageViewAvatarBoy.setOnClickListener {
            selectedOptionStyle(mainBinding.imageViewAvatarBoy, 1)
        }

        mainBinding.imageViewAvatarGirl.setOnClickListener {
            selectedOptionStyle(mainBinding.imageViewAvatarGirl, 2)
        }

        mainBinding.imageViewAvatarMan.setOnClickListener {
            selectedOptionStyle(mainBinding.imageViewAvatarMan, 3)
        }

        mainBinding.imageViewAvatarWoman.setOnClickListener {
            selectedOptionStyle(mainBinding.imageViewAvatarWoman, 4)
        }

    }

    fun setOptionStyle() {

        val optionList: ArrayList<ImageView> = arrayListOf()
        optionList.add(0, mainBinding.imageViewAvatarBoy)
        optionList.add(1, mainBinding.imageViewAvatarGirl)
        optionList.add(2, mainBinding.imageViewAvatarMan)
        optionList.add(3, mainBinding.imageViewAvatarWoman)

        for (op in optionList) {
            op.background = ContextCompat.getDrawable(this, R.drawable.question_option)
        }
    }

    fun selectedOptionStyle(view: ImageView, opt: Int) {

        setOptionStyle()
        selectedOption = opt
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_question_option)
    }

    fun setColor(opt: Int, color: Int) {

        when (opt) {
            1 -> mainBinding.imageViewAvatarBoy.background =
                ContextCompat.getDrawable(this, color)

            2 -> mainBinding.imageViewAvatarGirl.background =
                ContextCompat.getDrawable(this, color)

            3 -> mainBinding.imageViewAvatarMan.background =
                ContextCompat.getDrawable(this, color)

            4 -> mainBinding.imageViewAvatarWoman.background =
                ContextCompat.getDrawable(this, color)

        }
    }

}