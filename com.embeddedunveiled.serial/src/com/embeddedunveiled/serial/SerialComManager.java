/**
 * Author : Rishi Gupta
 * 
 * This file is part of 'serial communication manager' library.
 *
 * The 'serial communication manager' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * The 'serial communication manager' is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with serial communication manager. If not, see <http://www.gnu.org/licenses/>.
 */

package com.embeddedunveiled.serial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class is the root of scm library. The applications should call methods defined in this class only.</p>
 * <p>The WIKI page for this project is here : http://www.embeddedunveiled.com/ </p>
 */
public final class SerialComManager {

	/** Relase version of SCM library. */
	public static final String JAVA_LIB_VERSION = "1.0.4";

	/** Pre-defined enum constants for baud rate values. */
	public enum BAUDRATE {
		B0(0), B50(50), B75(75), B110(110), B134(134), B150(150), B200(200), B300(300), B600(600), B1200(1200),
		B1800(1800), B2400(2400), B4800(4800), B9600(9600), B14400(14400), B19200(19200), B28800(28800), B38400(38400),
		B56000(56000), B57600(57600), B115200(115200), B128000(128000), B153600(153600), B230400(230400), B256000(256000), 
		B460800(460800), B500000(500000), B576000(576000), B921600(921600), B1000000(1000000), B1152000(1152000),
		B1500000(1500000),B2000000(2000000), B2500000(2500000), B3000000(3000000), B3500000(3500000), B4000000(4000000),
		BCUSTOM(251);
		private int value;
		private BAUDRATE(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}

	/** Pre-defined enum constants for number of data bits in a serial frame. */
	public enum DATABITS {
		DB_5(5), DB_6(6), DB_7(7), DB_8(8);
		private int value;
		private DATABITS(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}

	/** Pre-defined enum constants for number of stop bits in a serial frame. */
	// SB_1_5(4) is 1.5 stop bits.
	public enum STOPBITS {
		SB_1(1), SB_1_5(4), SB_2(2);
		private int value;
		private STOPBITS(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}

	/** Pre-defined enum constants for enabling type of parity in a serial frame. */
	public enum PARITY {
		P_NONE(1), P_ODD(2), P_EVEN(3), P_MARK(4), P_SPACE(5);
		private int value;
		private PARITY(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}

	/** Pre-defined enum constants for controlling data flow between DTE and DCE. */
	public enum FLOWCONTROL {
		NONE(1), HARDWARE(2), SOFTWARE(3);
		private int value;
		private FLOWCONTROL(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}

	/** Pre-defined enum constants for defining endianness of data to be sent over serial port. */
	public enum ENDIAN {
		E_LITTLE(1), E_BIG(2), E_DEFAULT(3);
		private int value;
		private ENDIAN(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}

	/** Pre-defined enum constants for defining number of bytes given data can be represented in. */
	public enum NUMOFBYTES {
		/** Integer value requires 16 bits. */
		NUM_2(2),
		/** Integer value requires 32 bits. */
		NUM_4(4);
		private int value;
		private NUMOFBYTES(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}

	/** Pre-defined enum constants for defining file transfer protocol to use. */
	public enum FTPPROTO {
		/** XMODEM protocol with three variants checksum, CRC and 1k. */
		XMODEM(1),
		/** YMODEM protocol with two variants CRC + 128 data bytes and CRC + 1k block. */
		YMODEM(2),
		/** coming soon */
		ZMODEM(3);
		private int value;
		private FTPPROTO(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}
	
	/** Pre-defined enum constants for defining variant of file transfer protocol to use. */
	public enum FTPVAR {
		/** Checksum for XMODEM protocol, 128 data byte block for YMODEM.  *///TODO FOR zmodem
		DEFAULT(0),
		/** Checksum variant for XMODEM protocol (1 byte checksum with total block size of 132).  */
		CHKSUM(1),
		/** CRC variant for XMODEM protocol (2 byte CRC with total block size of 133).  */
		CRC(2),
		/** 1k variant for X/Y MODEM protocol (2 byte CRC with total block size of 1024). */ //TODO DOUBLE CHK THIS
		VAR1K(3),
		/** 128 byte data variant for YMODEM protocol (//TODO).  */
		VAR128B(4);
		private int value;
		private FTPVAR(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}
	
	/** Pre-defined enum constants for defining translation mode file transfer protocol to use. */ //TODO MAKE IT MORE COMPREHENSIVE 
	public enum FTPMODE {
		/** Specify translating of data as per the operating system on which receiver application is running. */
		TEXT(1),
		/** Specify no translation on data received. */
		BINARY(2);
		private int value;
		private FTPMODE(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}
	
	/** Pre-defined enum constants for defining behavior of byte stream. */
	public enum SMODE {
		/** Read will block till data is available. */
		BLOCKING(1), 
		/** Read will not block till data is available. */
		NONBLOCKING(2);
		private int value;
		private SMODE(int value) {
			this.value = value;	
		}
		public int getValue() {
			return this.value;
		}
	}
	
	public static boolean DEBUG = true;
	
	/** The value indicating that operating system is unknown to SCM library. Integer constant with value 0x00. */
	public static final int OS_UNKNOWN  = 0x00;
	
	/** The value indicating the Linux operating system. Integer constant with value 0x01. */
	public static final int OS_LINUX    = 0x01;
	
	/** The value indicating the Windows operating system. Integer constant with value 0x02. */
	public static final int OS_WINDOWS  = 0x02;
	
	/** The value indicating the Solaris operating system. Integer constant with value 0x03. */
	public static final int OS_SOLARIS  = 0x03;
	
	/** The value indicating the Mac OS X operating system. Integer constant with value 0x04. */
	public static final int OS_MAC_OS_X = 0x04;
	
	/** The value indicating the FreeBSD operating system.. Integer constant with value 0x05. */
	public static final int OS_FREEBSD  = 0x05;
	
	/** The value indicating the NetBSD operating system. Integer constant with value 0x06. */
	public static final int OS_NETBSD   = 0x06;
	
	/** The value indicating the OpenBSD operating system. Integer constant with value 0x07. */
	public static final int OS_OPENBSD  = 0x07;
	
	/** The value indicating the IBM AIX operating system. Integer constant with value 0x08. */
	public static final int OS_IBM_AIX  = 0x08;
	
	/** The value indicating the HP-UX operating system. Integer constant with value 0x09. */
	public static final int OS_HP_UX    = 0x09;
	
	/** The value indicating the Android operating system. Integer constant with value 0x0A. */
	public static final int OS_ANDROID  = 0x0A;

	/** Default number of bytes (1024) to read from serial port. */
	public static final int DEFAULT_READBYTECOUNT = 1024;

	/** Clear to send mask bit constant for UART control line. */
	public static final int CTS =  0x01;  // 0000001
	
	/** Data set ready mask bit constant for UART control line. */
	public static final int DSR =  0x02;  // 0000010
	
	/** Data carrier detect mask bit constant for UART control line. */
	public static final int DCD =  0x04;  // 0000100
	
	/** Ring indicator mask bit constant for UART control line. */
	public static final int RI  =  0x08;  // 0001000
	
	/** Loop indicator mask bit constant for UART control line. */
	public static final int LOOP = 0x10;  // 0010000
	
	/** Request to send mask bit constant for UART control line. */
	public static final int RTS =  0x20;  // 0100000
	
	/** Data terminal ready mask bit constant for UART control line. */
	public static final int DTR  = 0x40;  // 1000000
	
	/** The value indicating that a serial port has been added into system. */
	public static final int PORT_ADDED =  0x01;
	
	/** The value indicating that a serial port has been removed from system. */
	public static final int PORT_REMOVED  = 0x02;

	/** Operating system name as returned by JVM. */
	public static final String osName = System.getProperty("os.name").toLowerCase().trim();
	
	/** Operating system architecture as returned by JVM. */
	public static final String osArch = System.getProperty("os.arch").toLowerCase().trim();
	
	/** User home directory as returned by JVM. */
	public static final String userHome = System.getProperty("user.home");
	
	/** Temp/tmp directory on this system as returned by JVM. */
	public static final String javaTmpDir = System.getProperty("java.io.tmpdir");
	
	/** File separator identifier for this operating system as returned by JVM. */
	public static final String fileSeparator = System.getProperty("file.separator");
	
	/** Java library path for this system as returned by JVM. */
	public static final String javaLibPath = System.getProperty("java.library.path").toLowerCase();

	/** Maintain integrity and consistency among all operations, therefore synchronize them for
	 *  making structural changes. This array can be sorted array if scaled to large scale. */
	private ArrayList<SerialComPortHandleInfo> handleInfo = new ArrayList<SerialComPortHandleInfo>();
	private List<SerialComPortHandleInfo> mPortHandleInfo = Collections.synchronizedList(handleInfo);
	
	private SerialComJNINativeInterface mNativeInterface = null;
	private SerialComErrorMapper mErrMapper = null;
	private SerialComCompletionDispatcher mEventCompletionDispatcher = null;
	private Object lock = new Object();
	private static int osType = -1;
	private static final String HEXNUM = "0123456789ABCDEF";

