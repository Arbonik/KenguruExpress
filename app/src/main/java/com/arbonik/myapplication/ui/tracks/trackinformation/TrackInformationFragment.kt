package com.arbonik.myapplication.ui.tracks.trackinformation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.arbonik.myapplication.R
import com.arbonik.myapplication.ui.tracks.Track
import com.google.gson.Gson

class TrackInformationFragment : Fragment() {
    private var track: Track? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_track_information, container, false)
        val text = Gson().fromJson(arguments?.getString("ket"), Track::class.java)
            Log.d("DEBUGG", text.toString())
        view.findViewById<TextView>(R.id.textNumber).text = text.toString()

        return view
    }
}