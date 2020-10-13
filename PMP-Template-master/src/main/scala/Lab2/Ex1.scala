package Lab2

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._

import scala.collection.mutable.ListBuffer

/*
* 1: Done
* 2: Done
* 3: Done
* 4: Done
* 5: Done
* 6: Done
* 7: Done
* 8: In progress
* 9: done
* */


object Ex1 {
  def main(args: Array[String]) {
//    val m1 = Materie("aasd", 1)
//    var marray = Array(m1)

      def getPersonType(person: Persoana): Unit = {
          person.details()
      }

      def getCertainStudents(studentsList: ListBuffer[Student], year: Integer): ListBuffer[Student] = {
          var stdsList = new ListBuffer[Student]()
          var count = new Integer(0)
          for (student <- studentsList) {
              if(student.getAn() == year) {
                  stdsList += student
              }
          }

          return stdsList
      }

      //0 is equal to undefined.The student does not have any mark yet
      var materii = scala.collection.immutable.Map[String, Integer]()
      materii += ("PMP" -> 0)
      materii += ("WEB" -> 0)
      materii += ("AI" -> 0)
      materii += ("ML" -> 0)
      materii += ("DB" -> 0)

      println(materii.toString())

     //used ListBuffer instead of Array so that I can have a dynamic length
      var std1 = new Student("Mark", "Jacob", 1, materii)
      var std2 = new Student("Rick", "Johnson", 3, materii)
      var std3 = new Student("Morah", "Chuck", 3, materii)

      var students = new ListBuffer[Student]()
      students += std1
      students += std2
      students += std3

      students = getCertainStudents(students, 3)
      println(students) //updated list of students
      //println(getCertainStudents(students, 3))


      println(std1.toString)
      println("Random marks applied")
      std1.generateRandomMarks() //currently does not work
      println(std1.toString)




      //println("Nota la matematica : " + std.getNota("Matematica"))
      //println("Modific nota la matematica..")
      //std.setNota("Matematica", 5)
      //println("Noua nota la matematica : " + std.getNota("Matematica"))

      //getPersonType(std)

  }
}