package com.example.practico2.ui.models

import com.example.practico2.ui.components.Direccion
import kotlin.random.Random

class Tablero(private val anchoCelda: Int, private val altoCelda: Int) {
    var filas = altoCelda / 100
    var columnas = anchoCelda / 100

    var matriz = Array(filas) { Array(columnas) { 0 } }

    private var cabezaX = 5
    private var cabezaY = 4
    private var cuerpo = mutableListOf(Pair(5, 3), Pair(5, 2))
    private var comidaX = 0
    private var comidaY = 0
    private var comidaPresente = false

    var direccionActual = Direccion.DERECHA

    init {
        inicializarViborita()
    }

    fun inicializarViborita() {
        cabezaX = 5
        cabezaY = 4
        cuerpo = mutableListOf(Pair(5, 3), Pair(5, 2))
        matriz = Array(filas) { Array(columnas) { 0 } }
        matriz[cabezaY][cabezaX] = 2
        for (parte in cuerpo) {
            matriz[parte.second][parte.first] = 1
        }
        generarComida()
        direccionActual = Direccion.DERECHA
    }

    fun generarComida() {
        do {
            comidaX = Random.nextInt(columnas)
            comidaY = Random.nextInt(filas)
        } while (matriz[comidaY][comidaX] != 0)

        matriz[comidaY][comidaX] = 3
        comidaPresente = true
    }

    private fun mover(nuevaCabezaX: Int, nuevaCabezaY: Int) {
        var ajustadoX = nuevaCabezaX
        var ajustadoY = nuevaCabezaY

        if (ajustadoX >= columnas) ajustadoX = 0
        if (ajustadoX < 0) ajustadoX = columnas - 1
        if (ajustadoY >= filas) ajustadoY = 0
        if (ajustadoY < 0) ajustadoY = filas - 1

        if (matriz[ajustadoY][ajustadoX] == 1) {
            inicializarViborita()
            return
        }

        cuerpo.add(0, Pair(cabezaX, cabezaY))

        if (ajustadoX == comidaX && ajustadoY == comidaY) {
            generarComida()
        } else {
            cuerpo.removeAt(cuerpo.size - 1)
        }

        matriz = Array(filas) { Array(columnas) { 0 } }
        cabezaX = ajustadoX
        cabezaY = ajustadoY
        matriz[cabezaY][cabezaX] = 2

        for (parte in cuerpo) {
            matriz[parte.second][parte.first] = 1
        }

        if (comidaPresente) {
            matriz[comidaY][comidaX] = 3
        }
    }

    fun moverDerecha() {
        if (direccionActual != Direccion.IZQUIERDA) {
            mover(cabezaX + 1, cabezaY)
            direccionActual = Direccion.DERECHA
        }
    }

    fun moverIzquierda() {
        if (direccionActual != Direccion.DERECHA) {
            mover(cabezaX - 1, cabezaY)
            direccionActual = Direccion.IZQUIERDA
        }
    }

    fun moverAbajo() {
        if (direccionActual != Direccion.ARRIBA) {
            mover(cabezaX, cabezaY + 1)
            direccionActual = Direccion.ABAJO
        }
    }

    fun moverArriba() {
        if (direccionActual != Direccion.ABAJO) {
            mover(cabezaX, cabezaY - 1)
            direccionActual = Direccion.ARRIBA
        }
    }
}
