package com.example.updown_sona

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.updown_sona.databinding.ActivityMainBinding
import com.example.updown_sona.databinding.ActivitySecondBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class SecondActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var jugadorRecuperado : String
    private lateinit var layout: ConstraintLayout
    private var cartaActual: Int = 0
    private var cartaFutura: Int = 0
    private var puntos: Int = 0


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jugadorRecuperado = intent.extras!!.getString("nombre").toString()
        textoBienvenida()

        binding.btnUp.setOnTouchListener{_, event ->
            botonPresionado(binding.btnUp, event)
            false
        }
        binding.btnUp.setOnClickListener(this)

        binding.btnDown.setOnTouchListener{_, event ->
            botonPresionado(binding.btnDown, event)
            false
        }
        binding.btnDown.setOnClickListener(this)

    }

    private fun textoBienvenida(){
        //creamos el texto de bienvenida
        val mensaje = "Bienvenido/a ${jugadorRecuperado}"
        layout = findViewById(R.id.segundaPantalla)

        //descativamos botones
        binding.btnUp.isEnabled = false
        binding.btnDown.isEnabled = false

        Snackbar.make(layout, mensaje, Snackbar.LENGTH_INDEFINITE).setAction("Empezar"){
            cambiarFondo()
            //volvemos a activar los botones
            binding.btnUp.isEnabled = true
            binding.btnDown.isEnabled = true
        }.show()

    }

    override fun onClick(p0: View?) {
        //establecemos el funcionamiento de los botones
        when(p0?.id){
            binding.btnUp.id->{
                cambiarFondo()
                if(cartaFutura > cartaActual){
                    sumarPunto()
                } else {
                    terminarJuego()
                }
            }
            binding.btnDown.id->{
                cambiarFondo()
                if(cartaFutura < cartaActual){
                    sumarPunto()
                } else {
                    terminarJuego()
                }
            }
        }

    }

    private fun botonPresionado(imageButton: ImageButton, event: MotionEvent){
        if(event.action == MotionEvent.ACTION_DOWN){
            if (imageButton == binding.btnUp){
                imageButton.setBackgroundResource(R.drawable.uppress)
            }
            if (imageButton == binding.btnDown){
                imageButton.setBackgroundResource(R.drawable.downpress)
            }
        } else if (event.action == MotionEvent.ACTION_UP){
            if (imageButton == binding.btnUp){
                imageButton.setBackgroundResource(R.drawable.up)
            }
            if (imageButton == binding.btnDown){
                imageButton.setBackgroundResource(R.drawable.down)
            }
        }
    }

    private fun cambiarFondo(){
        //Creamos un LinkedHashMap para mapear los valores de las cartas
        val cartas = LinkedHashMap<Int, Int>()
        cartas.put(1, R.drawable.c1)
        cartas.put(2, R.drawable.c2)
        cartas.put(3, R.drawable.c3)
        cartas.put(4, R.drawable.c4)
        cartas.put(5, R.drawable.c5)
        cartas.put(6, R.drawable.c6)
        cartas.put(7, R.drawable.c7)
        cartas.put(8, R.drawable.c8)
        cartas.put(9, R.drawable.c9)
        cartas.put(10, R.drawable.c10)
        cartas.put(11, R.drawable.c11)
        cartas.put(12, R.drawable.c12)
        cartas.put(13, R.drawable.c13)

        //asignación de la carta actual a la futura
        cartaActual = cartaFutura

        //nos cambia de carta y aseguramos que no se repita la misma carta
        var claveCartaFutura: Int
        do {
            claveCartaFutura = Random.nextInt(1, 14)
        } while (claveCartaFutura == cartaActual)

        //se actualiza con el valor generado
        cartaFutura = claveCartaFutura

        //Obtenemos el id de la carta con cartaFutura como clave
        val cartaId = cartas[cartaFutura] ?: return

        //cambiamos el fondo y establecemos la carta nueva
        val constraintLayout: ConstraintLayout = findViewById(R.id.segundaPantalla)
        constraintLayout.setBackgroundResource(cartaId)

    }

    private fun sumarPunto(){
        //Sumar puntos al total por cada acierto
        puntos++
        binding.txtPuntos.text = "Puntos: $puntos"

    }

    private fun terminarJuego(){
        //primero desactivamos los botones
        binding.btnUp.isEnabled = false
        binding.btnDown.isEnabled = false

        // aparece el Snackbar con los puntos para terminar el juego
        Snackbar.make(layout, "Game Over! Puntos: $puntos", Snackbar.LENGTH_INDEFINITE).setAction("OK"){
            finish();
        }.show()
    }

}