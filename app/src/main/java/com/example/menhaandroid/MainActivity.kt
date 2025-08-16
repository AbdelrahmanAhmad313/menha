package com.example.menhaandroid

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val address = findViewById<TextView>(R.id.iv_address)
        address.setOnClickListener {
            val location =
                Uri.parse("geo:0,0?q=1600 Amphitheatre Parkway, Mountain View, CA 94043, United States")
            val mapIntent = Intent(Intent.ACTION_VIEW, location)
            mapIntent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(mapIntent)
            } catch (e: ActivityNotFoundException) {
                val mapsUrl = Uri.parse(
                    "https://www.google.com/maps/search/?api=1&query=Mountain+View,California,US"
                )
                startActivity(Intent(Intent.ACTION_VIEW, mapsUrl))
            }
        }
        val translate = findViewById<TextView>(R.id.iv_details)
        translate.setOnLongClickListener {
            val isEnglishNow = resources.configuration.locales[0].language == "en"
            if (isEnglishNow) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Change language")
                    .setMessage("Do you want to change the language to Arabic")
                    .setPositiveButton("Yes") { dialog, which ->
                        Locale.setDefault(Locale("ar"))
                        val config = resources.configuration
                        config.setLocale(Locale("ar"))
                        config.setLayoutDirection(Locale("ar"))
                        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                        resources.updateConfiguration(config, resources.displayMetrics)
                        recreate()
                    }
                    .setNegativeButton("No") { dialog, which ->

                    }
                    .show()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("تغيير اللغه")
                    .setMessage("هل تريد الرجوع إلى اللغة الإنجليزية؟")
                    .setPositiveButton("نعم") { dialog, which ->
                        Locale.setDefault(Locale("en"))
                        val config = resources.configuration
                        config.setLocale(Locale("en"))
                        config.setLayoutDirection(Locale("en"))
                        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
                        resources.updateConfiguration(config, resources.displayMetrics)
                        recreate()
                    }
                    .setNegativeButton("لا") { dialog, which ->

                    }
                    .show()
            }

            true
        }
        val mode = findViewById<SwitchCompat>(R.id.iv_mode)
        val background = findViewById<ImageView>(R.id.iv_background)
        val googleBack = findViewById<ImageView>(R.id.iv_google)
        val layout = findViewById<ConstraintLayout>(R.id.main)
        val link = findViewById<TextView>(R.id.iv_link)
        mode.setOnCheckedChangeListener { SwitchCompat, isChecked ->
            if (isChecked) {
                //Dark
                mode.thumbTintList = ColorStateList.valueOf(Color.BLACK)
                mode.trackTintList = ColorStateList.valueOf(Color.WHITE)
                background.setImageResource(R.drawable.android_black)
                googleBack.setImageResource(R.drawable.google_black)
                layout.setBackgroundResource(R.color.black)
                translate.setTextColor(ContextCompat.getColor(this, R.color.white))
                val isEnglishNow = resources.configuration.locales[0].language == "en"
                if (isEnglishNow) {
                    address.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.address_white,
                        0,
                        0,
                        0
                    )
                    link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.planet_white, 0, 0, 0)
                } else {
                    address.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.address_white,
                        0
                    )
                    link.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.planet_white, 0)
                }
            } else {
                //Light
                mode.thumbTintList = ColorStateList.valueOf(Color.WHITE)
                mode.trackTintList = ColorStateList.valueOf(Color.BLACK)
                background.setImageResource(R.drawable.android)
                googleBack.setImageResource(R.drawable.google)
                layout.setBackgroundResource(R.color.white)
                translate.setTextColor(ContextCompat.getColor(this, R.color.black))
                val isEnglishNow = resources.configuration.locales[0].language == "en"
                if (isEnglishNow) {
                    address.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.address,
                        0,
                        0,
                        0
                    )
                    link.setCompoundDrawablesWithIntrinsicBounds(R.drawable.planet, 0, 0, 0)
                } else {
                    address.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.address,
                        0
                    )
                    link.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.planet, 0)
                }

            }

        }
        val logo=findViewById<ImageView>(R.id.iv_google)
        logo.setOnClickListener {
         startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Google")))
        }



    }
}