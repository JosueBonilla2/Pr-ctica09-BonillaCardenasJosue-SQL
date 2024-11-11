package com.example.forest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.forest.DatabaseHelper

class ListaParquesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var editTextDetalle: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_parques)

        val toolbar = findViewById<Toolbar>(R.id.toolbarLP)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editTextDetalle = findViewById(R.id.editDetalle2)
        dbHelper = DatabaseHelper(this)

        mostrarRegistrosParques()
    }

    private fun mostrarRegistrosParques() {
        val cursor = dbHelper.obtenerParques()
        val registros = StringBuilder()

        cursor.use {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PARQUE_ID))
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PARQUE_NOMBRE))
                    val ubicacion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PARQUE_UBICACION))
                    val superficie = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PARQUE_SUPERFICIE))
                    val tipo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PARQUE_TIPO))
                    val fecha = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PARQUE_FECHA))

                    registros.append("ID: $id\n")
                    registros.append("Nombre: $nombre\n")
                    registros.append("Ubicación: $ubicacion\n")
                    registros.append("Superficie: $superficie\n")
                    registros.append("Tipo: $tipo\n")
                    registros.append("Fecha de establecimiento: $fecha\n")
                    registros.append("\n-------------------------\n\n")
                } while (cursor.moveToNext())
            } else {
                registros.append("No hay registros de parques.")
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