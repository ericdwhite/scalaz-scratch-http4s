package scratch.api

import cats.effect.Effect
import cats.syntax.functor._
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext.Implicits.global

trait Srv[F[_]] {
  def start: F[_]
}

object Srv {

  def default[F[_]: Effect]: Srv[F] = new Http4sServer[F]()

  //def helloWorldService[F[_]: Effect] = new HelloWorldService[F].service

  class Http4sServer[F[_]: Effect] extends Srv[F] with Http4sDsl[F] {

    override def start: F[_] = 
      BlazeBuilder[F]
        .bindHttp(9090, "127.0.0.1")
        .mountService(service, "/")
        .start
        //.mountService(helloWorldService, "/")

    private def service: HttpService[F] = HttpService[F] {
      case GET -> Root / "ping" => Ok("pong")
    }
  }
}
