package com.test.handler

import com.test.Handler
import com.test.Sandbox.BuzCommand

import scala.concurrent.{ ExecutionContext, Future }

class BuzHandler(attr: String) extends Handler[BuzCommand] {

  override def handle(cmd: BuzCommand)(implicit ec: ExecutionContext): Future[Unit] = Future {
    println(s"buz with $attr")
  }
}
