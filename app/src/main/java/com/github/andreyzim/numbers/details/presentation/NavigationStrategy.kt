package com.github.andreyzim.numbers.details.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface NavigationStrategy {

    fun navigate(supportFragmentManager: FragmentManager, containerId: Int)

    abstract class Abstract(protected val fragment: Fragment) : NavigationStrategy {

        override fun navigate(supportFragmentManager: FragmentManager, containerId: Int) {
            supportFragmentManager.beginTransaction()
                .executeTransaction(containerId)
                .commit()
        }

        protected abstract fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction

    }

    class Replace(fragment: Fragment) : Abstract(fragment) {
        override fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction {
            return replace(containerId, fragment)
        }
    }

    class Add(fragment: Fragment) : Abstract(fragment) {
        override fun FragmentTransaction.executeTransaction(containerId: Int): FragmentTransaction {
            return add(containerId, fragment).addToBackStack(fragment.javaClass.simpleName)
        }
    }
}