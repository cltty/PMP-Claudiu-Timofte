import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._
import scala.collection.immutable.Range


object Lab9{
  def main(args: Array[String]){
		val length = 10
		val studiat: Array[Element[Boolean]] = Array.fill(length)(Constant(false))
		val notaBoolean: Array[Element[Boolean]] = Array.fill(length)(Constant(false))
		val trecutBoolean: Array[Element[Boolean]] = Array.fill(length)(Constant(false))
		val nota: Array[Element[Int]]=Array.fill(length)(Constant(0))
		val trecut: Array[Element[Boolean]]=Array.fill(length)(Constant(false))

		studiat(0) = Flip(0.5)
		for{ i <- 1 until length} { studiat(i) = If(studiat(i-1),Flip(0.5),Flip(0.2)) }
		for{ i <- 0 until length} { notaBoolean(i) = If(studiat(i),Flip(0.8),Flip(0.2)) } 
		for{ i <- 0 until length} { trecutBoolean(i) = If(notaBoolean(i),Flip(1.0),Flip(0.0)) } 
	
		for{ i <- 0 until length} {
			nota(i) = If(studiat(i),1,9) // 1 ptr range -5; 9 ptr range +5
		} 

		for{ i <- 0 until length} { trecut(i) = Chain(nota(i), (valoare: Int) => if (valoare > 4) Constant(true) else Constant(false) )} 
	

		notaBoolean(1).observe(true)
		notaBoolean(2).observe(true)
		notaBoolean(3).observe(true)
		notaBoolean(4).observe(true)
		notaBoolean(5).observe(false)
		notaBoolean(6).observe(true)
		notaBoolean(7).observe(true)
		notaBoolean(8).observe(true)
		println("Probabilitate promovabilitate ultimul test : " + VariableElimination.probability(notaBoolean(9),true))
		nota(1).addConstraint((n:Int) => if(n > 4) 10; else 2)
		nota(2).addConstraint((n:Int) => if(n > 4) 10; else 2)
		nota(3).addConstraint((n:Int) => if(n > 4) 10; else 2)
		nota(4).addConstraint((n:Int) => if(n > 4) 10; else 2)
		nota(5).addConstraint((n:Int) => if(n > 4) 10; else 2)
		nota(6).addConstraint((n:Int) => if(n > 4) 10; else 2)
		nota(7).addConstraint((n:Int) => if(n > 4) 10; else 2)
		nota(8).addConstraint((n:Int) => if(n > 4) 10; else 2)
		println("Probabilitate promovabilitate ultimul test : " + VariableElimination.probability(trecut(9),true))
	}
}