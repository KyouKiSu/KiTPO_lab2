package sfxml

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

import java.io.IOException

/**
 * Example of using FXMLLoader from ScalaFX.
 *
 * @author Jarek Sacha
 */
object App extends JFXApp3 {
  override def start(): Unit = {

    val resource = getClass.getResource("AppForm.fxml")
    if (resource == null) {
      throw new IOException("Cannot load resource: fxml")
    }

    val root = FXMLView(resource, NoDependencyResolver)

    stage = new JFXApp3.PrimaryStage() {
      title = "Lab 2. Scala + ScalaFX"
      scene = new Scene(root)
    }
  }
}
