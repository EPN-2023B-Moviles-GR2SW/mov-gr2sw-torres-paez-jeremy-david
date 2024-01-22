package com.app.exam_i.model

import java.time.LocalDate

data class Semestre(
    var nombre: String,
    var año: Int,
    var fechaInicio: LocalDate,
    var activo: Boolean,
    var numeroTotalMateria: MutableList<Materia>
){

    override fun toString(): String {
        if(activo){
            return "$nombre - $año - $fechaInicio - Activo - $numeroTotalMateria"
        }else{
            return "$nombre - $año - $fechaInicio - Inactivo - $numeroTotalMateria"
        }
    }
}
