package com.rafayet.oneuiisland.island

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class IslandService : Service() {

    private lateinit var windowManager: WindowManager
    private var islandView: TextView? = null

    private val musicReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val title = intent?.getStringExtra("title") ?: return
            val artist = intent.getStringExtra("artist") ?: "Unknown Artist"

            islandView?.text = "♫  $title\n$artist"
        }
    }

    override fun onCreate() {
        super.onCreate()

        windowManager =
            getSystemService(WINDOW_SERVICE) as WindowManager

        val filter = IntentFilter("com.rafayet.oneuiisland.MUSIC_UPDATE")
        registerReceiver(
            musicReceiver,
            filter,
            Context.RECEIVER_NOT_EXPORTED
        )

        showIsland()
    }

    private fun showIsland() {
        val view = TextView(this).apply {
            text = "●  OneUI Island"
            textSize = 13f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER

            background = GradientDrawable().apply {
                setColor(Color.BLACK)
                cornerRadius = 100f
            }

            setPadding(35, 20, 35, 20)
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.y = 12

        windowManager.addView(view, params)
        islandView = view
    }

    override fun onDestroy() {
        unregisterReceiver(musicReceiver)

        islandView?.let {
            windowManager.removeView(it)
        }

        islandView = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
