package com.example.forest

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ListaGuardabosquesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editTextDetalle: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_guardabodques)

        val toolbar = findViewById<Toolbar>(R.id.toolbarLG)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editTextDetalle = findViewById(R.id.edGuardabosques)
        dbHelper = DatabaseHelper(this)

        mostrarRegistrosGuardabosques()
    }

    private fun mostrarRegistrosGuardabosques() {
        val cursor = dbHelper.obtenerGuardabosques()
        val registros = StringBuilder()

        cursor.use {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GUARDABOSQUE_ID))
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GUARDABOSQUE_NOMBRE))
                    val edad = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GUARDABOSQUE_EDAD))
                    val zona = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GUARDABOSQUE_ZONA))
                    val experiencia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GUARDABOSQUE_EXPERIENCIA))
                    val fechaIngreso = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GUARDABOSQUE_FECHA_INGRESO))

                    registros.append("ID: $id\n")
                    registros.append("Nombre: $nombre\n")
                    registros.append("Edad: $edad\n")
                    registros.append("Zona: $zona\n")
                    registros.append("Experiencia: $experiencia\n")
                    registros.append("Fecha de ingreso: $fechaIngreso\n")
                    registros.append("\n-------------------------\n\n")
                } while (cursor.moveToNext())
            } else {
                registros.append("No hay registros de guardabosques.")
            }
        }

        editTextDetalle.setText(registros.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_parques -> startActivity(Intent(this, ParquesActivity::class.java))
            R.id.menu_guardabosques -> startActivity(Intent(this, GuardabosquesActivity::class.java))
            R.id.menu_listaParques -> startActivity(Intent(this, ListaParquesActivity::class.java))
            R.id.menu_listaGuardabosques -> startActivity(Intent(this, ListaGuardabosquesActivity::class.java))
            R.id.menu_cerrar -> startActivity(Intent(this, MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}