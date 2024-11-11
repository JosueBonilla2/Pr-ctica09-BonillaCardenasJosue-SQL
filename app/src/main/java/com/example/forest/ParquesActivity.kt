package com.example.forest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ParquesActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etUbicacion: EditText
    private lateinit var etSuperficie: EditText
    private lateinit var spTipoParque: Spinner
    private lateinit var etFechaEstablecimiento: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbarP)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etNombre = findViewById(R.id.etNombre)
        etUbicacion = findViewById(R.id.etUbicacion)
        etSuperficie = findViewById(R.id.etSuperficie)
        spTipoParque = findViewById(R.id.spTipoParque)
        etFechaEstablecimiento = findViewById(R.id.etFechaEstablecimiento)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)
        dbHelper = DatabaseHelper(this)

        val tiposParque = arrayOf("Nacional", "Natural", "Recreativo", "Zoológico", "Botánico")
        spTipoParque.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposParque)

        btnRegistrar.setOnClickListener { registrarParque() }
        btnBuscar.setOnClickListener { buscarParque() }
        btnEditar.setOnClickListener { editarParque() }
        btnEliminar.setOnClickListener { eliminarParque() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun registrarParque() {
        val nombre = etNombre.text.toString()
        val ubicacion = etUbicacion.text.toString()
        val superficie = etSuperficie.text.toString().toDoubleOrNull()
        val tipoParque = spTipoParque.selectedItem.toString()
        val fecha = etFechaEstablecimiento.text.toString()

        if (nombre.isNotEmpty() && ubicacion.isNotEmpty() && superficie != null) {
            dbHelper.insertarParque(nombre, ubicacion, superficie, tipoParque, fecha)
            Toast.makeText(this, "Parque registrado", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarParque() {
        val nombre = etNombre.text.toString()
        if (nombre.isNotEmpty()) {
            val cursor = dbHelper.obtenerParques()
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(cursor.getColumnIndexOrThrow("nombre")) == nombre) {
                        etUbicacion.setText(cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")))
                        etSuperficie.setText(cursor.getString(cursor.getColumnIndexOrThrow("extension")))
                        val tipoParque = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                        spTipoParque.setSelection((spTipoParque.adapter as ArrayAdapter<String>).getPosition(tipoParque))
                        etFechaEstablecimiento.setText(cursor.getString(cursor.getColumnIndexOrThrow("fecha")))
                        break
                    }
                } while (cursor.moveToNext())
                cursor.close()
            } else {
                Toast.makeText(this, "Parque no encontrado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese el nombre del parque", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editarParque() {
        val nombre = etNombre.text.toString()
        val ubicacion = etUbicacion.text.toString()
        val superficie = etSuperficie.text.toString().toDoubleOrNull()
        val tipoParque = spTipoParque.selectedItem.toString()

        if (nombre.isNotEmpty() && ubicacion.isNotEmpty() && superficie != null) {
            val rowsUpdated = dbHelper.actualizarParque(1, nombre, ubicacion, superficie, tipoParque, "") // ID de ejemplo
            if (rowsUpdated > 0) {
                Toast.makeText(this, "Parque actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al actualizar parque", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarParque() {
        val nombre = etNombre.text.toString()
        if (nombre.isNotEmpty()) {
            val rowsDeleted = dbHelper.eliminarParque(nombre)
            if (rowsDeleted != nombre) {
                Toast.makeText(this, "Parque eliminado", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Error al eliminar parque", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese el nombre del parque", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        etNombre.text.clear()
        etUbicacion.text.clear()
        etSuperficie.text.clear()
        spTipoParque.setSelection(0)
        etFechaEstablecimiento.text.clear()
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
