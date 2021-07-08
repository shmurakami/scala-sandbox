package com.example

object Loop {

  def factorial(n: Int): Int = {
    @annotation.tailrec
    def go(n: Int, acc: Int): Int =
      if (n <= 0) acc
      else go(n - 1, n * acc)

    go(n, 1)
  }

  def fib(n: Int): Int = {
    @annotation.tailrec
    def calcFib(n: Int, first: Int, second: Int): Int = {
      if (n <= 1) first
      else calcFib(n - 1, second, first + second)
    }

    calcFib(n, 0, 1)
  }

}
