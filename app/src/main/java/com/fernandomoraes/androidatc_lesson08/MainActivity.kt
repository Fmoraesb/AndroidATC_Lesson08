package com.fernandomoraes.androidatc_lesson08

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var progressBarStatus = 0
    var rate = 0
    val calendario = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btDownload.setOnClickListener {
            Thread(Runnable {
                while (progressBarStatus < 100) {
                    try {
                        rate += 5
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    progressBarStatus = rate
                    progressBar.progress = progressBarStatus
                }
                progressBar.setVisibility(ProgressBar.INVISIBLE)
            }).start()
        }
    }

    //--------------------------------------------------------//

    fun save(view: View) {
        val saveAlert = AlertDialog.Builder(this)
        saveAlert.setTitle("Save")
        saveAlert.setMessage("Are you sure you want to save changes?")
        saveAlert.setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
        saveAlert.setNegativeButton("No") { dialog: DialogInterface?, which: Int ->
            Toast.makeText(this, "Changes Discarded", Toast.LENGTH_SHORT).show()
        }
        saveAlert.setNeutralButton("Remind me Later") { dialog: DialogInterface?, which: Int ->
            Toast.makeText(this, "Remind me Later", Toast.LENGTH_SHORT).show()
        }
        saveAlert.show()
    }

    //--------------------------------------------------------//

    fun setData(view: View) {
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        val mes = calendario.get(Calendar.MONTH)
        val ano = calendario.get(Calendar.YEAR)

        val datePD = DatePickerDialog(this,android.R.style.ThemeOverlay,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                lblData.text = "$dayOfMonth/${month + 1}/$year"}, dia, mes, ano)
        datePD.show()
    }

    //--------------------------------------------------------//

    fun setTime(view: View) {
        val hora = calendario.get(Calendar.HOUR_OF_DAY)
        val minutos = calendario.get(Calendar.MINUTE)
        val timePD = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            lblTime.text = "$hourOfDay : $minute" },hora, minutos,true)
        timePD.show()
    }

    //--------------------------------------------------------//

    fun msgSnack(view: View) {
        var snack: Snackbar = Snackbar.make(findViewById(R.id.constraintLayout),
            "No Internet Connection!",Snackbar.LENGTH_SHORT).setAction("Retry") { }
        snack.setActionTextColor(Color.RED)
        snack.show()
    }
}