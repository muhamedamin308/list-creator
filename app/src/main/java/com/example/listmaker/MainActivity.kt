package com.example.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity(),
    ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener
{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var listsRecyclerView: RecyclerView
    private val listDataManager : ListDataManager = ListDataManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val lists = listDataManager.readList()
        listsRecyclerView = findViewById<RecyclerView>(R.id.lists_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        binding.fab.setOnClickListener {
            showCreateListDialog()
        }
    }

    private fun showCreateListDialog() {
        // 1
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        // 2
        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)
        // 3
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)

            val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)
            dialog.dismiss()
            showListDetails(list)
        }
        // 4
        builder.create().show()
    }
    private fun showListDetails (list: TaskList){
        val listDetailsIntent = Intent(this ,
        activity_list_detail::class.java)
        listDetailsIntent.putExtra(INTENT_LIST_KEY, list)
        startActivity(listDetailsIntent)
    }
    companion object {
        const val INTENT_LIST_KEY = "list"
    }
    override fun listItemClicked(list: TaskList) {
        showListDetails(list)
    }
}