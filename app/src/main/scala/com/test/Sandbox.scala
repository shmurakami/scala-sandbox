package com.test

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ ActorContext, Behaviors }

object Sandbox {

  sealed trait Command {
    def name: String
  }

  case class FooCommand(name: String, greeting: String) extends Command
  case class BarCommand(name: String, greeting: String) extends Command
  case class BuzCommand(name: String, greeting: String) extends Command

  def apply(): Behavior[Command] = {
    Behaviors.setup[Command] { ctx => idle(ctx) }
  }

  def idle(ctx: ActorContext[Command]): Behavior[Command] = Behaviors.receiveMessagePartial {
    case s =>
      println("foo")
      Behaviors.same
  }

}
