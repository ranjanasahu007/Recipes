package com.example.recipes

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.recipes.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityRecipeBinding.inflate(layoutInflater)
    }
    var imgCrop = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.detailImg)
        binding.title.text = intent.getStringExtra("tittle")
        binding.stepsData.text = intent.getStringExtra("des")

        var ing = intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }
            ?.toTypedArray()
        binding.time.text = ing?.get(0)

        for(i in 1 until ing!!.size){
            binding.ingrediantsData.text =
                """${binding.ingrediantsData.text} 🟢 ${ing[i]}
                    
            """.trimIndent()
        }
        binding.steps.background = null
        binding.steps.setTextColor(getColor(R.color.black))

        binding.steps.setOnClickListener {
            binding.steps.setBackgroundResource(R.drawable.btn_ing)
            binding.steps.setTextColor(getColor(R.color.white))
            binding.ingredients.setTextColor(getColor(R.color.black))
            binding.ingredients.background = null
            binding.stepsScroll.visibility = View.VISIBLE
            binding.ingredientsScroll.visibility = View.GONE
        }

        binding.ingredients.setOnClickListener {
            binding.ingredients.setBackgroundResource(R.drawable.btn_ing)
            binding.ingredients.setTextColor(getColor(R.color.white))
            binding.steps.setTextColor(getColor(R.color.black))
            binding.steps.background = null
            binding.ingredientsScroll.visibility = View.VISIBLE
            binding.stepsScroll.visibility = View.GONE
        }

        binding.expandDetail.setOnClickListener {
            if (imgCrop){
                binding.detailImg.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.detailImg)
                binding.expandDetail.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                binding.overlay.visibility = View.GONE
                imgCrop = !imgCrop
            }else{
                binding.detailImg.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.detailImg)
                binding.expandDetail.setColorFilter(null)
                binding.overlay.visibility = View.VISIBLE
                imgCrop =!imgCrop
            }
        }
        binding.backDetail.setOnClickListener {
            finish()
        }
    }
}