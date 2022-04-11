package com.soulje.mapapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.MarkerOptions
import com.soulje.mapapp.ViewModel.MarkersListViewModel
import com.soulje.mapapp.R
import com.soulje.mapapp.databinding.MarkersListFragmentBinding
import com.soulje.mapapp.model.DataState
import com.soulje.mapapp.toEntity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MarkersListFragment : Fragment() {

    companion object {
        fun newInstance() = MarkersListFragment()
    }

    private var _binding: MarkersListFragmentBinding? = null
    private val binding get() = _binding!!

    private val markersViewModel: MarkersListViewModel by viewModel()
    private lateinit var adapter: MarkersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MarkersListFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        markersViewModel.getData().observe(viewLifecycleOwner, Observer<DataState> { renderData(it) })
    }

    private fun renderData(state: DataState){
        when(state){
            is DataState.Success ->{
                val response = state.markersData
                initRv(response)
            }
        }
    }

    private fun initRv(data: MutableList<MarkerOptions>) = with(binding) {
        markersList.setHasFixedSize(true)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        markersList.layoutManager = manager

        adapter = MarkersListAdapter(object : MarkersListAdapter.OnItemClickListener{
            override fun onItemClick(pos : Int) {
                showDialog(data[pos], pos)
            }

            override fun onLongItemClick(v: View, pos:Int) {
                val menu  = PopupMenu(requireContext(),v)
                menu.inflate(R.menu.popup_menu_item)

                menu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.delete_marker -> {
                            markersViewModel.deleteMarker(adapter.getMarker(pos).toEntity())
                            adapter.deleteMarker(pos)
                        }
                    }
                    true
                }

                menu.show()
            }

        })
        adapter.setData(data)
        markersList.adapter = adapter
    }

    private fun showDialog(marker:MarkerOptions, pos: Int){
        val v  = activity?.layoutInflater?.inflate(R.layout.update_dialog, null)

        val editTitle = v?.findViewById<EditText>(R.id.edit_name)
        val editSnippet = v?.findViewById<EditText>(R.id.edit_snippet)

        editTitle?.setText(marker.title)
        editSnippet?.setText(marker.snippet)

        val builder = AlertDialog.Builder(requireActivity())
            .setView(v)
            .setPositiveButton("OK",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    val title = editTitle?.text.toString()
                    val snippet = editSnippet?.text.toString()
                    marker.snippet(snippet).title(title)
                    markersViewModel.updateMarker(marker.toEntity())
                    adapter.updateMarker(marker, pos)

                }

            })
            .setCancelable(true)
        builder.create().show()
    }

}