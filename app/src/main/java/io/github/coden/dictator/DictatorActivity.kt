package io.github.coden.dictator

import android.app.Activity
import android.content.Intent
import android.os.Bundle



class DictatorActivity: Activity() {

    companion object{
        const val REQUEST_RULE_INTENT = "io.github.coden.dictator.REQUEST_RULE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val incomingIntent = intent
        val input = incomingIntent.getStringExtra("key")

        val resultIntent = Intent()
        resultIntent.putExtra("response_key", "response_value")
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}