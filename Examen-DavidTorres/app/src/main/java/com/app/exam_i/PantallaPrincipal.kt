package com.app.exam_i

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.exam_i.model.BDD
import com.app.exam_i.model.Semestre
import com.google.android.material.snackbar.Snackbar

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

        val botonFirestore = findViewById<Button>(R.id.btn_intent_firestore)
        botonFirestore
            .setOnClickListener {
                irActividad(IFirestore::class.java)
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

        val botonFirestore = findViewById<Button>(R.id.btn_intent_firestore)
        botonFirestore
            .setOnClickListener {
                irActividad(IFirestore::class.java)
            }
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
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }


    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        // Enviar parametros (solamente variables primitivas)
        intentExplicito.putExtra("nombre", "2024-A")
        intentExplicito.putExtra("anio", "2022")
        intentExplicito.putExtra("activo", true)
        intentExplicito.putExtra("fechaInicio", "03-03-2022")

        intentExplicito.putExtra("Semestre",
            Semestre("2024-A", 2022, "03-03-2022", true, mutableListOf())
        )

        val callbackContenidoIntentExplicito =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ){
                    result ->
                if(result.resultCode == Activity.RESULT_OK){
                    if(result.data != null){
                        // Logica Negocio
                        val data = result.data
                        mostrarSnackbar(
                            "${data?.getStringExtra("nombreModificado")}"
                        )
                    }
                }
            }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar.make(
            findViewById(R.id.pantallaPrincipal
            ),
            texto,
            Snackbar.LENGTH_LONG
        ).show()
    }

    val callbackIntentPickUri =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode === RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri, null, null, null,  null, null)
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackbar("Telefono ${telefono}")
                    }
                }
            }
        }
}