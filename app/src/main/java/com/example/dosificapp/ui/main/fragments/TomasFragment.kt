package com.example.dosificapp.ui.main.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.dosificapp.AlarmReceiver
import com.example.dosificapp.CalendarTab
import com.example.dosificapp.R
import com.example.dosificapp.data.DosisRepository
import com.example.dosificapp.data.LoginRepository
import com.example.dosificapp.databinding.FragmentTomasBinding
import com.example.dosificapp.service.UserService
import com.example.dosificapp.ui.main.adapters.DosisListAdapter
import kotlinx.coroutines.launch
import java.util.UUID

class TomasFragment : AbstractFragment() {

    private lateinit var binding: FragmentTomasBinding
    private val userService = UserService()
    private val dosisRepository = DosisRepository.getInstance()
    private val loginRepository = LoginRepository.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentTomasBinding.inflate(inflater, container, false)

        binding.buttonCrono.setOnClickListener {
            val intent = Intent(context, CalendarTab::class.java)
            startActivity(intent)
        }

        binding.pullToRefresh.setOnRefreshListener {
            fetchDoses()
        }

        fetchDoses()

        return binding.root
    }

    private fun fetchDoses() {
        lifecycleScope.launch {
            try {
                val userId = loginRepository.loggedInUser?.id ?: return@launch
                val doses = userService.getDoses(userId)
                dosisRepository.setDoses(doses)
                val adapter = DosisListAdapter(requireContext(), R.layout.listview_toma, doses)
                binding.listCrono.adapter = adapter
                binding.pullToRefresh.isRefreshing = false
                setAlarms()
            } catch (e: Exception) {
                Log.d("ERROR", e.message!!)
                Toast.makeText(context, "Error al recuperar recetas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarms() {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        dosisRepository.getDosisVigentes().forEach { dosis ->
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                action = dosis.getDoseTakeid().toString()
            }
            val pendingIntent = PendingIntent.getBroadcast(context, UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager?.setExact(AlarmManager.RTC_WAKEUP, dosis.getCalendar().timeInMillis, pendingIntent)
        }
    }

    override fun getName(): String = "Proximas tomas"

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(index: Int) = TomasFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
            }
        }
    }
}