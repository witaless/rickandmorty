package com.witaless.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.witaless.rickandmorty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}
