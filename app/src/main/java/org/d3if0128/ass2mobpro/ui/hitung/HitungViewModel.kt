package org.d3if0128.ass2mobpro.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0128.ass2mobpro.db.LimasDao
import org.d3if0128.ass2mobpro.db.LimasEntity
import org.d3if0128.ass2mobpro.model.HasilLimas
import org.d3if0128.ass2mobpro.model.hitungLimas


class HitungViewModel(private val db: LimasDao) : ViewModel() {

    private val hasil = MutableLiveData<HasilLimas?>()

    fun hitungLimas(editPanjang: Float, editLebar: Float, editTinggi: Float) {
        val dataLimas = LimasEntity(
            editPanjang = editPanjang,
            editLebar = editLebar,
            editTinggi = editTinggi
        )
        hasil.value = dataLimas.hitungLimas()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataLimas)
            }
        }

    }
    fun getHasilLimas(): LiveData<HasilLimas?> = hasil
}