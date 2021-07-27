package com.example

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class MyOptionSpec extends AnyWordSpecLike with Matchers {

  trait MyOption[+A] {
    def isEmpty: Boolean = this equals MyNone

    def isDefined: Boolean = !isEmpty

    def get: A

    def map[B](f: A => B): MyOption[B] = {
      if (isEmpty) MyNone
      else MySome(f(this.get))
    }

    def flatMap[B](f: A => MyOption[B]): MyOption[B] = {
      if (isEmpty) MyNone
      else f(this.get)
    }

    def getOrElse[B >: A](default: => B): B = {
      if (isEmpty) default
      else this.get
    }

    def orElse[B >: A](ob: => MyOption[B]): MyOption[B] = {
      if (isEmpty) ob
      else this
    }

    def filter(f: A => Boolean): MyOption[A] = {
      if (isEmpty) MyNone
      else {
        if (f(this.get)) this
        else MyNone
      }
    }
  }

  case class MySome[A](value: A) extends MyOption[A] {
    override def get: A = value
  }

  case object MyNone extends MyOption[Nothing] {
    override def get: Nothing = ???
  }

  "MyOption" when {
    "map" should {
      "Some" in {
        MySome(10).map(_.toString) shouldBe MySome("10")
      }

      "None" in {
        MyNone.map(_ => ()) shouldBe MyNone
      }
    }

    "flatMap" should {
      "Some" in {
        MySome(10).flatMap(i => MySome(i.toString)) shouldBe MySome("10")
      }

      "Some to be None" in {
        MySome(10).flatMap(i => MyNone) shouldBe MyNone
      }

      "None" in {
        MyNone.flatMap(_ => MySome(())) shouldBe MyNone
      }
    }

    "getOrElse" should {
      "Some" in {
        MySome(10).getOrElse(0) shouldBe 10
      }

      "None" in {
        MyNone.getOrElse(0) shouldBe 0
      }

    }

    "orElse" should {
      "Some" in {
        MySome(10).orElse(MySome(0)) shouldBe MySome(10)
      }

      "None" in {
        MyNone.orElse(MySome(1)) shouldBe MySome(1)
      }
    }

    "filter" should {
      "Some" in {
        MySome(10).filter(_ % 2 == 0) shouldBe MySome(10)
      }

      "Some to be None" in {
        MySome(10).filter(_ % 2 == 1) shouldBe MyNone
      }

      "None" in {
        MyNone.filter(_ => true) shouldBe MyNone
      }
    }

  }

}
