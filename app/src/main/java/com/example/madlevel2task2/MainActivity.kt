package com.example.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var questions: ArrayList<Question> = arrayListOf()
    private val questionAdapter: QuestionAdapter = QuestionAdapter(questions)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }


    private fun initViews() {
        questions.add(Question("A 'val' and 'var' are the same.", false))
        questions.add(Question("Mobile Application Development grants 12 ECTS", false))
        questions.add(Question("A Unit in Kotlin corresponds to a void in Java", true))
        questions.add(Question("In Kotlin 'when' replaced the 'switch' opeerator in Java.", true))
        binding.questionsRv.layoutManager = LinearLayoutManager(
            this@MainActivity, RecyclerView.VERTICAL, false)
        binding.questionsRv.adapter = questionAdapter
        binding.questionsRv.addItemDecoration(DividerItemDecoration(
            this@MainActivity, DividerItemDecoration.VERTICAL
        ))
        questionAdapter.notifyDataSetChanged()

        createItemTouchHelper().attachToRecyclerView(binding.questionsRv)
    }



    private fun createItemTouchHelper(): ItemTouchHelper {
        var callback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT &&
                    questions[viewHolder.adapterPosition].correct) {
                    questions.removeAt(viewHolder.adapterPosition)
                } else if (direction == ItemTouchHelper.LEFT &&
                    !questions[viewHolder.adapterPosition].correct) {
                    questions.removeAt(viewHolder.adapterPosition)
                } else {
                    Snackbar.make(viewHolder.itemView, "Incorrect, this question will not be " +
                            "removed from the list", Snackbar.LENGTH_LONG).show()
                }
                questionAdapter.notifyDataSetChanged()
            }
        }

        return ItemTouchHelper(callback)

    }


}