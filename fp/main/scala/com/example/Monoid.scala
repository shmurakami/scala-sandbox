package com.example

trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}

// Monoid
// type A
// op(op(x, y), z) == op(x, op(y, z))
// op(zero, x) == x && op(x, zero) == x
object Monoid {
  val stringMonoid = new Monoid[String] {
   override def op(a1: String, a2: String): String = a1 + a2
    override def zero: String = ""
  }

  def listMonoid[A] = new Monoid[List[A]] {
    override def op(a1: List[A], a2: List[A]): List[A] = a1 ++ a2
    override def zero: List[A] = Nil
  }

  val intMonoid = new Monoid[Int] {
    override def op(a1: Int, a2: Int): Int = a1 + a2
    override def zero: Int = 0
  }

  val boolAndMonoid = new Monoid[Boolean] {
    override def op(a1: Boolean, a2: Boolean): Boolean = a1 && a2
    override def zero: Boolean = true
  }

  val boolOrMonoid = new Monoid[Boolean] {
    override def op(a1: Boolean, a2: Boolean): Boolean = a1 || a2
    def zero: Boolean = false
  }

  def optionMonoid[A] = new Monoid[Option[A]] {
    override def op(a1: Option[A], a2: Option[A]): Option[A] = ???
    override def zero: Option[A] = None
  }

  def endoMonoid[A] = new Monoid[A => A] {
    override def op(a1: A => A, a2: A => A): A => A = a1 andThen a2
    override def zero: A => A = a => a
  }
}
