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
      trait P

      trait R extends P
      case class R1() extends R
      case class R2() extends R

      trait V

      class C[T]

      val c: C[R] = new C[R]
//      val c1: C[R] = new C[C1] // NG

      class CA[+T]
      val ca0: CA[R] = new CA[R]
      val ca1: CA[R] = new CA[R1] // OK
      val ca2: CA[R] = new CA[R2] // OK
//      val ca3: CA[R] = new CA[P]

      class CB[-T]
      val cb0: CB[R] = new CB[R]
//      val cb1: CB[R] = new CB[R1] // NG
      val cb2: CB[R1] = new CB[R] // OK

      class CC[T <: R]
      val cc0: CC[R] = new CC[R]
//      val cc1: CC[R] = new CC[R1] // NG
//      val cc2: CC[R1] = new CC[R] // NG
//      val cc3: CC[R] = new CC[V] // NG

      class CD[+T <: R]
      val cd0: CD[R] = new CD[R]
      val cd1: CD[R] = new CD[R1]
      val cd2: CD[R] = new CD[R2]
//      val cd3: CD[R] = new CD[V] // NG
//      val cd4: CD[R] = new CD[P] // NG

      class CE[-T <: R]
      val ce0: CE[R] = new CE[R]
//      val ce1: CE[R] = new CE[P] NG IDE is OK but not comfortable T <: R
//      val ce2: CE[R] = new CE[R1] // NG
      val ce3: CE[R1] = new CE[R]
//      val ce4: CE[R1] = new CE[P] NG IDE is OK but not comfortable T <: R

      class CF[-T >: R]
      val cf0: CF[R] = new CF[R]
//      val cf1: CF[R] = new CF[P] // NG
      val cf2: CF[R] = new CF[P]

      class CG[+T >: R]
      val cg0: CG[R] = new CG[R]
      val cg1: CG[P] = new CG[R]
//      val cg2: CG[R1] = new CG[R1] NG

      true shouldBe true

    }
  }

}
