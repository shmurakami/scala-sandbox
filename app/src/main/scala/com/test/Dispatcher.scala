package com.test

import com.test.Sandbox.{BarCommand, BuzCommand, Command, FooCommand}
import com.test.handler.{BarHandler, BuzHandler, FooHandler}

import scala.concurrent.{ExecutionContext, Future}

class Dispatcher(fooAttr: String, barAttr: String, buzAttr: String) {

  def dispatch(cmd: Command)(implicit ec: ExecutionContext): Future[Unit] = {
    cmd match {
      case c: FooCommand =>
        new FooHandler(fooAttr).handle(c)
      case c: BarCommand =>
        new BarHandler(barAttr).handle(c)
      case c: BuzCommand =>
        new BuzHandler(buzAttr).handle(c)
    }
  }

}
