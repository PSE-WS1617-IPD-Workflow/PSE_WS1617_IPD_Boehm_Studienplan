import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}

import scala.io.Source

object DocsPreprocessor extends App {
  assert (System.getProperty("user.dir") endsWith "Classindex")
  val docstex = new File(System.getProperty("user.dir") + "\\..\\docs.tex")
  assert(docstex.exists)

  val src = Source.fromFile(docstex).mkString

  val result = src.
    replace("(see ", "(siehe ").
    replace("docpackage}{edu.kit.informatik.studyplan.", "docpackage}{").
//    replace("Ã¤", "ä").
//    replace("ï¿½", "ü").
//    replace("Ã¶", "ö").
//    replace("Ã„", "Ä").
//    replace("ÃŸ", "ß").
//    replace("Ã¼", "ü").
//    replace("Ãœ", "Ü").
    replace("", "")

  Files.write(Paths.get(System.getProperty("user.dir") + "\\..\\docs-edited.tex"),
    result.getBytes(StandardCharsets.UTF_8))
}
