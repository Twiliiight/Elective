package com.example.finalproject

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.chart.BarChart
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.*
import javafx.scene.control.cell.ComboBoxTableCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.io.File
import java.text.NumberFormat
import java.util.*

class HelloController {

    @FXML lateinit var Logo: ImageView
    @FXML lateinit var Semester: ComboBox<String>
    @FXML lateinit var Course: ComboBox<String>
    @FXML lateinit var YrLvl: ComboBox<String>
    @FXML lateinit var Major: ComboBox<String>
    @FXML lateinit var Scholarship: ComboBox<String>

    @FXML lateinit var TotalUnits: TextField
    @FXML lateinit var Tuition: TextField
    @FXML lateinit var Discount: TextField
    @FXML lateinit var Percent: TextField

    @FXML lateinit var StudentId: TextField
    @FXML lateinit var Lname: TextField
    @FXML lateinit var Fname: TextField
    @FXML lateinit var Mname: TextField
    @FXML lateinit var ExtName: TextField
    @FXML lateinit var SchoolYear: TextField

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

    @FXML lateinit var ShowEnrolleesButton: Button

    private val COST_PER_UNIT = 1088.96
    private val peso = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
    private var activeSubjects: Map<String, List<String>> = emptyMap()

    private val FirstYear_FirstSem = mapOf(
        "CP 110" to listOf("Computer Programming 1 (LEC)", "7:30-8:15", "MWF", "3B", "3"),
        "CP 110L" to listOf("Computer Programming 1(LAB)", "8:15-9:00", "MWF", "201", "1"),
        "Math 111" to listOf("Mathematics in Modern World", "9:00-9:45", "MWF", "3B", "3"),
        "Engl 111" to listOf("Purposive Communication", "9:45-10:30", "MWF", "3B", "3"),
        "DCA 110" to listOf("DC & AC Circuits Analysis (LEC)", "7:30-8:15", "TTHS", "3B", "3"),
        "DCA 110L" to listOf("DC & AC Circuits Analysis (LAB)", "8:15-9:00", "TTHS", "3B", "1"),
        "IT 111" to listOf("Introduction to Computing", "9:00-9:45", "TTHS", "3B", "3"),
        "Fil 111" to listOf("Sining ng Komunikasyon", "9:45-10:30", "TTHS", "3B", "3"),
        "PE 1" to listOf("Physical Fitness", "10:30-12:00", "F", "3B", "2"),
        "NSTP 1" to listOf("National Service Training Program 1", "7:30-9:45", "SU", "3A", "3")
    )
    private val FirstYear_SecondSem = mapOf(
        "Math 122" to listOf("Applied Industrial Mathematics", "7:30-8:15", "MWF", "303", "3"),
        "MST 121" to listOf("Living In IT Era", "8:15-9:00", "MWF", "303", "3"),
        "CP 121" to listOf("Computer Programming 2(LEC)", "9:00-9:45", "MWF", "303", "3"),
        "CP 121L" to listOf("Computer Programming 2(LAB)", "9:45-10:30", "MWF", "201", "1"),
        "CT 121" to listOf("Basic Troubleshooting Techniques", "7:30-8:15", "TTHS", "201", "1"),
        "TCW 121" to listOf("The Contemporary World", "8:15-9:00", "TTHS", "303", "3"),
        "Fil 122" to listOf("Panitikang Pilipino", "9:00-9:45", "TTHS", "303", "3"),
        "IT 121" to listOf("Digital Logic Design", "9:45-10:30", "TTHS", "201", "3"),
        "PE 2" to listOf("Rhythmic Activities", "10:30-12:00", "S", "303", "2"),
        "NSTP 2" to listOf("National Service Training Program 2", "7:30-9:45", "SU", "303", "2")
    )
    private val SecondYear_FirstSem = mapOf(
        "DSA 210" to listOf("Data Structure & Algorithms (LEC)", "7:30-8:30", "MWF", "403", "3"),
        "DSA 210L" to listOf("Data Structure & Algorithms (LAB)", "8:30-9:30", "MWF", "201", "1"),
        "IS 211" to listOf("Information Management", "9:30-10:30", "MWF", "303", "3"),
        "Stat 211" to listOf("Probability & Statistics", "10:30-11:30", "MWF", "303", "3"),
        "PE 3" to listOf("Individual/Dual Sports", "1:30-3:30", "T", "WFA-3", "2"),
        "CT 212" to listOf("Computer Troubleshooting Techniques", "7:30-8:30", "TTHS", "403", "1"),
        "Hum 211" to listOf("Arts Appreciation", "8:30-9:30", "TTHS", "403", "3"),
        "DM 211" to listOf("Discrete Mathematics", "9:30-10:30", "TTHS", "403", "3"),
        "UTS 211" to listOf("Understanding The Self", "10:30-11:30", "TTHS", "403", "3")
    )
    private val ThirdYear_FirstSem = mapOf(
        "IT 311" to listOf("System Integration & Architecture (LEC)", "1:30-2:30", "MWF", "201", "3"),
        "IT 311L" to listOf("System Integration & Architecture (LAB)", "2:30-3:30", "MWF", "201", "1"),
        "IT 312" to listOf("Quantitative Methods", "3:30-4:30", "MWF", "201", "3"),
        "IT 313" to listOf("Platform Technologies", "4:30-5:30", "MWF", "402", "3"),
        "Sacl 314" to listOf("Philippine Indigenous Communities", "3:30-4:30", "TTHS", "303", "3"),
        "IT 310" to listOf("Elective 1 - Mobile Programming (LEC)", "4:30-5:30", "TTHS", "203", "3"),
        "IT 310L" to listOf("Elective 1 - Mobile Programming (LAB)", "5:30-6:30", "TTHS", "201", "1"),
        "IT 314" to listOf("Intro to Human-Computer Interaction", "6:30-7:30", "TTHS", "201", "3")
    )

