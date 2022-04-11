package com.soulje.mapapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.soulje.mapapp.R

class MarkersListAdapter(
    private var onClickListener: OnItemClickListener
) : RecyclerView.Adapter<MarkersListAdapter.ViewHolder>() {

    private lateinit var holder: ViewHolder
    private lateinit var markers: MutableList<MarkerOptions>


    fun setData(markers: MutableList<MarkerOptions>){
        this.markers = markers
        notifyDataSetChanged()
    }

    fun updateMarker(marker: MarkerOptions, pos: Int){
        markers[pos] = marker
        notifyItemChanged(pos)
    }

    fun getMarker(pos: Int): MarkerOptions{
        return markers[pos]
    }

    fun deleteMarker(pos:Int){
        markers.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.markers_rv_item, parent,false)
        holder = ViewHolder(v)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(markers[position])
    }

    override fun getItemCount(): Int {
        return markers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val markerTitle: TextView = itemView.findViewById(R.id.marker_title)
        val markerSnippet: TextView = itemView.findViewById(R.id.marker_snippet)
        val markerLat: TextView = itemView.findViewById(R.id.marker_lat)
        val markerLng: TextView = itemView.findViewById(R.id.marker_lng)

        fun bind(marker: MarkerOptions){
            markerTitle.text = marker.title
            markerSnippet.text = marker.snippet
            markerLat.text = marker.position.latitude.toString()
            markerLng.text = marker.position.longitude.toString()

            itemView.setOnClickListener {
                onClickListener.onItemClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                onClickListener.onLongItemClick(it, adapterPosition)
                true
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(pos: Int)
        fun onLongItemClick(v: View, pos: Int)
    }

}