package com.hbotonds.coin_chaser.ui.dialog;

import com.almasb.fxgl.ui.DialogFactoryService;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.val;

import javax.naming.OperationNotSupportedException;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class CustomDialogFactoryService extends DialogFactoryService {
    private final Exception notYetImplementedException = new OperationNotSupportedException("Not yet implemented");

    @Override
    public Pane messageDialog(String message, Runnable callback) {
        var text = getUIFactoryService().newText(message, 40);
        var btnOK = getUIFactoryService().newButton("OK");
        btnOK.setOnAction(event -> callback.run());

        var vbox = new VBox(50.0, text, btnOK);
        vbox.setAlignment(Pos.CENTER);

        return wrap(vbox);
    }

    @Override
    public Pane messageDialog(String message) {
        return messageDialog(message, () -> {});
    }

    @Override
    public Pane confirmationDialog(String message, Consumer<Boolean> callback) {
        var text = getUIFactoryService().newText(message, 40);
        var btnYes = getUIFactoryService().newButton("Yes");
        btnYes.setOnAction(event -> callback.accept(true));
        var btnNo = getUIFactoryService().newButton("No");
        btnNo.setOnAction(event -> callback.accept(false));
        var hbox = new HBox(btnYes, btnNo);
        hbox.setAlignment(Pos.CENTER);

        var vbox = new VBox(50.0, text, hbox);
        vbox.setAlignment(Pos.CENTER);

        return wrap(vbox);
    }

    private Pane wrap(Node n) {
        var wrapper = new StackPane(n);
        wrapper.setMinWidth(600.0);
        wrapper.setPadding(new Insets(20.0));
        wrapper.getStyleClass().add("dialog-border");

        return wrapper;
    }

    @Override
    public <T> Pane choiceDialog(String message, Consumer<T> resultCallback, T firstOption, T... options) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pane inputDialog(String message, Consumer<String> callback) {
        return inputDialog(message, s -> true, callback);
    }

    @Override
    public Pane inputDialog(String message, Predicate<String> filter, Consumer<String> callback) {
        var text = getUIFactoryService().newText(message, 40);

        var field = new TextField();
        field.setMaxWidth(Math.max(text.getLayoutBounds().getWidth(), 200));
        field.setFont(getUIFactoryService().newFont(20));
        field.focusedProperty().addListener((v1, v2, isFocused) -> {
            if (!isFocused && field.getScene() != null) {
                field.requestFocus();
            }
        });
        field.sceneProperty().addListener((v1, v2, scene) -> {
            if (scene != null) {
                field.requestFocus();
            }
        });
        field.setOnAction(e -> {
            var newInput = field.getText();

            if (newInput.isEmpty() || !filter.test(newInput)) {
                return;
            }

            callback.accept(newInput);
        });

        var btnOK = getUIFactoryService().newButton("OK");
        btnOK.setDisable(true);
        btnOK.setOnAction(e -> callback.accept(field.getText()));

        field.textProperty().addListener((v1, v2, newInput) -> {
            btnOK.setDisable(newInput.isEmpty() || !filter.test(newInput));
        });

        var vbox = new VBox(50, text, field, btnOK);
        vbox.setAlignment(Pos.CENTER);

        return wrap(vbox);
    }

    @Override
    public Pane inputDialogWithCancel(String message, Predicate<String> filter, Consumer<String> callback) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pane errorDialog(Throwable error, Runnable callback) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pane errorDialog(Throwable error) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pane errorDialog(String errorMessage) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pane errorDialog(String errorMessage, Runnable callback) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pane progressDialog(String message, ReadOnlyDoubleProperty observable, Runnable callback) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pane progressDialogIndeterminate(String message, Runnable callback) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pane customDialog(String message, Node content, Runnable callback, Button... buttons) {
        try {
            throw notYetImplementedException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
