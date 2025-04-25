package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.repository.TitleRepository
import com.example.myapplication.viewmodel.TitleViewModel
import com.example.myapplication.viewmodel.TitleViewModel.TitleViewModelFactory
import android.os.Parcelable
import com.example.myapplication.model.Title
import com.example.myapplication.ui.OnItemClickListener
import com.example.myapplication.ui.TitleAdapter
import com.example.myapplication.ui.TitleDetailsActivity

class MainActivity : AppCompatActivity(), OnItemClickListener { // Implement the interface

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TitleViewModel
    private lateinit var titleAdapter: TitleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate() called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = TitleRepository()
        val viewModelFactory = TitleViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TitleViewModel::class.java]
        Log.d("MainActivity", "ViewModel initialized")

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        Log.d("MainActivity", "setupRecyclerView() called")
        titleAdapter = TitleAdapter(this) // Pass the listener here
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = titleAdapter
            Log.d("MainActivity", "RecyclerView layoutManager and adapter set")
        }
    }

    private fun observeViewModel() {
        Log.d("MainActivity", "observeViewModel() called")
        viewModel.titles.observe(this) { titles ->
            Log.d("MainActivity", "Titles LiveData observed: ${titles?.size} items")
            titleAdapter.submitList(titles)
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.errorTextView.visibility = View.GONE
        }

        viewModel.errorMessage.observe(this) { message ->
            Log.e("MainActivity", "Error message observed: $message")
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.errorTextView.text = message
            binding.errorTextView.visibility = View.VISIBLE
        }

        viewModel.loading.observe(this) { isLoading ->
            Log.d("MainActivity", "Loading state observed: $isLoading")
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
            binding.errorTextView.visibility = View.GONE
        }
    }

    override fun onItemClick(title: Title) { // Implement the onItemClick method
        Log.d("MainActivity", "onItemClick: $title")
        val intent = Intent(this, TitleDetailsActivity::class.java)
        intent.putExtra("title", title as Parcelable)
        startActivity(intent)
    }
}
