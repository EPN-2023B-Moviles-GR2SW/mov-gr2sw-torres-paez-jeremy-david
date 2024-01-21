import model.Materia
import model.Semestre

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    while (true) {
        println("Menú Principal:")
        println("1. Operaciones con Semestres")
        println("2. Operaciones con Materias")
        println("3. Salir")

        print("Selecciona una opción: ")
        when (scanner.nextInt()) {
            1 -> menuSemestres(scanner)
            2 -> menuMaterias(scanner)
            3 -> {
                println("Saliendo del programa.")
                scanner.close()
                return
            }
            else -> println("Opción no válida. Inténtalo de nuevo.")
        }
    }
}
//-------------------------------- MENU SEMESTRES --------------------------------------------
fun menuSemestres(scanner: Scanner) {
    while (true) {
        println("\nMenú Semestres:")
        println("1. Crear Semestre")
        println("2. Mostrar Todos los Semestres")
        println("3. Buscar Semestre por Nombre")
        println("4. Actualizar Semestre")
        println("5. Eliminar Semestre")
        println("6. Volver al Menú Principal")

        print("Selecciona una opción: ")

        when (scanner.nextInt()) {
            1 -> crearSemestre(scanner)
            2 -> mostrarTodosLosSemestres()
            3 -> buscarSemestrePorNombre(scanner)
            4 -> actualizarSemestre(scanner)
            5 -> eliminarSemestre(scanner)
            6 -> return
            else -> println("Opción no válida. Inténtalo de nuevo.")
        }
    }
}

//-------------------------------- MENU MATERIAS --------------------------------------------
fun menuMaterias(scanner: Scanner) {
    while (true) {
        println("\nMenú Materias:")
        println("1. Crear Materia")
        println("2. Mostrar Todas las Materias")
        println("3. Buscar Materia por Código")
        println("4. Actualizar Materia")
        println("5. Eliminar Materia")
        println("6. Volver al Menú Principal")

        print("Selecciona una opción: ")

        when (scanner.nextInt()) {
            1 -> crearMateria(scanner)
            2 -> mostrarTodasLasMaterias()
            3 -> buscarMateriaPorCodigo(scanner)
            4 -> actualizarMateria(scanner)
            5 -> eliminarMateria(scanner)
            6 -> return
            else -> println("Opción no válida. Inténtalo de nuevo.")
        }
    }
}

//-------------------------------- CRUD SEMESTRE --------------------------------------------
fun crearSemestre(scanner: Scanner) {
    print("Ingrese el nombre del semestre: ")
    val nombre = scanner.next()
    print("Ingrese el año del semestre: ")
    val año = scanner.nextInt()
    print("Ingrese la fecha de inicio (formato YYYY-MM-DD): ")
    val fechaInicioStr = scanner.next()
    val fechaInicio = LocalDate.parse(fechaInicioStr, DateTimeFormatter.ISO_DATE)
    print("¿Está activo el semestre? (true/false): ")
    val activo = scanner.nextBoolean()
    print("Ingrese el número total de materias: ")
    val numeroTotalMaterias = scanner.nextInt()

    val semestre = Semestre(nombre, año, fechaInicio, activo, numeroTotalMaterias)
    Semestre.createSemestre(semestre)
}

fun mostrarTodosLosSemestres() {
    val semestres = Semestre.getAllSemestres()
    semestres.forEach { semestre ->
        println("Nombre: ${semestre.nombre}, Año: ${semestre.año}, Fecha Inicio: ${semestre.fechaInicio}, Activo: ${semestre.activo}, Número total de materias: ${semestre.numeroTotalMateria}")
    }
}

fun buscarSemestrePorNombre(scanner: Scanner) {
    print("Ingrese el nombre del semestre a buscar: ")
    val nombre = scanner.next()
    val semestre = Semestre.readSemestre(nombre)
    semestre?.let {
        println("Nombre: ${it.nombre}, Año: ${it.año}, Fecha Inicio: ${it.fechaInicio}, Activo: ${it.activo}, Número total de materias: ${it.numeroTotalMateria}")
    } ?: println("Semestre no encontrado.")
}

