package sfxml

import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.util.Pair
import org.json.JSONObject
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, ChoiceBox, Label, TextArea, TextField}
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml
import types.{UserFactory, UserType}
import vtree.{VTree, VTreeFactory}

import java.io.IOException
import java.nio.file.{Files, Paths}
import java.util

@sfxml
class AppFormController(val userTypeChoiceBox: ChoiceBox[String],
                        val showTextArea: TextArea,
                        val userTypeFieldGrid: GridPane,
                        val changeUserTypeButton: Button,
                        val filenameField: TextField) {
  private val vTreeFactory: VTreeFactory = new VTreeFactory
  private var tree: VTree = null



    //System.out.println("HE")
    val list: ObservableList[String] = FXCollections.observableArrayList
    UserFactory.types.forEach(_class => {
      list.add(_class.getClassName)
    })

    userTypeChoiceBox.setItems(list)
    userTypeChoiceBox.setValue(list.get(0))



  def initTree(): Unit = {
    setFactoryClasses()
    resetTree()
    refreshTree()
  }

   def resetTree(): Unit = {
    tree = vTreeFactory.createTree
  }

   def refreshTree(): Unit = {
    showTextArea.setText(tree.print)
  }

   def setFactoryClasses(): Unit = {
    val selectedClass: String = userTypeChoiceBox.getValue.asInstanceOf[String]
    vTreeFactory.setType(selectedClass)
  }

   def createElement: UserType = {
    val values: util.ArrayList[String] = new util.ArrayList[String]
    userTypeFieldGrid.getChildren.forEach(element=>{
      //println(element.getClass)
      if (element.getClass.toString().equalsIgnoreCase("class javafx.scene.control.TextField")) {
        //println("GOGT")
        val field: javafx.scene.control.TextField = element.asInstanceOf[javafx.scene.control.TextField]
        values.add(field.getText)
      }
    })

    val builder: UserType = vTreeFactory.getTypeInstance
     //println(values)
    val instance: UserType = builder.create(values)
    return instance
  }

   def insertElement(element: UserType): Unit = {
    tree.Insert(element)
  }

   def rebalanceTree(): Unit = {
    tree.rebalance()
  }

   def removeElement(element: UserType): Unit = {
    tree.Remove(element)
  }


  @FXML
  def onChangeUserTypeButtonClick(): Unit = { // update selected class in factory
    setFactoryClasses()
    // update grid with fields from factory
    val selectedClass: String = userTypeChoiceBox.getValue.asInstanceOf[String]
    val userTypeFieldInfo: util.ArrayList[Pair[String, String]] = UserFactory.getBuilderByName(selectedClass).getFields
    userTypeFieldGrid.getChildren.clear()
    var row: Int = 0

    userTypeFieldInfo.forEach(field=>{
      val label: Label = new Label(field.getKey)
      val text: TextField = new TextField
      text.setPromptText(field.getValue)
      userTypeFieldGrid.add(label, 0, row)
      userTypeFieldGrid.add(text, 1, row)
      row += 1
    })

    initTree()
  }

  @FXML  def onAddButtonClick(): Unit = {
    val element: UserType = createElement
    insertElement(element)
    refreshTree()
  }

  @FXML  def onRebalanceButtonClick(): Unit = {
    rebalanceTree()
    refreshTree()
  }

  @FXML  def onRemoveButtonClick(): Unit = {
    val element: UserType = createElement
    removeElement(element)
    refreshTree()
  }

  @FXML  def onResetButtonClick(): Unit = {
    resetTree()
    refreshTree()
  }

  @FXML  def onRefreshButtonClick(): Unit = {
    refreshTree()
  }

  @FXML  def onShowJSONButtonClick(): Unit = {
    showTextArea.setText(new JSONObject(tree.packValue).toString(4))
  }

  @FXML  def onToJSONButtonClick(): Unit = {
    val packed: String = tree.packValue
    val path: String = filenameField.getText
    try Files.write(Paths.get(path), packed.getBytes)
    catch {
      case e: IOException =>
        throw new RuntimeException(e)
    }
    refreshTree()
  }

  @FXML  def onFromJSONButtonClick(): Unit = {
    val path: String = filenameField.getText
    try {
      val json: String = Files.readString(Paths.get(path))
      tree = vTreeFactory.parseTree(new JSONObject(json))
    } catch {
      case e: IOException =>
        throw new RuntimeException(e)
    }
    refreshTree()
  }
}
