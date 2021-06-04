package com.test

import akka.actor.testkit.typed.scaladsl.{ActorTestKit, ScalaTestWithActorTestKit}
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors
import com.test.Sandbox.FooCommand
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class SandboxTest extends ScalaTestWithActorTestKit(ActorTestKit()) with AnyWordSpecLike with Matchers {

  "App" should {
    "work" in {

      val ref: ActorRef[Sandbox.Command] = spawn(Sandbox(), "worker")

      ref ! FooCommand("foo", "Hello")

      true shouldBe true
    }
  }

}
