package com.example.myapplication

import UI.Characters.Fragment
import android.R.attr.id
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = FrameLayout(this).apply {
            id = View.generateViewId()
        }

        setContentView(container)

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(container.id, Fragment())
                .commit()
        }
    }
}