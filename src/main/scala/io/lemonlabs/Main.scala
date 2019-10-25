package io.lemonlabs

import io.lemonlabs.uri.{Uri, Url, Urn}
import org.scalajs.dom._

import scala.scalajs.js.annotation.JSExportTopLevel
import scala.util.{Failure, Success, Try}

object Main {
  def main(args: Array[String]): Unit = {
    urlTextTyped()
  }

  @JSExportTopLevel("urlTextTyped")
  def urlTextTyped(): Unit = {
    document.querySelectorAll(".list-group-item").setAttribute("style", "display: none;")

    val url = Try(Uri.parse(document.getElementById("url-input").asInstanceOf[html.Input].value))
    url match {
      case Success(url: Url) => writeUrl(url)
      case Success(urn: Urn) => writeUrn(urn)
      case Failure(e)        => writeException(e)
    }
  }

  def writeUri(uri: Uri): Unit = {
    writeListGroupText("type-lg", uri.getClass.getSimpleName)
    uri.schemeOption.foreach(scheme => writeListGroupText("scheme-lg", scheme))

    if(uri.path.nonEmpty) {
      writeListGroupText("path-lg", uri.path.toString)
    }
  }

  def writeUrn(urn: Urn): Unit = {
    writeUri(urn)

    writeListGroupText("nid-lg", urn.nid)
    writeListGroupText("nss-lg", urn.nss)
  }

  def writeUrl(url: Url): Unit = {
    writeUri(url)

    url.hostOption.foreach { host =>
      writeListGroupText("host-lg", host.toString)
      writeListGroupText("host-type-lg", host.getClass.getSimpleName)
    }

    if(url.query.nonEmpty) {
      writeQueryStringTable("query-string-lg", url.query.params)
    }

    url.fragment.foreach(fragment => writeListGroupText("fragment-lg", fragment))
  }

  def writeException(ex: Throwable): Unit = {
    writeListGroupText("error-lg", ex.getMessage)
  }

  def writeQueryStringTable(id: String, params: Vector[(String, Option[String])]): Unit = {
    document.getElementById(id).setAttribute("style", "display: block;")
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
    document.getElementById(id).setAttribute("style", "display: block;")
    document.querySelector(s"#$id .list-group-item-text").innerHTML = text
  }

  implicit class EnchancedDomList(list: NodeList) {

    def setAttribute(name: String, value: String): Unit =
      foreach(_.asInstanceOf[Element].setAttribute(name, value))

    def foreach(f: Node => Unit): Unit = {
      (0 until list.length).foreach { i =>
        f(list(i))
      }
    }
  }
}
