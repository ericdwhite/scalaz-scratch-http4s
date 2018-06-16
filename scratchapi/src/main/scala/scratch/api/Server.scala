package scratch.api

import cats.effect.Effect
import cats.implicits._

import fs2.StreamApp
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext

object App extends StreamApp[Task] {

  import scala.concurrent.ExecutionContext.Implicits.global

  override def stream(args: List[String], requestShutdown: Task[Unit]) = ServerStream.stream[Task]
}

object ServerStream {

  def helloWorldService[F[_]: Effect] = new HelloWorldService[F].service

  def stream[F[_]: Effect](implicit ec: ExecutionContext) = for {
    init <- BlazeBuilder[F]
      .bindHttp(9090, "0.0.0.0")
      .mountService(helloWorldService, "/")
      .serve
  } yield init
}
