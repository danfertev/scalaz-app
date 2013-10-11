package ru.anfdenis.scalaz

/**
 * Denis Anfertev
 * 10/11/13 6:50 PM
 */
trait Functor[F[_]] {
  def fmap[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {

  implicit class FunctorOp[F[_] : Functor, A](value: F[A]) {
    def fmap[B](f: A => B): F[B] = implicitly[Functor[F]].fmap(value)(f)
  }

  implicit val listFunctor = new Functor[List] {
    def fmap[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }
}

object day2 extends App {

  import Functor._

  println(List(1, 2, 3, 4).fmap(_ + 1))
}
