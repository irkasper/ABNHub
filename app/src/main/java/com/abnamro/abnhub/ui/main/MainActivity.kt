package com.abnamro.abnhub.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abnamro.abnhub.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * sole application's Activity for building a Single-Page Application (SPA)
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
