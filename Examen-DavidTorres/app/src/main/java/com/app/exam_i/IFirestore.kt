package com.app.exam_i

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class IFirestore : AppCompatActivity() {
    var query: Query? = null
    val arreglo: ArrayList<ISemestre> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirestore)
        // Configurando el list view
        val listView = findViewById<ListView>(R.id.lv_firestore)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        // Botones
        // Crear Datos Prueba
        val botonDatosPrueba = findViewById<Button>(
            R.id.btn_fs_datos_prueba)
        botonDatosPrueba.setOnClickListener { crearDatosPrueba() }
        // Order By
        val botonOrderBy = findViewById<Button>(R.id.btn_fs_order_by)
        botonOrderBy.setOnClickListener { consultarConOrderBy(adaptador) }

        // Boton Eliminar
        val botonFirebaseEliminar = findViewById<Button>(
            R.id.btn_fs_eliminar)
        botonFirebaseEliminar.setOnClickListener {
            eliminarRegistro() }
        // Empezar a paginar
        val botonFirebaseEmpezarPaginar = findViewById<Button>(
            R.id.btn_fs_epaginar)
        botonFirebaseEmpezarPaginar.setOnClickListener {
            query = null; consultarSemestres(adaptador);
        }

        // Consultar indice compuesto
        val botonIndiceCompuesto = findViewById<Button>(
            R.id.btn_fs_ind_comp
        )
        botonIndiceCompuesto.setOnClickListener {
            consultarIndiceCompuesto(adaptador)
        }
    }

    fun consultarSemestres(
        adaptador: ArrayAdapter<ISemestre>
    ){
        val db = Firebase.firestore
        val semestreRef = db.collection("semestre")
            .orderBy("anio")
            .limit(1)
        var tarea: Task<QuerySnapshot>? = null
        if (query == null) {
            tarea = semestreRef.get() // 1era vez
            limpiarArreglo()
            adaptador.notifyDataSetChanged()
        } else {
            // consulta de la consulta anterior empezando en el nuevo documento
            tarea = query!!.get()
        }
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, semestreRef)
                    for (ciudad in documentSnapshots) {
                        anadirAArregloSemestre(ciudad)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    // si hay fallos
                }
        }
        // [4,5,6,1,2,3,7,8,9,10,11]
        // [1,2,3] (limit = 3)
        // [4,5,6] (limit = 3) (cursor =3)
        // [7,8,9] (limit = 3) (cursor =6)
        // [10,11] (limit = 3) (cursor =9)
        // [] (limit = 3) (cursor =11)
    }

    fun eliminarRegistro(){
        val db = Firebase.firestore
        val referenciaEjemploEstudiante = db
            .collection("ejemplo")

        referenciaEjemploEstudiante
            .document("12345678")
            .delete() // elimina
            .addOnCompleteListener { /* Si todo salio bien*/ }
            .addOnFailureListener { /* Si algo salio mal*/ }
    }
    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        refCities: Query
    ){
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = refCities
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    fun consultarIndiceCompuesto(
        adaptador: ArrayAdapter<ISemestre>
    ){
        val db = Firebase.firestore
        val SemestresRefUnico = db.collection("semestre")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        SemestresRefUnico
            .whereEqualTo("activo", false)
            .whereLessThanOrEqualTo("anio", 2000)
            .orderBy("semestre", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    anadirAArregloSemestre(ciudad)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {  }

    }

    fun consultarConOrderBy(
        adaptador: ArrayAdapter<ISemestre>
    ){
        val db = Firebase.firestore
        val semestreRefUnico = db.collection("semestre")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        semestreRefUnico
            .orderBy("semestre", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                // it => eso (lo que llegue)
                for (semestre in it){
                    semestre.id
                    anadirAArregloSemestre(semestre)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {
                // Errores
            }
    }

    fun crearDatosPrueba(){
        val db = Firebase.firestore
        // COPIAR EJEMPLO WEB
        val semestre = db.collection("semestre")
        val data1 = hashMapOf(
            "name" to "2022-A",
            "anio" to "2022",
            "fecha Inicio" to "03-03-2022",
            "activo" to true,
        )
        val data2 = hashMapOf(
            "name" to "2022-B",
            "anio" to "2022",
            "fecha Inicio" to "03-03-2022",
            "activo" to true,
        )

        val data3 = hashMapOf(
            "name" to "2023-A",
            "anio" to "2023",
            "fecha Inicio" to "03-03-2023",
            "activo" to true,
        )

        val data4 = hashMapOf(
            "name" to "2023-B",
            "anio" to "2023",
            "fecha Inicio" to "03-03-2023",
            "activo" to true,
        )

        val data5 = hashMapOf(
            "name" to "2024-A",
            "anio" to "2024",
            "fecha Inicio" to "03-03-2024",
            "activo" to true,
        )
        semestre.document("BJ").set(data5)
    }

    fun limpiarArreglo() {
        arreglo.clear()
    }
    fun anadirAArregloSemestre(
        semestre: QueryDocumentSnapshot
    ){
        val nombre = semestre.getString("nombre").toString()
        val año = semestre.getLong("año")!!.toInt()
        val fechaInicio = semestre.getDate("fechaInicio")!!
        val activo = semestre.getBoolean("activo")!!

    }
}