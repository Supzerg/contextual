package contextual.tests

import contextual.data._

import shell._
import email._
import binary._
import hex._
import scalac._
import xpath._
import txt._

import estrapade._

object Tests extends TestApp {

  def tests() = {
    test("literal zero byte") {
      bin"00000000"
    }.assert(_.to[List] == List(0.toByte))

    test("literal 255 byte") {
      bin"11111111"
    }.assert(_.to[List] == List(255.toByte))

    test("literal 15 byte") {
      bin"00001111"
    }.assert(_.to[List] == List(15.toByte))
        
    test("literal two-byte sequence") {
      bin"0101010100001111"
    }.assert(_.to[List] == List(85.toByte, 15.toByte))

    test("txt interpolator strips margin") {
      txt"""This is some text
           |This is a second line"""
    }.assert(_ == "This is some text\nThis is a second line")
  
    test("simple XPath expression") {
      xpath"foo / bar / baz"
    }.returns()
    
    test("failing XPath expression") {
      scalac"""
        import contextual.data.xpath._
        xpath"foo ^ bar"
      """
    }.assert(_ == TypecheckError("xpath: could not parse expression: Extra illegal tokens: '^', 'bar'"))
  }
}
/*
  object BinaryTest extends InterpolatorTest[BinParser.type] {
    def interpolator = BinParser

  object EmailTest extends InterpolatorTest[EmailParser.type] {
    def interpolator = EmailParser

    def examples =
      List(
        (() => email"aaa@ddd.com", EmailAddress("aaa@ddd.com")),
        (() => email"complex+test@dep.company.eu", EmailAddress("complex+test@dep.company.eu"))
      )
  }

  object HexTest extends InterpolatorTest[HexParser.type] {
    def interpolator = HexParser

    override def eq(x: Array[Byte], y: Array[Byte]): Boolean =
      x.toList == y.toList

    def examples =
      List(
        (() => hex"00", Array(0.toByte)),
        (() => hex"ff", Array(255.toByte)),
        (() => hex"0f", Array(15.toByte)),
        (() => hex"55", Array(85.toByte)),
        (() => hex"550f", Array(85.toByte, 15.toByte))
      )
  }

  object ShTest extends InterpolatorTest[ShellInterpolator.type] {
    def interpolator = ShellInterpolator

    override def eq(x: Process, y: Process): Boolean =
      x.args.filter(_.nonEmpty).toList == y.args.filter(_.nonEmpty).toList

    def examples =
      List(
        (() => sh"""a b c d ${"e"} ${"f"}""", Process("a","b","c","d","e","f"))
      )
  }

  val tests: List[InterpolatorTest[_]] = List(BinaryTest, EmailTest, HexTest, ShTest)

  def runTests() = tests.foreach(_.runTests())
}*/
