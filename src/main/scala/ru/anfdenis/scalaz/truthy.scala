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

  final def thruthy: Boolean = F.truthys(self)
}

object ToCanIsTruthyOps {
  implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]) =
    new CanTruthyOps[A] {
      def self = v

      implicit def F: CanTruthy[A] = ev
    }
}

object Main extends App {

  import ToCanIsTruthyOps._

  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
    case 0 => false
    case _ => true
  })


  val res1 = 10.truthy
}
