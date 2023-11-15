import java.util.*

fun main (args: Array<String>){
    println("Hello World!")
    // INMUTABLES (NO se reasignan"=")
    val inmutable: String ="Adrian";
    //inmutable = "Vicente";

    //Mutables (RE asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    // val > var
    /// Duck typing
    var ejemploVariable = "Adrian Eguez"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo

    //Variable primitiva
    val nombreProfesor:String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases Java
    val fechaNacimiento: Date = Date()
}