package com.example.recipes

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        binding.menuicon.setOnClickListener {
            var dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_nav)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }

        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.salad.setOnClickListener {
            var myIntent = Intent(this@MainActivity, CategoryActivity::class.java)
            myIntent.putExtra("TITTLE", "Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)
        }
        binding.main.setOnClickListener {
            var myIntent = Intent(this@MainActivity, CategoryActivity::class.java)
            myIntent.putExtra("TITTLE", "Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent)
        }
        binding.drinks.setOnClickListener {
            var myIntent = Intent(this@MainActivity, CategoryActivity::class.java)
            myIntent.putExtra("TITTLE", "Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)
        }
        binding.dessert.setOnClickListener {
            var myIntent = Intent(this@MainActivity, CategoryActivity::class.java)
            myIntent.putExtra("TITTLE", "Desserts")
            myIntent.putExtra("CATEGORY","Desserts")
            startActivity(myIntent)
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.rvPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var db = Room.databaseBuilder(this@MainActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        var recipes = daoObject.getAll()

        for (i in recipes!!.indices){
            if (recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)

            }
            rvAdapter = PopularAdapter(dataList,this)
            binding.rvPopular.adapter = rvAdapter
        }
    }
}