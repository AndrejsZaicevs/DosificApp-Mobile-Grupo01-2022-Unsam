package com.example.dosificapp

import android.annotation.SuppressLint
import android.content.Context
import android.media.Ringtone
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.dosificapp.data.DosisRepository
import com.example.dosificapp.databinding.ActivityAlertBinding
import com.example.dosificapp.dominio.Dosis
import com.example.dosificapp.service.DosesService
import java.util.Calendar

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.lang.RuntimeException


class AlertActivity : AppCompatActivity() {

    private val dosisRepository = DosisRepository.getInstance()
    private val dosisService = DosesService()
    private lateinit var binding: ActivityAlertBinding

    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        binding = ActivityAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.action?.toLongOrNull()?.let { dosisId ->
            val dosis = dosisRepository.getDosisTomaById(dosisId)

            setupRingtone()
            displayDosisName(dosis?.name)
            setupButtons(dosis)

            lifecycleScope.launch { dosisService.actualizarNotificacionDosis(dosisId) }
        } ?: throw RuntimeException("Could not retrieve dosisId in AlertActivity")
    }

    private fun setupWindow() {
        window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
    }

    private fun setupRingtone() {
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        ringtone?.play()
    }

    private fun displayDosisName(name: String?) {
        binding.medicamento.text = name
    }

    private fun setupButtons(dosis: Dosis) {
        binding.post.setOnClickListener {
            lifecycleScope.launch {
                dosisService.postergarNotificacionDosis(dosis.doseTakeid)
                Toast.makeText(applicationContext, "Toma pospuesta por ${dosis.intervaloPost} minutos", Toast.LENGTH_SHORT).show()
                scheduleAlarm(dosis.intervaloPost, dosis.doseTakeid)
                ringtone?.stop()
                finish()
            }
        }

        binding.tomar.setOnClickListener {
            lifecycleScope.launch {
                dosisService.confirmTomaDosis(dosis.doseTakeid)
                Toast.makeText(applicationContext, "Toma Confirmada", Toast.LENGTH_SHORT).show()
                ringtone?.stop()
                finish()
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleAlarm(minutes: Int, dosisId: Long) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.MINUTE, minutes)
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, AlarmReceiver::class.java).apply {
            action = dosisId.toString()
        }
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}
