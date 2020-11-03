package Lab5

import com.cra.figaro.library.atomic.discrete
import com.cra.figaro.language.Chain
import com.cra.figaro.library.compound.{RichCPD, OneOf, *}
import com.cra.figaro.language.{Flip, Constant, Apply}
import com.cra.figaro.algorithm.factored.VariableElimination
import scala.collection.mutable.ListBuffer
import com.cra.figaro.library.compound._

object Ex1 {
	def main(args: Array[String]) {
		//EX4
		val cards = List(5, 4, 3, 2, 1)

		val player1Card1 = discrete.Uniform(cards:_*)

		//Al doilea player poate alege orice carte inafara de cartea primului player
		val player2Card1 =
			Chain(player1Card1, (card: Int) =>
				discrete.Uniform(cards.filter(_ != card ):_*))

		val player1Bet1 =
			RichCPD(player1Card1,
				OneOf(5, 4) -> Flip(0.9),
				OneOf(3) -> Flip(0.5),
				* -> Flip(0.4)
			)

		val player2Bet =
			RichCPD(player2Card1, player1Bet1,
				(OneOf(5, 4), *) -> Flip(0.9),
				(OneOf(3), OneOf(false)) -> Flip(0.6),
				(OneOf(3), OneOf(true)) -> Flip(0.4),
				(*, *) -> Flip(0.1))

		val player1Bet2 =
			Apply(player1Card1, player1Bet1, player2Bet,
				(card: Int, bet11: Boolean, bet2: Boolean) =>
					!bet11 && bet2 && (card == 5 || card == 4))
		//player1Gain reprezinta gain-ul primului player in urma jocului
		val player1Gain =
			Apply(player1Card1, player2Card1, player1Bet1, player2Bet, player1Bet2,
				(card1: Int, card2: Int, bet11: Boolean,
				 bet2: Boolean, bet12: Boolean) =>
					if (!bet11 && !bet2) 0.0
					else if (bet11 && !bet2) 1.0
					else if (!bet11 && bet2 && !bet12) -1.0
					else if (card1 > card2) 2.0
					else -2.0)


		player1Card1.observe(4)
		player1Bet1.observe(true)
		val alg1 = VariableElimination(player1Gain)
		alg1.start()
		alg1.stop()
		println("Expected gain for betting:" + alg1.mean(player1Gain))
		player1Bet1.observe(false)
		val alg2 = VariableElimination(player1Gain)
		alg2.start()
		alg2.stop()
		println("Expected gain for passing:" + alg2.mean(player1Gain))
		player1Card1.unobserve()
		player1Bet1.unobserve()


		player2Card1.observe(3)
		player1Bet1.observe(true)
		player2Bet.observe(true)
		val alg3 = VariableElimination(player1Gain)
		alg3.start()
		alg3.stop()
		println("Expected gain for betting:" + alg3.mean(player1Gain))
		player2Bet.observe(false)
		val alg4 = VariableElimination(player1Gain)
		alg4.start()
		alg4.stop()
		println("Expected gain for passing:" + alg4.mean(player1Gain))

	}
}