package com.example.forest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.forest.DatabaseHelper

class GuardabosquesActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var etZona: EditText
    private lateinit var etExperiencia: EditText
    private lateinit var etFechaIngreso: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardabosques)

        val toolbar = findViewById<Toolbar>(R.id.toolbarG)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etNombre = findViewById(R.id.etNombreGuardabosque)
        etEdad = findViewById(R.id.etEdad)
        etZona = findViewById(R.id.etZona)
        etExperiencia = findViewById(R.id.etExperiencia)
        etFechaIngreso = findViewById(R.id.etFechaIngreso)
        btnRegistrar = findViewById(R.id.btnRegistrarGuardabosque)
        btnBuscar = findViewById(R.id.btnBuscarGuardabosque)
        btnEditar = findViewById(R.id.btnEditarGuardabosque)
        btnEliminar = findViewById(R.id.btnEliminarGuardabosque)
        dbHelper = DatabaseHelper(this)

        btnRegistrar.setOnClickListener { registrarGuardabosque() }
        btnBuscar.setOnClickListener { buscarGuardabosque() }
        btnEditar.setOnClickListener { editarGuardabosque() }
        btnEliminar.setOnClickListener { eliminarGuardabosque() }
    }

    private fun registrarGuardabosque() {
        val nombre = etNombre.text.toString()
        val edad = etEdad.text.toString().toIntOrNull()
        val zona = etZona.text.toString()
        val experiencia = etExperiencia.text.toString()
        val fechaIngreso = etFechaIngreso.text.toString()

        if (nombre.isNotEmpty() && edad != null && zona.isNotEmpty() && experiencia.isNotEmpty()) {
            dbHelper.insertarGuardabosque(nombre, edad, zona, experiencia, fechaIngreso)
            Toast.makeText(this, "Guardabosque registrado", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarGuardabosque() {
        val nombre = etNombre.text.toString()
        if (nombre.isNotEmpty()) {
            val cursor = dbHelper.obtenerGuardabosques()
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(cursor.getColumnIndexOrThrow("nombre")) == nombre) {
                        etEdad.setText(cursor.getString(cursor.getColumnIndexOrThrow("edad")))
                        etZona.setText(cursor.getString(cursor.getColumnIndexOrThrow("zona")))
                        etExperiencia.setText(cursor.getString(cursor.getColumnIndexOrThrow("experiencia")))
                        break
                    }
                } while (cursor.moveToNext())
                cursor.close()
            } else {
                Toast.makeText(this, "Guardabosque no encontrado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese el nombre del guardabosque", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editarGuardabosque() {
        val nombre = etNombre.text.toString()
        val edad = etEdad.text.toString().toIntOrNull()
        val zona = etZona.text.toString()
        val experiencia = etExperiencia.text.toString()

        if (nombre.isNotEmpty() && edad != null && zona.isNotEmpty() && experiencia.isNotEmpty()) {
            val rowsUpdated = dbHelper.actualizarGuardabosque(1, nombre, edad, zona, experiencia, "") // ID de ejemplo
            if (rowsUpdated > 0) {
                Toast.makeText(this, "Guardabosque actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar guardabosque", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarGuardabosque() {
        val nombre = etNombre.text.toString()
        if (nombre.isNotEmpty()) {
            val rowsDeleted = dbHelper.eliminarGuardabosque(nombre)
            if (rowsDeleted != nombre) {
                Toast.makeText(this, "Guardabosque eliminado", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Error al eliminar guardabosque", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese el nombre del guardabosque", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        etNombre.text.clear()
        etEdad.text.clear()
        etZona.text.clear()
        etExperiencia.text.clear()
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