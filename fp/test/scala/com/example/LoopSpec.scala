package com.example

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class LoopSpec extends AnyWordSpec with Matchers {

  "Loop" should {
    "run" in {
      Loop.factorial(4) shouldBe 24
    }
  }

  "fib" should {
    "10" in {
      Loop.fib(10) shouldBe 34
    }

    "20" in {
      Loop.fib(20) shouldBe 4181
    }
  }

}
