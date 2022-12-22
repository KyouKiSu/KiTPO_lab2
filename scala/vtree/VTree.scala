package vtree

import types.UserType

import java.lang.Thread.sleep
import java.util
import scala.util.control.Breaks.{break, breakable}

class VTree() {
  private[vtree] var root: Node = null

  def Insert(value: UserType): Unit = if (root == null) {
    val newNode = new Node(value)
    newNode.setCount(1)
    root = newNode
  }
  else {
    var current = root
    var parent = root
    var left = true

    var _value = value
    while ( {
      true
    }) {

      if (current == null) {
        val newNode = new Node(_value)
        newNode.setCount(1)
        if (left) parent.setLeftChild(newNode)
        else parent.setRightChild(newNode)
        return
      }
      current.incrementCount()
      val cmpResult = _value.compareTo(current.getValue)
      if (cmpResult < 0) {
        val c = current.getValue
        current.setValue(_value)
        _value = c
      }
      parent = current
      if (current.getLeftChild == null || current.getRightChild != null && current.getLeftChild.getCount < current.getRightChild.getCount) {
        current = current.getLeftChild
        left = true
      }
      else {
        current = current.getRightChild
        left = false
      }
    }
  }

  def find(value: UserType): Node = {
    val nodes = new util.Stack[Node]
    nodes.push(root)
    val states = new util.Stack[java.lang.Integer]
    states.push(0)
    // 0 - new on this level, 1 - just visited left, 2 - just visited right
    while ( {
      true
    }) {
      breakable {

        if (nodes.empty) return null
        val current = nodes.peek
        if (current == null) { // go back?
          nodes.pop
          states.pop
          // inc last
          if (nodes.empty) return null
          //                Integer l = Integer.valueOf(states.peek().intValue() + 1);
          //                states.pop();
          //                states.push(l);

            break;
           //todo: continue is not supported
        }
        val cmpResult = value.compareTo(current.getValue)
        if (cmpResult == 0) return current
        if (cmpResult < 0 || (states.peek eq new Integer(2))) { // +-
          nodes.pop
          states.pop
          if (nodes.empty) return null
        }
        else { // go deeper?
          if (states.peek eq new Integer(0)) {
            val l = Integer.valueOf(states.peek.intValue + 1)
            states.pop
            states.push(l)
            // go left
            states.push(0)
            nodes.push(current.getLeftChild)

              break;
             //todo: continue is not supported
          }
          if (states.peek eq new Integer(1) ) {
            val l = Integer.valueOf(states.peek.intValue + 1)
            states.pop
            states.push(l)
            // go right
            states.push(0)
            nodes.push(current.getRightChild)

            break;
            //todo: continue is not supported
          }
          //                if(states.peek()==2){
          //                    // go up
          //                }
        }
      }
    }
    null
  }

  def ForEach(func: DoWith): Unit = {
    val nodes = new util.Stack[Node]
    nodes.push(root)
    val states = new util.Stack[Integer]
    states.push(0)
    while ( {
      true
    }) {
      breakable {
        println(states.size())
        if (nodes.empty) return
        val current = nodes.peek
        if (current == null) {
          nodes.pop
          states.pop
          if (nodes.empty) return

          break;
          //todo: continue is not supported
        }
        if (states.peek.intValue() eq Integer.valueOf(2)) {
          func.doWith(current.getValue)
          nodes.pop
          states.pop
          if (nodes.empty) return
        }
        else {
          if (states.peek.intValue() eq Integer.valueOf(0)) {
            val l = Integer.valueOf(1)
            states.pop
            states.push(l)
            states.push(0)
            nodes.push(current.getLeftChild)

            break;
            //todo: continue is not supported
          }
          if (states.peek.intValue() eq Integer.valueOf(1)) {
            val l = Integer.valueOf(2)
            states.pop
            states.push(l)
            states.push(0)
            nodes.push(current.getRightChild)

            break;
            //todo: continue is not supported
          }
        }
      }
    }
  }

  def GetByIndex(index: Int): UserType = {
    val elementList: Array[util.Vector[UserType]] = Array[util.Vector[UserType]](new util.Vector[UserType])
    val curindex = Array(0)
    val getElement = new DoWith() {
      override def doWith(obj: Any) = {
        if (index == curindex(0)) elementList(0).add(obj.asInstanceOf[UserType])
        curindex(0) += 1
      }
    }
    ForEach(getElement)
    if (elementList(0).size > 0) return elementList(0).get(0)
    null
  }

  def GetByIndexOld(index: Int): UserType = {
    var currentIndex = 0
    val nodes = new util.Stack[Node]
    nodes.push(root)
    val states = new util.Stack[Integer]
    states.push(0)
    while ( {
      true
    }) {
      breakable {

        if (nodes.empty) return null
        val current = nodes.peek
        if (current == null) {
          nodes.pop
          states.pop
          if (nodes.empty) return null

          break;
          //todo: continue is not supported
        }
        if (states.peek eq new Integer(2)) {
          if (currentIndex == index) return current.getValue
          currentIndex += 1
          nodes.pop
          states.pop
          if (nodes.empty) return null
        }
        else {
          if (states.peek eq new Integer(0)) {
            val l = Integer.valueOf(states.peek.intValue + 1)
            states.pop
            states.push(l)
            states.push(0)
            nodes.push(current.getLeftChild)

            break;
            //todo: continue is not supported
          }
          if (states.peek eq new Integer(1)) {
            val l = Integer.valueOf(states.peek.intValue + 1)
            states.pop
            states.push(l)
            states.push(0)
            nodes.push(current.getRightChild)

            break;
            //todo: continue is not supported
          }
        }
      }
    }
    null
  }

