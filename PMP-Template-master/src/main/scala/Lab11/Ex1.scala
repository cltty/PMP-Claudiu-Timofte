package Lab11

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.algorithm.factored.VariableElimination

object Ex1 {
	val citizenIsPresident = Flip(0.00000002)
  	val citizenIsLeftHanded = Flip(0.1)
  	val presidentIsLeftHanded = Flip(0.5)
	
	citizenIsLeftHanded.observe(true)
  	
	println("P(POTUS|LH): " + VariableElimination.probability(citizenIsPresident, true))

	def main(args: Array[String]) {
  	}

}
