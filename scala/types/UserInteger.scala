package types

import javafx.util.Pair
import org.json.{JSONException, JSONObject}

import java.util

object UserInteger {
  private[types] val className = "UserInteger"
}

class UserInteger() extends UserType {
  private var value:Integer = null

  def this(data: Integer) {
    this()
    this.value = data
  }

  override def copy = new UserInteger(this.value).asInstanceOf[UserType]

  override def create = new UserInteger()

  override def create(values: util.ArrayList[String]) = new UserInteger(values.get(0).toInt)

  override def getFields = new util.ArrayList[Pair[String, String]](util.Arrays.asList(new Pair[String, String]("Value", "Integer")))

  override def getClassName = UserInteger.className

  override def parseValue(json: JSONObject) = try new UserInteger(json.getInt("raw_value"))
  catch {
    case e: JSONException =>
      null
  }

  override def packValue = "{\"className\":\"" + UserInteger.className + "\"," + "\"raw_value\"" + ":" + value.toString + "}"

  override def toString: String = {
    if (value == null) return "null"
    value.toString
  }

  override def compareTo(o: Any) = value.compareTo(o.asInstanceOf[UserInteger].value)
}