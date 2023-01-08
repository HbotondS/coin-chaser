package com.hbotonds.coin_chaser

import com.almasb.fxgl.input.UserAction

class MyInputActionKt: UserAction {
    private var onAction: Runnable? = null
    private var onActionEnd: Runnable? = null

    constructor() : super ("")

    constructor(builder: Builder) : super(builder.name) {
        onAction = builder.onAction
        onActionEnd = builder.onActionEnd
    }

    override fun onAction() {
        this.onAction?.run()
    }

    override fun onActionEnd() {
        this.onActionEnd?.run()
    }

    class Builder(val name: String) {
        var onAction: Runnable? = null
        var onActionEnd: Runnable? = null

        fun setOnAction(func: Runnable?): Builder {
            onAction = func
            return this
        }

        fun setOnActionEnd(func: Runnable?): Builder {
            onActionEnd = func
            return this
        }

        fun build(): MyInputActionKt {
            return MyInputActionKt(this)
        }
    }
}