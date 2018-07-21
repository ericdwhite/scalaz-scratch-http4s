package scratch.api

import scalaz.ioeffect.SafeApp
import scalaz.ioeffect.IO
import scalaz.ioeffect.Void
import scalaz.ioeffect.catz._
import scalaj.http.Http

object App extends SafeApp {

  def run (args: List[String]): IO[Void, ExitStatus] =
    for {
      svr <- bootstrap.attempt.map(_.fold(handleException(_), _ => IO.now(0)))
      exitCode <- svr.attempt.map(_.fold(_ => 2, e => e))
      _ <- loadEndPoint.attempt
    } yield handleExit(exitCode)

  def loadEndPoint : IO[Void, Unit] = 
    // Required to trigger http4s to fully startup
    IO.sync[Void, Unit](Http("http://127.0.0.1:9090/").asString)

  def handleExit (exitCode: Int): ExitStatus =
    exitCode match {
      case 0 => ExitStatus.DoNotExit
      case _ => ExitStatus.ExitNow(exitCode) 
    }

  def handleException (t: Throwable) : IO[Void, Int] =
    for {
      _ <- IO.sync[Void, Unit](t.printStackTrace)
    } yield 1 

  def bootstrap: IO[Throwable, _] = 
    Srv.default.start
}
