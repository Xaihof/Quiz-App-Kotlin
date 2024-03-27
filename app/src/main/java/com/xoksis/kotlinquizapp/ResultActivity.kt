package com.xoksis.kotlinquizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xoksis.kotlinquizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val userName = intent.getStringExtra(setData.name)
        val score = intent.getStringExtra(setData.score)
        val totalQuestion = intent.getStringExtra("total size")
        val avatar = intent.getStringExtra(setData.avatar)

        binding.congo.text = "Congratulations ${userName}!"
        binding.Score.text = "${score} / ${totalQuestion}"
        if (score!!.toInt() <= 2) {
            binding.textViewGrade.text = "Improve Yourself!"
        } else if (score!!.toInt() > 2 && score!!.toInt() <= 4) {
            binding.textViewGrade.text = "You scored Good!"
        } else {
            binding.textViewGrade.text = "You got the best scores!"
        }

        if (avatar.equals("1")) {
            binding.imageViewAvatar.setImageResource(R.drawable.avatar_boy)
        } else if (avatar.equals("2")) {
            binding.imageViewAvatar.setImageResource(R.drawable.avatar_girl)

        } else if (avatar.equals("3")) {
            binding.imageViewAvatar.setImageResource(R.drawable.avatar_man)
        } else {
            binding.imageViewAvatar.setImageResource(R.drawable.avatar_woman)
        }


        binding.button.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }


    }
}