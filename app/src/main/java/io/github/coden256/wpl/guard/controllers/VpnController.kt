package io.github.coden256.wpl.guard.controllers

import android.content.Context
import io.github.coden256.wpl.guard.core.Owner.Companion.asOwner
import io.github.coden256.wpl.judge.Action
import io.github.coden256.wpl.judge.JudgeRuling

class VpnController(private val context: Context) {
    fun onRulings(rulings: List<JudgeRuling>){
        asOwner(context){
            rulings.firstOrNull {it.action == Action.FORCE}?.let {
                setAlwaysOnVpn(it.path)
                removeAlwaysOnVpn()
            } ?: removeAlwaysOnVpn()
        }
    }
}