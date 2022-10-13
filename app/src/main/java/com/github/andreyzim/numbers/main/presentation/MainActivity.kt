package com.github.andreyzim.numbers.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.andreyzim.numbers.R
import com.github.andreyzim.numbers.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity(), ShowFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            NavigationStrategy.Replace(NumbersFragment())
                .navigate(supportFragmentManager, R.id.container)

    }

    override fun show(fragment: Fragment) =
        NavigationStrategy.Add(fragment).navigate(supportFragmentManager, R.id.container)
}