package com.arbonik.myapplication.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.arbonik.myapplication.network.models.ProductResponse
import com.arbonik.myapplication.network.models.tariff.Data
import com.arbonik.myapplication.profile.OrderData
import com.arbonik.myapplication.profile.Profile
import com.arbonik.myapplication.ui.tracks.trackinformation.StatusRow
import java.time.LocalDateTime

@Deprecated("First model")
data class Track(
    val name: String,
    val number: Long,
    val status: String,
    val tkName: String,
    val weight: Double,
    val statusStory: MutableList<StatusRow> = mutableListOf(StatusRow("now"))
) {
    fun latestUpdate() = statusStory.last().update
}

// класс, с помощью которого формируется конечная заявка, при неоформлении она записывается в бд, чтобы менеджер мог проаналоизировать
@Entity(tableName = "requests")
data class FullRequest(
    @Embedded(prefix = "loc_")
    var localityPair: LocalityPair? = null,
    @Embedded(prefix = "prod_")
    var cargo: ProductResponse? = null,
    @Embedded(prefix = "tariff_")
    var tariff: Data? = null,
    @Embedded(prefix = "receiv_")
    var receiver: OrderData? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


@RequiresApi(Build.VERSION_CODES.O)
fun instanceTrack() =
    Track(
        "TEST",
        88005553533,
        "IN PUTH",
        "ДЕЛОВЫЕ ЛИНИИ",
        0.1,
        mutableListOf(StatusRow("in path", LocalDateTime.now().toString()))
    )
