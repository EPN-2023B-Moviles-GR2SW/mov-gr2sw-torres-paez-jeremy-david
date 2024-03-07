package com.app.exam_i.model

object BDD {
    private val materia1 = Materia(
        "Sistemas Embebidos",
        2586,
        3.0,
        false,
        "2021-1"
    )
    private val materia2 = Materia(
        "Sistemas Operativos",
        8484,
        2.0,
        false,
        "2021-2"
    )
    private val materia3 = Materia(
        "Redes y Tele-Comunicaciones",
        3284,
        3.0,
        false,
        "2022-2"
    )
    private val materia4 = Materia(
        "Aplicaciones Moviles",
       4584,
        3.0,
        false,
        "2022-1"
    )
    private val semestre1 = Semestre(
        "David Torres",
        2023,
        "2023-11-28)",
        true,
        mutableListOf(materia1, materia2)
    )
    private val semestre2 = Semestre(
        "David Torres 2",
        2020,
        "2023-10-28)",
        true,
        mutableListOf(materia3, materia4)
    )

    val datos = mutableListOf(semestre1, semestre2)
}