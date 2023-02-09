package com.example.chalengeroom2_lutfi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class
MainActivity : AppCompatActivity() {

    lateinit var BukuAdapter: BukuAdapter
    val db by lazy { dbperpustakaan(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()
    }

    override fun onStart(){
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val buku = db.tbbukDao().tampilsemua()
            Log.d("MainActivity", "dbResponse:$buku")
            withContext(Dispatchers.Main){
                BukuAdapter.setData(buku)
            }
        }
    }

    private fun halEdit(){
        btnInput.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(tbsisnis:Int, intentType:Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_nis",tbsisnis)
                .putExtra("intent_type",intentType)
        )
    }

    fun setupRecyclerView(){
        BukuAdapter = BukuAdapter(arrayListOf(),object : BukuAdapter.OnAdapterListener{
            override fun onClick(tbbuk: tbBuku) {
                intentEdit(tbbuk.id_buku,Constant.TYPE_READ)
            }

            override fun onUpdate(tbbuk: tbBuku) {
                intentEdit(tbbuk.id_buku,Constant.TYPE_UPDATE)
            }
            override fun onDelete(tbbuk: tbBuku) {
                deleteDialog(tbbuk)

            }
        })

        //idRecyclerView
        listdatabuku.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = BukuAdapter
        }
    }
    private fun deleteDialog(tbBuku: tbBuku) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${tbBuku.id_buku}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbbukDao().deletetbbuku(tbBuku)
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
}
