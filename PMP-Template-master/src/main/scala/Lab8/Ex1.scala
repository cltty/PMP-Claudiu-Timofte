package Lab8

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.compound.{*, CPD, OneOf}
import com.cra.figaro.library.collection.Container
import scala.reflect.runtime.universe.If

object Ex1 {
	def main(args: Array[String]) {
		 class ResearchAndDevelopment{
			 val design = Flip(0.60)
			 val test = Select(0.3-> 'easy , 0.5->'medium, 0.2->'hard)
			 val implement = Flip(0.97)
			 val improve = Flip(0.55)
		 }

		class HumanResources{
			 val women = Flip(0.68)
			 val betweenAges = Select(0.3 -> 'morethan25, 0.2 -> 'morethan35, 0.2 -> 'morethan45, 0.3 -> 'else)
		}

		class Production{
			val volumeOfProduction = Select(0.2->100, 0.4->1000,0.4->10000)
			val productFinal = Flip(0.67)
			val inTermToDelivery = CPD(productFinal,
				false->Flip(0.1),
				true ->Flip(0.9)
			)

		}

		class Sales(products:List[Production]){
			 private val deliveriContainer=Container(products.map(_.productFinal): _x)
			private val  succes= deliveriContainer.foldLeft(0.0)(_ max_)
			val succesDelivery= Normal(succes, 0.1)
		}

		class Finance(production: Production)
		{  val fundsForProduction=Flip(0.40)
			val inLimits= If(production.volumeOfProduction,
			Select(0.6 -> "Ok", 0.4 -> "Not ok"),
			Select(0.2 -> "Ok", 0.2 -> " Not ok")
			)

		}

		class Firm(sales: Sales,production: Production,
							 humanResources: HumanResources, researchAndDev: ResearchAndDevelopment,
							 finance: Finance)
		{ val goodProductDelivery=(finance.fundsForProduction,researchAndDev.design,
		humanResources.betweenAges,production.inTermToDelivery,sales.succesDelivery,
			(*, *, OneOf('morethan25), *, *) -> Constant('zero),
			(*, *, OneOf('morethan35), *, *) -> Constant('zero),
			(*, *, OneOf('morethan45),*,*) -> Constant('zero))

			val goodTest = CPD(researchAndDev.test, humanResources.women,
				('hard, *) -> Flip(0.57),
				(*,'medium) -> Flip(0.30))
		}

		val sales = new Sales(List(production(0),production(1)))
		val researchAndDevelopment = new ResearchAndDevelopment
		val production = Array.fill(10)(new Production)
		val production1 = new Production
		val humanResource = new HumanResources
		val finance = new Finance(production1)

	}
}
