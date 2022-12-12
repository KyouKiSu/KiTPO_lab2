import types.UserVector
import vtree.VTreeFactory


object helloworld {
  def main(args: Array[String]) = {
    println("Hello, world")
    val vec = new UserVector(1,1)
    //println(vec.toString())
    val treeFactory = new VTreeFactory()
    treeFactory.setType("UserVector")
    val tree = treeFactory.createTree()
    tree.Insert(vec)
    tree.Insert(vec)
    tree.Insert(vec)
    tree.Insert(vec)
    println(tree.print())
  }
}