package com.github.andreyzim.numbers.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.github.andreyzim.numbers.R
import com.github.andreyzim.numbers.databinding.FragmentNumbersBinding
import com.github.andreyzim.numbers.details.presentation.DetailsFragment
import com.github.andreyzim.numbers.main.presentation.ShowFragment
import com.github.andreyzim.numbers.main.sl.ProvideViewModel
import com.google.android.material.textfield.TextInputEditText

class NumbersFragment : Fragment() {

    private var _binding: FragmentNumbersBinding? = null
    private val binding get() = _binding!!
    private var showFragment: ShowFragment = ShowFragment.Empty()
    private lateinit var viewModel: NumbersViewModel
    private lateinit var inputEditText: TextInputEditText

    private val watcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) = viewModel.clearError()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment = context as ShowFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel).provideViewModel(
            NumbersViewModel::class.java, this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNumbersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = binding.progressBar
        val factButton = binding.getFactButton
        val randomButton = binding.randomFactButton
        val inputLayout = binding.textInputLayout
        inputEditText = binding.editText
        val recyclerView = binding.recyclerView
        val mapper = DetailUi()
        val adapter = NumbersAdapter(object : ClickListener {
            override fun click(item: NumberUi) =
                showFragment.show(DetailsFragment.newInstance(item.map(mapper)))
        })
        recyclerView.adapter = adapter

        factButton.setOnClickListener {
            viewModel.fetchNumberFact(inputEditText.text.toString())
        }

        randomButton.setOnClickListener {
            viewModel.fetchRandomNumbersFact()
        }

        viewModel.observeState(this) {
            it.apply(inputLayout, inputEditText)
        }

        viewModel.observeList(this) {
            adapter.map(it)
        }

        viewModel.observeProgress(this) {
            progressBar.visibility = it
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        inputEditText.addTextChangedListener(watcher)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        inputEditText.removeTextChangedListener(watcher)
    }

    override fun onDetach() {
        super.onDetach()
        showFragment = ShowFragment.Empty()
        _binding = null
    }

    abstract class SimpleTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) = Unit
    }
}