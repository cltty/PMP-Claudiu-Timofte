package Lab10

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.algorithm.factored.VariableElimination

object Ex4 {
  val length = 10
  val investment: Array[Element[Double]] = Array.fill(length)(Constant(0))
  val profit: Array[Element[Double]] = Array.fill(length)(Constant(0))
  val capital: Array[Element[Double]] = Array.fill(length)(Constant(0))
  
  investment(0) = Constant(0)
  capital(0) = Constant(100)
  profit(0) = Constant(0)


  def investmentPolicy1() {
    //investment policy : 30% of Capital from previous step
    //profit : 120% of Investment from previous step
    for { step <- 1 until length } {
      investment(step) = Apply(capital(step - 1), (i: Double) => 30 * i / 100)
      profit(step) = Apply(investment(step), (i: Double) => 120 * i / 100)
      capital(step) =  Apply(capital(step -1), profit(step), investment(step),
                  (currentCapital: Double, currentProfit: Double, currentInvestment: Double) => currentCapital + currentProfit - currentInvestment)
    }

    val algorithm = VariableElimination(capital(length - 1))
    algorithm.start()
    println("Earned capital using investment policy 1 : " + algorithm.mean(capital(length - 1)))
    algorithm.stop()
  }

  def investmentPolicy2() {
    //investment policy : 40% of Capital from previous step
    //profit : 120% of Investment from previous step
    for { step <- 1 until length } {
      investment(step) = Apply(capital(step - 1), (i: Double) => 40 * i / 100)
      profit(step) = Apply(investment(step), (i: Double) => 120 * i / 100)
      capital(step) =  Apply(capital(step -1), profit(step), investment(step),
                  (currentCapital: Double, currentProfit: Double, currentInvestment: Double) => currentCapital + currentProfit - currentInvestment)
    }

    val algorithm = VariableElimination(capital(length - 1))
    algorithm.start()
    println("Earned capital using investment policy 2 : " + algorithm.mean(capital(length - 1)))
    algorithm.stop()
  }

  def investmentPolicy3() {
    //investment policy : 50% of Capital from previous step
    //profit : 120% of Investment from previous step
    for { step <- 1 until length } {
      investment(step) = Apply(capital(step - 1), (i: Double) => 50 * i / 100)
      profit(step) = Apply(investment(step), (i: Double) => 120 * i / 100)
      capital(step) =  Apply(capital(step -1), profit(step), investment(step),
                  (currentCapital: Double, currentProfit: Double, currentInvestment: Double) => currentCapital + currentProfit - currentInvestment)
    }

    val algorithm = VariableElimination(capital(length - 1))
    algorithm.start()
    println("Earned capital using investment policy 2 : " + algorithm.mean(capital(length - 1)))
    algorithm.stop()
  }

  def main(args: Array[String]) {
    investmentPolicy1()
    investmentPolicy2()
    investmentPolicy3() 
  }
}