	/**
	 * <p>Allocates a new SerialComManager object. Identify operating system type, initialize various 
	 * classes and initiate loading of native library.</p>
	 */
	public SerialComManager() {
		String osNameMatch = osName.toLowerCase();
		if(osNameMatch.contains("linux")) {
			osType = OS_LINUX;
		}else if(osNameMatch.contains("windows")) {
			osType = OS_WINDOWS;
		}else if(osNameMatch.contains("solaris") || osNameMatch.contains("sunos")) {
			osType = OS_SOLARIS;
		}else if(osNameMatch.contains("mac os") || osNameMatch.contains("macos") || osNameMatch.contains("darwin")) {
			osType = OS_MAC_OS_X;
		}else if(osNameMatch.contains("freebsd") || osNameMatch.contains("free bsd")) {
			osType = OS_FREEBSD;
		}else if(osNameMatch.contains("netbsd")) {
			osType = OS_NETBSD;
		}else if(osNameMatch.contains("openbsd")) {
			osType = OS_OPENBSD;
		}else if(osNameMatch.contains("aix")) {
			osType = OS_IBM_AIX;
		}else if(osNameMatch.contains("hp-ux")) {
			osType = OS_HP_UX;
		}else {
			osType = OS_UNKNOWN;
		}

		mErrMapper = new SerialComErrorMapper(osType);
		mNativeInterface = new SerialComJNINativeInterface();
		mEventCompletionDispatcher = new SerialComCompletionDispatcher(mNativeInterface, mErrMapper, mPortHandleInfo);
	}

	/**
	 * <p>Gives library versions of java and native modules.</p>
	 * 
	 * @return Java and C library versions implementing this library.
	 * @throws SerialComException 
	 */
	public String getLibraryVersions() throws SerialComException {
		String version = null;
		SerialComRetStatus retStatus = new SerialComRetStatus(0);
		String nativeLibversion = mNativeInterface.getNativeLibraryVersion(retStatus);
		if(nativeLibversion != null) {
			version = "Java lib version: " + JAVA_LIB_VERSION + "\n" + "Native lib version: " + nativeLibversion;
		}else if(retStatus.status < 0){
			throw new SerialComException("getLibraryVersions()", mErrMapper.getMappedError(retStatus.status));
		}else {
		}
		return version;
	}

	/**
	 * <p>Gives operating system type as identified by this library. To interpret return integer see constants defined
	 * SerialComManager class.</p>
	 * 
	 * @return Operating system type as identified by the scm library
	 * @throws IllegalStateException if application calls this method without first creating an instance of SerialComManager class
	 */
	public static int getOSType() throws IllegalStateException {
		if(osType == -1) {
			throw new IllegalStateException("getOSType() " + SerialComErrorMapper.ERR_SCM_DOES_NOT_INSTANTIATED);
		}
		return osType;
	}

	/**
	 * <p>Returns all available UART style ports available on this system, otherwise an empty array of strings, if no serial style port is
	 * found in the system.</p>
	 * 
	 * <p>This should find regular UART ports, hardware/software virtual COM ports, port server, USB-UART converter, bluetooth/3G dongles, 
	 * ports connected through USB hub/expander, serial card, serial controller, pseudo terminals, printers and virtual modems etc.</p>
	 * 
	 * <p>Note : The BIOS may ignore UART ports on a PCI card and therefore BIOS settings has to be corrected if you modified
	 * default BIOS in OS.</p>
	 * 
	 * <p>This method may be used to find valid serial ports for communications before opening them for writing more robust code.</p>
	 * 
	 * @return Available UART style ports name for windows, full path with name for Unix like OS, returns empty array if no ports found.
	 * @throws SerialComException if an I/O error occurs.
	 */
	public String[] listAvailableComPorts() throws SerialComException {
		SerialComPortsList scpl = new SerialComPortsList(this.mNativeInterface);
		SerialComRetStatus retStatus = new SerialComRetStatus(1);
		String[] availablePorts = scpl.listAvailableComPorts(retStatus);
		
		if(availablePorts != null) {
			return availablePorts;
		}else {
			if(retStatus.status == 1) {
				return new String[]{};
			}else if(retStatus.status < 0) {
				throw new SerialComException("listAvailableComPorts()", mErrMapper.getMappedError(retStatus.status));
			}else {
			}
		}
		return null;		
	}

	/** 
	 * <p>This opens a serial port for communication. If an attempt is made to open a port which is already opened exception in throw.</p>
	 * 
	 * <p>For Linux and Mac OS X, if exclusiveOwnerShip is true, before this method return, the caller will either be exclusive owner
	 * or not. If the caller is successful in becoming exclusive owner than all the attempt to open the same port again will cause
	 * native code to return error. Note that a root owned process (root user) will still be able to open the port.</p>
	 * 
	 * <p>The exclusiveOwnerShip must be true for Windows as it does not allow sharing COM ports. An exception is thrown if 
	 * exclusiveOwnerShip is set to false.</p>
	 * 
	 * <p>For Solaris, exclusiveOwnerShip should be set to false as of now.</p>
	 * 
	 * <p>Sometimes, DTR acts as a modem on-hook/off-hook control for other end. By default when the SCM opens a port, it sets both
	 *  DTR and RTS signals. So just in case other end was waiting for its DTS line to be asserted can see this end as online.
	 *  Modern modems are highly flexible in their dependency, working and configurations. It is best to consult modem manual.</p>
	 * 
	 * <p>This method is thread safe.</p>
	 * 
	 * @param portName name of the port to be opened for communication
	 * @param enableRead allows application to read bytes from this port
	 * @param enableWrite allows application to write bytes to this port
	 * @param exclusiveOwnerShip application wants to become exclusive owner of this port or not
	 * @return handle of the port successfully opened
	 * @throws SerialComException if both enableWrite and enableRead are false, trying to become exclusive owner when port is already opened
	 * @throws IllegalArgumentException if portName is null or invalid length
	 */
	public long openComPort(String portName, boolean enableRead, boolean enableWrite, boolean exclusiveOwnerShip) throws SerialComException {
		long handle = 0;
		if(portName == null) {
			throw new IllegalArgumentException("openComPort(), " + SerialComErrorMapper.ERR_PORT_NAME_FOR_PORT_OPENING);
		}
		portName = portName.trim();
		if(portName.length() == 0) {
			throw new IllegalArgumentException("openComPort(), " + SerialComErrorMapper.ERR_PORT_NAME_FOR_PORT_OPENING);
		}
		if((enableRead == false) && (enableWrite == false)) {
			throw new SerialComException(portName, "openComPort()",  "Enable at-least read, write or both.");
		}
		
		// For windows COM port can not be shared, so throw exception
		if(getOSType() == OS_WINDOWS) {
			if(exclusiveOwnerShip == false) {
				throw new SerialComException(portName, "openComPort()",  SerialComErrorMapper.ERR_WIN_OWNERSHIP);
			}
		}
		
		synchronized(lock) {
			/* Try to reduce transitions from java to JNI layer as it is possible here by performing check in java layer itself. */
			if(exclusiveOwnerShip == true) {
				for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
					if(mInfo.containsPort(portName)) {
						throw new SerialComException(portName, "openComPort()", SerialComErrorMapper.ERR_PORT_ALREADY_OPEN);
					}
				}
			}
	
			handle = mNativeInterface.openComPort(portName, enableRead, enableWrite, exclusiveOwnerShip);
			if(handle < 0) {
				throw new SerialComException(portName, "openComPort()",  mErrMapper.getMappedError(handle));
			}
	
			boolean added = mPortHandleInfo.add(new SerialComPortHandleInfo(portName, handle, null, null, null));
			if(added != true) {
				closeComPort(handle);
				throw new SerialComException(portName, "openComPort()",  SerialComErrorMapper.ERR_SCM_NOT_STORE_PORTINFO);
			}
		}

		return handle;
	}

	/**
	 * <p>Close the serial port. Application should unregister listeners if it has registered any.</p>
	 * 
	 * <p>DTR line is dropped when port is closed.</p>
	 * 
	 * <p>This method is thread safe.</p>
	 * 
	 * @param handle of the port to be closed
	 * @return Return true on success in closing the port false otherwise
	 * @throws SerialComException if invalid handle is passed or when it fails in closing the port
	 * @throws IllegalStateException if application tries to close port while data/event listener exist
	 */
	public boolean closeComPort(long handle) throws SerialComException {
		boolean handlefound = false;
		SerialComPortHandleInfo mHandleInfo = null;

		synchronized(lock) {
			for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
				if(mInfo.containsHandle(handle)) {
					handlefound = true;
					mHandleInfo = mInfo;
					break;
				}
			}
	
			if(handlefound == false) {
				throw new SerialComException("closeComPort()", SerialComErrorMapper.ERR_WRONG_HANDLE);
			}
	
			if(mHandleInfo.getDataListener() != null) {
				/* Proper clean up requires that, native thread should be destroyed before closing port. */
				throw new IllegalStateException("closeComPort() " + SerialComErrorMapper.ERR_CLOSE_WITHOUT_UNREG_DATA);
			}
			if(mHandleInfo.getEventListener() != null) {
				throw new IllegalStateException("closeComPort() " + SerialComErrorMapper.ERR_CLOSE_WITHOUT_UNREG_EVENT);
			}
	
			int ret = mNativeInterface.closeComPort(handle);
			// native close() returns 0 on success
			if(ret < 0) {
				throw new SerialComException("closeComPort()",  mErrMapper.getMappedError(ret));
			}
	
			/* delete info about this port/handle from global info arraylist. */
			mPortHandleInfo.remove(mHandleInfo);
		}

		return true;
	}

