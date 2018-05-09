package io.atleastonce.transform

import java.io.BufferedReader
import better.files._
import File._
import better.files.Dsl.SymbolicOperations

object ExpandData extends App {
  val numbereddna = root/"Users"/"winston"/"projects"/"avro-ivr-spikes"/"data"/"numbereddnaredux.csv"
  val tranlistexpand = root/"Users"/"winston"/"projects"/"avro-ivr-spikes"/"data"/"transactionlistexpand.csv"

  def addTransactionIdExpand(reader: BufferedReader): Unit = {
    var data: String = null
    tranlistexpand << "id,node"
    while ({data = reader.readLine(); data != null}) {
      val d = data.split(',')
      val sp = d(2).split('|')

      sp.distinct.foreach(i => {
        if (i != null && i.nonEmpty) {
          val ospi = ScriptPoint.spdata.get(i)
          ospi match {
            case Some(spi) =>
              tranlistexpand << d(0) + "," + spi.description
            case _ =>
          }
        }
      })
    }
  }

  for {
    reader <- numbereddna.bufferedReader
  } yield addTransactionIdExpand(reader)
}

case class ScriptPointInfo(id: Int, description: String, longDescription: String, functionalGroup: String,
                           transactions: scala.collection.mutable.ListBuffer[Int] = scala.collection.mutable.ListBuffer[Int]()) {
  val iddescription: String = id + description
}

object ScriptPoint {
  val spdata = scala.collection.mutable.Map[String, ScriptPointInfo]()
  private val dsp = root/"Users"/"winston"/"projects"/"avro-ivr-spikes"/"data"/"descScriptPoint1.csv"

  def addFreq(reader: BufferedReader): Unit = {
    var data: String = null
    while ({data = reader.readLine(); data != null}) {
      val d = data.split(',')

      spdata.getOrElseUpdate(d(0), ScriptPointInfo(d(0).toInt, d(1), d(2), d(3)))
    }
  }

  for {
    reader <- dsp.bufferedReader
  } yield addFreq(reader)
}