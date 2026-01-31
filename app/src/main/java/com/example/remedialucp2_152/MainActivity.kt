package com.example.remedialucp2_152

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remedialucp2_152.room.Category
import com.example.remedialucp2_152.databinding.ActivityMainBinding
import com.example.remedialucp2_152.view.adapter.BookAdapter
import com.example.remedialucp2_152.viewmodel.HomeViewModel
import com.example.remedialucp2_152.viewmodel.EntryViewModel
import com.example.remedialucp2_152.viewmodel.PenyediaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    private val homeViewModel: HomeViewModel by viewModels { PenyediaViewModel.Factory }
    private val entryViewModel: EntryViewModel by viewModels { PenyediaViewModel.Factory }
    
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter { book ->
            homeViewModel.deleteBook(book)
            Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show()
        }
        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        binding.rvBooks.adapter = bookAdapter
    }

    private fun setupObservers() {
        homeViewModel.allCategories.observe(this) { categories ->
            setupCategorySpinner(categories)
        }
    }

    private fun setupCategorySpinner(categories: List<Category>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategories.adapter = adapter
        
        binding.spinnerCategories.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                 val selectedCategory = categories[position]
                 homeViewModel.getBooksByCategory(selectedCategory.categoryId).observe(this@MainActivity) { books ->
                     bookAdapter.submitList(books)
                 }
             }

             override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
             }
        }
    }

    private fun setupListeners() {

        
        binding.btnSave.setOnClickListener {
            saveBook()
        }
    }

    private fun saveBook() {
        val title = binding.etTitle.text.toString()
        val isbn = binding.etIsbn.text.toString()
        val yearStr = binding.etYear.text.toString()
        
        val selectedPosition = binding.spinnerCategories.selectedItemPosition
        if (selectedPosition == android.widget.AdapterView.INVALID_POSITION) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show()
            return
        }
        
        val categories = homeViewModel.allCategories.value
        if (categories.isNullOrEmpty()) {
             Toast.makeText(this, "No categories available", Toast.LENGTH_SHORT).show()
             return
        }
        val category = categories[selectedPosition]
        
        val result = entryViewModel.insertBook(title, isbn, yearStr, category.categoryId)
        
        if (result == "Success") {
            Toast.makeText(this, "Book Saved!", Toast.LENGTH_SHORT).show()
            binding.etTitle.text.clear()
            binding.etIsbn.text.clear()
            binding.etYear.text.clear()
        } else {
            Toast.makeText(this, "Error: $result", Toast.LENGTH_SHORT).show()
        }
    }


}