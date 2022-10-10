package com.github.andreyzim.numbers.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.github.andreyzim.numbers.R
import com.github.andreyzim.numbers.details.presentation.DetailsFragment
import com.github.andreyzim.numbers.main.presentation.ShowFragment

class NumbersFragment : Fragment() {

    private var showFragment: ShowFragment = ShowFragment.Empty()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = context as ShowFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE

        view.findViewById<View>(R.id.get_fact_button).setOnClickListener {
            val detailsFragment = DetailsFragment.newInstance("TI UROD EBANIY")
            showFragment.show(detailsFragment)
        }
    }

    override fun onDetach() {
        super.onDetach()
        showFragment = ShowFragment.Empty()
    }

}