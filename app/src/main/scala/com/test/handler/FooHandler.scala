package com.test.handler

import com.test.Handler
import com.test.Sandbox.FooCommand

import scala.concurrent.{ ExecutionContext, Future }

class FooHandler(attr: String) extends Handler[FooCommand] {

  override def handle(cmd: FooCommand)(implicit ec: ExecutionContext): Future[Unit] = Future {
    println(s"foo with $attr")
  }
}
