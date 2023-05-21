package org.d3if0128.ass2mobpro.ui.hitung

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if0128.ass2mobpro.R
import org.d3if0128.ass2mobpro.databinding.FragmentHitungBinding
import org.d3if0128.ass2mobpro.db.LimasDb
import org.d3if0128.ass2mobpro.model.HasilLimas
import org.d3if0128.ass2mobpro.ui.hitung.HitungViewModel
import org.d3if0128.ass2mobpro.ui.hitung.HitungViewModelFactory


class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel: HitungViewModel by lazy {
        val db = LimasDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //setContentView(R.layout.activity_main)

        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnHitung.setOnClickListener { hitungLimas() }
        binding.shareButton.setOnClickListener { shareData() }

        viewModel.getHasilLimas().observe(requireActivity(), { showResult(it) })

    }

  @SuppressLint("StringFormatMatches")
    private fun shareData() {
        val message = getString(
            R.string.bagikan_template,
            binding.ePanjang.text,
            binding.eLebar.text,
            binding.eTinggi.text,
            binding.hasil.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_hitungFragment_to_historiFragment)
                return true
            }

            R.id.menu_about -> {
                findNavController().navigate(R.id.action_hitungFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hitungLimas() {
        val editPanjang = binding.ePanjang.text.toString()
        if (TextUtils.isEmpty(editPanjang.toString())){
            Toast.makeText(context, R.string.panjang_invalid, Toast.LENGTH_LONG).show()

            return
        }

        val editlebar = binding.eLebar.text.toString()
        if (TextUtils.isEmpty(editlebar.toString())){
            Toast.makeText(context, R.string.lebar_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val editTinggi = binding.eTinggi.text.toString()
        if (TextUtils.isEmpty(editTinggi.toString())){
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungLimas(
            editPanjang.toFloat(),
            editTinggi.toFloat(),
            editlebar.toFloat()
        )
    }

    private fun showResult(result: HasilLimas?){
        if (result == null) return
        binding.hasil.text = getString(R.string.hasil_x, result.volumeLimas)
        binding.shareButton.visibility = View.VISIBLE
    }


}