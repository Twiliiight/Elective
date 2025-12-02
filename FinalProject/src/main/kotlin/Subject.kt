package com.example.finalproject

import javafx.beans.property.SimpleStringProperty

class Subject(
    ClassCode: String,
    Description: String,
    Time: String,
    Days: String,
    Room: String,
    Units: String
) {
    val classCodeProperty = SimpleStringProperty(ClassCode)
    val descriptiveTitleProperty = SimpleStringProperty(Description)
    val timeProperty = SimpleStringProperty(Time)
    val daysProperty = SimpleStringProperty(Days)
    val roomProperty = SimpleStringProperty(Room)
    val unitsProperty = SimpleStringProperty(Units)
}