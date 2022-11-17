package com.github.andreyzim.numbers.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.github.andreyzim.numbers.R
import com.github.andreyzim.numbers.main.sl.ProvideViewModel
import com.github.andreyzim.numbers.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity(), ShowFragment, ProvideViewModel {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            NavigationStrategy.Replace(NumbersFragment())
                .navigate(supportFragmentManager, R.id.container)

    }

    override fun show(fragment: Fragment) =
        NavigationStrategy.Add(fragment).navigate(supportFragmentManager, R.id.container)

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T =
        (application as ProvideViewModel).provideViewModel(clazz, owner)
}