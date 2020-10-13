package Lab2

import spire.std.map

import scala.util.Random

abstract class Persoana {
  def details()
}

case class Student(var nume: String, var prenume: String, var an: Integer, var materii:  Map[String, Integer]) extends Persoana{
  def details(): Unit = {
    println("I am a student")
  }
  def setNota(materie: String, nota: Integer): Unit = {
    materii -= materie
    materii += (materie -> nota)
  }
  def getNota(materie: String): Integer={
    return materii(materie)
  }
  def getNume(): String={
    return nume
  }
  def generateRandomMarks(): Unit={
    //not working
    materii.transform((key, value) => value + Random.nextInt(10))

  }
  def getAn(): Integer={
    return an
  }
  def addMaterie(materie: String, nota: Integer): Unit = {
    materii += (materie -> nota)
  }
}

case class Profesor(var nume: String, var prenume: String, var materie: String) {
  def details(): Unit = {
    println("I am a teacher")
  }
}
