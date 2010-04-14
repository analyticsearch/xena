/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hssf.record;

import org.apache.poi.util.LittleEndian;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

/**
 * Title:  Record Input Stream<P>
 * Description:  Wraps a stream and provides helper methods for the construction of records.<P>
 *
 * @author Jason Height (jheight @ apache dot org)
 */
public final class RecordInputStream extends InputStream {
	/** Maximum size of a single record (minus the 4 byte header) without a continue*/
	public final static short MAX_RECORD_DATA_SIZE = 8224;
	private static final int INVALID_SID_VALUE = -1;

	private InputStream in;
	private short currentSid;
	private short currentLength = -1;
	private short nextSid;

	private final byte[] data = new byte[MAX_RECORD_DATA_SIZE];
	private short recordOffset;
	private long pos;

  private boolean autoContinue = true;

  public RecordInputStream(InputStream in) throws RecordFormatException {
    this.in = in;
    try {
      nextSid = LittleEndian.readShort(in);
      //Don't increment the pos just yet (technically we are at the start of
      //the record stream until nextRecord is called).
    } catch (IOException ex) {
      throw new RecordFormatException("Error reading bytes", ex);
    }
  }

	/** This method will read a byte from the current record*/
	public int read() {
		checkRecordPosition(LittleEndian.BYTE_SIZE);

		byte result = data[recordOffset];
		recordOffset += LittleEndian.BYTE_SIZE;
		pos += LittleEndian.BYTE_SIZE;
		return result;
	}

  public short getSid() {
    return currentSid;
  }

  public short getLength() {
    return currentLength;
  }

  public short getRecordOffset() {
    return recordOffset;
  }

  public long getPos() {
    return pos;
  }

  public boolean hasNextRecord() {
    return nextSid != INVALID_SID_VALUE;
  }

  /** Moves to the next record in the stream.
   *
   * <i>Note: The auto continue flag is reset to true</i>
   */
  public void nextRecord() throws RecordFormatException {
    if ((currentLength != -1) && (currentLength != recordOffset)) {
      System.out.println("WARN. Unread "+remaining()+" bytes of record 0x"+Integer.toHexString(currentSid));
    }
    currentSid = nextSid;
    pos += LittleEndian.SHORT_SIZE;
    autoContinue = true;
    try {
      recordOffset = 0;
      currentLength = LittleEndian.readShort(in);
      if (currentLength > MAX_RECORD_DATA_SIZE)
        throw new RecordFormatException("The content of an excel record cannot exceed "+MAX_RECORD_DATA_SIZE+" bytes");
      pos += LittleEndian.SHORT_SIZE;
      in.read(data, 0, currentLength);

      //Read the Sid of the next record
      if (in.available() < EOFRecord.ENCODED_SIZE) {
          if (in.available() > 0) {
              // some scrap left over?
              // ex45582-22397.xls has one extra byte after the last record
              // Excel reads that file OK
          }
          nextSid = INVALID_SID_VALUE;
      } else {
          nextSid = LittleEndian.readShort(in);
          if (nextSid == INVALID_SID_VALUE) {
              throw new RecordFormatException("Found sid " + nextSid + " after record with sid 0x"
                      + Integer.toHexString(currentSid).toUpperCase());
          }
      }
    } catch (IOException ex) {
      throw new RecordFormatException("Error reading bytes", ex);
    }
  }

  public void setAutoContinue(boolean enable) {
    this.autoContinue = enable;
  }

  public boolean getAutoContinue() {
    return autoContinue;
  }

	private void checkRecordPosition(int requiredByteCount) {

		if (remaining() < requiredByteCount) {
			if (isContinueNext() && autoContinue) {
				nextRecord();
			} else {
			   throw new ArrayIndexOutOfBoundsException();
			}
		}
	}

	/**
	 * Reads an 8 bit, signed value
	 */
	public byte readByte() {
		checkRecordPosition(LittleEndian.BYTE_SIZE);

		byte result = data[recordOffset];
		recordOffset += LittleEndian.BYTE_SIZE;
		pos += LittleEndian.BYTE_SIZE;
		return result;
	}

	/**
	 * Reads a 16 bit, signed value
	 */
	public short readShort() {
		checkRecordPosition(LittleEndian.SHORT_SIZE);

		short result = LittleEndian.getShort(data, recordOffset);
		recordOffset += LittleEndian.SHORT_SIZE;
		pos += LittleEndian.SHORT_SIZE;
		return result;
	}

	public int readInt() {
		checkRecordPosition(LittleEndian.INT_SIZE);

		int result = LittleEndian.getInt(data, recordOffset);
		recordOffset += LittleEndian.INT_SIZE;
		pos += LittleEndian.INT_SIZE;
		return result;
	}

	public long readLong() {
		checkRecordPosition(LittleEndian.LONG_SIZE);

		long result = LittleEndian.getLong(data, recordOffset);
		recordOffset += LittleEndian.LONG_SIZE;
		pos += LittleEndian.LONG_SIZE;
		return result;
	}

	/**
	 * Reads an 8 bit, unsigned value
	 */
	public short readUByte() {
		return (short) (readByte() & 0x00FF);
	}

