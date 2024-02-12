package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.app.exam_i.model.BDD
import com.app.exam_i.model.Materia

class PantallaMaterias : AppCompatActivity() {
    val semestreBD = BDD(this)
    val materiaDB = BDD(this)
    var positionSemestreSelected = 0
    var positionMateriaSelected = 0
    lateinit var adapter: ArrayAdapter<Materia>

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
        setContentView(R.layout.activity_pantalla_materias)
        positionSemestreSelected = intent.getIntExtra("positionMateriaSelected", 0)

        val txtDirector = findViewById<TextView>(R.id.txtViewNombreSemestre)
        txtDirector.text =
            "Materia pertenece a ${data[positionSemestreSelected].nombre}"
        val listView = findViewById<ListView>(R.id.tblListaMaterias)


        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            data[positionSemestreSelected].numeroTotalMateria
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        val btnCrearPelicula = findViewById<TextView>(R.id.btnCrearMateria)
        btnCrearPelicula.setOnClickListener {
            val intent = Intent(this, pantallaEditarMateria::class.java)
            intent.putExtra("positionMateriaSelected", -1)
            intent.putExtra("positionSemestreSelected", positionSemestreSelected)
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
        inflater.inflate(R.menu.menumateria, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        positionMateriaSelected = info.position
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editarMateria -> {
                val intent = Intent(this, pantallaEditarMateria::class.java)
                intent.putExtra("positionMateriaSelected", positionMateriaSelected)
                intent.putExtra("positionSemestreSelected", positionSemestreSelected)
                callbackContenido.launch(intent)
                true
            }

            R.id.eliminarMateria -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Desea eliminar esta Materia?")
                builder.setPositiveButton("Si") { dialog, which ->
                    data[positionSemestreSelected].numeroTotalMateria.removeAt(
                        positionMateriaSelected
                    )
                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        this,
                        "Materia eliminada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
                }
                builder.create().show()
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}