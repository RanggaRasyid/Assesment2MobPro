package org.d3if0128.ass2mobpro.model

import org.d3if0128.ass2mobpro.db.LimasEntity


fun LimasEntity.hitungLimas(): HasilLimas {
    val limas = (editPanjang * editLebar * editTinggi) / 3
    return HasilLimas(limas)
}
