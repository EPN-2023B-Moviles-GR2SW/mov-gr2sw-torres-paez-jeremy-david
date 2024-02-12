package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.app.exam_i.model.BDD
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.app.exam_i.model.Materia
import com.app.exam_i.model.Semestre
import java.text.ParseException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class pantallaEditarSemestre : AppCompatActivity() {

    lateinit var nombre: String
    var año: Int = 0
    var fechaInicio: LocalDate = LocalDate.now()
    var activo: Boolean = false
    var numeroTotalMateria: MutableList<Materia> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_editar_semestre)
        val semestreBDD = BDD(this)

        val positionSemestreSelected = intent.getIntExtra("positionSemestreSelected", -1)

        val inputNombreSemestre = findViewById<EditText>(R.id.txtNombreEstudiante)
        val inputAño = findViewById<EditText>(R.id.txtAño)
        val inputFechaInicio = findViewById<EditText>(R.id.txtAñoInicio)
        val inputNumeroMaterias = findViewById<EditText>(R.id.txtNumeroMaterias)
        val inputActivo = findViewById<RadioButton>(R.id.rbtActivo)
        val inputInactivo = findViewById<RadioButton>(R.id.rbtInactivo)
        val btnCrear = findViewById<Button>(R.id.btnCrearSemestre)
        val btnActualizar = findViewById<Button>(R.id.btnActualizarSemestre)


        if (positionSemestreSelected != -1) {
            // Editar
            btnCrear.isEnabled = false
            btnActualizar.isEnabled = true

            val semestre = BDD.datos[positionSemestreSelected]

            inputNombreSemestre.setText(BDD.datos[positionSemestreSelected].nombre.toString())
            inputAño.setText(BDD.datos[positionSemestreSelected].año.toString())
            inputFechaInicio.setText(BDD.datos[positionSemestreSelected].fechaInicio.toString())
            inputNumeroMaterias.setText(BDD.datos[positionSemestreSelected].numeroTotalMateria.toString())

            if (BDD.datos[positionSemestreSelected].activo) {
                inputActivo.isChecked = true
            } else {
                inputInactivo.isChecked = true
            }

            btnActualizar.setOnClickListener {

                try {
                    // Parseo seguro de la fecha
                    var fechaInicio = parseFecha(inputFechaInicio.text.toString())
                    nombre = inputNombreSemestre.text.toString()
                    año = inputAño.text.toString().toInt()
                    activo = inputActivo.isChecked

                    BDD.datos[positionSemestreSelected].nombre = nombre
                    BDD.datos[positionSemestreSelected].año = año
                    BDD.datos[positionSemestreSelected].fechaInicio = fechaInicio
                    BDD.datos[positionSemestreSelected].numeroTotalMateria = numeroTotalMateria
                    BDD.datos[positionSemestreSelected].activo = activo
                    Toast.makeText(this, "Semestre actualizado", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, Intent())
                    finish()
                } catch (e: ParseException) {
                    Toast.makeText(this, "Error al parsear la fecha", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Crear
            btnCrear.isEnabled = true
            btnActualizar.isEnabled = false
            btnCrear.setOnClickListener {
                try {
                    // Parseo seguro de la fecha
                    var fechaInicio = parseFecha(inputFechaInicio.text.toString())
                    nombre = inputNombreSemestre.text.toString()
                    año = inputAño.text.toString().toInt()
                    activo = inputActivo.isChecked

                    var semestre = Semestre(nombre, año, fechaInicio, activo, mutableListOf())
                    BDD.datos.add(semestre)
                    Toast.makeText(this, "Semestre creado", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, Intent())
                    finish()
                } catch (e: ParseException) {
                    Toast.makeText(this, "Error al parsear la fecha", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun parseFecha(fecha: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(fecha, formatter)
    }
}