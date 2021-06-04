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
  }

}
