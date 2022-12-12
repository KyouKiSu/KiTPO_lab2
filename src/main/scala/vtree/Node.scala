package vtree

import types.UserType

object Node {
  private[vtree] val className = "Node"
}

class Node(var value: UserType) {

  this.cnt = 0
  private var leftChild:Node = null
  private var rightChild:Node = null
  private var cnt = 0

  def packValue():String = {
    var packed = "{" + "\"className\"" + ":\"" + Node.className + "\"," + "\"cnt\"" + ":" + cnt
    if (value != null) packed += "," + "\"value\"" + ":" + value.packValue
    if (leftChild != null) packed += "," + "\"leftChild\"" + ":" + leftChild.packValue()
    if (rightChild != null) packed += "," + "\"rightChild\"" + ":" + rightChild.packValue()
    packed += "}"
    packed
  }

  def getValue = this.value

  def setCount(i: Int) = this.cnt = i

  def getCount = this.cnt

  def incrementCount() = this.cnt += 1

  def decrementCount() = this.cnt -= 1

  def setValue(value: UserType) = this.value = value

  def getLeftChild = this.leftChild

  def setLeftChild(leftChild: Node) = this.leftChild = leftChild

  def getRightChild = this.rightChild

  def setRightChild(rightChild: Node) = this.rightChild = rightChild
}