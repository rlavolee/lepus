/*
                           /!\ HUMANS BEWARE /!\
=================================================================================
||                                                                             ||
|| THIS FILE IS GENERATED BY MACHINES AND ANY EDITING BY HUMANS IS PROHIBITED! ||
||                                                                             ||
=================================================================================
 */

package lepus.codecs

import lepus.protocol.classes.*
import lepus.protocol.domains.*
import lepus.protocol.constants.*
import ArbitraryDomains.given
import org.scalacheck.Gen
import org.scalacheck.Arbitrary

object ConfirmDataGenerator {

  val selectGen: Gen[ConfirmClass.Select] =
    for {
      arg0 <- Arbitrary.arbitrary[NoWait]
    } yield ConfirmClass.Select(arg0)

  given Arbitrary[ConfirmClass.Select] = Arbitrary(selectGen)

  val selectOkGen: Gen[ConfirmClass.SelectOk.type] =
    Gen.const(ConfirmClass.SelectOk)

  given Arbitrary[ConfirmClass.SelectOk.type] = Arbitrary(selectOkGen)

  val classGen: Gen[ConfirmClass] = Gen.oneOf(selectGen, selectOkGen)

  given Arbitrary[ConfirmClass] = Arbitrary(classGen)

}