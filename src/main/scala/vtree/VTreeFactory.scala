package vtree

import org.json.{JSONException, JSONObject}
import types.{UserFactory, UserType}

class VTreeFactory() {
  setType(0)
  private var typeInstance: UserType = null

  def getTypeInstance = typeInstance.create

  def setType(id: Int) = typeInstance = UserFactory.getBuilderByName(UserFactory.types.get(id).getClassName)

  def setType(name: String) = typeInstance = UserFactory.getBuilderByName(name)

  def parseTree(json: JSONObject) = {
    val node = parseNode(json)
    val _vtree = new VTree
    _vtree.SetRoot(node)
    _vtree
  }

  def createTree() = {
    val tree = new VTree
    tree
  }

  private def parseNode(json: JSONObject): Node = {
    val _nodeClassName = json.getString("className")
    assert(_nodeClassName ne Node.className)
    var _valueJSON: JSONObject = null
    var _value: UserType = null
    try {
      _valueJSON = json.getJSONObject("value")
      setType(_valueJSON.getString("className"))
      if (typeInstance == null) throw new Exception("Wrong type")
      _value = typeInstance.parseValue(_valueJSON)
    } catch {
      case e: Exception =>
        System.out.println(e)
    }
    val _cnt = json.getInt("cnt")
    var _leftChild: Node = null
    var _rightChild: Node = null
    try _leftChild = parseNode(json.getJSONObject("leftChild"))
    catch {
      case e: JSONException =>
    }
    try _rightChild = parseNode(json.getJSONObject("rightChild"))
    catch {
      case e: JSONException =>
    }
    val node = new Node(_value)
    node.setCount(_cnt)
    node.setLeftChild(_leftChild)
    node.setRightChild(_rightChild)
    node
  }
}