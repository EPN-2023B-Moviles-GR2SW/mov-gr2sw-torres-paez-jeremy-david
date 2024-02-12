package com.app.exam_i.model

data class Materia(
    val nombre: String,
    val codigo: Int,
    var creditos: Double,
    var segundaM: Boolean,
    var semestre: String
){
    override fun toString(): String {
        if(segundaM){
            return "$nombre - $codigo - $creditos - Segunda Matrícula - $semestre"
    }else{
            return "$nombre - $codigo - $creditos - Primera Matrícula - $semestre"
        }
    }
}