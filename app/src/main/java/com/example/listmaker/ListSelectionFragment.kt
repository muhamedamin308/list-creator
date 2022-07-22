package com.example.listmaker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListSelectionFragment : Fragment(),
    ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener
{
    // 1
    private var listener: OnListItemFragmentInteractionListener? = null
    lateinit var listDataManager: ListDataManager
    lateinit var listsRecyclerView: RecyclerView
    // 2
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
        } else {
            throw RuntimeException("$context must implement OnListItemFragmentInteractionListener")
        }
    }
    // 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    // 4
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_selection,
            container, false)
    }
    // 5
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    interface OnListItemFragmentInteractionListener {
        fun onListItemClicked(list: TaskList)
    }
    // 6
    companion object {
        fun newInstance(): ListSelectionFragment {
            return ListSelectionFragment()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val lists = listDataManager.readList()
        view?.let {
            listsRecyclerView = it.findViewById(R.id.lists_recyclerview)
            listsRecyclerView.layoutManager =
                LinearLayoutManager(activity)
            listsRecyclerView.adapter =
                ListSelectionRecyclerViewAdapter(lists, this)
        }
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
    }
    fun addList(list : TaskList) {
        listDataManager.saveList(list)
        val recyclerAdapter = listsRecyclerView.adapter as
                ListSelectionRecyclerViewAdapter
        recyclerAdapter.addList(list)
    }
    fun saveList(list: TaskList) {
        listDataManager.saveList(list)
        updateLists()
    }
    private fun updateLists() {
        val lists = listDataManager.readList()
        listsRecyclerView.adapter =
            ListSelectionRecyclerViewAdapter(lists, this)
    }

}