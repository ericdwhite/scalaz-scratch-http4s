package scratch

package object api {

  type Task[A] = cats.effect.IO[A]

}
