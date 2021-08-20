/*
                           /!\ HUMANS BEWARE /!\
=================================================================================
||                                                                             ||
|| THIS FILE IS GENERATED BY MACHINES AND ANY EDITING BY HUMANS IS PROHIBITED! ||
||                                                                             ||
=================================================================================
 */

package lepus.client.codecs

import lepus.protocol.*
import lepus.protocol.domains.*
import lepus.protocol.classes.*
import lepus.protocol.classes.TxClass.*
import lepus.protocol.constants.*
import lepus.client.codecs.DomainCodecs.*
import scodec.{Codec, Encoder, Decoder}
import scodec.codecs.*

object TxCodecs {

  private val selectCodec: Codec[Select.type] =
    provide(Select)
      .withContext("select method")

  private val selectOkCodec: Codec[SelectOk.type] =
    provide(SelectOk)
      .withContext("selectOk method")

  private val commitCodec: Codec[Commit.type] =
    provide(Commit)
      .withContext("commit method")

  private val commitOkCodec: Codec[CommitOk.type] =
    provide(CommitOk)
      .withContext("commitOk method")

  private val rollbackCodec: Codec[Rollback.type] =
    provide(Rollback)
      .withContext("rollback method")

  private val rollbackOkCodec: Codec[RollbackOk.type] =
    provide(RollbackOk)
      .withContext("rollbackOk method")

  val all: Codec[TxClass] =
    discriminated[TxClass]
      .by(methodId)
      .typecase(MethodId(10), selectCodec)
      .typecase(MethodId(11), selectOkCodec)
      .typecase(MethodId(20), commitCodec)
      .typecase(MethodId(21), commitOkCodec)
      .typecase(MethodId(30), rollbackCodec)
      .typecase(MethodId(31), rollbackOkCodec)
      .withContext("tx methods")

}
