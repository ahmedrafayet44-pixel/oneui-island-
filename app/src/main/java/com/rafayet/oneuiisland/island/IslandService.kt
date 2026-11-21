package com.rafayet.oneuiisland.island

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.view.LayoutInflater
import android.widget.TextView
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable

class IslandService : Service() {

    private lateinit var windowManager: WindowManager
    private var islandView: TextView? = null

    override fun onCreate() {
        super.onCreate()

        windowManager =
            getSystemService(WINDOW_SERVICE) as WindowManager

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
        super.onDestroy()

        islandView?.let {
            windowManager.removeView(it)
        }

        islandView = null
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
