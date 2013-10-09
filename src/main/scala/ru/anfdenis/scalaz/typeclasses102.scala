package ru.anfdenis.scalaz

/**
 * Denis Anfertev
 * 10/9/13 6:20 PM
 */
object typeclasses102 extends App {

  sealed trait TrafficLight

  case object Red extends TrafficLight

  case object Yellow extends TrafficLight

  case object Green extends TrafficLight

  import scalaz._

  import syntax.equal._

  implicit val trafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _)

  //Fail
  //println(Red === Red)

  case class TrafficLight2(name: String)
  val red = TrafficLight2("red")
  val yellow = TrafficLight2("yellow")
  val green = TrafficLight2("green")
  implicit val TrafficLightEqual: Equal[TrafficLight2] = Equal.equal(_ == _)
  println(red === yellow)
}




