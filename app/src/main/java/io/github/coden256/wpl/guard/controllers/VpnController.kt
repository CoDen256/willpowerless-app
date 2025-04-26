package io.github.coden256.wpl.guard.controllers

import android.content.Context
import android.util.Log
import io.github.coden256.wpl.guard.core.Owner.Companion.asOwner
import io.github.coden256.wpl.judge.Action
import io.github.coden256.wpl.judge.JudgeRuling

class VpnController(private val context: Context) {
    fun onRulings(rulings: List<JudgeRuling>){
        val target = rulings.firstOrNull {it.action == Action.FORCE}
        Log.i("GuardVpnController", "Setting vpn by: $target out of $rulings")
        asOwner(context){
            target?.let { setAlwaysOnVpn(it.path) } ?: removeAlwaysOnVpn()
        }
    }
}