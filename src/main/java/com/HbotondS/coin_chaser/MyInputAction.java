package com.HbotondS.coin_chaser;

import com.almasb.fxgl.input.UserAction;
import org.jetbrains.annotations.NotNull;

public class MyInputAction extends UserAction {
    private Runnable onAction;
    private Runnable onActionEnd;

    // default constructor
    public MyInputAction() {
        super("");
    }

    private MyInputAction(@NotNull Builder builder) {
        super(builder.name);
        this.onAction = builder.onAction;
        this.onActionEnd = builder.onActionEnd;
    }

    @Override
    protected void onAction() {
        if (this.onAction != null) {
            this.onAction.run();
        }
    }

    @Override
    protected void onActionEnd() {
        if (this.onActionEnd != null){
            this.onActionEnd.run();
        }
    }

    public static class Builder {
        private final String name;
        private Runnable onAction;
        private Runnable onActionEnd;

        public Builder(String name) {
            this.name = name;
        }

        public Builder setOnAction(Runnable func) {
            this.onAction = func;
            return this;
        }

        public Builder setOnActionEnd(Runnable func) {
            this.onActionEnd = func;
            return this;
        }

        public MyInputAction build() {
            return new MyInputAction(this);
        }
    }
}