	/**
	 * Reads a 16 bit, unsigned value.
	 * @return
	 */
	public int readUShort() {
		checkRecordPosition(LittleEndian.SHORT_SIZE);

		int result = LittleEndian.getUShort(data, recordOffset);
		recordOffset += LittleEndian.SHORT_SIZE;
		pos += LittleEndian.SHORT_SIZE;
		return result;
	}

	public double readDouble() {
		checkRecordPosition(LittleEndian.DOUBLE_SIZE);
		long valueLongBits = LittleEndian.getLong(data, recordOffset);
		double result = Double.longBitsToDouble(valueLongBits);
		if (Double.isNaN(result)) {
			throw new RuntimeException("Did not expect to read NaN"); // (Because Excel typically doesn't write NaN
		}
		recordOffset += LittleEndian.DOUBLE_SIZE;
		pos += LittleEndian.DOUBLE_SIZE;
		return result;
	}

	public String readString() {
		int requestedLength = readUShort();
		byte compressFlag = readByte();
		return readStringCommon(requestedLength, compressFlag == 0);
	}
	/**
	 *  given a byte array of 16-bit unicode characters, compress to 8-bit and
	 *  return a string
	 *
	 * { 0x16, 0x00 } -0x16
	 *
	 * @param requestedLength the length of the final string
	 * @return                                     the converted string
	 * @exception  IllegalArgumentException        if len is too large (i.e.,
	 *      there is not enough data in string to create a String of that
	 *      length)
	 */
	public String readUnicodeLEString(int requestedLength) {
		return readStringCommon(requestedLength, false);
	}

	public String readCompressedUnicode(int requestedLength) {
		return readStringCommon(requestedLength, true);
	}

	private String readStringCommon(int requestedLength, boolean pIsCompressedEncoding) {
		// Sanity check to detect garbage string lengths
		if (requestedLength < 0 || requestedLength > 0x100000) { // 16 million chars?
			throw new IllegalArgumentException("Bad requested string length (" + requestedLength + ")");
		}
		char[] buf = new char[requestedLength];
		boolean isCompressedEncoding = pIsCompressedEncoding;
		int curLen = 0;
		while(true) {
			int availableChars =isCompressedEncoding ?  remaining() : remaining() / LittleEndian.SHORT_SIZE;
			if (requestedLength - curLen <= availableChars) {
				// enough space in current record, so just read it out
				while(curLen < requestedLength) {
					char ch;
					if (isCompressedEncoding) {
						ch = (char)readUByte();
					} else {
						ch = (char)readShort();
					}
					buf[curLen] = ch;
					curLen++;
				}
				return new String(buf);
			}
			// else string has been spilled into next continue record
			// so read what's left of the current record
			while(availableChars > 0) {
				char ch;
				if (isCompressedEncoding) {
					ch = (char)readUByte();
				} else {
					ch = (char)readShort();
				}
				buf[curLen] = ch;
				curLen++;
				availableChars--;
			}
			if (!isContinueNext()) {
				throw new RecordFormatException("Expected to find a ContinueRecord in order to read remaining " 
						+ (requestedLength-curLen) + " of " + requestedLength + " chars");
			}
			if(remaining() != 0) {
				throw new RecordFormatException("Odd number of bytes(" + remaining() + ") left behind");
			}
			nextRecord();
			// note - the compressed flag may change on the fly
			byte compressFlag = readByte();
			isCompressedEncoding = (compressFlag == 0); 
		}
	}

  /** Returns an excel style unicode string from the bytes reminaing in the record.
   * <i>Note:</i> Unicode strings differ from <b>normal</b> strings due to the addition of
   * formatting information.
   *
   * @return The unicode string representation of the remaining bytes.
   */
  public UnicodeString readUnicodeString() {
    return new UnicodeString(this);
  }

  /** Returns the remaining bytes for the current record.
   *
   * @return The remaining bytes of the current record.
   */
  public byte[] readRemainder() {
    int size = remaining();
    byte[] result = new byte[size];
    System.arraycopy(data, recordOffset, result, 0, size);
    recordOffset += size;
    pos += size;
    return result;
  }

  /** Reads all byte data for the current record, including any
   *  that overlaps into any following continue records.
   *
   *  @deprecated Best to write a input stream that wraps this one where there is
   *  special sub record that may overlap continue records.
   */
  public byte[] readAllContinuedRemainder() {
    //Using a ByteArrayOutputStream is just an easy way to get a
    //growable array of the data.
    ByteArrayOutputStream out = new ByteArrayOutputStream(2*MAX_RECORD_DATA_SIZE);

    while (isContinueNext()) {
      byte[] b = readRemainder();
      out.write(b, 0, b.length);
      nextRecord();
    }
    byte[] b = readRemainder();
    out.write(b, 0, b.length);

    return out.toByteArray();
  }

  /** The remaining number of bytes in the <i>current</i> record.
   *
   * @return The number of bytes remaining in the current record
   */
  public int remaining() {
    return (currentLength - recordOffset);
  }

  /** Returns true iif a Continue record is next in the excel stream
   *
   * @return True when a ContinueRecord is next.
   */
  public boolean isContinueNext() {
    return (nextSid == ContinueRecord.sid);
  }
}
