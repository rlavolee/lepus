package lepus.protocol.gen

import cats.effect.IO
import fs2.{Stream, Pipe}
import fs2.io.file.Path
import fs2.text.utf8
import fs2.io.file.Files

type Lines = Stream[IO, String]
type FileGen = Pipe[IO, String, Nothing]

object Helpers {
  private val genWarn: String = """
/*
                           /!\ HUMANS BEWARE /!\
=================================================================================
||                                                                             ||
|| THIS FILE IS GENERATED BY MACHINES AND ANY EDITING BY HUMANS IS PROHIBITED! ||
||                                                                             ||
=================================================================================
*/
"""
  def file(module: String, out: Path): FileGen = lines =>
    (Stream(genWarn) ++ lines)
      .intersperse("\n")
      .through(utf8.encode)
      .through(
        Files[IO].writeAll(Path(s"modules/${module}/src/main/scala") / out)
      )

  private val namePattern = "\\W*\\b(\\w)".r
  def idName(str: String): String =
    namePattern.replaceAllIn(str, m => m.group(1).toUpperCase)
  def valName(str: String): String = {
    val id = idName(str)
    id.headOption.map(_.toLower).map(id.tail.prepended).getOrElse(id)
  }

  def comment(str: String): String = s"/**\n$str\n */"

  def obj(name: String)(lines: Lines): Lines =
    (Stream(s"object ${idName(name)} {") ++ lines ++ Stream("}"))
      .intersperse("\n")

  def headers(hs: String*): Lines = Stream.emits(hs)
}