  def findParents(value: UserType): util.Stack[Node] = {
    val nodes = new util.Stack[Node]
    nodes.push(root)
    val states = new util.Stack[Integer]
    states.push(0)
    while ( {
      true
    }) {
      //println(nodes.peek().getValue)
      breakable {
        if (nodes.empty) return null
        val current = nodes.peek
        if (current == null) {
          nodes.pop
          states.pop
          if (nodes.empty) return null
          break;
          //todo: continue is not supported
        }
        val cmpResult = value.compareTo(current.getValue)

        if (cmpResult == 0) {
          nodes.pop
          if (nodes.empty) return null
          return nodes
        }
        if (cmpResult < 0 || (states.peek.intValue() eq Integer.valueOf(2))) {
          nodes.pop
          states.pop
          if (nodes.empty) return null
        }
        else {
          if (states.peek.intValue() eq Integer.valueOf(0)) {
            val l = Integer.valueOf(states.peek.intValue + 1)
            states.pop
            states.push(l)
            states.push(0)
            nodes.push(current.getLeftChild)
            break;
            //todo: continue is not supported
          }
          if (states.peek.intValue() eq Integer.valueOf(1)) {
            val l = Integer.valueOf(states.peek.intValue + 1)
            states.pop
            states.push(l)
            states.push(0)
            nodes.push(current.getRightChild)
            break;
            //todo: continue is not supported
          }
        }
      }
    }
    null
  }

  def RemoveByIndex(index: Int) = {
    val value = GetByIndex(index)
    if (value != null) Remove(value)
  }

  def Remove(value: UserType): UserType = {
    val parents = findParents(value)

    if (parents == null || parents.empty) { // root or not found
      if (root.getValue.compareTo(value) == 0) {
        if (root.getRightChild == null && root.getLeftChild == null) {
          val toreturn = root.getValue
          root = null
          return toreturn
          // if any child present, continue below}
        } else {}
      }
      else { // no element found
        return null
      }
    }

      var current: Node = null
      var parent: Node = null
      val childs = new util.Stack[Node]
      val states = new util.Stack[Integer] // 1 left 0 right
      if (parents == null) current = root
      else {
        parent = parents.peek
        if (parent.getRightChild != null && value.compareTo(parent.getRightChild.getValue) == 0) current = parent.getRightChild
        else current = parent.getLeftChild
      }
      childs.push(current)
      var shouldReturn = false
      var cvalue = value
      var deleted: Node = null
    breakable {
      while ( {
        true
      }) {
        //println(childs.peek().getValue)
          var continue=false;
          if (childs.empty) break //todo: break is not supported
          current = childs.peek
          if (shouldReturn) {
            if (parent == null) {
              // remove root
            }
            if (parent != null && (parent.getRightChild eq deleted)) {
              parent.setRightChild(null)
              break //todo: break is not supported
            }
            if (parent != null && (parent.getLeftChild eq deleted)) {
              parent.setLeftChild(null)
              break //todo: break is not supported
            }
            if (childs.peek.getLeftChild eq deleted) childs.peek.setLeftChild(null)
            if (childs.peek.getRightChild eq deleted) childs.peek.setRightChild(null)
            val b = childs.peek.getValue
            childs.peek.setValue(cvalue)
            childs.peek.decrementCount()
            cvalue = b
            childs.pop
            continue=true; //todo: continue is not supported
          }
        if(!continue) {
          continue=false;
          if (current.getLeftChild == null && current.getRightChild == null) {
            deleted = current
            cvalue = current.getValue
            shouldReturn = true
            continue=true;
            //todo: continue is not supported
          }
          if(!continue) {
            if (childs.peek.getLeftChild == null || childs.peek.getRightChild != null && childs.peek.getLeftChild.getValue.compareTo(childs.peek.getRightChild.getValue) > 0) { // going right
              childs.push(childs.peek.getRightChild)
              println("RIGHT")
            }
            else { // going left
              println("LEFT")
              childs.push(childs.peek.getLeftChild)
            }
          }


        }

        }
      }
    breakable {
      while ( {
        true
      }) {
        if (parents == null || parents.empty) break //todo: break is not supported
        parents.peek.decrementCount()
        parents.pop
      }
    }
      value
    }


    def rebalance() = {
      val elementList = new util.Vector[UserType]
      val getElement = new DoWith() {
        override def doWith(obj: Any) = elementList.add(obj.asInstanceOf[UserType])
      }
      ForEach(getElement)
      root = null
      elementList.forEach(element=>Insert(element))
    }


    def print(): String = {
      if (root == null) return "Empty tree"
      print("", root, false)
    }

    private def print(prefix: String, n: Node, isLeft: Boolean): String = {
      var result:String = ""
      if (n != null) {
        result += prefix + (if (isLeft) "├── "
        else "└── ") + n.getValue + "(" + n.getCount + ")" + "\n"
        result += print(prefix + (if (isLeft) "|   "
        else "    "), n.getLeftChild, true)
        result += print(prefix + (if (isLeft) "|   "
        else "    "), n.getRightChild, false)
      }
      result
    }

    def GetRoot = root.getValue

    def SetRoot(_root: Node) = root = _root

    def packValue: String = {
      if (root == null) return "{}"
      root.packValue
    }
  }