import java.io.File

import scala.io.Source

/**
  * This is a small, quickly and dirtily hacked Scala script which extracts a class index out of a "..\docs.tex" file.
  * Try at home at own risk.
  *
  * @author Daniel Jungkind
  */
object ClassIndex extends App {
  assert (System.getProperty("user.dir") endsWith "Classindex")
  val docstex = new File(System.getProperty("user.dir") + "\\..\\docs.tex")
  assert(docstex.exists)


  val pattern = "\\\\label\\{texdoclet:([^\\}]*)\\}".r

  val text = Source.fromFile(docstex).mkString
  val labels = pattern.findAllMatchIn(text).map{_.group(1)}.toList

  def lastIdent(s: String) = s.reverse.takeWhile('.'!=).reverse  //org.bla.whatever.abc => abc
  def getPackage(s: String) = s contains "." match {
    case true => s.reverse.dropWhile('.'!=).tail.reverse
    case false => ""
  }

  def duplicates[T, B](l: List[T], f: T => B) = l.groupBy(f).collect { case (x, ds) if ds.size > 1  => ds }.flatten
  def singles[T, B](l: List[T], f: T => B)    = l.groupBy(f).collect { case (x, ss) if ss.size == 1 => ss.head }

  val (classes, packages) = labels.partition(s => lastIdent(s).head.isUpper)

  val classesDisambig = (
      duplicates(classes, lastIdent).map{ s => s"${lastIdent(s)} (${getPackage(s)})" } ++
        (singles(classes, lastIdent) map lastIdent)
    ).toList.sorted


  classesDisambig foreach println

  packages foreach println

}