package com.example.finalproject

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.ComboBoxTableCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.File

class HelloController {

    @FXML lateinit var Logo: ImageView
    @FXML lateinit var Semester: ComboBox<String>
    @FXML lateinit var Course: ComboBox<String>
    @FXML lateinit var YrLvl: ComboBox<String>
    @FXML lateinit var Major: ComboBox<String>
    @FXML lateinit var TotalUnits: TextField

    @FXML private lateinit var SubTable: TableView<Subject>
    @FXML private lateinit var ClassCode: TableColumn<Subject, String>
    @FXML private lateinit var Desc: TableColumn<Subject, String>
    @FXML private lateinit var Time: TableColumn<Subject, String>
    @FXML private lateinit var Days: TableColumn<Subject, String>
    @FXML private lateinit var Room: TableColumn<Subject, String>
    @FXML private lateinit var Units: TableColumn<Subject, String>

    @FXML lateinit var cb1: CheckBox
    @FXML lateinit var cb2: CheckBox
    @FXML lateinit var cb3: CheckBox
    @FXML lateinit var cb4: CheckBox

    private var activeSubjects: Map<String, List<String>> = emptyMap()

    private val FirstYear_FirstSem = mapOf(
        "ZA001" to listOf("Computer Programming 1 (LEC)", "7:30-8:15", "MWF", "3B", "3"),
        "ZA001L" to listOf("Computer Programming 1(LAB)", "8:15-9:00", "MWF", "201", "1"),
        "ZA002" to listOf("Mathematics in Modern World", "9:00-9:45", "MWF", "3B", "3"),
        "ZA003" to listOf("Purposive Communication", "9:45-10:30", "MWF", "3B", "3"),
        "ZA004" to listOf("DC & AC Circuits Analysis (LEC)", "7:30-8:15", "TTHS", "3B", "3"),
        "ZA004L" to listOf("DC & AC Circuits Analysis (LAB)", "8:15-9:00", "TTHS", "3B", "1"),
        "ZA005" to listOf("Introduction to Computing", "9:00-9:45", "TTHS", "3B", "3"),
        "ZA006" to listOf("Sining ng Komunikasyon", "9:45-10:30", "TTHS", "3B", "3"),
        "ZAPE1" to listOf("Physical Fitness", "10:30-12:00", "F", "3B", "2"),
        "ZANSTP1" to listOf("National Service Training Program 1", "7:30-9:45", "SU", "3A", "3")
    )

    private val FirstYear_SecondSem = mapOf(
        "ZB006" to listOf("Applied Industrial Mathematics", "7:30-8:15", "MWF", "303", "3"),
        "ZB007" to listOf("Living In IT Era", "8:15-9:00", "MWF", "303", "3"),
        "ZB008" to listOf("Computer Programming 2(LEC)", "9:00-9:45", "MWF", "303", "3"),
        "ZB008L" to listOf("Computer Programming 2(LAB)", "9:45-10:30", "MWF", "201", "1"),
        "ZB009" to listOf("Basic Troubleshooting Techniques", "7:30-8:15", "TTHS", "201", "1"),
        "ZB010" to listOf("The Contemporary World", "8:15-9:00", "TTHS", "303", "3"),
        "ZB011" to listOf("Panitikang Pilipino", "9:00-9:45", "TTHS", "303", "3"),
        "ZB012" to listOf("Digital Logic Design", "9:45-10:30", "TTHS", "201", "3"),
        "ZBPE2" to listOf("Rhythmic Activities", "10:30-12:00", "S", "303", "2"),
        "ZBNSTP2" to listOf("National Service Training Program 2", "7:30-9:45", "SU", "303", "2")
    )

    private val SecondYear_FirstSem = mapOf(
        "ZD007" to listOf("Data Structure & Algorithms (LEC)", "7:30-8:30", "MWF", "403", "3"),
        "ZD007L" to listOf("Data Structure & Algorithms (LAB)", "8:30-9:30", "MWF", "201", "1"),
        "ZD008" to listOf("Information Management", "9:30-10:30", "MWF", "303", "3"),
        "ZD009" to listOf("Probability & Statistics", "10:30-11:30", "MWF", "303", "3"),
        "ZDPE3" to listOf("Individual/Dual Sports", "1:30-3:30", "T", "WFA-3", "2"),
        "ZD010" to listOf("Computer Troubleshooting Techniques", "7:30-8:30", "TTHS", "403", "1"),
        "ZD011" to listOf("Arts Appreciation", "8:30-9:30", "TTHS", "403", "3"),
        "ZD012" to listOf("Discrete Mathematics", "9:30-10:30", "TTHS", "403", "3"),
        "ZD013" to listOf("Understanding The Self", "10:30-11:30", "TTHS", "403", "3")
    )

    private val ThirdYear_FirstSem = mapOf(
        "ZF027" to listOf("System Integration & Architecture (LEC)", "1:30-2:30", "MWF", "201", "3"),
        "ZF027L" to listOf("System Integration & Architecture (LAB)", "2:30-3:30", "MWF", "201", "1"),
        "ZF028" to listOf("Quantitative Methods", "3:30-4:30", "MWF", "201", "3"),
        "ZF029" to listOf("Platform Technologies", "4:30-5:30", "MWF", "402", "3"),
        "ZF030" to listOf("Philippine Indigenous Communities", "3:30-4:30", "TTHS", "303", "3"),
        "ZF031" to listOf("Elective 1 - Mobile Programming (LEC)", "4:30-5:30", "TTHS", "203", "3"),
        "ZF031L" to listOf("Elective 1 - Mobile Programming (LAB)", "5:30-6:30", "TTHS", "201", "1"),
        "ZF032" to listOf("Intro to Human-Computer Interaction", "6:30-7:30", "TTHS", "201", "3")
    )

