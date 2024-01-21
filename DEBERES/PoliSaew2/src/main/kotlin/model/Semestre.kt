package model

import persistence.GestorArchivos
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Semestre(

    val nombre: String,
    var año: Int,
    var fechaInicio: LocalDate,
    var activo: Boolean,
    var numeroTotalMateria: Int

){

    companion object {
    private val gestorArchivos = GestorArchivos("src/main/kotlin/persistence/ArchivosSemestres.txt")

    fun getAllSemestres(): MutableList<Semestre> {
        val semestreData = gestorArchivos.readData()
        val semestreList = mutableListOf<Semestre>()
        for (line in semestreData) {
            val semestreProperties = line.split(",")
            if (semestreProperties.size == 5) {
                val semestre = Semestre(
                    semestreProperties[0],
                    semestreProperties[1].toInt(),
                    LocalDate.parse(semestreProperties[2]),
                    semestreProperties[3].toBoolean(),
                    semestreProperties[4].toInt()
                )
                semestreList.add(semestre)
            }
        }
        return semestreList
    }

    fun saveAllSemestres(semestreList: List<Semestre>) {
        val semestreData = semestreList.map { semestre ->
            "${semestre.nombre},${semestre.año},${semestre.fechaInicio.format(DateTimeFormatter.ISO_DATE)},${semestre.activo},${semestre.numeroTotalMateria}"
        }
        gestorArchivos.writeData(semestreData)
    }

    fun createSemestre(semestre: Semestre) {
        val semestreList = getAllSemestres()

        // Verificar si el nombre del semestre ya existe
        if (semestreList.any { it.nombre == semestre.nombre }) {
            println("Error: El semestre ya existe.")
            return
        }

        // Agregar el semestre a la lista y guardar la lista actualizada
        semestreList.add(semestre)
        saveAllSemestres(semestreList)
        println("Semestre agregado exitosamente.")
    }

    fun readSemestre(nombre: String): Semestre? {
        val semestreList = getAllSemestres()
        return semestreList.find { it.nombre == nombre }
    }

    fun updateSemestre(semestreActualizado: Semestre) {
        val semestreList = getAllSemestres()
        val semestreExistente = semestreList.find { it.nombre == semestreActualizado.nombre }

        if (semestreExistente != null) {
            semestreExistente.año = semestreActualizado.año
            semestreExistente.fechaInicio = semestreActualizado.fechaInicio
            semestreExistente.activo = semestreActualizado.activo
            semestreExistente.numeroTotalMateria = semestreActualizado.numeroTotalMateria

            saveAllSemestres(semestreList)
            println("Semestre actualizado exitosamente.")
        } else {
            println("Error: Semestre no encontrado.")
        }
    }
        fun deleteSemestre(nombre: String) {
            val semestreList = getAllSemestres()
            val existeSemestre = semestreList.any { it.nombre == nombre }

            if (existeSemestre) {
                semestreList.removeIf { it.nombre == nombre }
                saveAllSemestres(semestreList)
                println("Semestre eliminado exitosamente.")
            } else {
                println("Error: Semestre no encontrado.")
            }
        }
    }

}
