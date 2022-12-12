package types

import javafx.util.Pair
import org.json.JSONObject

import java.util

trait UserType {
  def copy: UserType

  def create: UserType

  def toString: String

  def getClassName:String

  def parseValue(json: JSONObject):UserType

  def packValue:String

  def create(values: util.ArrayList[String]): UserType

  def getFields: util.ArrayList[Pair[String, String]]

  def compareTo(o: Any): Integer
}