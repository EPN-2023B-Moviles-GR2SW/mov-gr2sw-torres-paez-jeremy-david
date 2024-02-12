package com.app.exam_i.model

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

object BDD (context: Context): SQLiteOpenHelper(context, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS){

    companion object {
        const val NOMBRE_BASE_DATOS = "exam_david_torres"
        const val VERSION_BASE_DATOS = 1

            const val SCRIPT_CREATE_TABLE_SEMESTRE = "CREATE TABLE semestre (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT)"
            const val SCRIPT_CREATE_TABLE_MATERIA = "CREATE TABLE materia (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "id_semestre INTEGER)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SCRIPT_CREATE_TABLE_SEMESTRE)
        db?.execSQL(SCRIPT_CREATE_TABLE_MATERIA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS usuarios")
        db?.execSQL("DROP TABLE IF EXISTS preguntas")
        onCreate(db)
    }


    // CRUD SEMESTRE

    fun insertarSemestre(nombre: String){
        val db = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        db.insert("semestres", null, valoresAGuardar)
        db.close()
    }

    fun eliminarSemestre(id: Int){
        val db = writableDatabase
        db.delete("semestres", "id = $id", null)
        db.close()
    }

    fun actualizarSemestre(id: Int, nombre: String){
        val db = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        db.update("semestres", valoresAActualizar, "id = $id", null)
        db.close()
    }

    fun obtenerSemestres(): ArrayList<Semestre>{
        val db = readableDatabase
        val listaSemestres = ArrayList<Semestre>()
        val cursor = db.rawQuery("SELECT * FROM semestres", null)
        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                listaSemestres.add(Semestre(id, nombre))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaSemestres
    }

    fun obtenerSemestrePorId(id: Int): Semestre?{
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM semestres WHERE id = $id", null)
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            cursor.close()
            db.close()
            return Semestre(id, nombre)
        }
        cursor.close()
        db.close()
        return null
    }

    // CRUD MATERIA

    fun insertarMateria(nombre: String, idSemestre: Int){
        val db = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("id_semestre", idSemestre)
        db.insert("materias", null, valoresAGuardar)
        db.close()
    }

    fun eliminarMateria(id: Int){
        val db = writableDatabase
        db.delete("materias", "id = $id", null)
        db.close()
    }

    fun actualizarMateria(id: Int, nombre: String, idSemestre: Int){
        val db = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("id_semestre", idSemestre)
        db.update("materias", valoresAActualizar, "id = $id", null)
        db.close()
    }

    fun obtenerMaterias(): ArrayList<Materia>{
        val db = readableDatabase
        val listaMaterias = ArrayList<Materia>()
        val cursor = db.rawQuery("SELECT * FROM materias", null)
        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val idSemestre = cursor.getInt(2)
                listaMaterias.add(Materia(id, nombre, idSemestre))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaMaterias
    }

    fun obtenerMateriaPorId(id: Int): Materia?{
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM materias WHERE id = $id", null)
        if(cursor.moveToFirst()){
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val idSemestre = cursor.getInt(2)
            cursor.close()
            db.close()
            return Materia(id, nombre, idSemestre)
        }
        cursor.close()
        db.close()
        return null
    }

}