    @FXML
    fun initialize() {
        Logo.image = Image(javaClass.getResource("/images/logo.png")!!.toExternalForm())

        TotalUnits.isEditable = false
        Tuition.isEditable = false
        Discount.isEditable = false
        Percent.isEditable = false

        listOf(cb1, cb2, cb3, cb4).forEach { c ->
            c.setOnAction {
                if (c.isSelected) listOf(cb1, cb2, cb3, cb4).filter { it != c }.forEach { it.isSelected = false }
            }
        }

        Scholarship.items.setAll("ACADEMIC SCHOLARSHIP", "ATHLETE SCHOLARSHIP")
        Scholarship.valueProperty().addListener { _, _, v ->
            Percent.text = when (v) {
                "ACADEMIC SCHOLARSHIP" -> "30%"
                "ATHLETE SCHOLARSHIP" -> "100%"
                else -> "0%"
            }
            computeFees()
        }

        Semester.items.setAll("1ST Semester", "2ND Semester", "Summer")
        YrLvl.items.setAll("1st", "2nd", "3rd", "4th")

        Course.items.setAll(
            "Bachelor of Science in Information Technology (INFO. TECH)",
            "Bachelor of Science in Industrial Technology (IND. TECH)",
            "Bachelor of Science in Industrial Engineering",
            "Bachelor of Science in Computer Engineering",
            "Bachelor of Science in Industrial Education"
        )

        Major.items.setAll("ELECTRONICS", "ELECTRICITY")
        Major.isDisable = true

        Course.valueProperty().addListener { _, _, c ->
            if (c == "Bachelor of Science in Industrial Technology (IND. TECH)") {
                Major.isDisable = false
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

        Semester.valueProperty().addListener { _, _, _ -> updateActiveSubjects() }
        YrLvl.valueProperty().addListener { _, _, _ -> updateActiveSubjects() }

        updateActiveSubjects()

        loadLastEnrollmentFromFile()
        updateTotalUnits()
    }

    private fun updateActiveSubjects() {
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

        SubTable.isEditable = activeSubjects.isNotEmpty()
        ClassCode.isEditable = activeSubjects.isNotEmpty()
    }

    @FXML fun onAddRowClick() {
        if (activeSubjects.isEmpty()) return
        for ((code, data) in activeSubjects) {
            if (SubTable.items.none { it.classCodeProperty.get() == code }) {
                SubTable.items.add(Subject(code, data[0], data[1], data[2], data[3], data[4]))
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
        TotalUnits.text = SubTable.items.sumOf {
            it.unitsProperty.get().toIntOrNull() ?: 0
        }.toString()
        computeFees()
    }

    private fun computeFees() {
        val units = TotalUnits.text.toIntOrNull() ?: 0
        val percent = Percent.text.replace("%", "").toDoubleOrNull()?.div(100) ?: 0.0
        val gross = units * COST_PER_UNIT
        val discount = gross * percent
        val net = gross - discount

        Discount.text = peso.format(discount)
        Tuition.text = peso.format(net)
    }

    private fun studentType() = when {
        cb1.isSelected -> "NEW"
        cb2.isSelected -> "OLD"
        cb3.isSelected -> "TRANSFEREE"
        cb4.isSelected -> "CROSS ENROLLEE"
        else -> "NONE"
    }

    @FXML
    fun onSaveClick() {
        val file = File("Enrolled.txt")
        if (!file.exists()) file.createNewFile()

        file.appendText("ID= ${StudentId.text}\n")
        file.appendText("NAME= ${Lname.text}, ${Fname.text}, ${Mname.text}, ${ExtName.text}\n")
        file.appendText("COURSE= ${Course.value}\n")
        file.appendText("MAJOR= ${Major.value ?: "NO MAJOR"}\n")
        file.appendText("YEAR= ${YrLvl.value}\n")
        file.appendText("SEMESTER= ${Semester.value}\n")
        file.appendText("TYPE= ${studentType()}\n")
        file.appendText("SCHOLARSHIP= ${Scholarship.value}\n")
        file.appendText("PERCENT= ${Percent.text}\n")
        file.appendText("TOTAL UNITS= ${TotalUnits.text}\n")
        file.appendText("DISCOUNT= ${Discount.text}\n")
        file.appendText("TUITION FEE= ${Tuition.text}\n")
        file.appendText("SUBJECTS_ENROLLED\n")
        SubTable.items.forEach {
            file.appendText("${it.classCodeProperty.get()}, ${it.descriptiveTitleProperty.get()}, ${it.timeProperty.get()}, ${it.daysProperty.get()}, ${it.roomProperty.get()}, ${it.unitsProperty.get()}\n")
        }
        file.appendText("----\n")

        Alert(Alert.AlertType.INFORMATION, "Enrollment saved successfully").showAndWait()
    }

    private fun loadLastEnrollmentFromFile() {
        val file = File("Enrolled.txt")
        if (!file.exists()) return

        val blocks = mutableListOf<MutableList<String>>()
        var current: MutableList<String>? = null

        file.forEachLine {
            if (it == "---------") {
                current?.let { blocks.add(it) }
                current = null
            } else {
                if (current == null) current = mutableListOf()
                current!!.add(it)
            }
        }

        if (blocks.isEmpty()) return
        val last = blocks.last()

        SubTable.items.clear()

        last.forEach {
            when {
                it.startsWith("ID= ") -> StudentId.text = it.substringAfter("= ")
                it.startsWith("SCHOLARSHIP= ") -> Scholarship.value = it.substringAfter("= ")
                it.startsWith("PERCENT= ") -> Percent.text = it.substringAfter("= ")
                it.startsWith("DISCOUNT= ") -> Discount.text = it.substringAfter("= ")
                it.startsWith("TUITION= ") -> Tuition.text = it.substringAfter("= ")
                it.contains(", ") && !it.startsWith("NAME= ") ->
                    it.split(", ").takeIf { p -> p.size == 6 }?.let { p ->
                        SubTable.items.add(Subject(p[0], p[1], p[2], p[3], p[4], p[5]))
                    }
            }
        }
        updateTotalUnits()
    }

    @FXML
    fun onShowEnrolleesClick() {
        val file = File("Enrolled.txt")
        if (!file.exists()) {
            Alert(Alert.AlertType.WARNING, "No enrollment data found.").showAndWait()
            return
        }

        val courseCounts = mutableMapOf<String, Int>()
        val blocks = mutableListOf<MutableList<String>>()
        var current: MutableList<String>? = null

        try {
            file.forEachLine {
                if (it == "----") {
                    current?.let { blocks.add(it) }
                    current = null
                } else {
                    if (current == null) current = mutableListOf()
                    current!!.add(it)
                }
            }

            blocks.forEach { block ->
                var course: String? = null
                block.forEach { line ->
                    if (line.startsWith("COURSE= ")) {
                        course = line.substringAfter("= ")
                    }
                }
                course?.let { courseCounts[it] = courseCounts.getOrDefault(it, 0) + 1 }
            }

            if (courseCounts.isNotEmpty()) {
                val xAxis = CategoryAxis()
                val yAxis = NumberAxis()
                val barChart = BarChart<String, Number>(xAxis, yAxis)
                barChart.title = "Total Enrollees per Course: "

                val series = XYChart.Series<String, Number>()
                series.name = "Enrollees"
                courseCounts.forEach { (course, count) ->
                    series.data.add(XYChart.Data(course, count))
                }
                barChart.data.add(series)
                barChart.applyCss()
                barChart.lookupAll(".chart-bar").forEach {
                    it.style = "-fx-bar-fill: #750000;"
                }

                val scene = Scene(StackPane(barChart), 800.0, 600.0)
                val stage = Stage()
                stage.title = "Enrollees per Course"
                stage.scene = scene
                stage.show()
            } else {
                Alert(Alert.AlertType.INFORMATION, "No enrollment data to display.").showAndWait()
            }
        } catch (e: Exception) {
            Alert(Alert.AlertType.ERROR, "Error reading file: ${e.message}").showAndWait()
        }
    }
}
