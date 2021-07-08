package com.example

object Curry {

  def curry[A, B, C](f: (A, B) => C): A => B => C = { (a: A) => (b: B) => f(a, b) }

  def uncurry[A, B, C](f: A => B => C): (A, B) => C = { (a: A, b: B) => f(a)(b) }

  def compose[A, B, C](f: A => B, g: B => C): A => C = { a: A => g(f(a)) }

}
