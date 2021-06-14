package com.test

import akka.actor.testkit.typed.scaladsl.{ ActorTestKit, ScalaTestWithActorTestKit }
import akka.actor.typed.scaladsl.AskPattern.Askable
import akka.actor.typed.scaladsl.{ ActorContext, Behaviors }
import akka.actor.typed.{ ActorRef, ActorSystem, Behavior, Scheduler }
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpMethods, HttpRequest, Uri }
import akka.stream.Materializer
import akka.stream.scaladsl.{ Sink, Source }
import akka.util.Timeout
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, Future }
import scala.util.control.NonFatal

class HttpSpec extends ScalaTestWithActorTestKit(ActorTestKit()) with AnyWordSpecLike with Matchers {

//  implicit val ec: ExecutionContext = system.executionContext

  case class Message(message: String, replyTo: ActorRef[String])

  "HttpSpec" should {
    "timeout" in {

      object Request {
        def behave: Behavior[Message] = Behaviors.receiveMessagePartial {
          case Message(m, replyTo) =>
            println(s"$m")
            send(system, system.executionContext).futureValue

            replyTo ! "reply"
            Behaviors.same
        }
      }

      def test(ctx: ActorContext[Message]): Behavior[Message] =
        Behaviors.receiveMessagePartial {
          case message: Message =>
            implicit val timeout              = Timeout(3.seconds)
            implicit val scheduler: Scheduler = ctx.system.scheduler
            implicit val mat: Materializer    = Materializer(ctx)

            val req = ctx.spawn(Request.behave, "req")

            Source
              .single(message)
              .map { m => req.ask[String] { ref2 => Message(m.message, m.replyTo) } }
              .runWith(Sink.head)

            Behaviors.same
        }

      def behavior: Behavior[Message] = Behaviors.setup[Message] { ctx =>
        test(ctx)
      }

      implicit val mySystem = ActorSystem[Message](
        behavior,
        "system"
      )

      implicit val ec: ExecutionContext = mySystem.executionContext

      val probe = createTestProbe[String]()
      mySystem ! Message("message", probe.ref)

      probe.expectMessage(10.seconds, "reply")
    }
  }

  def send(implicit system: ActorSystem[_], ec: ExecutionContext): Future[Unit] = {
    Http()
      .singleRequest(
        HttpRequest(
          HttpMethods.GET,
          Uri("http://localhost:8080/index.php")
        )
      ).map { res => println(res.toString()) }
//      .recover {
//        case NonFatal(e) =>
//          println(e)
//      }
  }

}
