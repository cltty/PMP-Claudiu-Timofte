package Lab13

import com.cra.figaro.language._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.library.compound._
import com.cra.figaro.algorithm.factored.beliefpropagation.BeliefPropagation
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.algorithm.OneTimeMPE
import com.cra.figaro.algorithm.factored.beliefpropagation.MPEBeliefPropagation
import com.cra.figaro.algorithm.sampling.{MetropolisHastingsAnnealer}

object Ex1 {
	//Clasa state ce are ca valoare confident si possession.
	abstract class State {
 		val confident: Element[Boolean]
 		def possession: Element[Boolean] = If(confident, Flip(0.7), Flip(0.3))
 	}

	//Functia pentru a genera state-ul initial
	class InitialState() extends State {
 		val confident = Flip(0.4)
 	}

	//Functia ce genereaza un state urmator bazat ce un state curent
	class NextState(current: State) extends State {
		val confident = If(current.confident, Flip(0.6), Flip(0.3))
 	}

 	//Functia ce creeaza secventa de state-uri
	def stateSequence(n: Int): List[State] = {
		if (n == 0) List(new InitialState())
		else {
			val last :: rest = stateSequence(n - 1)
			new NextState(last) :: last :: rest
		}
 	}


	//Modificam functia run din carte pentru a afisa ce valoare tinde sa aiba variabila confident si ce observatie a fost facuta pentru fiecare iteratie in parte 
 	def timing(algorithm: OneTimeMPE, obsSeq: List[Boolean], algType: String) {
		println(algType)
		val stateSeq = stateSequence(obsSeq.length)
		//Facem observ-uri pentru fiecare state din secventa de stateuri
		for { i <- 0 until obsSeq.length } {
			stateSeq(i).possession.observe(obsSeq(obsSeq.length - 1 - i))
		}
	
		//Pornim OneTimeMPE
		algorithm.start()

		//Pentru fiecare state afisam ce valoare tinde sa aiba variabila confident si ce observatie a fost facuta pentru fiecare iteratie in parte  
		for { i <- 0 until obsSeq.length } {
			println(" i -> " + i)
			println(" -> " +algorithm.mostLikelyValue(stateSeq(i).confident))
			println(" -> " + obsSeq(i))
			println()			
		}
	
		algorithm.kill()
	}


	//Initializam numarul de iteratii
	val steps = 1000
	val obsSeq = List.fill(steps)(scala.util.Random.nextBoolean())
	println(steps + ": " + steps)

	def foo() {
 		timing(MPEVariableElimination(), obsSeq, "Variable elimination")
 		timing(MPEBeliefPropagation(1000), obsSeq, "Belief propagation")
		timing(MetropolisHastingsAnnealer(1000, ProposalScheme.default, Schedule.default(1.0)), obsSeq, "Metropolis Gastings Annealer")
	}

	def main(args: Array[String]) {
		foo()
	}
}
