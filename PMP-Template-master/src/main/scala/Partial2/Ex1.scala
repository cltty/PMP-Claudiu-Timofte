package Partial2

import com.cra.figaro.language._
import com.cra.figaro.library.compound.{If, CPD, RichCPD, OneOf}
import com.cra.figaro.algorithm.factored.VariableElimination

object Ex1
{

    // class Weather {
    //     val weatherArray: Array[Element[Symbol]] = Array.fill(hours)(Constant('sunny))
    //     def setInitialState() {
    //         weather(0) = Select(0.5 -> 'sunny, 0.3 -> 'cloudy, 0.2 -> 'rainy)
    //     }
    // }

    // class Umbrella {
    //     val umbrellaArray: Array[Element[Symbol]] = Array.fill(hours)(Constant('no))
    // }

    // def aDouaVariantaCuClase() {
    //     val hours = 8 
    //     // aceleasi explicatii ca la varianta fara clase.
    //     println("Varianta 2, cu clase")
    //     val w = new Weather()
    //     w.setInitialState()
    //     val u = new Umbrella()

    //      for { hour <- 1 until hours } {
    //         weather(hour) = CPD(weather(hour - 1),
	// 			'sunny -> Select(0.1 -> 'rainy, 0.3 -> 'cloudy, 0.6 -> 'sunny),
	// 			'cloudy -> Select(0.35 -> 'rainy, 0.5 -> 'cloudy, 0.15 -> 'sunny),
	// 			'rainy -> Select(0.45 -> 'rainy, 0.4 -> 'cloudy, 0.15 -> 'sunny)
    //         )
	// 	}

    //     for { hour <- 0 until hours } {   
    //         umbrella(hour) = CPD(weather(hour),
	// 			'sunny -> Select(0.15 -> 'yes, 0.85 -> 'no),
	// 			'cloudy -> Select(0.65 -> 'yes, 0.35 -> 'no),
	// 			'rainy -> Select(0.75 -> 'yes, 0.25 -> 'no)
    //         )
	// 	}

    //     weather(3).observe('cloudy)
    //     weather(4).observe('cloudy)
    //     println("După 5 ore, dacă în ultimele două ore a fost innorat, care e probabilitatea să iau umbrela la ora 6 :" + VariableElimination.probability(umbrella(5), 'yes))
        
    //     weather(3).unobserve
    //     weather(4).unobserve

    //     weather(3).unobserve
    //     weather(4).unobserve

    //     umbrella(4).observe('no)
	//     println("Dacă la ora 5 nu iau umbrela, care este probabilitatea ca acum 3 ore să fi plouat :" + VariableElimination.probability(weather(1),'ploios))

    // }

	def main(args: Array[String])
	{
    
        // Prima varianta, fara clase
        val hours = 8 

        // valorile vremii in functie de ora
        val weather: Array[Element[Symbol]] = Array.fill(hours)(Constant('sunny))

        // valoarea expresie "Iau umbrela"
        val umbrella: Array[Element[Symbol]] = Array.fill(hours)(Constant('no))
        // initializarea primul element din Array-ul weather
		weather(0) = Select(0.5 -> 'sunny, 0.3 -> 'cloudy, 0.2 -> 'rainy) // se pune random cu anumite probabilitati prima valoare in learn
		
        // 
        for { hour <- 1 until hours } {
            // probabilitatea curenta weather(hour) depinde de cea anterioara
			weather(hour) = CPD(weather(hour - 1),
				'sunny -> Select(0.1 -> 'rainy, 0.3 -> 'cloudy, 0.6 -> 'sunny),
				'cloudy -> Select(0.35 -> 'rainy, 0.5 -> 'cloudy, 0.15 -> 'sunny),
				'rainy -> Select(0.45 -> 'rainy, 0.4 -> 'cloudy, 0.15 -> 'sunny))
		}

        for { hour <- 0 until hours }
		{   
            // in functie de vremea de afara, luam umbrela sau nu
            // se observa ca nu se initializeza primul element, ci se bazeaza pe array ul weather
			umbrella(hour) = CPD(weather(hour),
				'sunny -> Select(0.15 -> 'yes, 0.85 -> 'no),
				'cloudy -> Select(0.65 -> 'yes, 0.35 -> 'no),
				'rainy -> Select(0.75 -> 'yes, 0.25 -> 'no)
            )
		}

        weather(3).observe('cloudy)
        weather(4).observe('cloudy)
        println("După 5 ore, dacă în ultimele două ore a fost innorat, care e probabilitatea să iau umbrela la ora 6 :" + VariableElimination.probability(umbrella(5), 'yes))
        
        weather(3).unobserve
        weather(4).unobserve

        umbrella(4).observe('no)
	    println("Dacă la ora 5 nu iau umbrela, care este probabilitatea ca acum 3 ore să fi plouat :" + VariableElimination.probability(weather(1),'ploios))


        // aDouaVariantaCuClase()
		
    }
}
