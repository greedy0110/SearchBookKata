package com.greedy0110.searchbookkata.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greedy0110.searchbookkata.databinding.FragmentSearchBinding
import com.greedy0110.searchbookkata.presentation.BookUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val bookAdapter = BookAdapter(
        onClick = { model -> /* TODO: go to the detail fragment with the model. */ }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initBooks()
    }

    private fun FragmentSearchBinding.initBooks() {
        layoutBooks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}