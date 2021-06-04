package com.test

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}

import scala.concurrent.ExecutionContext

object Sandbox {

  sealed trait Command {
    def name: String
  }

  case class FooCommand(name: String, greeting: String) extends Command
  case class BarCommand(name: String, greeting: String) extends Command
  case class BuzCommand(name: String, greeting: String) extends Command

  def apply(dispatcher: Dispatcher): Behavior[Command] = {
    Behaviors.setup[Command] { ctx => idle(ctx, dispatcher) }
  }

  def idle(ctx: ActorContext[Command], dispatcher: Dispatcher): Behavior[Command] = Behaviors.receiveMessagePartial {
    // process for each Foo, Bar, Buz
    case s: Command =>
      implicit val ec: ExecutionContext = ctx.executionContext
      println(s"got message $s")
      dispatcher.dispatch(s)
      Behaviors.same
    case _ =>
      println("other")
      Behaviors.same
  }

}