fun actualizarSemestre(scanner: Scanner) {
    print("Ingrese el nombre del semestre a actualizar: ")
    val nombre = scanner.next()
    val semestreExistente = Semestre.readSemestre(nombre)
    if (semestreExistente != null) {
        print("Ingrese el nuevo año del semestre: ")
        val año = scanner.nextInt()
        print("Ingrese la nueva fecha de inicio (formato YYYY-MM-DD): ")
        val fechaInicio = LocalDate.parse(scanner.next(), DateTimeFormatter.ISO_DATE)
        print("¿Está activo el semestre? (true/false): ")
        val activo = scanner.nextBoolean()
        print("Ingrese el nuevo número total de materias: ")
        val numeroTotalMaterias = scanner.nextInt()

        val semestreActualizado = Semestre(nombre, año, fechaInicio, activo, numeroTotalMaterias)
        Semestre.updateSemestre(semestreActualizado)
        println("Semestre actualizado correctamente.")
    } else {
        println("Semestre no encontrado.")
    }
}

fun eliminarSemestre(scanner: Scanner) {
    print("Ingrese el nombre del semestre a eliminar: ")
    val nombre = scanner.next()
    Semestre.deleteSemestre(nombre)
    println("Semestre eliminado correctamente.")
}

//-------------------------------- CRUD MATERIA --------------------------------------------

fun crearMateria(scanner: Scanner) {
    print("Ingrese el nombre de la materia: ")
    val nombre = scanner.next()
    print("Ingrese el código de la materia: ")
    val codigo = scanner.next()
    print("Ingrese los créditos de la materia: ")
    val creditos = scanner.nextDouble()
    print("Ingrese el nombre del profesor: ")
    val profesor = scanner.next()
    print("Ingrese el nombre del semestre al que pertenece: ")
    val semestre = scanner.next()

    val materia = Materia(nombre, codigo, creditos, profesor, semestre)
    Materia.createMateria(materia)
    println("Materia creada exitosamente.")
}

fun mostrarTodasLasMaterias() {
    val materias = Materia.getAllMaterias()
    materias.forEach { materia ->
        println("Nombre: ${materia.nombre}, Código: ${materia.codigo}, Créditos: ${materia.creditos}, Profesor: ${materia.profesor}, Semestre: ${materia.semestre}")
    }
}

fun buscarMateriaPorCodigo(scanner: Scanner) {
    print("Ingrese el código de la materia a buscar: ")
    val codigo = scanner.next()
    val materia = Materia.readMateriaByCodigo(codigo)
    materia?.let {
        println("Nombre: ${it.nombre}, Código: ${it.codigo}, Créditos: ${it.creditos}, Profesor: ${it.profesor}, Semestre: ${it.semestre}")
    } ?: println("Materia no encontrada.")
}

fun actualizarMateria(scanner: Scanner) {
    print("Ingrese el código de la materia a actualizar: ")
    val codigo = scanner.next()
    val materiaExistente = Materia.readMateriaByCodigo(codigo)
    if (materiaExistente != null) {
        print("Ingrese el nuevo nombre de la materia: ")
        val nombre = scanner.next()
        print("Ingrese los nuevos créditos de la materia: ")
        val creditos = scanner.nextDouble()
        print("Ingrese el nuevo nombre del profesor: ")
        val profesor = scanner.next()
        print("Ingrese el nombre del nuevo semestre al que pertenece: ")
        val semestre = scanner.next()

        val materiaActualizada = Materia(nombre, codigo, creditos, profesor, semestre)
        Materia.updateMateria(materiaActualizada)
        println("Materia actualizada correctamente.")
    } else {
        println("Materia no encontrada.")
    }
}

fun eliminarMateria(scanner: Scanner) {
    print("Ingrese el código de la materia a eliminar: ")
    val codigo = scanner.next()
    Materia.deleteMateria(codigo)
    println("Materia eliminada correctamente.")
}