package Lab12

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.factored.beliefpropagation.BeliefPropagation
import com.cra.figaro.algorithm.sampling.{DisjointScheme, Importance, MetropolisHastings, ProposalScheme}
import com.cra.figaro.language.{Apply, Constant, Flip}
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.compound.If
import scalax.chart.api._

 object Ex1 {
	 val z1 = Flip(0.8)
	 val z2 = Flip(0.2)

	 val x0 = Apply(Normal(0.75, 0.3), (d: Double) => d.max(0).min(1))
	 val y0 = Apply(Normal(0.4, 0.2), (d: Double) => d.max(0).min(1))
	 val x = Flip(x0)
	 val y = Flip(y0)

	 var z = If(x === y, z1, z2)
	 z.observe(false)
	 val veAnswer = VariableElimination.probability(y, true)
	 println("Variable elimination probability: " + veAnswer)

	 def ex1(): Unit = {
	 	 	 println(" Ex 1 Start")
		 //		 Iterating until 3000 instead of 10000, because iterating to 10000 takes forever
		 //		 val data = for  (i <- 10000 to 100000 by 10000) yield {
		 val data = for { i <- 1000 to 3000 by 1000 } yield {
			 var totalSquaredError = 0.0
			 for { j <- 1 to 100 } {
				 val imp = Importance(i, y)
				 imp.start()
				 val impAnswer = imp.probability(y, true)
				 val diff = veAnswer - impAnswer
				 totalSquaredError = totalSquaredError + diff * diff
			 }
			 val rmse = math.sqrt(totalSquaredError / 100)
			 println(i + " samples: RMSE = " + rmse)
			 (i,rmse)
		 }
		 val chart = XYLineChart(data)
		 chart.show()
	 	 	 println(" Ex 1 Done")
	 }

	 def ex2() = {
	 	 println(" Ex 2 Start")
		 //		 Iterating until 3000 instead of 10000, because iterating to 10000 takes forever
		 //		 val data = for  (i <- 10000 to 100000 by 10000) yield {
		 val data = for  (i <- 10000 to 3000 by 10000) yield {
			 var totalSquaredError = 0.0
			 for (j <- 1 to 100) {
				 val mh = MetropolisHastings(i, ProposalScheme.default, y)
				 mh.start()
				 val mhAnswer = mh.probability(y, true)
				 val diff = veAnswer - mhAnswer
				 totalSquaredError += diff * diff
			 }
			 val rmse = math.sqrt(totalSquaredError / 100)
			 println(i + " samples(ex2): RMSE = " + rmse)
			 (i, rmse)
		 }

		 val chart = XYLineChart(data)
		 chart.show()
	 	 println(" Ex 2 Done")
	 }

	 def ex3() = {
		 println(" Ex 3 Start")
		 val imp = Importance(1000000, y)
		 imp.start()
		 val impAnswer = imp.probability(y, true)
		 val diff = veAnswer - impAnswer
		 var totalSquaredError = diff*diff
		 val rmse = math.sqrt(totalSquaredError / 100)
		 println("Difference = "+ (veAnswer - impAnswer))
		 println("RMSE = "+ (rmse))

		 println(" Ex 3 Done")
	 }


	 def ex4() = {
		 println(" Ex 4 Start")
		 val hm = MetropolisHastings(10000000, ProposalScheme.default ,y)
		 hm.start()
		 val hmAnswer = hm.probability(y, true)
		 val diff = veAnswer - hmAnswer
		 var totalSquaredError = diff*diff
		 val rmse = math.sqrt(totalSquaredError / 100)
		 println("Difference =  "+ (veAnswer - hmAnswer))
		 println("RMSE = "+ (rmse))
		 println(" Ex 4 Done")
	 }

	 def ex5() = {
		 println(" Ex 5 Start")
		 val scheme = DisjointScheme(
			 0.1 -> (() => ProposalScheme(z1)),
			 0.1 -> (() => ProposalScheme(z2)),
			 0.8 -> (() => ProposalScheme(x, y))
		 )
		 val hm = MetropolisHastings(10000000, scheme ,y)
		 hm.start()
		 val hmAnswer = hm.probability(y, true)
		 val diff = veAnswer - hmAnswer
		 var totalSquaredError = diff*diff
		 val rmse = math.sqrt(totalSquaredError / 100)
		 println("Difference =  "+ (veAnswer - hmAnswer))
		 println("RMSE = "+ (rmse))
		 println(" Ex 5 Done")
	 }

	 def main(args: Array[String]) {
		 ex1()
		 ex2()
		 ex3()
		 ex4()
		 ex5()
	 }
 }
