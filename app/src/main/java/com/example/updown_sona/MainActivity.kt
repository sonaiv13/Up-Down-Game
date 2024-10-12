package com.example.updown_sona

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputBinding
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.updown_sona.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEmpezar.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            binding.btnEmpezar.id ->{
                //Comprobamos si está vacío o no
                if(!binding.editNombre.text.isEmpty()){
                    val nombre = binding.editNombre.text.toString()

                    //Cerrar el teclado
                    val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(p0.windowToken, 0)

                    //cambiamos de pantalla -> accion INIT
                    val intent: Intent = Intent(applicationContext, SecondActivity::class.java)
                    intent.putExtra("nombre", nombre)
                    Snackbar.make(p0, "Estas seguro de que quieres empezar?", Snackbar.LENGTH_INDEFINITE).setAction("Sí"){
                        this.startActivity(intent)
                    }.show()
                } else {
                    //creamos Toast y lo mostramos
                    Toast.makeText(applicationContext, "Por favor introduce un nombre", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}