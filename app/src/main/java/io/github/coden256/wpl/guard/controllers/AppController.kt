package io.github.coden256.wpl.guard.controllers

import android.content.Context
import android.util.Log
import io.github.coden256.wpl.guard.config.AppConfig
import io.github.coden256.wpl.guard.core.Owner.Companion.asOwner
import io.github.coden256.wpl.judge.Action
import io.github.coden256.wpl.judge.JudgeRuling

class AppController(private val context: Context, private val appConfig: AppConfig) {
    fun onRulings(rulings: List<JudgeRuling>){
        asOwner(context){
            rulings.forEach { (action, path) ->
                if (action == Action.FORCE){
                    Log.i("GuardAppManager", "FORCING: $path")
//                    blockUninstall(path)
                }
                if (action == Action.BLOCK){
                    Log.i("GuardAppManager", "BLOCKING: $path")
//                    hide(path)
                }
            }
        }
    }

    fun enforce(){

    }
}