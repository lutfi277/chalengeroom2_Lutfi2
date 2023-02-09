package com.example.chalengeroom2_lutfi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { dbperpustakaan(this) }
    private var tbbukid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        simpandata()
        tbbukid = intent.getIntExtra("intent_id", tbbukid)
        Toast.makeText(this, tbbukid.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btnupdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btnSimpan.visibility = View.GONE
                btnupdate.visibility = View.GONE
                Etidbuku.visibility = View.GONE
                tampilsiswa()

            }
            Constant.TYPE_UPDATE -> {
                btnSimpan.visibility = View.GONE
                Etidbuku.visibility = View.GONE
                tampilsiswa()

            }
        }
    }

    private fun simpandata() {
        btnSimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbbukDao().addtbbuku(
                    tbBuku(Etidbuku.text.toString().toInt(),
                        EtKategori.text.toString(),
                        EtJudul.text.toString(),
                        EtPengarang.text.toString(),
                        EtPenerbit.text.toString(),
                        EtJumlahbuku.text.toString())
                )
                finish()
            }
        }
        //menambahkan tombol Update
        btnupdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbbukDao().updatetbbuku(
                    tbBuku(tbbukid,
                        EtKategori.text.toString(),
                        EtJudul.text.toString(),
                        EtPengarang.text.toString(),
                        EtPenerbit.text.toString(),
                        EtJumlahbuku.text.toString())
                )
                finish()
            }
        }
    }

    fun tampilsiswa() {
        tbbukid = intent.getIntExtra("intent_nis", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.tbbukDao().tampilid(tbbukid).get(0)
            //EtNis.setText(notes.nis)
            EtKategori.setText(notes.kategori)
            EtJudul.setText(notes.judul)
            EtPengarang.setText(notes.pengarang)
            EtPenerbit.setText(notes.penerbit)
            EtJumlahbuku.setText(notes.jml_buku)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }
}

