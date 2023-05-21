package org.d3if0128.ass2mobpro.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.d3if0128.ass2mobpro.model.HasilLimas


@Dao
interface LimasDao {
    @Insert
    fun insert(limas: LimasEntity)
    @Query("SELECT * FROM limas ORDER BY id DESC")
    fun getLastLimas(): LiveData<List<LimasEntity>>
    @Query("DELETE FROM limas")
    fun clearData()
}