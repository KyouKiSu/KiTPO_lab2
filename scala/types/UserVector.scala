package types

import javafx.util.Pair
import org.json.{JSONException, JSONObject}

import java.util

object UserVector {
  private[types] val className = "UserVector"
}

class UserVector() extends UserType {
  private var x = .0
  private var y = .0

  def this(_x: Float, _y: Float) {
    this()
    this.x = _x
    this.y = _y
  }

  override def copy =  new UserVector(this.x.asInstanceOf[Float], this.y.asInstanceOf[Float])

  override def create = new UserVector()

  override def create(values: util.ArrayList[String]) = new UserVector(values.get(0).toFloat, values.get(1).toFloat)

  override def getFields = new util.ArrayList[Pair[String, String]](util.Arrays.asList(new Pair[String, String]("X", "Float"), new Pair[String, String]("Y", "Float")))

  override def getClassName = UserVector.className

  override def toString: String = {
    if (x == null || y == null) return "null;null"
    x + ";" + y
  }

  override def parseValue(json: JSONObject) = try new UserVector(json.getFloat("x"), json.getFloat("y"))
  catch {
    case e: JSONException =>
      null
  }

  override def packValue = "{\"className\":\"" + UserVector.className + "\"," + "\"x\"" + ":" + x.toString + "," + "\"y\"" + ":" + y.toString + "}"

  override def compareTo(o: Any) = {
    val tValue = x * x + y * y
    val oValue = o.asInstanceOf[UserVector].x * o.asInstanceOf[UserVector].x + o.asInstanceOf[UserVector].y * o.asInstanceOf[UserVector].y
    tValue.compareTo(oValue)
  }
}