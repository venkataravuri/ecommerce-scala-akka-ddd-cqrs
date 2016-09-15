package ecommerce.cart.app

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import ecommerce.cart.{CartId, CartItem, CreateCart, Product}

import scala.concurrent.Await
import scala.util.Success
import scala.concurrent.duration._
import akka.pattern.ask
/**
  * Created by vsubbarravuri on 9/6/16.
  */
object CartFrontendApp extends App {

  implicit val system = ActorSystem("CartFrontendSystem")
  val cartFrontendActor = system.actorOf(Props[CartFrontendActor], name = "cartFrontendActor") // the local actor
  println(cartFrontendActor.path.name)

  cartFrontendActor ! "CREATE_CART"

}


class CartFrontendActor extends Actor {

  val cartRemoteActor = context.actorSelection("akka.tcp://cart@127.0.0.1:2552/user/cartActor")

  implicit val timeout = Timeout(1 minutes)


  def receive = {
    case "CREATE_CART" => {
      println(cartRemoteActor.toString())
      val future = cartRemoteActor ? CreateCart(CartId("124"),
        List.apply(CartItem(Product("1", 0L, "Camlin", "Gum", 9.0D), 11)))

      val result: Success[String] = Await.result(future, timeout.duration).asInstanceOf[Success[String]]

      println("Result: {}", result)    }
  }

}
