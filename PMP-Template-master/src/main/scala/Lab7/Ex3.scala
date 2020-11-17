package Lab7

import com.cra.figaro.language.Select
import com.cra.figaro.library.atomic.continuous.Uniform
import com.cra.figaro.language.{Element, Chain, Apply}
import com.cra.figaro.library.collection.Container
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.sampling.Importance

object Ex3
{
    def main(args: Array[String])
    {
        //lista -> 3 4 3 5 4 3 5 4 5 3 4 5 4 5 3 4 3 4
        val par = List(3, 4, 3, 5, 4, 3, 5, 4, 5, 3, 4, 5, 4, 5, 3, 4, 3, 4)   
        val skill = Uniform(0.0, 8.0/13.0)
        val shots = Array.tabulate(18)((hole: Int) => Chain(skill, (s: Double) =>
                                Select(s/8.0 -> (par(hole)-2),
                                       s/2.0 -> (par(hole)-1),
                                       s -> par(hole),
                                       (4.0/5.0) * (1.0 - (13.0 * s)/8.0) -> (par(hole)+1),
                                       (1.0/5.0) * (1.0 - (13.0 * s)/8.0) -> (par(hole)+2))))
        val vars = for { i <- 0 until 18} yield shots(i)
        val sum = Container(vars:_*).reduce(_+_)
        def greaterThan80(s: Int) = s >= 80
        def greaterThan03(s: Double) = s >= 0.3
        skill.addConstraint(s => if(s >= 0.3) 1.0; else 0.0)
        println(Importance.probability(sum, greaterThan80 _))
    }
}
