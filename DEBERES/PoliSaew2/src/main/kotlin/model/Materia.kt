package model

import persistence.GestorArchivos
data class Materia(
    val nombre: String,
    val codigo: String,
    var creditos: Double,
    var profesor: String,
    var semestre: String
){
    companion object {
        private val gestorArchivos = GestorArchivos("src/main/kotlin/persistence/ArchivosMaterias.txt")

        //--------------------------------- GET & SET de ARCHIVOS ------------------------------------
        fun getAllMaterias(): MutableList<Materia> {
            val materiaData = gestorArchivos.readData()
            val materiaList = mutableListOf<Materia>()
            for (line in materiaData) {
                val materiaProperties = line.split(",")
                if (materiaProperties.size == 5) { // Verificar que haya al menos 5 elementos
                    val materia = Materia(
                        materiaProperties[0],
                        materiaProperties[1],
                        materiaProperties[2].toDouble(),
                        materiaProperties[3],
                        materiaProperties[4]
                    )
                    materiaList.add(materia)
                }
            }
            return materiaList
        }

        private fun saveAllMaterias(materiaList: List<Materia>) {
            val materiaData = materiaList.map { materia ->
                "${materia.nombre},${materia.codigo},${materia.creditos}," +
                        "${materia.profesor},${materia.semestre}"
            }
            gestorArchivos.writeData(materiaData)
        }

        //--------------------------------- CRUD de MATERIAS ------------------------------------
        fun createMateria(materia: Materia) {
            val materiaList = getAllMaterias()

            // Verificar que el código de la materia no exista previamente
            if (materiaList.any { it.codigo == materia.codigo }) {
                println("Error: El código de la materia ya existe.")
                return
            }

            // Agregar la materia solo si todas las validaciones son exitosas
            materiaList.add(materia)
            saveAllMaterias(materiaList)
            println("Materia creada exitosamente.")
        }

        fun readMateriaByCodigo(codigo: String): Materia? {
            val materiaList = getAllMaterias()
            return materiaList.find { it.codigo == codigo }
        }

        fun updateMateria(materia: Materia) {
            val materiaList = getAllMaterias()
            val existingMateria = materiaList.find { it.codigo == materia.codigo }
            if (existingMateria != null) {
                existingMateria.creditos = materia.creditos
                existingMateria.profesor = materia.profesor
                existingMateria.semestre = materia.semestre
                saveAllMaterias(materiaList)
                println("Materia actualizada exitosamente.")
            } else {
                println("Error: Materia no encontrada.")
            }
        }

        fun deleteMateria(codigo: String) {
            val materiaList = getAllMaterias()
            materiaList.removeIf { it.codigo == codigo }
            saveAllMaterias(materiaList)
            println("Materia eliminada exitosamente.")
        }
    }
}