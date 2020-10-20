package Lab3

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.compound._

object Ex1 {
	private val fever = Flip(0.06)
	private val cough = Flip(0.04)

	private val covid = CPD(fever, cough,
		(false, false) -> Flip(0.025),
		(false, true) -> Flip(0.07),
		(true, false) -> Flip(0.08),
		(true, true) -> Flip(0.5)
	)

	private val chanceOfCovid = CPD(covid,
		false -> Flip(0.4),
		true -> Flip(0.5)
	)

	def main(args: Array[String]): Unit = {
		cough.observe(false)
		fever.observe(false)

		val noSymptoms = VariableElimination(chanceOfCovid, fever, cough)
			noSymptoms.start()
			println("Sansa de covid neavand simptome : " + noSymptoms.probability(chanceOfCovid, true))
			noSymptoms.kill()

		cough.unobserve()
		fever.unobserve()

		chanceOfCovid.observe(true)
		val symptoms = VariableElimination(chanceOfCovid, fever, cough)
		symptoms.start()

		println("Sansa de tuse avand covid : " + symptoms.probability(cough, true))
		println("Sansa de febra avand covid : " + symptoms.probability(fever, true))
		symptoms.kill();
	}
}