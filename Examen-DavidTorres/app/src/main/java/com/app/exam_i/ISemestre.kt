package com.app.exam_i

class ISemestre(
    public var nombre: String,
    public var anio: Int,
    public var fechaInicio: String,
    public var activo: Boolean,
){
    override fun toString(): String {
        return "${activo} - ${anio}"
    }
}