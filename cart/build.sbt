lazy val cart = (project in file("."))
  .aggregate(`cart-contracts`, `cart-write-back`, `cart-write-front`)

lazy val `cart-contracts` = (project in file("contracts"))
  .dependsOn("commons")

lazy val `cart-write-back` = (project in file("write-back"))
  .settings(
    mainClass in Compile := Some("ecommerce.cart.app.CartBackendApp"),
    libraryDependencies ++= Seq(
      // level db for local persistence
      "org.iq80.leveldb" % "leveldb" % "0.9",
      "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8")
  )
  .dependsOn(`cart-contracts`)

lazy val `cart-write-front` = (project in file("write-front"))
  .settings(
    mainClass in Compile := Some("ecommerce.cart.app.CartFrontendApp"),
    libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-http-experimental" % "2.4.9")
  )
  .dependsOn(`cart-contracts`)