package ru.anfdenis.scalaz

/**
 * Denis Anfertev
 * 10/9/13 12:03 PM
 */
object Main extends App {
  val o1: Option[Int] = Some(0)
  val o2: Option[Option[Int]] = Some(Some(0))

  import scalaz._

  {
    import std.option.optionInstance.monadSyntax._

    val res1: Option[String] = o1 >>= (x => if (x == 0) Some("Zero") else None)
    val res2: Option[Int] = o2.join

    println(res1)
    println(res2)
  }

  {
    import std.option._
    import syntax.monad._

    val res3: Option[Int] = 1.point[Option]

    println(res3)
  }

  //Equal
  {
    import syntax.equal._
    import std.anyVal._
    import std.string._
    val res4 = 1 =/= 2
    val res5 = "" === "String"

    1 assert_=== 1

    println(res4)
    println(res5)
  }

  {
    import std.option._, std.anyVal._

    import syntax.std.option._

    import syntax.apply._, syntax.semigroup._

    val res6 = 1.some
    val res7 = (1.some |@| none) {
      (i1: Int, i2: Int) => i1 |+| i2
    }

    println(res6)
    println(res7)
  }

  //Order
  {
    import syntax.order._
    import std.anyVal._

    val res8 = 1 gt 2
    val res9 = 1.0 ?|? 2.0
    val res10 = 1 max 2

    println(res8)
    println(res9)
    println(res10)
  }

  //Show
  {
    import syntax.show._
    import std.anyVal._
    import std.string._

    val res11: Cord = 3.show
    val res12: String = 3.shows

    println(res11)
    println(res12)
    "hello".println
  }

  //Enum
  {
    import syntax.show._

    import syntax.enum._
    import std.anyVal._
    import std.list._

    implicit def ephemeralStreamShow[A: Show]: Show[EphemeralStream[A]] = new Show[EphemeralStream[A]] {
      override def show(as: EphemeralStream[A]) = "[" +: Cord.mkCord(",", as.map(Show[A].show).toSeq: _*) :+ "]"
    }

    val res13: List[Char] = 'a' |-> 'e'
    val res14: EphemeralStream[Int] = 3 |=> 5
    val res15: Char = 'b'.succ
    val res16: Char = 'a' -+- 2
    val res17: List[Int] = 2 |-->(3, 10)
    val res18: EphemeralStream[Int] = 2 |==>(3, 10)
    val res19: Char = 'c' --- 2


    println(res13.show)
    println(res14.show)
    println(res15.show)
    println(res16.show)
    println(res17.show)
    println(res18.show)
    println(res19.show)
  }

  //Bounded ~ Enum
  {
    import syntax.enum._
    import std.anyVal._

    val res20: Option[Char] = Enum[Char].max
    val res21: Option[Int] = Enum[Int].min

    println(res20)
    println(res21)

  }
}