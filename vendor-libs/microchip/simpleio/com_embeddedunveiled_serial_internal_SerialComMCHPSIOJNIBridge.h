/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge */

#ifndef _Included_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
#define _Included_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    initMCP2200
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_initMCP2200
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    isConnected
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_isConnected
  (JNIEnv *, jobject);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    configureMCP2200
 * Signature: (BJIIZZZZ)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_configureMCP2200
  (JNIEnv *, jobject, jbyte, jlong, jint, jint, jboolean, jboolean, jboolean, jboolean);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    setPin
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_setPin
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    clearPin
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_clearPin
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    readPinValue
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_readPinValue
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    readPin
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_readPin
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    writePort
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_writePort
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    readPort
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_readPort
  (JNIEnv *, jobject);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    readPortValue
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_readPortValue
  (JNIEnv *, jobject);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    selectDevice
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_selectDevice
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    getSelectedDevice
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_getSelectedDevice
  (JNIEnv *, jobject);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    getNumOfDevices
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_getNumOfDevices
  (JNIEnv *, jobject);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    getDeviceInfo
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_getDeviceInfo
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    getSelectedDeviceInfo
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_getSelectedDeviceInfo
  (JNIEnv *, jobject);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    readEEPROM
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_readEEPROM
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    writeEEPROM
 * Signature: (IS)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_writeEEPROM
  (JNIEnv *, jobject, jint, jshort);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    fnRxLED
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_fnRxLED
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    fnTxLED
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_fnTxLED
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    hardwareFlowControl
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_hardwareFlowControl
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    fnULoad
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_fnULoad
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    fnSuspend
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_fnSuspend
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    fnInvertUartPol
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_fnInvertUartPol
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    fnSetBaudRate
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_fnSetBaudRate
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    configureIO
 * Signature: (S)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_configureIO
  (JNIEnv *, jobject, jshort);

/*
 * Class:     com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge
 * Method:    configureIoDefaultOutput
 * Signature: (SS)I
 */
JNIEXPORT jint JNICALL Java_com_embeddedunveiled_serial_internal_SerialComMCHPSIOJNIBridge_configureIoDefaultOutput
  (JNIEnv *, jobject, jshort, jshort);

#ifdef __cplusplus
}
#endif
#endif
