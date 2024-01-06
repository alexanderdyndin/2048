package org.andstatus.game2048

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import korlibs.render.GameWindowCreationConfig
import korlibs.render.KorgwActivity

/** It's inconvenient to customize KorGe-generated [MainActivity],
 * so we replace it with our own "main activity" */
class MyMainActivity : KorgwActivity(config = GameWindowCreationConfig(msaa = 1, fullscreen = null)) {
    private val REQUEST_CODE_OPEN_JSON_GAME = 1
    private var gameRecordConsumer: ((Sequence<String>) -> Unit)? = null
    private var orientation: Int = Configuration.ORIENTATION_UNDEFINED
    private var paused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        myLog("onCreate MainActivity")
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        gameWindow.fullscreen = getSharedPreferences("KorgeNativeStorage", Context.MODE_PRIVATE)
            .getString(keyFullscreen, "true").toBoolean()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (orientation != Configuration.ORIENTATION_UNDEFINED && newConfig.orientation != orientation) {
            recreate()
        }
    }

    override suspend fun activityMain() {
        myLog("activityMain started")
        orientation = resources.configuration.orientation
        main()
        myLog("activityMain ended")
    }

    override fun onPause() {
        paused = true
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (paused) {
            paused = false
            recreate()
        }
    }

    override fun finish() {
        myLog("activityMain finish")
        finishAffinity()
        super.finish()
    }

    fun openJsonGameRecord(consumer: (Sequence<String>) -> Unit) {
        gameRecordConsumer = consumer
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
        }
        startActivityForResult(intent, REQUEST_CODE_OPEN_JSON_GAME)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        myLog("Got result $resultCode on request $requestCode")
        val consumer = gameRecordConsumer
        gameRecordConsumer = null
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_OPEN_JSON_GAME && consumer != null) {
            data?.data?.let { uri ->
                DocumentSequence(this, uri)
                    .let { strValue -> consumer.invoke(strValue) }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
