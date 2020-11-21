package com.arbonik.myapplication.ui.tracks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbonik.myapplication.R
import com.arbonik.myapplication.databinding.TrackItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class TracksFragment : Fragment() {

    private lateinit var tracksViewModel: TracksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        tracksViewModel =
                ViewModelProvider(this).get(TracksViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_tracks, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_tracks).apply {
            adapter = TrackAdapter()
            layoutManager = LinearLayoutManager(context)
        }

        tracksViewModel.tracksList.observe(viewLifecycleOwner){
            (recyclerView.adapter as TrackAdapter).tracks = it
            (recyclerView.adapter as TrackAdapter).notifyDataSetChanged()
        }

//        tracksViewModel.tracksList.value[]
//        recyclerView.onCli .adapter .getItemId()

        val actionButton = root.findViewById<FloatingActionButton>(R.id.floating_action_button)
            actionButton.setOnClickListener {
                addTrackTolist()
//                onTrackCardClicked(root)
            }

        return root
    }

    fun onTrackCardClicked(v : View){
        val navController = v.findNavController()
        navController.navigate(R.id.action_navigation_tracks_to_trackInformationFragment)
    }

    fun addTrackTolist() {
        var result = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instanceTrack()
        } else {
            Track("NOnE", 123123, "wq","Wqe",12.0, mutableListOf())
        }
        MaterialAlertDialogBuilder(requireContext())
                .setTitle("Введите трек - номер")
                .setView(EditText(requireContext()).apply {
                    addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            result = result.copy(number = s.toString().toLong())

                        }
                        override fun afterTextChanged(s: Editable?) {
                        }
                        })
                    })
                // добавление пользовательского id
                .setPositiveButton("Добавить") { dialog, which ->
                        tracksViewModel.addTrack(result)
                }
                .show()
    }

    inner class TrackAdapter : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {
        var tracks : MutableList<Track> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                TrackViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.track_item,parent, false))

        override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

            holder.onBind(tracks[position])
        }

        override fun getItemCount() = tracks.size

        inner class TrackViewHolder(item : View) : RecyclerView.ViewHolder(item){
            val trackBinding = TrackItemBinding.bind(item)

            fun onBind(track: Track) {
                var bundle = Bundle().apply {
                    var text = Gson().toJson(track)
                    Log.d("DEBUGG", text)
                    putString("ket", text)
                }
                trackBinding.materialCardView.setOnClickListener {
                    activity?.findNavController(R.id.nav_host_fragment)
                            ?.navigate(R.id.action_navigation_tracks_to_trackInformationFragment,
                            bundle)
                }
                trackBinding.track = track
            }
        }
    }
}