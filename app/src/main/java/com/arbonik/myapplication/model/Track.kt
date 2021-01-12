package com.arbonik.myapplication.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.arbonik.myapplication.ui.tracks.trackinformation.StatusRow
import java.time.LocalDateTime

data class Track(
        val name: String,
        val number: Long,
        val status: String,
        val tkName: String,
        val weight: Double,
        val statusStory: MutableList<StatusRow> = mutableListOf(StatusRow("now"))) {
fun latestUpdate() = statusStory.last().update
}


@RequiresApi(Build.VERSION_CODES.O)
fun instanceTrack() =
            Track("TEST",
                    88005553533,
                    "IN PUTH",
                    "ДЕЛОВЫЕ ЛИНИИ",
                    0.1,
                    mutableListOf(StatusRow("in path", LocalDateTime.now().toString())))
