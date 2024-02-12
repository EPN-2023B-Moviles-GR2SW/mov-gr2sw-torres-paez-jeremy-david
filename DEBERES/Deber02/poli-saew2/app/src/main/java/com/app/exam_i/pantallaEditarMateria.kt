package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.app.exam_i.model.BDD
import com.app.exam_i.model.Materia
import java.text.ParseException

class pantallaEditarMateria : AppCompatActivity() {
    val semestreBD = BDD(this)
    val materiaDB = BDD(this)

    lateinit var nombre: String
    var codigo: Int = 0
    var creditos: Double = 0.0
    var segundaM: Boolean = false
    lateinit var semestre: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_editar_materia)

        val positionMateriaSelected = intent.getIntExtra("positionMateriaSelected", -1)
        val positionSemestreSelected = intent.getIntExtra("positionSemestreSelected", -1)

        val inputNombreMateria = findViewById<EditText>(R.id.txtNombreMateria)
        val inputCodigo = findViewById<EditText>(R.id.txtCodigo)
        val inputCreditos = findViewById<EditText>(R.id.txtCreditos)
        val inputSegundaM = findViewById<RadioButton>(R.id.rbtSegundaM)
        val inputPrimeraM = findViewById<RadioButton>(R.id.rbtPrimeraM)
        val inputSemestre = findViewById<EditText>(R.id.txtSemestre)
        val btnCrear = findViewById<Button>(R.id.btnCrearMateria)
        val btnActualizar = findViewById<Button>(R.id.btnActualizarMateria)


        if (positionMateriaSelected != -1) {
            // Editar
            btnCrear.isEnabled = false
            btnActualizar.isEnabled = true

            val materia = BDD.datos[positionSemestreSelected].numeroTotalMateria[positionMateriaSelected]

            inputNombreMateria.setText(BDD.datos[positionSemestreSelected].numeroTotalMateria[positionMateriaSelected].nombre.toString())
            inputCodigo.setText(BDD.datos[positionSemestreSelected].numeroTotalMateria[positionMateriaSelected].codigo.toString())
            inputCreditos.setText(BDD.datos[positionSemestreSelected].numeroTotalMateria[positionMateriaSelected].creditos.toString())
            inputSemestre.setText(BDD.datos[positionSemestreSelected].numeroTotalMateria[positionMateriaSelected].semestre.toString())


            if (BDD.datos[positionSemestreSelected].numeroTotalMateria[positionMateriaSelected].segundaM) {
                inputSegundaM.isChecked = true
            } else {
                inputPrimeraM.isChecked = true

            }

            btnActualizar.setOnClickListener {
                try {
                    nombre = inputNombreMateria.text.toString()
                    codigo = inputCodigo.text.toString().toInt()
                    creditos = inputCreditos.text.toString().toDouble()
                    semestre = inputSemestre.text.toString()
                    segundaM = inputSegundaM.isChecked

                    val materia = Materia(nombre, codigo, creditos, segundaM, semestre)
                    BDD.datos[positionSemestreSelected].numeroTotalMateria[positionMateriaSelected] = materia
                    Toast.makeText(this, "Materia Actualizada", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, Intent())
                    finish()

                } catch (e: ParseException) {
                    Toast.makeText(this, "Error al parsear la fecha", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        } else {

            btnCrear.isEnabled = true
            btnActualizar.isEnabled = false
            btnCrear.setOnClickListener {
                try {
                    nombre = inputNombreMateria.text.toString()
                    codigo = inputCodigo.text.toString().toInt()
                    creditos = inputCreditos.text.toString().toDouble()
                    semestre = inputSemestre.text.toString()
                    segundaM = inputSegundaM.isChecked

                    val materia = Materia(nombre, codigo, creditos, segundaM, semestre)
                    BDD.datos[positionSemestreSelected].numeroTotalMateria.add(materia)
                    Toast.makeText(this, "Materia Creada", Toast.LENGTH_SHORT).show()
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
}