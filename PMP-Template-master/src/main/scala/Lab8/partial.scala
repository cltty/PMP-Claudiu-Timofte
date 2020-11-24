package partial

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._

object partial {
    //persoana uita sa seteze alarma
    val alarmNotSet = Flip(0.1)

    //probabiltatea sa se trezeasca tarziu conditionat de 10%prob sa seteze alarma
    val wakeUpLate = CPD(alarmNotSet,
        (true) -> Flip(0.1),
        (false) -> Flip(0.9)
    )

    //20% prob ca autobuzul sa intarzie
    val busDelayed = Flip(0.2)

    //probabilitate sa ajunga la timp la munca dupa tabel
    val arriveToWork = CPD(wakeUpLate, busDelayed,
        (true, true) -> Flip(0.1, 0.9),
        (true, false) -> Flip(0.3, 0.7),
        (false, true) -> Flip(0.2, 0.8)
        (false, false) -> Flip(0.9, 0.1),
    )



	def main(args: Array[String]) {
        wakeUpLate.observe(true)
        println("Probabilitate sa ajung la timp : " + arriveToWork)
        wakeUpLate.unobserve()

        //probabilitate sa adoarma la loc
        wakeUpLate.observe(true)
        println("Probabilitate sa adoarma la loc : " + wakeUpLate)
        wakeUpLate.unobserve()

    }
}