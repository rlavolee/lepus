/*
 * Copyright 2021 Hossein Naderi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lepus.codecs

import lepus.wire.FrameCodec
import lepus.protocol.domains.*
import lepus.protocol.frame.Frame
import org.scalacheck.Gen
import org.scalacheck.Prop.*

import java.nio.ByteBuffer

import DomainGenerators.*

class FramesTest extends CodecTest {

  override def scalaCheckTestParameters =
    super.scalaCheckTestParameters
      .withMinSuccessfulTests(2000)
      .withMaxDiscardRatio(10)

  val method: Gen[Frame.Method] = for {
    ch <- channelNumber
    vl <- AllClassesDataGenerator.methods
  } yield Frame.Method(ch, vl)

  val header: Gen[Frame.Header] = for {
    ch <- channelNumber
    cls <- classIds
    size <- Gen.posNum[Long]
    props <- properties
  } yield Frame.Header(ch, cls, size, props)

  val body: Gen[Frame.Body] = for {
    ch <- channelNumber
    pl <- Gen.asciiStr.map(_.getBytes).map(ByteBuffer.wrap)
  } yield Frame.Body(ch, pl)

  val heartbeat: Gen[Frame.Heartbeat.type] =
    Gen.const(Frame.Heartbeat)

  val frames: Gen[Frame] =
    Gen.oneOf(method, header, body, header)

  property("All frame codecs must be reversible") {
    forAll(frames) { f =>
      val res = for {
        enc <- FrameCodec.frame.encode(f)
        dec <- FrameCodec.frame.decode(enc)
      } yield dec

      assertReversed(f, res)
    }
  }

}