package ru.anfdenis.scalaz

/**
 * Denis Anfertev
 * 10/10/13 10:42 AM
 */
object day0 extends App {

  //  def sum(xs: List[Int]): Int = xs.foldLeft(0)(_ + _)


  //  object IntMonoid {
  //    def mappend(a: Int, b: Int): Int = a + b
  //
  //    def mzero: Int = 0
  //  }

  //  def sum(xs:List[Int]):Int = xs.foldLeft(IntMonoid.mzero)(IntMonoid.mappend)

  trait Monoid[A] {
    def mappend(a1: A, a2: A): A

    def mzero: A
  }

  object Monoid {
    implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
      def mappend(a: Int, b: Int): Int = a + b

      def mzero: Int = 0
    }
    implicit val StringMonoid: Monoid[String] = new Monoid[String] {
      def mappend(a: String, b: String): String = a + b

      def mzero: String = ""
    }
  }

  //  def sum(xs: List[Int], m: Monoid[Int]): Int = xs.foldLeft(m.mzero)(m.mappend)

  //  def sum[A](xs: List[A])(implicit m: Monoid[A]): A = xs.foldLeft(m.mzero)(m.mappend)

  //  def sum[A: Monoid](xs: List[A]): A = {
  //    val m = implicitly[Monoid[A]]
  //    xs.foldLeft(m.mzero)(m.mappend)
  //  }

  val multiMonoid: Monoid[Int] = new Monoid[Int] {
    def mappend(a: Int, b: Int): Int = a * b

    def mzero: Int = 1
  }

    object FoldLeftList {
      def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
    }

  trait FoldLeft[F[_]] {
    def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
  }

  object FoldLeft {
    implicit val foldLeftList: FoldLeft[List] = new FoldLeft[List] {
      def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
    }
  }

  def sum[M[_] : FoldLeft, A: Monoid](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val fl = implicitly[FoldLeft[M]]
    fl.foldLeft(xs, m.mzero, m.mappend)
  }

  def plus[A: Monoid](a: A, b: A) = implicitly[Monoid[A]].mappend(a, b)

  trait MonoidOp[A] {
    val F: Monoid[A]
    val value: A

    def |+|(a2: A) = F.mappend(value, a2)
  }

  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    val F = implicitly[Monoid[A]]
    val value = a
  }

  println(plus(1, 2))
  println(1 |+| 2)
  println("a" |+| "b")
  println(sum(List(1, 2, 3, 4)))
  println(sum(List("a", "b", "c", "d")))
  println(sum(List(1, 2, 3, 4))(FoldLeft.foldLeftList, multiMonoid))

}
