package com.example.forest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ParquesGuardabosquesDB"
        private const val DATABASE_VERSION = 1


        const val TABLE_PARQUES = "parques"
        const val COLUMN_PARQUE_ID = "id"
        const val COLUMN_PARQUE_NOMBRE = "nombre"
        const val COLUMN_PARQUE_UBICACION = "ubicacion"
        const val COLUMN_PARQUE_SUPERFICIE = "superficie"
        const val COLUMN_PARQUE_TIPO = "tipo"
        const val COLUMN_PARQUE_FECHA = "fecha_establecimiento"

        const val TABLE_GUARDABOSQUES = "guardabosques"
        const val COLUMN_GUARDABOSQUE_ID = "id"
        const val COLUMN_GUARDABOSQUE_NOMBRE = "nombre"
        const val COLUMN_GUARDABOSQUE_EDAD = "edad"
        const val COLUMN_GUARDABOSQUE_ZONA = "zona"
        const val COLUMN_GUARDABOSQUE_EXPERIENCIA = "experiencia"
        const val COLUMN_GUARDABOSQUE_FECHA_INGRESO = "fecha_ingreso"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableParques = """
            CREATE TABLE $TABLE_PARQUES (
                $COLUMN_PARQUE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PARQUE_NOMBRE TEXT,
                $COLUMN_PARQUE_UBICACION TEXT,
                $COLUMN_PARQUE_SUPERFICIE REAL,
                $COLUMN_PARQUE_TIPO TEXT,
                $COLUMN_PARQUE_FECHA TEXT
            )
        """
        db.execSQL(createTableParques)

        val createTableGuardabosques = """
            CREATE TABLE $TABLE_GUARDABOSQUES (
                $COLUMN_GUARDABOSQUE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_GUARDABOSQUE_NOMBRE TEXT,
                $COLUMN_GUARDABOSQUE_EDAD INTEGER,
                $COLUMN_GUARDABOSQUE_ZONA TEXT,
                $COLUMN_GUARDABOSQUE_EXPERIENCIA TEXT,
                $COLUMN_GUARDABOSQUE_FECHA_INGRESO TEXT
            )
        """
        db.execSQL(createTableGuardabosques)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARQUES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GUARDABOSQUES")
        onCreate(db)
    }

    fun insertarParque(nombre: String, ubicacion: String, superficie: Double, tipo: String, fecha: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PARQUE_NOMBRE, nombre)
            put(COLUMN_PARQUE_UBICACION, ubicacion)
            put(COLUMN_PARQUE_SUPERFICIE, superficie)
            put(COLUMN_PARQUE_TIPO, tipo)
            put(COLUMN_PARQUE_FECHA, fecha)
        }
        return db.insert(TABLE_PARQUES, null, values).also { db.close() }
    }

    fun obtenerParques(): Cursor {
        val db = readableDatabase
        return db.query(TABLE_PARQUES, null, null, null, null, null, null)
    }

    fun actualizarParque(id: Int, nombre: String, ubicacion: String, superficie: Double, tipo: String, fecha: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PARQUE_NOMBRE, nombre)
            put(COLUMN_PARQUE_UBICACION, ubicacion)
            put(COLUMN_PARQUE_SUPERFICIE, superficie)
            put(COLUMN_PARQUE_TIPO, tipo)
            put(COLUMN_PARQUE_FECHA, fecha)
        }
        return db.update(TABLE_PARQUES, values, "$COLUMN_PARQUE_ID=?", arrayOf(id.toString())).also { db.close() }
    }

    fun eliminarParque(nombre: String): String {
        val db = writableDatabase
        return db.delete(TABLE_PARQUES, "$COLUMN_PARQUE_NOMBRE=?", arrayOf(nombre.toString())).also { db.close() } .toString()
    }

    fun insertarGuardabosque(nombre: String, edad: Int, zona: String, experiencia: String, fechaIngreso: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GUARDABOSQUE_NOMBRE, nombre)
            put(COLUMN_GUARDABOSQUE_EDAD, edad)
            put(COLUMN_GUARDABOSQUE_ZONA, zona)
            put(COLUMN_GUARDABOSQUE_EXPERIENCIA, experiencia)
            put(COLUMN_GUARDABOSQUE_FECHA_INGRESO, fechaIngreso)
        }
        return db.insert(TABLE_GUARDABOSQUES, null, values).also { db.close() }
    }

    fun obtenerGuardabosques(): Cursor {
        val db = readableDatabase
        return db.query(TABLE_GUARDABOSQUES, null, null, null, null, null, null)
    }

    fun actualizarGuardabosque(id: Int, nombre: String, edad: Int, zona: String, experiencia: String, fechaIngreso: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_GUARDABOSQUE_NOMBRE, nombre)
            put(COLUMN_GUARDABOSQUE_EDAD, edad)
            put(COLUMN_GUARDABOSQUE_ZONA, zona)
            put(COLUMN_GUARDABOSQUE_EXPERIENCIA, experiencia)
            put(COLUMN_GUARDABOSQUE_FECHA_INGRESO, fechaIngreso)
        }
        return db.update(TABLE_GUARDABOSQUES, values, "$COLUMN_GUARDABOSQUE_ID=?", arrayOf(id.toString())).also { db.close() }
    }

    fun eliminarGuardabosque(nombre: String): String {
        val db = writableDatabase
        return db.delete(TABLE_GUARDABOSQUES, "$COLUMN_GUARDABOSQUE_NOMBRE=?", arrayOf(nombre.toString())).also { db.close() }
            .toString()
    }
}
