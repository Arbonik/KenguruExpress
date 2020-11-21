package com.arbonik.myapplication.ui.tracks.trackinformation

import android.annotation.SuppressLint
import java.time.LocalDateTime
@SuppressLint("NewApi")
data class StatusRow(val status: String = "",
                      val update: String = LocalDateTime.now().toString())