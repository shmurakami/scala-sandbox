package com.test.handler

import com.test.Handler
import com.test.Sandbox.BarCommand

import scala.concurrent.{ ExecutionContext, Future }

class BarHandler(attr: String) extends Handler[BarCommand] {

  override def handle(cmd: BarCommand)(implicit ec: ExecutionContext): Future[Unit] = Future {
    println(s"foo with $attr")
  }
}
