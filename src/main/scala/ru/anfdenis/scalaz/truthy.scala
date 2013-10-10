package ru.anfdenis.scalaz

/**
 * Denis Anfertev
 * 10/9/13 6:28 PM
 */
trait CanTruthy[A] {
  self =>
  def truthys(a: A): Boolean
}

object CanTruthy {
  def apply[A](implicit ev: CanTruthy[A]): CanTruthy[A] = ev

  def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    def truthys(a: A): Boolean = f(a)
  }
}

trait CanTruthyOps[A] {
  def self: A

  implicit def F: CanTruthy[A]

  final def truthy: Boolean = F.truthys(self)
}

object ToCanIsTruthyOps {
  implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]) =
    new CanTruthyOps[A] {
      def self = v

      implicit def F: CanTruthy[A] = ev
    }

  implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
    case Nil => false
    case _ => true
  })

  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
    case 0 => false
    case _ => true
  })

  implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false)

  implicit val booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity)
}

trait ShowInConsole[A] {
  def show(a: A): String
}

object ShowInConsole {
  implicit val booleanShow: ShowInConsole[Boolean] = new ShowInConsole[Boolean] {
    def show(a: Boolean): String = a.toString
  }
  implicit val stringShow: ShowInConsole[String] = new ShowInConsole[String] {
    def show(a: String): String = a
  }
}

object truthy extends App {

  import ToCanIsTruthyOps._

  implicit class ShowOp[A: ShowInConsole](a: A) {
    val F = implicitly[ShowInConsole[A]]

    def show() {
      println(F.show(a))
    }
  }

  def truthyIf[A: CanTruthy, B, C](cond: A)(ifyes: => B)(ifno: => B):B =
    if (cond.truthy) ifyes else ifno

  10.truthy.show()
  List("foo").truthy.show()
  Nil.truthy.show()
  false.truthy.show()

  truthyIf(Nil)("YEAH!")("NO!").show()
}
