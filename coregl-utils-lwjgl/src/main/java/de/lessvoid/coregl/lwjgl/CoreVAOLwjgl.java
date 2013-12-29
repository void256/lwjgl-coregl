/**
 * Copyright (c) 2013, Jens Hohmuth 
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are 
 * met: 
 * 
 *  * Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer. 
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.lessvoid.coregl.lwjgl;


import static org.lwjgl.opengl.ARBInstancedArrays.glVertexAttribDivisorARB;
import static org.lwjgl.opengl.GL11.GL_BYTE;
import static org.lwjgl.opengl.GL11.GL_DOUBLE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_SHORT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL12.GL_UNSIGNED_INT_2_10_10_10_REV;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.GL_HALF_FLOAT;
import static org.lwjgl.opengl.GL30.GL_UNSIGNED_INT_10F_11F_11F_REV;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glVertexAttribIPointer;
import static org.lwjgl.opengl.GL33.GL_INT_2_10_10_10_REV;
import static org.lwjgl.opengl.GL41.GL_FIXED;

import java.util.Hashtable;
import java.util.Map;

import de.lessvoid.coregl.CoreCheckGL;
import de.lessvoid.coregl.CoreVAO;

/**
 * A Vertex Array Object (VAO).
 * @author void
 */
public class CoreVAOLwjgl implements CoreVAO {
  private final CoreCheckGL checkGL;
  private int vao;
  private final static Map<IntType, Integer> intTypeMap;
  private final static Map<FloatType, Integer> floatTypeMap;

  static {
    intTypeMap = new Hashtable<CoreVAO.IntType, Integer>();
    intTypeMap.put(IntType.BYTE, GL_BYTE);
    intTypeMap.put(IntType.UNSIGNED_BYTE, GL_UNSIGNED_BYTE);
    intTypeMap.put(IntType.SHORT, GL_SHORT);
    intTypeMap.put(IntType.UNSIGNED_SHORT, GL_UNSIGNED_SHORT);
    intTypeMap.put(IntType.INT, GL_INT);
    intTypeMap.put(IntType.UNSIGNED_INT, GL_UNSIGNED_INT);

    floatTypeMap = new Hashtable<CoreVAO.FloatType, Integer>();
    floatTypeMap.put(FloatType.BYTE, GL_BYTE);
    floatTypeMap.put(FloatType.UNSIGNED_BYTE, GL_UNSIGNED_BYTE);
    floatTypeMap.put(FloatType.SHORT, GL_SHORT);
    floatTypeMap.put(FloatType.UNSIGNED_SHORT, GL_UNSIGNED_SHORT);
    floatTypeMap.put(FloatType.INT, GL_INT);
    floatTypeMap.put(FloatType.UNSIGNED_INT, GL_UNSIGNED_INT);
    floatTypeMap.put(FloatType.HALF_FLOAT, GL_HALF_FLOAT);
    floatTypeMap.put(FloatType.FLOAT, GL_FLOAT);
    floatTypeMap.put(FloatType.DOUBLE, GL_DOUBLE);
    floatTypeMap.put(FloatType.FIXED, GL_FIXED);
    floatTypeMap.put(FloatType.INT_2_10_10_10_REV, GL_INT_2_10_10_10_REV);
    floatTypeMap.put(FloatType.UNSIGNED_INT_2_10_10_10_REV, GL_UNSIGNED_INT_2_10_10_10_REV);
    floatTypeMap.put(FloatType.UNSIGNED_INT_10F_11F_11F_REV, GL_UNSIGNED_INT_10F_11F_11F_REV);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#bind()
   */
  @Override
  public void bind() {
    glBindVertexArray(vao);
    checkGL.checkGLError("glBindVertexArray");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#unbind()
   */
  @Override
  public void unbind() {
    glBindVertexArray(0);
    checkGL.checkGLError("glBindVertexArray(0)");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#delete()
   */
  @Override
  public void delete() {
    glDeleteVertexArrays(vao);
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#vertexAttribPointer(int, int, FloatType, int, int)
   */
  @Override
  public void vertexAttribPointer(
      final int index,
      final int size,
      final FloatType type,
      final int stride,
      final int offset) {
    glVertexAttribPointer(index, size, floatTypeMap.get(type), false, stride * 4, offset * 4);
    checkGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#enableVertexAttributeDivisorf(int, int, FloatType, int, int, int)
   */
  @Override
  public void enableVertexAttributeDivisorf(
      final int index,
      final int size,
      final FloatType type,
      final int stride,
      final int offset,
      final int divisor) {
    glVertexAttribPointer(index, size, floatTypeMap.get(type), false, stride * 4, offset * 4);
    glVertexAttribDivisorARB(index, divisor);
    glEnableVertexAttribArray(index);
    checkGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#enableVertexAttribute(int)
   */
  @Override
  public void enableVertexAttribute(final int index) {
    glEnableVertexAttribArray(index);
    checkGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#vertexAttribIPointer(int, int, de.lessvoid.coregl.CoreVAO.IntType, int, int)
   */
  @Override
  public void vertexAttribIPointer(
      final int index,
      final int size,
      final IntType type,
      final int stride,
      final int offset) {
    glVertexAttribIPointer(index, size, intTypeMap.get(type), stride * 4, offset * 4);
    checkGL.checkGLError("glVertexAttribIPointer (" + index + ")");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#enableVertexAttributeDivisori(int, int, IntType, int, int, int)
   */
  @Override
  public void enableVertexAttributeDivisori(
      final int index,
      final int size,
      final IntType type,
      final int stride,
      final int offset,
      final int divisor) {
    glVertexAttribIPointer(index, size, intTypeMap.get(type), stride * 4, offset * 4);
    glVertexAttribDivisorARB(index, divisor);
    glEnableVertexAttribArray(index);
    checkGL.checkGLError("glVertexAttribPointer (" + index + ")");
  }

  /*
   * (non-Javadoc)
   * @see de.lessvoid.coregl.CoreVAO#disableVertexAttribute(int)
   */
  @Override
  public void disableVertexAttribute(final int index) {
    glDisableVertexAttribArray(index);
    checkGL.checkGLError("glDisableVertexAttribArray (" + index + ")");
  }

  /**
   * Create a new VAO. This calls glGenVertexArrays.
   */
  CoreVAOLwjgl(final CoreCheckGL checkGLParam) {
    checkGL = checkGLParam;
    vao = glGenVertexArrays();
    checkGL.checkGLError("glGenVertexArrays");
  }
}
