package com.test

import com.test.Sandbox.Command

import scala.concurrent.{ExecutionContext, Future}

trait Handler[C <: Command] {

  def handle(cmd: C)(implicit ec: ExecutionContext): Future[Unit]

}
