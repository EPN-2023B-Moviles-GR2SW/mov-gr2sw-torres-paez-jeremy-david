package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import com.app.exam_i.model.Semestre
import com.app.exam_i.model.BDD
import android.app.AlertDialog
import android.content.Intent
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast


class PantallaPrincipal : AppCompatActivity() {
    val data = BDD.datos
    var positionSemestreSelected = -1
    lateinit var adapter: ArrayAdapter<Semestre>

    val callbackContenido =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode === RESULT_OK) {
                if (result.data != null) {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        val listView = findViewById<ListView>(R.id.tbl_ListaSemestre)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            data
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        val btnCrearSemestre = findViewById<Button>(R.id.btn_CrearSemestre)
        btnCrearSemestre.setOnClickListener {
            val intent = Intent(this, pantallaEditarSemestre::class.java)
            intent.putExtra("positionDirectorSelected", -1)
            callbackContenido.launch(intent)
        }
        registerForContextMenu(listView)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menusemestre, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        positionSemestreSelected = info.position
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editarSemestre -> {
                val intent = Intent(this, pantallaEditarSemestre::class.java)
                intent.putExtra("positionSemestreSelected", positionSemestreSelected)
                callbackContenido.launch(intent)
                true
            }

            R.id.eliminarSemestre -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Desear eliminar el Semestre?")
                builder.setPositiveButton("Sí") { dialog, which ->
                    data.removeAt(positionSemestreSelected)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        applicationContext,
                        "Semestre eliminado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        "Operación cancelada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.create().show()
                true
            }

            R.id.verMaterias -> {
                val intent = Intent(this, PantallaMaterias::class.java)
                intent.putExtra("positionSemestreSelected", positionSemestreSelected)
                startActivity(intent)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}