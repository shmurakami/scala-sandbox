package com.test

import com.typesafe.config.ConfigFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import java.io.{File, PrintWriter}

class PlaceholderSpec extends AnyWordSpecLike with Matchers {

  "Placeholder" should {
    "replace" in {

      class PlaceholderString(val src: String) {
        def placeholder(key: String, value: String): String = src.replace(s"%%$key%%", value)
      }

      implicit def placeholder(arg: String): PlaceholderString = new PlaceholderString(arg)

      val conf = ConfigFactory.load()
      val template = conf.getString("placeholder.template").stripMargin

      val replaced = template.placeholder("package", "app")
        .placeholder("import", {
          val imports: Seq[String] = Seq(
            "scala.concurrent.Future"
          )
          imports.map(i => s"import $i").mkString("\n")
        })
        .placeholder("class", "FooBar")
        .placeholder("type", "Foo")
        .placeholder("body", {
          """Foo
            | .method(
            |   Request(
            |     foo = "bar"
            |   )
            | )
            | .flatMap { response =>
            |   case _ => {
            |     response
            |   }
            | }
            |""".stripMargin
        })


      val path = "app/src/test/scala/output.scala"
      val writer = new PrintWriter(new File(path))
      try {
        writer.write(replaced)
      } finally {
        writer.close()
      }

      true shouldBe true

//      replaced shouldBe
//        """Hello Alice
//          |bar
//          |""".stripMargin
    }
  }

}
