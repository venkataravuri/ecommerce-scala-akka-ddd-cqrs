package ddd.cqrs

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.persistence.{PersistentActor, Recovery}
import akka.contrib.pattern.ReceivePipeline
import akka.contrib.pattern.ReceivePipeline.Inner

import scala.util.{Success, Try}

/**
  * Created by vsubbarravuri on 8/31/16.
  */

trait AggregateRootBase extends PersistentActor with ActorLogging {

  def id = self.path.name

  /**
    * Event handler, not invoked during recovery.
    */
  def handle(event: DomainEvent) {
    acknowledgeCommandProcessed(event)
  }

  def acknowledgeCommandProcessed(event: DomainEvent, result: Try[Any] = Success("Command processed. Thank you!")) {
    log.debug("Acknowledge response to sender({}).", sender)
    sender ! result
  }

}

