/**************************************************************************
/* This class adds all the DCRAW methods to an Operation.
/*
/* Copyright (c) 2009 by Bernhard Bablok (mail@bablokb.de)
/*
/* This program is free software; you can redistribute it and/or modify
/* it under the terms of the GNU Library General Public License as published
/* by  the Free Software Foundation; either version 2 of the License or
/* (at your option) any later version.
/*
/* This program is distributed in the hope that it will be useful, but
/* WITHOUT ANY WARRANTY; without even the implied warranty of
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/* GNU Library General Public License for more details.
/*
/* You should have received a copy of the GNU Library General Public License
/* along with this program; see the file COPYING.LIB.  If not, write to
/* the Free Software Foundation Inc., 59 Temple Place - Suite 330,
/* Boston, MA  02111-1307 USA
/**************************************************************************/

package org.im4java.core;

/**
   This class subclasses Operation and adds methods for all commandline
   options of dcraw.

   <p>This class is automatically generated by the source-code generator of
   im4java.</p>

   @version $Revision$
   @author  $Author$
*/

public class DCRAWOps extends Operation {

  //////////////////////////////////////////////////////////////////////////////

  /**
     The protected Constructor. You should only use subclasses of DCRAWOps.
  */

  protected DCRAWOps() {
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -v to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps verbose() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-v");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -c to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps write2stdout() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-c");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -e to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps extractThumbnail() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-e");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -z to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps changeTimestamp() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-z");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -i to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps identify() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-i");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -P to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps setDeadpixelFile(String pFile) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-P");

    if (pFile != null) {
      buf.append(pFile.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -K to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps setDarkframeFile(String pFile) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-K");

    if (pFile != null) {
      buf.append(pFile.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -k to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps darkness(Double pDdarkness) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-k");

    if (pDdarkness != null) {
      buf.append(pDdarkness.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -S to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps saturation(Double pSaturation) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-S");

    if (pSaturation != null) {
      buf.append(pSaturation.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -n to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps noiseThreshold(Double pThreshold) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-n");

    if (pThreshold != null) {
      buf.append(pThreshold.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -C to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps colorFactor(Double pRedFactor) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-C");

    if (pRedFactor != null) {
      buf.append(pRedFactor.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -H to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps clipMethod(Integer pMethod) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-H");

    if (pMethod != null) {
      buf.append(pMethod.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -w to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps useCameraWB() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-w");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -a to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps useAverageWB() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-a");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -A to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps calcAverageWB(Integer pLeft, Integer pWidth) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-A");

    if (pLeft != null) {
      buf.append(pLeft.toString());
    }
    buf.append("Integer:pTop");
    if (pWidth != null) {
      buf.append(pWidth.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -r to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps setWB(Double pMult0, Double pMult2) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-r");

    if (pMult0 != null) {
      buf.append(pMult0.toString());
    }
    buf.append("Double:pMult1");
    if (pMult2 != null) {
      buf.append(pMult2.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option +M to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps useColorMatrix() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("+M");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -M to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps ignoreColorMatrix() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-M");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -o to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps setOutputColorSpace(Integer pMethod) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-o");

    if (pMethod != null) {
      buf.append(pMethod.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -o to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps setOutputColorSpace(String pColorProfileFile) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-o");

    if (pColorProfileFile != null) {
      buf.append(pColorProfileFile.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -o to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps setCameraColorSpace(String pColorProfileFile) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-o");

    if (pColorProfileFile != null) {
      buf.append(pColorProfileFile.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -d to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps grayscale() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-d");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -D to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps grayscaleRaw() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-D");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -h to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps halfSize() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-h");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -q to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps setInterpolationMethod(Integer pMethod) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-q");

    if (pMethod != null) {
      buf.append(pMethod.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -f to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps use4ColorRGB() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-f");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -m to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps cleanupPasses(Integer pPasses) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-m");

    if (pPasses != null) {
      buf.append(pPasses.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -W to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps fixedWhiteLevel() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-W");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -b to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps brightness(Double pLevel) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-b");

    if (pLevel != null) {
      buf.append(pLevel.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -4 to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps depth16() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-4");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -T to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps createTIFF() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-T");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -t to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps flipImage(Integer pValue) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-t");

    if (pValue != null) {
      buf.append(pValue.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -j to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps tilt45() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-j");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -s to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps selectImage(Integer pNumber) {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-s");

    if (pNumber != null) {
      buf.append(pNumber.toString());
    }
    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }

  //////////////////////////////////////////////////////////////////////////////

  /**
     Add option -s to the dcraw commandline
     (see the documentation of dcraw for details).
  */

  public DCRAWOps selectAllImages() {

    String       oper;                      // only used in some methods
    StringBuffer buf = new StringBuffer();  // local buffer for option-args
    iCmdArgs.add("-s");

    if (buf.length()>0) {
      iCmdArgs.add(buf.toString());
    }
    return this;
  }


}
