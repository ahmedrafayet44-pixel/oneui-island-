package com.rafayet.oneuiisland.island
import com.rafayet.oneuiisland.music.EqualizerView
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
import android.graphics.Bitmap
import android.widget.LinearLayout
import android.widget.ImageView
class IslandService : Service() {

    private lateinit var windowManager: WindowManager
    private var islandView: LinearLayout? = null
private var albumArtView: ImageView? = null
private var equalizerView: EqualizerView? = null
private val musicReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val art = if (android.os.Build.VERSION.SDK_INT >= 33) {
            intent?.getParcelableExtra("art", Bitmap::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra("art") as? Bitmap
        }

        albumArtView?.setImageBitmap(art)
        val hasPlaybackState = intent?.hasExtra("isPlaying") == true
val isPlaying = intent?.getBooleanExtra("isPlaying", false) ?: false
if (!hasPlaybackState) return
if (isPlaying) {
    equalizerView?.visibility = android.view.View.VISIBLE
    equalizerView?.startAnimation()
} else {
    equalizerView?.stopAnimation()
    equalizerView?.visibility = android.view.View.GONE
    albumArtView?.setImageDrawable(null)
}
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
    val artView = ImageView(this).apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
        background = GradientDrawable().apply {
            setColor(Color.DKGRAY)
            cornerRadius = 50f
        }
    }

    albumArtView = artView

val equalizer = EqualizerView(this)
equalizerView = equalizer
equalizer.visibility = android.view.View.GONE

    val layout = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        background = GradientDrawable().apply {
            setColor(Color.BLACK)
            cornerRadius = 100f
        }
        setPadding(8, 6, 12, 6)

        addView(artView, LinearLayout.LayoutParams(36, 36))
        addView(equalizer, LinearLayout.LayoutParams(65, 36))
    }

    val params = WindowManager.LayoutParams(
        125,
        48,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )

    params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    params.y = 12

    windowManager.addView(layout, params)
    islandView = layout


        
    }

    override fun onDestroy() {
    unregisterReceiver(musicReceiver)

    islandView?.let {
        windowManager.removeView(it)
    }

    albumArtView = null
    islandView = null

    super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
