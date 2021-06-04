package com.test

trait MyApp {
  def say: Unit

}

class MyAppImpl(val name: String) extends MyApp {

  def say = {
    println(s"Hello $name")
  }
}
