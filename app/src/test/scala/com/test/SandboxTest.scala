package com.test

import akka.actor.testkit.typed.scaladsl.{ActorTestKit, ScalaTestWithActorTestKit}
import akka.actor.typed.ActorRef
import com.test.Sandbox.FooCommand
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class SandboxTest extends ScalaTestWithActorTestKit(ActorTestKit()) with AnyWordSpecLike with Matchers {

  "App" should {
    "work" in {

      val dispatcher: Dispatcher = new Dispatcher("fooAttr", "barAttr", "buzAttr")

      val ref: ActorRef[Sandbox.Command] = spawn(Sandbox(dispatcher), "worker")

      ref ! FooCommand("foo", "Hello")

      true shouldBe true
    }

    "sample" in {
      sealed trait Co
      case class Co1() extends Co
      case class Co2() extends Co

      class C[T]
      val c1: C[Co] = new C[Co]

      class C2[+T]
      val c2: C2[Co] = new C2[Co1]

      class C3[-T]
      val c3: C3[Co2] = new C3[Co]

      trait T4[T]
      class Dispatcher extends T4[Co] {
        def d(t: Co): Unit = ()
      }
      val dispatcher = new Dispatcher
      dispatcher.d(Co1())

      trait T5[T]
      class Dispatcher5 extends T5[Co] {
        def d(t: Co): Unit = ()
      }
      val dispatcher5 = new Dispatcher5
      dispatcher5.d(Co2())

      true shouldBe true

    }
  }

}
