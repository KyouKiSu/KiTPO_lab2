package types

import javafx.util.Pair
import org.json.{JSONException, JSONObject}

import java.util

object UserFloat {
  private[types] val className = "UserFloat"
}

class UserFloat() extends UserType {
  private var value:Float = 0

  def this(data: Float) {
    this()
    this.value = data
  }

  override def copy = new UserFloat(this.value).asInstanceOf[UserType]

  override def create = new UserFloat()

  override def getClassName = UserFloat.className

  override def toString: String = {
    if (value == null) return "null"
    value.toString
  }

  override def parseValue(json: JSONObject) = try new UserFloat(json.getFloat("raw_value"))
  catch {
    case e: JSONException =>
      null
  }

  override def packValue = "{\"className\":\"" + UserFloat.className + "\"," + "\"raw_value\"" + ":" + value.toString + "}"

  override def create(values: util.ArrayList[String]) = new UserFloat(values.get(0).toFloat)

  override def getFields = new util.ArrayList[Pair[String, String]](util.Arrays.asList(new Pair[String, String]("Value", "Integer")))

  override def compareTo(o: Any) = value.compareTo(o.asInstanceOf[UserFloat].value)
}