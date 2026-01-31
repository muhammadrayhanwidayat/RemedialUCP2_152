package com.example.remedialucp2_152.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remedialucp2_152.room.Book
import com.example.remedialucp2_152.databinding.ItemBookBinding

class BookAdapter(
    private var books: List<Book> = emptyList(),
    private val onDeleteClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.binding.tvBookTitle.text = book.title
        holder.binding.tvBookIsbn.text = "ISBN: ${book.isbn}"
        
        holder.binding.btnDelete.setOnClickListener {
            // Requirement 6: Delete Feature (INTENTIONALLY INCOMPLETE)
            onDeleteClick(book)
        }
    }

    override fun getItemCount() = books.size

    fun submitList(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
