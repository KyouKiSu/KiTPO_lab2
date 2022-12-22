package types

import java.util

object UserFactory {
  val types = new util.ArrayList[UserType](util.Arrays.asList(new UserInteger, new UserFloat, new UserVector))

  def getBuilderByName(name: String): UserType = {
    types.forEach(element=>{
      if (element.getClassName.equalsIgnoreCase(name)) return element.create
    })
    null
  }
}