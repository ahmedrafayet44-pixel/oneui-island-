package com.rafayet.oneuiisland.music

import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.session.MediaController
import android.service.notification.NotificationListenerService

class MusicNotificationListener : NotificationListenerService() {

    private val controllers = mutableListOf<MediaController>()

    override fun onListenerConnected() {
        super.onListenerConnected()
        refreshMusicSessions()
    }

    override fun onNotificationPosted(
        sbn: android.service.notification.StatusBarNotification?
    ) {
        refreshMusicSessions()
    }

    private fun refreshMusicSessions() {
        try {
            val manager = getSystemService(MEDIA_SESSION_SERVICE)
                as android.media.session.MediaSessionManager

            val sessions = manager.getActiveSessions(
                ComponentName(this, MusicNotificationListener::class.java)
            )

            controllers.forEach {
                it.unregisterCallback(callback)
            }
            controllers.clear()

            sessions.forEach { controller ->
                controllers.add(controller)
                controller.registerCallback(callback)
                sendMusicInfo(controller.metadata)
            }
        } catch (_: SecurityException) {
        }
    }

    private val callback = object : MediaController.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadata?) {
            sendMusicInfo(metadata)
        }
    }

    private fun sendMusicInfo(metadata: MediaMetadata?) {
    val title = metadata?.getString(MediaMetadata.METADATA_KEY_TITLE)
        ?: return

    val artist = metadata.getString(MediaMetadata.METADATA_KEY_ARTIST)
        ?: "Unknown Artist"

    val art = metadata.getBitmap(MediaMetadata.METADATA_KEY_ART)
        ?: metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)

    val intent = Intent("com.rafayet.oneuiisland.MUSIC_UPDATE")
    intent.setPackage(packageName)
    intent.putExtra("title", title)
    intent.putExtra("artist", artist)
    intent.putExtra("art", art)
    sendBroadcast(intent)
    
    }

    override fun onListenerDisconnected() {
        controllers.forEach {
            it.unregisterCallback(callback)
        }
        controllers.clear()
        super.onListenerDisconnected()
    }
}