	/**
	 * <p>This method writes bytes from the specified byte type buffer. If the method returns false, the application
	 * should try to re-send bytes. The data has been transmitted out of serial port when this method returns.</p>
	 * 
	 * <p>If large amount of data need to be written, consider breaking it into chunks of data of size for example
	 * 2KB each.</p>
	 * 
	 * <p>Writing empty buffer i.e. zero length array is not allowed.</p>
	 * 
	 * <p>It should be noted that on Linux system reading from the terminal after a disconnect causes an end-of-file
	 * condition, and writing causes an EIO error to be returned. The terminal device must be closed and reopened to
	 * clear the condition.</p>
	 * 
	 * @param handle handle of the opened port on which to write bytes
	 * @param buffer byte type buffer containing bytes to be written to port
	 * @param delay  time gap between transmitting two successive bytes
	 * @return true on success, false on failure or if empty buffer is passed
	 * @throws SerialComException if an I/O error occurs.
	 * @throws IllegalArgumentException if buffer is null or delay is negative
	 */
	public boolean writeBytes(long handle, byte[] buffer, int delay) throws SerialComException {
		if(buffer == null) {
			throw new IllegalArgumentException("writeBytes(), " + SerialComErrorMapper.ERR_WRITE_NULL_DATA_PASSED);
		}
		if(buffer.length == 0) {
			return false;
		}
		if(delay < 0) {
			throw new IllegalArgumentException("writeBytes(), " + SerialComErrorMapper.ERR_DELAY_CAN_NOT_NEG);
		}
		int ret = mNativeInterface.writeBytes(handle, buffer, delay);
		if(ret < 0) {
			throw new SerialComException("write",  mErrMapper.getMappedError(ret));
		}
		return true;
	}

	/**
	 * <p>Utility method to call writeBytes without delay between successive bytes.</p>
	 * <p>The writeBytes(handle, buffer) method for class SerialComManager has the same effect
	 * as: </p>
	 * <p>writeBytes(handle, buffer, 0) </p>
	 * 
	 * @param handle handle of the opened port on which to write bytes
	 * @param buffer byte type buffer containing bytes to be written to port
	 * @return true on success, false on failure or if empty buffer is passed
	 * @throws SerialComException if an I/O error occurs.
	 * @throws IllegalArgumentException if buffer is null
	 */
	public boolean writeBytes(long handle, byte[] buffer) throws SerialComException {
		return writeBytes(handle, buffer, 0);
	}

	/**
	 * <p>This method writes a single byte to the specified port. The data has been transmitted out of serial port when 
	 * this method returns.</p>
	 * 
	 * @param handle handle of the opened port on which to write byte
	 * @param data byte to be written to port
	 * @return true on success false otherwise
	 * @throws SerialComException if an I/O error occurs.
	 */
	public boolean writeSingleByte(long handle, byte data) throws SerialComException {
		return writeBytes(handle, new byte[] { data }, 0);
	}

	/**
	 * <p>This method writes a string to the specified port. The library internally converts string to byte buffer. 
	 * The data has been transmitted out of serial port when this method returns.</p>
	 * 
	 * @param handle handle of the opened port on which to write byte
	 * @param data the string to be send to port
	 * @param delay interval between two successive bytes while sending string
	 * @return true on success false otherwise
	 * @throws SerialComException if an I/O error occurs.
	 * @throws IllegalArgumentException if data is null
	 */
	public boolean writeString(long handle, String data, int delay) throws SerialComException {
		if(data == null) {
			throw new IllegalArgumentException("writeString(), " + SerialComErrorMapper.ERR_WRITE_NULL_DATA_PASSED);
		}
		return writeBytes(handle, data.getBytes(), delay);
	}

	/**
	 * <p>This method writes a string to the specified port. The library internally converts string to byte buffer. 
	 * The data has been transmitted out of serial port when this method returns.</p>
	 * 
	 * @param handle handle of the opened port on which to write byte
	 * @param data the string to be send to port
	 * @param charset the character set into which given string will be encoded
	 * @param delay  time gap between transmitting two successive bytes in this string
	 * @return true on success false otherwise
	 * @throws SerialComException if an I/O error occurs.
	 * @throws IllegalArgumentException if data is null
	 */
	public boolean writeString(long handle, String data, Charset charset, int delay) throws UnsupportedEncodingException, SerialComException {
		if(data == null) {
			throw new IllegalArgumentException("writeString(), " + SerialComErrorMapper.ERR_WRITE_NULL_DATA_PASSED);
		}
		return writeBytes(handle, data.getBytes(charset), delay);
	}

