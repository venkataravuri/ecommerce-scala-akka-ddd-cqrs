package ecommerce.cart.app

import java.util.UUID

import akka.actor._
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}
import ecommerce.cart._
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration._
import akka.pattern.ask

import scala.concurrent.Await
import scala.util.Success

/**
  * Created by vsubbarravuri on 8/29/16.
  */
object CartBackendApp {

  def main(args: Array[String]): Unit = {
    lazy val log: Logger = LoggerFactory.getLogger(getClass.getName)
    lazy val config: Config = ConfigFactory.load()
    implicit lazy val system = ActorSystem("cart", config)

    val cartActor = system.actorOf(Props[Cart], "cartActor")
    system.actorOf(Props(classOf[Terminator], cartActor), "terminator")

    log.debug(cartActor.path.name)

    implicit val timeout = Timeout(1 minutes)

    val future = cartActor ? CreateCart(CartId("123"),
      List.apply(CartItem(Product("1", 0L, "Camlin", "Gum", 5.0D), 5)))
    val result: Success[String] = Await.result(future, timeout.duration).asInstanceOf[Success[String]]
    log.debug("Result: {}", result)

    val addItemFuture = cartActor ? AddToCart(CartId("123"),
      CartItem(Product("1", 0L, "Camlin", "Gum", 6.0D), 6))
    val addItemResult: Success[String] = Await.result(future, timeout.duration).asInstanceOf[Success[String]]
    log.debug("Result: {}", addItemResult)

    cartActor ! AddToCart(CartId("123"),
      CartItem(Product("1", 0L, "Camlin", "Gum", 7.0D), 7))

    cartActor ! CreateCart(CartId("125"),
      List.apply(CartItem(Product("1", 0L, "Camlin", "Gum", 30.0D), 4)))

    //system.terminate()

  }

  class Terminator(ref: ActorRef) extends Actor with ActorLogging {
    context watch ref

    def receive = {
      case Terminated(_) =>
        log.info("{} has terminated, shutting down system", ref.path)
        context.system.terminate()
    }
  }

}
