package drew.feels.blue

import cats.effect.IO
import cats.effect.IOApp
import com.evolutiongaming.catshelper.LogOf
import fs2.Pull

import scala.concurrent.duration.DurationInt
import scala.util.Random

object Main extends IOApp.Simple {
  val action: IO[Option[Int]] = for {
    log <- LogOf.slf4j[IO].flatMap(_(getClass))
    res <- IO.sleep(3.second) >> IO.delay(Random.nextBoolean()).map(Option.when(_)(15))
    _   <- log.info(res.toString)
  } yield res

  def run: IO[Unit] = {
    def loop(effect: => IO[Option[Int]]): Pull[IO, Int, Unit] = Pull.eval(effect).flatMap {
      case Some(value) => Pull.output1(value) >> Pull.done
      case None        => loop(effect)
    }

    loop(action).stream.compile.last.timeoutTo(7.seconds, IO.pure(Some(4))).void
  }
}