	/** 
	 * <p>Different CPU and OS will have different endianness. It is therefore we handle the endianness conversion 
	 * as per the requirement. If the given integer is in range −32,768 to 32,767, only two bytes will be needed.
	 * In such case we might like to send only 2 bytes to serial port. On the other hand application might be implementing
	 * some custom protocol so that the data must be 4 bytes (irrespective of its range) in order to be interpreted 
	 * correctly by the receiver terminal. This method assumes that integer value can be represented by 32 or less
	 * number of bits. On x86_64 architecture, loss of precision will occur if the integer value is of more than 32 bit.</p>
	 * 
	 * <p>The data has been transmitted physically out of serial port when this method returns.</p>
	 * 
	 * <p>In java numbers are represented in 2's complement, so number 650 whose binary representation is 0000001010001010
	 * is printed byte by byte, then will be printed as 1 and -118, because 10001010 in 2's complement is negative number.</p>
	 * 
	 * @param handle handle of the opened port on which to write byte
	 * @param data an integer number to be sent to port
	 * @param delay interval between two successive bytes 
	 * @param endianness big or little endian sequence to be followed while sending bytes representing this integer
	 * @param numOfBytes number of bytes this integer can be represented in
	 * @return true on success false otherwise
	 * @throws SerialComException if an I/O error occurs.
	 * @throws IllegalArgumentException if endianness or numOfBytes is null
	 */
	public boolean writeSingleInt(long handle, int data, int delay, ENDIAN endianness, NUMOFBYTES numOfBytes) throws SerialComException {
		byte[] buffer = null;
		
		if(endianness == null) {
			throw new IllegalArgumentException("writeSingleInt() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_ENDIAN);
		}
		if(numOfBytes == null) {
			throw new IllegalArgumentException("writeSingleInt() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_NUMBYTE);
		}

		if(numOfBytes.getValue() == 2) {             // conversion to two bytes data
			buffer = new byte[2];
			if(endianness.getValue() == 1) {         // Little endian
				buffer[1] = (byte) (data >>> 8);
				buffer[0] = (byte)  data;
			}else {                                 // big endian/default (java is big endian by default)
				buffer[1] = (byte)  data;
				buffer[0] = (byte) (data >>> 8);
			}
			return writeBytes(handle, buffer, delay);
		}else {                                     // conversion to four bytes data
			buffer = new byte[4];
			if(endianness.getValue() == 1) {        // Little endian
				buffer[3] = (byte) (data >>> 24);
				buffer[2] = (byte) (data >>> 16);
				buffer[1] = (byte) (data >>> 8);
				buffer[0] = (byte)  data;
			}else {                                 // big endian/default (java is big endian by default)
				buffer[3] = (byte)  data;
				buffer[2] = (byte) (data >>> 8);
				buffer[1] = (byte) (data >>> 16);
				buffer[0] = (byte) (data >>> 24);
			}
			return writeBytes(handle, buffer, delay);
		}
	}

	/** 
	 * <p>This method send an array of integers on the specified port. The data has been transmitted out of serial 
	 * port when this method returns.</p>
	 * 
	 * @param handle handle of the opened port on which to write byte
	 * @param buffer an array of integers to be sent to port
	 * @param delay interval between two successive bytes 
	 * @param endianness big or little endian sequence to be followed while sending bytes representing this integer
	 * @param numOfBytes number of bytes this integer can be represented in
	 * @return true on success false otherwise
	 * @throws SerialComException if an I/O error occurs.
	 * @throws IllegalArgumentException if endianness or numOfBytes is null
	 */
	public boolean writeIntArray(long handle, int[] buffer, int delay, ENDIAN endianness, NUMOFBYTES numOfBytes) throws SerialComException {
		byte[] localBuf = null;
		
		if(endianness == null) {
			throw new IllegalArgumentException("writeIntArray() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_ENDIAN);
		}
		if(numOfBytes == null) {
			throw new IllegalArgumentException("writeIntArray() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_NUMBYTE);
		}

		if(numOfBytes.getValue() == 2) {
			localBuf = new byte[2 * buffer.length];
			if(endianness.getValue() == 1) {                 // little endian
				int a = 0;
				for(int b=0; b<buffer.length; b++) {
					localBuf[a] = (byte)  buffer[b];
					a++;
					localBuf[a] = (byte) (buffer[b] >>> 8);
					a++;
				}
			}else {                                         // big/default endian
				int c = 0;
				for(int d=0; d<buffer.length; d++) {
					localBuf[c] = (byte) (buffer[d] >>> 8);
					c++;
					localBuf[c] = (byte)  buffer[d];
					c++;
				}
			}
			return writeBytes(handle, localBuf, delay);
		}else {
			localBuf = new byte[4 * buffer.length];
			if(endianness.getValue() == 1) {                  // little endian
				int e = 0;
				for(int f=0; f<buffer.length; f++) {
					localBuf[e] = (byte)  buffer[f];
					e++;
					localBuf[e] = (byte) (buffer[f] >>> 8);
					e++;
					localBuf[e] = (byte) (buffer[f] >>> 16);
					e++;
					localBuf[e] = (byte) (buffer[f] >>> 24);
					e++;
				}
			}else {                                          // big/default endian
				int g = 0;
				for(int h=0; h<buffer.length; h++) {
					localBuf[g] = (byte)  buffer[h];
					g++;
					localBuf[g] = (byte) (buffer[h] >>> 8);
					g++;
					localBuf[g] = (byte) (buffer[h] >>> 16);
					g++;
					localBuf[g] = (byte) (buffer[h] >>> 24);
					g++;
				}
			}
			return writeBytes(handle, localBuf, delay);
		}
	}
	
	/** 
	 * <p>Read specified number of bytes from given serial port and stay blocked till bytes arrive at serial port.</p>
	 * <p>1. If data is read from serial port, array of bytes containing data is returned.</p>
	 * <p>2. If there was no data in serial port to read, null is returned.</p>
	 * 
	 * <p>The number of bytes to read must be greater than or equal to 1 and less than or equal to 2048 (1 <= byteCount <= 2048).
	 * This method may return less than the requested number of bytes due to reasons like, there is less data in operating system
	 * buffer (serial port) or operating system returned less data which is also legal.</p>
	 * 
	 * @param handle of the serial port from which to read bytes
	 * @param byteCount number of bytes to read from serial port
	 * @return array of bytes read from port or null
	 * @throws SerialComException if an I/O error occurs.
	 */
	public byte[] readBytesBlocking(long handle, int byteCount) throws SerialComException {
		byte[] buffer = null;
		SerialComReadStatus retStatus = new SerialComReadStatus(1);
		int osType = SerialComManager.getOSType();
		if(osType == SerialComManager.OS_WINDOWS) {
			buffer = mNativeInterface.readBytesBlocking(handle, byteCount, retStatus);
		}else {
			buffer = mNativeInterface.readBytes(handle, byteCount, retStatus);
		}
		
		// data read from serial port, pass to application
		if(buffer != null) {
			return buffer;
		}

		// reaching here means JNI layer passed null indicating either no data read or an error
		if(retStatus.status < 0) {
			throw new SerialComException("readBytesBlocking()", mErrMapper.getMappedError(retStatus.status));
		}else if(retStatus.status == 1) { 
			return null; // not possible for blocking call
		}else {
		}

		return null;
	}

	/** 
	 * <p>Read specified number of bytes from given serial port.</p>
	 * <p>1. If data is read from serial port, array of bytes containing data is returned.</p>
	 * <p>2. If there was no data in serial port to read, null is returned.</p>
	 * 
	 * <p>The number of bytes to read must be greater than or equal to 1 and less than or equal to 2048 (1 <= byteCount <= 2048).
	 * This method may return less than the requested number of bytes due to reasons like, there is less data in operating system
	 * buffer (serial port) or operating system returned less data which is also legal.</p>
	 * 
	 * @param handle of the serial port from which to read bytes
	 * @param byteCount number of bytes to read from serial port
	 * @return array of bytes read from port or null
	 * @throws SerialComException if an I/O error occurs.
	 */
	public byte[] readBytes(long handle, int byteCount) throws SerialComException {
		
		SerialComReadStatus retStatus = new SerialComReadStatus(1);
		byte[] buffer = mNativeInterface.readBytes(handle, byteCount, retStatus);

		// data read from serial port, pass to application
		if(buffer != null) {
			return buffer;
		}

		// reaching here means JNI layer passed null indicating either no data read or an error
		if(retStatus.status == 1) {
			return null;               // serial port does not have any data
		}else if(retStatus.status < 0) {
			throw new SerialComException("reading", mErrMapper.getMappedError(retStatus.status));
		}else {
		}

		return null;
	}

	/** 
	 * <p>If user does not specify any count, library try to read DEFAULT_READBYTECOUNT (1024 bytes) bytes as default value.</p>
	 * 
	 * <p>It has same effect as readBytes(handle, 1024)</p>
	 * 
	 * @param handle of the port from which to read bytes
	 * @return array of bytes read from port or null
	 * @throws SerialComException if an I/O error occurs.
	 */
	public byte[] readBytes(long handle) throws SerialComException {
		return readBytes(handle, DEFAULT_READBYTECOUNT);
	}

	/**
	 * <p>This method reads data from serial port and converts it into string. Caller has more finer control over the byte operation.</p>
	 * 
	 * <p> It Constructs a new string by decoding the specified array of bytes using the platform's default charset. The length of the new
     * string is a function of the charset, and hence may not be equal to the length of the byte array read from serial port.</p>
	 * 
	 * @param handle of port from which to read bytes
	 * @param byteCount number of bytes to read from this port
	 * @return string constructed from data read from serial port or null
	 * @throws SerialComException if an I/O error occurs.
	 */
	public String readString(long handle, int byteCount) throws SerialComException {
		byte[] buffer = readBytes(handle, byteCount);
		if(buffer != null) {
			return new String(buffer);
		}
		return null;
	}

	/**
	 * <p>This method reads data from serial port and converts it into string. Caller has more finer control over the byte operation.</p>
	 * 
	 * <p> It Constructs a new string by decoding the specified array of bytes using the platform's default charset. The length of the new
     * string is a function of the charset, and hence may not be equal to the length of the byte array read from serial port.</p>
     * 
	 * <p>Note that the length of data bytes read using this method can not be greater than DEFAULT_READBYTECOUNT i.e. 1024.</p>
	 * 
	 * @param handle of the port from which to read bytes
	 * @return string constructed from data read from serial port or null
	 * @throws SerialComException if an I/O error occurs.
	 */
	public String readString(long handle) throws SerialComException {
		return readString(handle, DEFAULT_READBYTECOUNT);
	}

	/** 
	 * <p>This is a utility method to read a single byte from serial port.</p>
	 * 
	 * <p>Its effect is same as readBytes(handle, 1)</p>
	 * 
	 * @param handle of the port from which to read bytes
	 * @return array of length 1 representing 1 byte data read from serial port or null
	 * @throws SerialComException if an I/O error occurs.
	 */
	public byte[] readSingleByte(long handle) throws SerialComException {
		return readBytes(handle, 1);
	}

	/**
	 * <p>This method configures the rate at which communication will occur and the format of data frame. Note that, most of the DTE/DCE (hardware)
	 * does not support different baud rates for transmission and reception and therefore we take only single value applicable to both transmission and
	 * reception. Further, all the hardware and OS does not support all the baud rates (maximum change in signal per second). It is the applications 
	 * responsibility to consider these factors when writing portable software.</p>
	 * 
	 * <p>If parity is enabled, the parity bit will be removed from frame before passing it library.</p>
	 * 
	 * Note: (1) some restrictions apply in case of Windows. Please refer http://msdn.microsoft.com/en-us/library/windows/desktop/aa363214(v=vs.85).aspx
	 * for details.
	 * 
	 * <p>(2) Some drivers especially windows driver for usb to serial converters support non-standard baud rates. They either supply a text file that can be used for 
	 * configuration or user may edit windows registry directly to enable this support. The user supplied standard baud rate is translated to custom baud rate as 
	 * specified in vendor specific configuration file.</p>
	 * 
	 * <p>Take a look at http://www.ftdichip.com/Support/Documents/AppNotes/AN232B-05_BaudRates.pdf to understand using custom baud rates with USB-UART chips.</p>
	 * 
	 * @param handle of opened port to which this configuration applies to
	 * @param dataBits number of data bits in one frame (refer DATABITS enum for this)
	 * @param stopBits number of stop bits in one frame (refer STOPBITS enum for this)
	 * @param parity of the frame (refer PARITY enum for this)
	 * @param baudRate of the frame (refer BAUDRATE enum for this)
	 * @param custBaud custom baudrate if the desired rate is not included in BAUDRATE enum
	 * @return true on success false otherwise
	 * @throws SerialComException if invalid handle is passed or an error occurs in configuring the port
	 * @throws IllegalArgumentException if dataBits or stopBits or parity or baudRate is null, or if custBaud is zero or negative
	 */
	public boolean configureComPortData(long handle, DATABITS dataBits, STOPBITS stopBits, PARITY parity, BAUDRATE baudRate, int custBaud) throws SerialComException {

		int baudRateTranslated = 0;
		int custBaudTranslated = 0;
		int baudRateGiven = 0;
		
		if(dataBits == null) {
			throw new IllegalArgumentException("configureComPortData() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_DBITS);
		}
		if(stopBits == null) {
			throw new IllegalArgumentException("configureComPortData() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_SBITS);
		}
		if(parity == null) {
			throw new IllegalArgumentException("configureComPortData() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_PARITY);
		}
		if(baudRate == null) {
			throw new IllegalArgumentException("configureComPortData() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_BRATE);
		}

		boolean handlefound = false;
		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("configureComPortData()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		baudRateGiven = baudRate.getValue();
		if(baudRateGiven != 251) {
			baudRateTranslated = baudRateGiven;
			custBaudTranslated = 0;
		}else {
			// custom baud rate
			if(custBaud <= 0) {
				throw new IllegalArgumentException("configureComPortData() " + SerialComErrorMapper.ERR_CUSTB_CAN_NOT_NEG_ZERO);
			}
			baudRateTranslated = baudRateGiven;
			custBaudTranslated = custBaud;
		}

		int ret = mNativeInterface.configureComPortData(handle, dataBits.getValue(), stopBits.getValue(), parity.getValue(), baudRateTranslated, custBaudTranslated);
		if(ret < 0) {
			throw new SerialComException("configureComPortData()", mErrMapper.getMappedError(ret));
		}

		return true;
	}

	/**
	 * <p>This method configures the way data communication will be controlled between DTE and DCE. This specifies flow control and actions that will
	 * be taken when an error is encountered in communication.</p>
	 * 
	 * @param handle of opened port to which this configuration applies to
	 * @param flowctrl flow control, how data flow will be controlled (refer FLOWCONTROL enum for this)
	 * @param xon character representing on condition if software flow control is used
	 * @param xoff character representing off condition if software flow control is used
	 * @param ParFraError true if parity and frame errors are to be checked false otherwise
	 * @param overFlowErr true if overflow error is to be detected false otherwise
	 * @return true on success false otherwise
	 * @throws SerialComException if invalid handle is passed or an error occurs in configuring the port
	 * @throws IllegalArgumentException if flowctrl is null
	 */
	public boolean configureComPortControl(long handle, FLOWCONTROL flowctrl, char xon, char xoff, boolean ParFraError, boolean overFlowErr) throws SerialComException {
		boolean handlefound = false;
		
		if(flowctrl == null) {
			throw new IllegalArgumentException("configureComPortControl() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_FLOWCTRL);
		}
		
		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("configureComPortControl()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		int ret = mNativeInterface.configureComPortControl(handle, flowctrl.getValue(), xon, xoff, ParFraError, overFlowErr);
		if(ret < 0) {
			throw new SerialComException("configureComPortControl()", mErrMapper.getMappedError(ret));
		}

		return true;
	}

	/**
	 * <p>This method gives currently applicable settings associated with particular serial port.
	 * The values are bit mask so that application can manipulate them to get required information.</p>
	 * 
	 * <p>For Unix-like OS the order is : c_iflag, c_oflag, c_cflag, c_lflag, c_line, c_cc[0], c_cc[1], c_cc[2], c_cc[3]
	 * c_cc[4], c_cc[5], c_cc[6], c_cc[7], c_cc[8], c_cc[9], c_cc[10], c_cc[11], c_cc[12], c_cc[13], c_cc[14],
	 * c_cc[15], c_cc[16], c_ispeed and c_ospeed.</p>
	 * 
	 * <p>For Windows OS the order is :DCBlength, BaudRate, fBinary, fParity, fOutxCtsFlow, fOutxDsrFlow, fDtrControl,
	 * fDsrSensitivity, fTXContinueOnXoff, fOutX, fInX, fErrorChar, fNull, fRtsControl, fAbortOnError, fDummy2,
	 * wReserved, XonLim, XoffLim, ByteSize, Parity, StopBits, XonChar, XoffChar, ErrorChar, StopBits, EvtChar,
	 * wReserved1.</p>
	 * 
	 * @param handle of the opened port
	 * @return array of string giving configuration
	 * @throws SerialComException if invalid handle is passed or an error occurs while reading current settings
	 */
	public String[] getCurrentConfiguration(long handle) throws SerialComException {

		boolean handlefound = false;
		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("getCurrentConfiguration()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		if(getOSType() != OS_WINDOWS) {
			// for unix-like os
			int[] config = mNativeInterface.getCurrentConfigurationU(handle);
			String[] configuration = new String[config.length];
			if(config[0] < 0) {
				throw new SerialComException("getCurrentConfiguration()", mErrMapper.getMappedError(config[0]));
			}
			// if an error occurs, config[0] will contain error code, otherwise actual data
			for(int x=0; x<config.length; x++) {
				configuration[x] = "" + config[x];
			}
			return configuration;
		}else {
			// for windows os
			String[] configuration = mNativeInterface.getCurrentConfigurationW(handle);
			return configuration;
		}
	}

	/**
	 * <p>This method assert/de-assert RTS line of serial port. Set "true" for asserting signal, false otherwise. This changes the state of RTS line electrically.</p>
	 * 
	 * <p>The RS-232 standard defines the voltage levels that correspond to logical one and logical zero levels for the data 
	 * transmission and the control signal lines. Valid signals are either in the range of +3 to +15 volts or the range 
	 * −3 to −15 volts with respect to the ground/common pin; consequently, the range between −3 to +3 volts is not a 
	 * valid RS-232 level.</p>
	 * 
	 * <p>In asserted condition, voltage at pin number 7 (RTS signal) will be greater than 3 volts. Voltage 5.0 volts
	 * was observed when using USB-UART converter http://www.amazon.in/Bafo-USB-Serial-Converter-DB9/dp/B002SCRCDG.</p>
	 * 
	 * <p>On some hardware IC, signals may be active low and therefore for actual voltage datasheet should be consulted.<p>
	 * 
	 * @param handle of the opened port
	 * @param enabled if true RTS will be asserted and vice-versa
	 * @return true on success false otherwise
	 * @throws SerialComException if system is unable to complete requested operation
	 */
	public boolean setRTS(long handle, boolean enabled) throws SerialComException {
		int ret = mNativeInterface.setRTS(handle, enabled);
		if(ret < 0) {
			throw new SerialComException("setRTS()", mErrMapper.getMappedError(ret));
		}
		return true;
	}

	/**
	 * <p>This method assert/de-assert DTR line of serial port. Set "true" for asserting signal, false otherwise. This changes the state of RTS line electrically.</p>
	 * 
	 * @param handle of the opened port
	 * @param enabled if true DTR will be asserted and vice-versa
	 * @return true on success false otherwise
	 * @throws SerialComException if system is unable to complete requested operation
	 */
	public boolean setDTR(long handle, boolean enabled) throws SerialComException {
		int ret = mNativeInterface.setDTR(handle, enabled);
		if(ret < 0) {
			throw new SerialComException("setDTR()", mErrMapper.getMappedError(ret));
		}
		return true;
	}

	/**
	 * <p>This method associate a data looper with the given listener. This looper will keep delivering new data whenever
	 * it is made available from native data collection and dispatching subsystem.
	 * Note that listener will start receiving new data, even before this method returns.</p>
	 * 
	 * <p>Application (listener) should implement ISerialComDataListener and override onNewSerialDataAvailable method.</p>
	 * 
	 * <p>The scm library can manage upto 1024 listeners corresponding to 1024 port handles.</p>
	 * <p>This method is thread safe.</p>
	 * 
	 * @param handle of the port opened
	 * @param dataListener instance of class which implements ISerialComDataListener interface
	 * @return true on success false otherwise
	 * @throws SerialComException if invalid handle passed, handle is null or data listener already exist for this handle
	 * @throws IllegalArgumentException if dataListener is null 
	 */
	public boolean registerDataListener(long handle, ISerialComDataListener dataListener) throws SerialComException {

		boolean handlefound = false;
		SerialComPortHandleInfo mHandleInfo = null;

		if(dataListener == null) {
			throw new IllegalArgumentException("registerDataListener(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_LISTENER);
		}
		
		synchronized(lock) {
			for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
				if(mInfo.containsHandle(handle)) {
					handlefound = true;
					if(mInfo.getDataListener() != null) {
						throw new SerialComException("registerDataListener()", SerialComErrorMapper.ERR_DATA_LISTENER_ALREADY_EXIST);
					}else {
						mHandleInfo = mInfo;
					}
					break;
				}
			}
	
			if(handlefound == false) {
				throw new SerialComException("registerDataListener()", SerialComErrorMapper.ERR_WRONG_HANDLE);
			}
	
			return mEventCompletionDispatcher.setUpDataLooper(handle, mHandleInfo, dataListener);
		}
	}

	/**
	 * <p>This method destroys complete java and native looper subsystem associated with this particular data listener. This has no
	 * effect on event looper subsystem. This method returns only after native thread has been terminated successfully.</p>
	 * 
	 * <p>This method is thread safe.</p>
	 * 
	 * @param dataListener instance of class which implemented ISerialComDataListener interface
	 * @return true on success false otherwise
	 * @throws SerialComException if null value is passed in dataListener field
	 * @throws IllegalArgumentException if dataListener is null 
	 */
	public boolean unregisterDataListener(ISerialComDataListener dataListener) throws SerialComException {
		if(dataListener == null) {
			throw new IllegalArgumentException("unregisterDataListener(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_LISTENER);
		}

		synchronized(lock) {
			if(mEventCompletionDispatcher.destroyDataLooper(dataListener)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>This method associate a event looper with the given listener. This looper will keep delivering new event whenever
	 * it is made available from native event collection and dispatching subsystem.</p>
	 * 
	 * <p>Application (listener) should implement ISerialComEventListener and override onNewSerialEvent method.</p>
	 * 
	 * <p>By default all four events are dispatched to listener. However, application can mask events through setEventsMask()
	 * method. In current implementation, native code sends all the events irrespective of mask and we actually filter
	 * them in java layers, to decide whether this should be sent to application or not (as per the mask set by
	 * setEventsMask() method).</p>
	 * 
	 * <p>Before calling this method, make sure that port has been configured for hardware flow control using configureComPortControl
	 * method.</p>
	 * <p>This method is thread safe.</p>
	 * 
	 * @param handle of the port opened
	 * @param eventListener instance of class which implements ISerialComEventListener interface
	 * @return true on success false otherwise
	 * @throws SerialComException if invalid handle passed, handle is null or event listener already exist for this handle
	 * @throws IllegalArgumentException if eventListener is null 
	 */
	public boolean registerLineEventListener(long handle, ISerialComEventListener eventListener) throws SerialComException {
		boolean handlefound = false;
		SerialComPortHandleInfo mHandleInfo = null;

		if(eventListener == null) {
			throw new IllegalArgumentException("registerLineEventListener(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_LISTENER);
		}

		synchronized(lock) {
			for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
				if(mInfo.containsHandle(handle)) {
					handlefound = true;
					if(mInfo.getEventListener() != null) {
						throw new SerialComException("registerLineEventListener()", SerialComErrorMapper.ERR_LISTENER_ALREADY_EXIST);
					}else {
						mHandleInfo = mInfo;
					}
					break;
				}
			}
	
			if(handlefound == false) {
				throw new SerialComException("registerLineEventListener()", SerialComErrorMapper.ERR_WRONG_HANDLE);
			}
	
			return mEventCompletionDispatcher.setUpEventLooper(handle, mHandleInfo, eventListener);
		}
	}

	/**
	 * <p>This method destroys complete java and native looper subsystem associated with this particular event listener. This has no
	 * effect on data looper subsystem.</p>
	 * <p>This method is thread safe.</p>
	 * 
	 * @param eventListener instance of class which implemented ISerialComEventListener interface
	 * @return true on success false otherwise
	 * @throws SerialComException if null value is passed in eventListener field
	 * @throws IllegalArgumentException if eventListener is null 
	 */
	public boolean unregisterLineEventListener(ISerialComEventListener eventListener) throws SerialComException {
		if(eventListener == null) {
			throw new IllegalArgumentException("unregisterLineEventListener(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_LISTENER);
		}
		synchronized(lock) {
			if(mEventCompletionDispatcher.destroyEventLooper(eventListener)) {
				return true;
			}
		}

		return false;
	}


	/**
	 * <p>The user don't need data for some time or he may be managing data more efficiently.</p>
	 * 
	 * @param eventListener instance of class which implemented ISerialComEventListener interface
	 * @return true on success false otherwise
	 * @throws SerialComException if null is passed for eventListener field
	 * @throws IllegalArgumentException if eventListener is null 
	 */
	public boolean pauseListeningEvents(ISerialComEventListener eventListener) throws SerialComException {
		if(eventListener == null) {
			throw new IllegalArgumentException("pauseListeningEvents(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_LISTENER);

		}
		if(mEventCompletionDispatcher.pauseListeningEvents(eventListener)) {
			return true;
		}

		return false;
	}

	/**
	 * <p>The user don't need data for some time or he may be managing data more efficiently.
	 * Note that the native thread will continue to receive events and data, it will pass this data to
	 * java layer. User must be careful that new data will exist in queue if received after pausing, but
	 * it will not get delivered to application.</p>
	 * 
	 * @param eventListener is an instance of class which implements ISerialComEventListener
	 * @return true on success false otherwise
	 * @throws SerialComException if error occurs
	 * @throws IllegalArgumentException if eventListener is null 
	 */
	public boolean resumeListeningEvents(ISerialComEventListener eventListener) throws SerialComException {
		if(eventListener == null) {
			throw new IllegalArgumentException("pauseListeningEvents(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_LISTENER);

		}
		if(mEventCompletionDispatcher.resumeListeningEvents(eventListener)) {
			return true;
		}

		return false;
	}
	
	/**
	 * <p>This method gives more fine tune control to application for tuning performance and behavior of read
	 * operations to leverage OS specific facility for read operation. The read operations can be optimized for
	 * receiving for example high volume data speedily or low volume data but received in burst mode.</p>
	 * 
	 * <p>If more than one client has opened the same port, then all the clients will be affected by new settings.</p>
	 * 
	 * <p>When this method is called application should make sure that previous read or write operation is not in progress.</p>
	 * 
	 * @param handle of the opened port
	 * @param vmin c_cc[VMIN] field of termios structure
	 * @param vtime c_cc[VTIME] field of termios structure (10th of a second)
	 * @param rit ReadIntervalTimeout field of COMMTIMEOUTS structure
	 * @param rttm ReadTotalTimeoutMultiplier field of COMMTIMEOUTS structure
	 * @param rttc ReadTotalTimeoutConstant field of COMMTIMEOUTS structure
	 * @return true on success false otherwise
	 * @throws SerialComException if wrong handle is passed or operation can not be done successfully
	 * @throws IllegalArgumentException if invalid combination of arguments is passed
	 */
	public boolean fineTuneRead(long handle, int vmin, int vtime, int rit, int rttm, int rttc) throws SerialComException {
		boolean handlefound = false;
		int osType = SerialComManager.getOSType();
		
		if(osType == SerialComManager.OS_WINDOWS) {
			if((rit < 0) || (rttm < 0) || (rttc < 0)) {
				throw new IllegalArgumentException("fineTuneRead(), " + SerialComErrorMapper.ERR_ARG_CAN_NOT_NEGATIVE);
			}
		}else {
			if((vmin == 0) && (vtime == 0)) {
				throw new IllegalArgumentException("fineTuneRead(), " + SerialComErrorMapper.ERR_INVALID_COMBINATION_ARG);
			}
			if((vmin < 0) || (vtime < 0)) {
				throw new IllegalArgumentException("fineTuneRead(), " + SerialComErrorMapper.ERR_ARG_CAN_NOT_NEGATIVE);
			}
		}

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}

		if(handlefound == false) {
			throw new SerialComException("fineTuneRead()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		int ret = mNativeInterface.fineTuneRead(handle, vmin, vtime, rit, rttm, rttc);
		if(ret < 0) {
			throw new SerialComException("fineTuneRead()",  mErrMapper.getMappedError(ret));
		}
		
		return true;
	}

	/**
	 * <p>Defines for which line events registered event listener will be called.</p>
	 * 
	 * <p>In future we may shift modifying mask in the native code itself, so as to prevent JNI transitions.
	 * This filters what events should be sent to application. Note that, although we sent only those event
	 * for which user has set mask, however native code send all the events to java layer as of now.</p>
	 * 
	 * @param eventListener instance of class which implemented ISerialComEventListener interface
	 * @return true on success false otherwise
	 * @throws SerialComException if null is passed for listener field or invalid listener is passed
	 * @throws IllegalArgumentException if eventListener is null
	 */
	public boolean setEventsMask(ISerialComEventListener eventListener, int newMask) throws SerialComException {

		SerialComLooper looper = null;
		ISerialComEventListener mEventListener = null;

		if(eventListener == null) {
			throw new IllegalArgumentException("setEventsMask(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_LISTENER);
		}

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsEventListener(eventListener)) {
				looper = mInfo.getLooper();
				mEventListener = mInfo.getEventListener();
				break;
			}
		}

		if(looper != null && mEventListener != null) {
			looper.setEventsMask(newMask);
			return true;
		}else {
			throw new SerialComException("setEventsMask()", SerialComErrorMapper.ERR_WRONG_LISTENER_PASSED);
		}
	}

	/**
	 * <p>This method return currently applicable mask for events on serial port.</p>
	 * 
	 * @param eventListener instance of class which implemented ISerialComEventListener interface
	 * @return an integer containing bit fields representing mask
	 * @throws SerialComException if null or wrong listener is passed
	 * @throws IllegalArgumentException if eventListener is null
	 */
	public int getEventsMask(ISerialComEventListener eventListener) throws SerialComException {
		
		SerialComLooper looper = null;
		ISerialComEventListener mEventListener = null;

		if(eventListener == null) {
			throw new IllegalArgumentException("getEventsMask(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_LISTENER);
		}

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsEventListener(eventListener)) {
				looper = mInfo.getLooper();
				mEventListener = mInfo.getEventListener();
				break;
			}
		}

		if(looper != null && mEventListener != null) {
			return looper.getEventsMask();
		}else {
			throw new SerialComException("setEventsMask()", SerialComErrorMapper.ERR_WRONG_LISTENER_PASSED);
		}
	}

	/**
	 * <p>Discards data sent to port but not transmitted, or data received but not read. Some device/OS/driver might
	 * not have support for this, but most of them may have.
	 * If there is some data to be pending for transmission, it will be discarded and therefore no longer sent.
	 * If the application wants to make sure that all data has been transmitted before discarding anything, it must
	 * first flush data and then call this method.</p>
	 * 
	 * @param handle of the opened port
	 * @param clearRxPort if true receive buffer will be cleared otherwise will be left untouched 
	 * @param clearTxPort if true transmit buffer will be cleared otherwise will be left untouched
	 * @return true on success false otherwise
	 * @throws SerialComException if invalid handle is passed or operation can not be completed successfully
	 */
	public boolean clearPortIOBuffers(long handle, boolean clearRxPort, boolean clearTxPort) throws SerialComException {
		boolean handlefound = false;
		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("clearPortIOBuffers()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		if(clearRxPort == true || clearTxPort == true) {
			int ret = mNativeInterface.clearPortIOBuffers(handle, clearRxPort, clearTxPort);
			if(ret < 0) {
				throw new SerialComException("clearPortIOBuffers()", mErrMapper.getMappedError(ret));
			}
			return true;
		}

		return false;
	}

	/**
	 * <p>Assert a break condition on the specified port for the duration expressed in milliseconds.
	 * If the line is held in the logic low condition (space in UART jargon) for longer than a character 
	 * time, this is a break condition that can be detected by the UART.</p>
	 * 
	 * <p>A "break condition" occurs when the receiver input is at the "space" level for longer than some duration
	 * of time, typically, for more than a character time. This is not necessarily an error, but appears to the
	 * receiver as a character of all zero bits with a framing error. The term "break" derives from current loop
	 * Signaling, which was the traditional signaling used for tele-typewriters. The "spacing" condition of a 
	 * current loop line is indicated by no current flowing, and a very long period of no current flowing is often
	 * caused by a break or other fault in the line.</p>
	 * 
	 * @param handle of the opened port
	 * @param duration the time in milliseconds for which break will be active
	 * @return true on success
	 * @throws SerialComException if invalid handle is passed or operation can not be successfully completed
	 * @throws IllegalArgumentException if duration is zero or negative
	 */
	public boolean sendBreak(long handle, int duration) throws SerialComException {
		boolean handlefound = false;
		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("sendBreak()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}
		
		if((duration < 0) || (duration == 0)) {
			throw new IllegalArgumentException("sendBreak(), " + SerialComErrorMapper.ERR_DUR_CAN_NOT_NEG_ZERO);
		}

		int ret = mNativeInterface.sendBreak(handle, duration);
		if(ret < 0) {
			throw new SerialComException("sendBreak()", mErrMapper.getMappedError(ret));
		}

		return true;
	}

	/**
	 * <p>This method gives the number of interrupts on serial line that have occurred. The interrupt count is in following
	 * order in array beginning from index 0 and ending at index 11 :
	 * CTS, DSR, RING, CARRIER DETECT, RECEIVER BUFFER, TRANSMIT BUFFER, FRAME ERROR, OVERRUN ERROR, PARITY ERROR,
	 * BREAK AND BUFFER OVERRUN.</p>
	 * 
	 * <p>Note: It is supported for Unix-like OS only. For other operating systems, this will return 0 for all the indexes.</p>
	 * 
	 * @param handle of the port opened on which interrupts might have occurred
	 * @return array of integers containing values corresponding to each interrupt source
	 * @throws SerialComException if invalid handle is passed or operation can not be completed
	 */
	public int[] getInterruptCount(long handle) throws SerialComException {
		int x = 0;
		boolean handlefound = false;
		int[] ret = null;
		int[] interruptsCount = new int[11];

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("getInterruptCount()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		ret = mNativeInterface.getInterruptCount(handle);
		if(ret[0] < 0) {
			throw new SerialComException("getInterruptCount()", mErrMapper.getMappedError(ret[0]));
		}

		for(x=0; x<11; x++) {
			interruptsCount[x] = ret[x];
		}

		return interruptsCount;
	}

	/**
	 * <p>Gives status of serial port's control lines as supported by underlying operating system.
	 * The sequence of status in returned array is :</p>
	 * 
	 * <p>Linux OS &nbsp;&nbsp;&nbsp;: CTS, DSR, DCD, RI, LOOP, RTS, DTR respectively.</p>
	 * <p>MAC OS X &nbsp;&nbsp;:       CTS, DSR, DCD, RI, 0,    RTS, DTR respectively.</p>
	 * <p>Windows OS :                 CTS, DSR, DCD, RI, 0,    0,   0   respectively.</p>
	 * 
	 * @param handle of the port whose status is to be read
	 * @return status of control lines
	 * @throws SerialComException if invalid handle is passed or operation can not be completed successfully
	 */
	public int[] getLinesStatus(long handle) throws SerialComException {
		int x = 0;
		boolean handlefound = false;
		int[] ret = null;
		int[] status = {0,0,0,0,0,0,0};

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("getLinesStatus()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		ret = mNativeInterface.getLinesStatus(handle);
		if(ret[0] < 0) {
			throw new SerialComException("getLinesStatus()", mErrMapper.getMappedError(ret[0]));
		}

		for(x=0; x<7; x++) {
			status[x] = ret[x+1];
		}

		return status;
	}

	/**
	 * <p>Get number of bytes in input and output port buffers used by operating system for instance tty buffers
	 * in Unix like systems. Sequence of data in array is : Input count, Output count.</p>
	 * 
	 * <p>It should be noted that some chipset specially USB to UART converters might have FIFO buffers in chipset
	 * itself. For example FT232R has internal buffers controlled by FIFO CONTROLLERS. For this reason this method
	 * should be tested carefully if application is using USB-UART converters. This is driver and OS specific scenario.</p>
	 * 
	 * @param handle of the opened port
	 * @return array containing number of bytes in input and output buffer
	 * @throws SerialComException if invalid handle is passed or operation can not be completed successfully
	 */
	public int[] getByteCountInPortIOBuffer(long handle) throws SerialComException {
		boolean handlefound = false;
		int[] ret = null;
		int[] numBytesInfo = {0,0};

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("getByteCountInPortIOBuffer()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		// sequence returned : ret[0]=error info, ret[1]=byte count in input buffer, ret[2]=byte count in output buffer
		ret = mNativeInterface.getByteCount(handle);
		if(ret[0] < 0) {
			throw new SerialComException("getByteCountInPortIOBuffer()", mErrMapper.getMappedError(ret[0]));
		}

		numBytesInfo[0] = ret[1];  // Input buffer count
		numBytesInfo[1] = ret[2];  // Output buffer count

		return numBytesInfo;
	}

	/**
	 * <p>This registers a listener who will be invoked whenever a port has been plugged or un-plugged in system.
	 * Initially, the port has to be present into system, as that is only when we will be able to open port.</p>
	 * 
	 * <p>Application must implement ISerialComPortMonitor interface and override onPortMonitorEvent method. An event value
	 * 1 represents addition of device while event value 2 represents removal (unplugging) of device from system.</p>
	 * 
	 * @param handle which will be monitored
	 * @return true on success false otherwise
	 * @throws SerialComException - if invalid handle is passed or registration fails due to some reason
	 * @throws IllegalArgumentException if portMonitor is null
	 */
	public boolean registerPortMonitorListener(long handle, ISerialComPortMonitor portMonitor) throws SerialComException {
		boolean handlefound = false;
		String portName = null;
		int ret = 0;

		if(portMonitor == null) {
			throw new IllegalArgumentException("registerPortMonitorListener(), " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_MONITOR);
		}

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				portName = mInfo.getOpenedPortName();
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("registerPortMonitorListener()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		ret = mNativeInterface.registerPortMonitorListener(handle, portName, portMonitor);
		if(ret < 0) {
			throw new SerialComException("registerPortMonitorListener()", mErrMapper.getMappedError(ret));
		}

		return true;
	}

	/**
	 * <p>This unregisters listener and terminate native thread used for monitoring hot plugging of port.</p>
	 * 
	 * @param handle for which listener will be unregistered
	 * @return true on success false otherwise
	 * @throws SerialComException if invalid handle is passed or un-registration fails due to some reason
	 */
	public boolean unregisterPortMonitorListener(long handle) throws SerialComException {
		boolean handlefound = false;
		int ret = 0;

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("unregisterPortMonitorListener()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		ret = mNativeInterface.unregisterPortMonitorListener(handle);
		if(ret < 0) {
			throw new SerialComException("unregisterPortMonitorListener()", mErrMapper.getMappedError(ret));
		}

		return true;
	}

	/**
	 * <p>This method gives the port name with which given handle is associated. If the given handle is
	 * unknown to scm library, null is returned. The port is known to scm if it was opened using scm.</p>
	 * 
	 * @param handle for which the port name is to be found
	 * @return port name if port found for given handle or null if not found
	 */
	public String getPortName(long handle) {
		String portName = null;

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				portName = mInfo.getOpenedPortName();
				break;
			}
		}
		if(portName == null) {
			return null;
		}

		return portName;
	}

	/**
	 * <p>Send given file using specified file transfer protocol.</p>
	 * 
	 * @param handle of the port on which file is to be sent
	 * @param fileToSend File instance representing file to be sent
	 * @param ftpProto file transfer protocol to use for communication over serial port
	 * @param ftpVariant variant of file transfer protocol to use
	 * @param ftpMode define whether data should be translated(ASCII mode) or not (binary mode)
	 * @return true on success false otherwise
	 * @throws SerialComException if invalid handle is passed
	 * @throws SecurityException If a security manager exists and its SecurityManager.checkRead(java.lang.String) method denies read access to the file
	 * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
	 * @throws SerialComTimeOutException if timeout occurs as per file transfer protocol
	 * @throws IOException if error occurs while reading data from file to be sent
	 * @throws IllegalArgumentException if fileToSend or ftpProto or ftpVariant or ftpMode argument is null
	 */
	public boolean sendFile(long handle, java.io.File fileToSend, FTPPROTO ftpProto, FTPVAR ftpVariant, FTPMODE ftpMode) throws SerialComException, SecurityException,
							  FileNotFoundException, SerialComTimeOutException, IOException {
		int protocol = 0;
		int variant = 0;
		int mode = 0;
		boolean handlefound = false;
		boolean result = false;
		
		if(fileToSend == null) {
			throw new IllegalArgumentException("sendFile()" + SerialComErrorMapper.ERR_NULL_POINTER_FOR_FILE_SEND);
		}
		if(ftpProto == null) {
			throw new IllegalArgumentException("sendFile() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_PROTOCOL);
		}
		if(ftpVariant == null) {
			throw new IllegalArgumentException("sendFile() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_VARIANT);
		}
		if(ftpMode == null) {
			throw new IllegalArgumentException("sendFile() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_MODE);
		}

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("sendFile()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		protocol = ftpProto.getValue();
		variant = ftpVariant.getValue();
		mode = ftpMode.getValue();
		if(protocol == 1) {
			if((variant == 0) || (variant == 1)) {
				SerialComXModem xmodem = new SerialComXModem(this, handle, fileToSend, mode);
				result = xmodem.sendFileX();
			}else if(variant == 2) {
				SerialComXModemCRC xmodem = new SerialComXModemCRC(this, handle, fileToSend, mode);
				result = xmodem.sendFileX();
			}else if(variant == 3) {
				SerialComXModem1K xmodem = new SerialComXModem1K(this, handle, fileToSend, mode);
				result = xmodem.sendFileX();
			}else {
			}
		}else if(protocol == 2) {
			
		}else if(protocol == 3) {
			
		}else {
		}
		
		return result;
	}

	/**
	 * <p>Receives file using specified file transfer protocol.</p>
	 * 
	 * @param handle of the port on which file is to be sent
	 * @param fileToReceive File instance representing file to be sent
	 * @param ftpProto file transfer protocol to use for communication over serial port
	 * @param ftpVariant variant of file transfer protocol to use
	 * @param ftpMode define whether data should be translated(ASCII mode) or not (binary mode)
	 * @return true on success false otherwise
	 * @throws SerialComException if invalid handle is passed
	 * @throws SecurityException If a security manager exists and its SecurityManager.checkRead(java.lang.String) method denies read access to the file
	 * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
	 * @throws SerialComTimeOutException if timeout occurs as per file transfer protocol
	 * @throws IOException if error occurs while reading data from file to be sent
	 * @throws IllegalArgumentException if fileToReceive or ftpProto or ftpVariant or ftpMode argument is null
	 */
	public boolean receiveFile(long handle, java.io.File fileToReceive, FTPPROTO ftpProto, FTPVAR ftpVariant, FTPMODE ftpMode) throws SerialComException,
								SecurityException, FileNotFoundException, SerialComTimeOutException, IOException {
		int protocol = 0;
		int variant = 0;
		int mode = 0;
		boolean handlefound = false;
		boolean result = false;
		
		if(fileToReceive == null) {
			throw new IllegalArgumentException("receiveFile()" + SerialComErrorMapper.ERR_NULL_POINTER_FOR_FILE_RECEIVE);
		}
		if(ftpProto == null) {
			throw new IllegalArgumentException("receiveFile() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_PROTOCOL);
		}
		if(ftpVariant == null) {
			throw new IllegalArgumentException("receiveFile() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_VARIANT);
		}
		if(ftpMode == null) {
			throw new IllegalArgumentException("receiveFile() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_MODE);
		}
		
		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("receiveFile()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}

		protocol = ftpProto.getValue();
		variant = ftpVariant.getValue();
		mode = ftpMode.getValue();
		if(protocol == 1) {
			if((variant == 0) || (variant == 1)) {
				SerialComXModem xmodem = new SerialComXModem(this, handle, fileToReceive, mode);
				result = xmodem.receiveFileX();
			}else if(variant == 2) {
				SerialComXModemCRC xmodem = new SerialComXModemCRC(this, handle, fileToReceive, mode);
				result = xmodem.receiveFileX();
			}else if(variant == 3) {
				SerialComXModem1K xmodem = new SerialComXModem1K(this, handle, fileToReceive, mode);
				result = xmodem.receiveFileX();
			}else {
			}
		}else if(protocol == 2) {
			
		}else if(protocol == 3) {
			
		}else {
		}

		return result;
	}

	/**
	 * <p>This method writes bytes from the specified byte type buffer. If the method returns false, the application
	 * should try to re-send bytes. The data has been transmitted out of serial port when this method returns.</p>
	 * 
	 * <p>This method may be used for Internet of things applications, large data transfer, implementing userspace drivers,
	 * middleware frameworks, quick prototyping of Wifi/BT modules connected to UART port.</p>
	 * 
	 * @param handle handle of the opened port on which to write bytes
	 * @param buffer byte type buffer containing bytes to be written to port
	 * @return true on success, false on failure or if empty buffer is passed
	 * @throws SerialComException - if an I/O error occurs.
	 * @throws IllegalArgumentException if buffer is null
	 */
	public boolean writeBytesBulk(long handle, ByteBuffer buffer) throws SerialComException {
		if(buffer == null) {
			throw new IllegalArgumentException("writeBytesBulk(), " + SerialComErrorMapper.ERR_WRITE_NULL_DATA_PASSED);
		}

		int ret = mNativeInterface.writeBytesBulk(handle, buffer);
		if(ret < 0) {
			throw new SerialComException("writeBytesBulk()",  mErrMapper.getMappedError(ret));
		}
		return true;
	}
	
	/**
	 * <p>Prepares context and returns an input streams of bytes for receiving data bytes from the 
	 * serial port.</p>
	 * 
	 * <p>A handle can have only one input stream. Application should close stream after it is done.</p>
	 * 
	 * @param handle handle of the opened port from which to read data bytes
	 * @return reference to an object of type SerialComInByteStream
	 * @throws SerialComException if input stream already exist for this handle or invalid handle is passed
	 * @throws IllegalArgumentException if streamMode is null
	 */
	public SerialComInByteStream createInputByteStream(long handle, SMODE streamMode) throws SerialComException {
		boolean handlefound = false;
		SerialComInByteStream scis = null;
		SerialComPortHandleInfo mHandleInfo = null;
		
		if(streamMode == null) {
			throw new IllegalArgumentException("createInputByteStream() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_SMODE);
		}

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				scis = mInfo.getSerialComInByteStream();
				mHandleInfo = mInfo;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("createInputByteStream()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}
		
		if(scis == null) {
			scis = new SerialComInByteStream(this, handle, streamMode);
			mHandleInfo.setSerialComInByteStream(scis);
		}else {
			// if 2nd attempt is made to create already existing input stream, throw exception
			throw new SerialComException("createInputByteStream()", SerialComErrorMapper.ERR_IN_STREAM_ALREADY_EXIST);
		}
		
		return scis;
	}
	
	/**
	 * <p>Prepares context and returns an output streams of bytes for transferring data bytes out of 
	 * serial port.</p>
	 * 
	 * <p>A handle can have only one output stream. Application should close stream after it is done.</p>
	 * 
	 * <p>Using SerialComOutByteStream for writing data while not using SerialComInByteStream for
	 * reading is a valid use case.</p>
	 * 
	 * @param handle handle of the opened port on which to write data bytes
	 * @return reference to an object of type SerialComOutByteStream
	 * @throws SerialComException if output stream already exist for this handle or invalid handle is passed
	 * @throws IllegalArgumentException if streamMode is null
	 */
	public SerialComOutByteStream createOutputByteStream(long handle, SMODE streamMode) throws SerialComException {
		boolean handlefound = false;
		SerialComOutByteStream scos = null;
		SerialComPortHandleInfo mHandleInfo = null;
		
		if(streamMode == null) {
			throw new IllegalArgumentException("createOutputByteStream() " + SerialComErrorMapper.ERR_NULL_POINTER_FOR_SMODE);
		}

		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.containsHandle(handle)) {
				handlefound = true;
				scos = mInfo.getSerialComOutByteStream();
				mHandleInfo = mInfo;
				break;
			}
		}
		if(handlefound == false) {
			throw new SerialComException("createOutputByteStream()", SerialComErrorMapper.ERR_WRONG_HANDLE);
		}
		
		if(scos == null) {
			scos = new SerialComOutByteStream(this, handle, streamMode);
			mHandleInfo.setSerialComOutByteStream(scos);
		}else {
			// if 2nd attempt is made to create already existing output stream, throw exception
			throw new SerialComException("createOutputByteStream()", SerialComErrorMapper.ERR_OUT_STREAM_ALREADY_EXIST);
		}
		
		return scos;
	}
	
	/** Internal use */
	public void destroyInputByteStream(SerialComInByteStream scis) {
		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.getSerialComInByteStream() == scis) {
				mInfo.setSerialComInByteStream(null);
				break;
			}
		}
	}
	
	/** Internal use */
	public void destroyOutputByteStream(SerialComOutByteStream scos) {
		for(SerialComPortHandleInfo mInfo: mPortHandleInfo){
			if(mInfo.getSerialComOutByteStream() == scos) {
				mInfo.setSerialComOutByteStream(null);
				break;
			}
		}
	}
	
	/**
	 * <p>This method creates hex string from byte array. This is useful in bluetooth low energy applications where characteristics
	 * returned are to be interpreted or for example Internet of things applications where sensor data is getting exchanged.</p>
	 * 
	 * @param data byte array to be converted into string
	 * @param separator to be inserted after each hex value
	 * @return constructed hex string if data.length > 0 otherwise empty string
	 * @throws IllegalArgumentException if data is null
	 */
	public String byteArrayToHexStr(byte[] data, String separator) {
		if(data == null) {
			throw new IllegalArgumentException("byteArrayToHexStr(), " + SerialComErrorMapper.ERR_CAN_NOT_BE_NULL);
		}
		
		if(data.length > 0) {
			if(separator != null) {
				final StringBuilder sBuilder = new StringBuilder(2 * data.length);
				for (final byte b : data) {
					sBuilder.append(HEXNUM.charAt((b & 0xF0) >> 4)).append(HEXNUM.charAt((b & 0x0F)));
					sBuilder.append(separator);
				}
				return sBuilder.toString();
			}else {
				final StringBuilder sBuilder = new StringBuilder(2 * data.length);
				for (final byte b : data) {
					sBuilder.append(HEXNUM.charAt((b & 0xF0) >> 4)).append(HEXNUM.charAt((b & 0x0F)));
				}
				return sBuilder.toString();
			}
		}

		return new String();
	}
}