    @FXML
    fun initialize() {
        Logo.image = Image(javaClass.getResource("/images/logo.png")!!.toExternalForm())
        TotalUnits.isEditable = false

        listOf(cb1, cb2, cb3, cb4).forEach { cb ->
            cb.setOnAction {
                if (cb.isSelected) listOf(cb1, cb2, cb3, cb4).filter { it != cb }.forEach { it.isSelected = false }
            }
        }

        Semester.items.addAll("1ST Semester", "2ND Semester", "Summer")
        YrLvl.items.addAll("1st", "2nd", "3rd", "4th")

        Course.items.addAll(
            "Bachelor of Science in Information Technology (INFO. TECH)",
            "Bachelor of Science in Industrial Technology (IND. TECH)",
            "Bachelor of Science in Industrial Engineering",
            "Bachelor of Science in Computer Engineering",
            "Bachelor of Science in Industrial Education"
        )

        Major.items.setAll("ELECTRONICS", "ELECTRICITY")

        Course.valueProperty().addListener { _, _, course ->
            if (course == "Bachelor of Science in Industrial Technology (IND. TECH)") {
                Major.isDisable = false
                Major.value = null
            } else {
                Major.isDisable = true
                Major.selectionModel.clearSelection()
                Major.value = "NO MAJOR"
            }
        }

        ClassCode.setCellValueFactory { it.value.classCodeProperty }
        Desc.setCellValueFactory { it.value.descriptiveTitleProperty }
        Time.setCellValueFactory { it.value.timeProperty }
        Days.setCellValueFactory { it.value.daysProperty }
        Room.setCellValueFactory { it.value.roomProperty }
        Units.setCellValueFactory { it.value.unitsProperty }

        Semester.valueProperty().addListener { _, _, _ -> refreshSubjects() }
        YrLvl.valueProperty().addListener { _, _, _ -> refreshSubjects() }

        loadFromFile()
    }

    private fun refreshSubjects() {
        SubTable.items.clear()

        activeSubjects = when {
            YrLvl.value == "1st" && Semester.value == "1ST Semester" -> FirstYear_FirstSem
            YrLvl.value == "1st" && Semester.value == "2ND Semester" -> FirstYear_SecondSem
            YrLvl.value == "2nd" && Semester.value == "1ST Semester" -> SecondYear_FirstSem
            YrLvl.value == "3rd" && Semester.value == "1ST Semester" -> ThirdYear_FirstSem
            else -> emptyMap()
        }

        ClassCode.setCellFactory(
            ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(activeSubjects.keys))
        )

        ClassCode.setOnEditCommit { e ->
            val d = activeSubjects[e.newValue] ?: return@setOnEditCommit
            val r = e.rowValue
            r.classCodeProperty.set(e.newValue)
            r.descriptiveTitleProperty.set(d[0])
            r.timeProperty.set(d[1])
            r.daysProperty.set(d[2])
            r.roomProperty.set(d[3])
            r.unitsProperty.set(d[4])
            updateTotalUnits()
        }
    }

    @FXML
    fun onAddRowClick() {
        if (activeSubjects.isEmpty()) return
        for (c in activeSubjects.keys) {
            if (SubTable.items.none { it.classCodeProperty.get() == c }) {
                val d = activeSubjects[c]!!
                SubTable.items.add(Subject(c, d[0], d[1], d[2], d[3], d[4]))
                break
            }
        }
        updateTotalUnits()
    }

    @FXML
    fun onDeleteRowClick() {
        SubTable.selectionModel.selectedItem?.let {
            SubTable.items.remove(it)
            updateTotalUnits()
        }
    }

    private fun updateTotalUnits() {
        val total = SubTable.items.sumOf { it.unitsProperty.get().toInt() }
        TotalUnits.text = total.toString()
    }

    @FXML
    fun onSaveClick() {
        File("Enrolled.txt").printWriter().use { out ->
            out.println(TotalUnits.text)
            SubTable.items.forEach {
                out.println(
                    "${it.classCodeProperty.get()}," +
                            "${it.descriptiveTitleProperty.get()}," +
                            "${it.timeProperty.get()}," +
                            "${it.daysProperty.get()}," +
                            "${it.roomProperty.get()}," +
                            "${it.unitsProperty.get()}"
                )
            }
        }
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Saved"
        alert.headerText = null
        alert.contentText = "Enrollment saved successfully!"
        alert.showAndWait()
    }

    private fun loadFromFile() {
        val file = File("Enrolled.txt")
        if (!file.exists()) return

        val lines = file.readLines()
        if (lines.isEmpty()) return

        TotalUnits.text = lines.first()

        lines.drop(1).forEach {
            val p = it.split(",")
            if (p.size == 6)
                SubTable.items.add(Subject(p[0], p[1], p[2], p[3], p[4], p[5]))
        }
    }
}
