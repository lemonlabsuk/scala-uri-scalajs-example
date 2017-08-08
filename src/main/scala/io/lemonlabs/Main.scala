package io.lemonlabs

import org.scalajs.dom
import dom.{document, html}
import com.netaporter.uri.Uri

import scala.collection.Seq
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit = {
    urlTextTyped()
  }

  @JSExportTopLevel("urlTextTyped")
  def urlTextTyped(): Unit = {
    val url = Try(Uri.parse(document.getElementById("url-input").asInstanceOf[html.Input].value))
    val success = url.toOption
    writeListGroupText("scheme-lg", success.flatMap(_.scheme).getOrElse(""))
    writeListGroupText("host-lg", success.flatMap(_.host).getOrElse(""))
    writeListGroupText("path-lg", success.map(_.path).getOrElse(""))
    writeQueryStringTable("query-string-lg", success.map(_.query.params).getOrElse(Nil))
    writeListGroupText("fragment-lg", success.flatMap(_.fragment).getOrElse(""))
    writeListGroupText("error-lg", url.failed.map(_.getMessage).getOrElse(""))
  }

  def writeQueryStringTable(id: String, params: Seq[(String, Option[String])]): Unit = {
    val tableBody = document.querySelector(s"#$id .list-group-item-text tbody")
    tableBody.innerHTML = ""
    params.foreach { case (name, value) =>
      val rowElem = document.createElement("tr")
      val nameElem = document.createElement("td")
      nameElem.innerHTML = name
      val valueElem = document.createElement("td")
      valueElem.innerHTML = value.getOrElse("")
      rowElem.appendChild(nameElem)
      rowElem.appendChild(valueElem)
      tableBody.appendChild(rowElem)
    }
  }

  def writeListGroupText(id: String, text: String): Unit = {
    val lgText = document.querySelector(s"#$id .list-group-item-text")
    lgText.innerHTML = text
  }
}
