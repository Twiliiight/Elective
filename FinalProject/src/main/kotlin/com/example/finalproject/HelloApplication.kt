package com.example.finalproject

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 1173.0, 661.0)

        val icon = Image(HelloApplication::class.java.getResource("/images/icon.png")!!.toExternalForm())
        stage.icons.add(icon)

        stage.title = "BCT Enrollment Form"
        stage.scene = scene
        stage.show()
    }
}
