/*
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

package test72;

import com.embeddedunveiled.serial.SerialComException;
import com.embeddedunveiled.serial.SerialComManager;
import com.embeddedunveiled.serial.hid.SerialComHID;
import com.embeddedunveiled.serial.hid.SerialComRawHID;
import com.embeddedunveiled.serial.usb.SerialComUSBHID;

// tested with MCP2200 for HID raw mode communication
public class Test72  {

	public static SerialComManager scm = null;
	public static SerialComUSBHID scuh = null;
	public static SerialComRawHID scrh = null;
	public static int osType = 0;
	public static String PORT = null;
	public static long handle = 0;
	public static int ret = 0;
	public static byte[] inputReportBuffer = new byte[32];
	public static byte[] outputReportBuffer = new byte[16];

	public static void main(String[] args) {

		try {
			scm = new SerialComManager();
			scrh = (SerialComRawHID) scm.getSerialComHIDInstance(SerialComHID.MODE_RAW, null, null);
			scuh = (SerialComUSBHID) scrh.getHIDTransportInstance(SerialComHID.HID_USB);
		} catch (Exception e) {
			e.printStackTrace();
		}

		osType = scm.getOSType();
		if(osType == SerialComManager.OS_LINUX) {
			PORT = "/dev/hidraw1";
		}else if(osType == SerialComManager.OS_WINDOWS) {
			PORT = "HID\\VID_04D8&PID_00DF&MI_02\\7&33842c3f&0&0000";
		}else if(osType == SerialComManager.OS_MAC_OS_X) {
			PORT = null;
		}else if(osType == SerialComManager.OS_SOLARIS) {
			PORT = null;
		}else{
		}

		try {
			// opened handle : 5
			handle = scrh.openHidDeviceR(PORT, true);
			System.out.println("\nopened handle : " + handle);
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			// writeOutputReport : 17
			outputReportBuffer[0] = (byte) 0x80;
			ret = scrh.writeOutputReportR(handle, (byte) -1, outputReportBuffer);
			System.out.println("\nwriteOutputReport : " + ret);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(500); // let device prepare response to output report we sent previously
			// MCP2200
			// readInputReportWithTimeout : 16
			// 80 00 6A 00 FF 00 FF 00 04 E1 00 88 CB 08 05 46 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
			ret = scrh.readInputReportWithTimeoutR(handle, inputReportBuffer, 100);
			System.out.println("\nreadInputReportWithTimeout : " + ret);
			System.out.println(scrh.formatReportToHexR(inputReportBuffer, " "));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\nManufacturer string: " + scrh.getManufacturerStringR(handle));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\nProduct string: " + scrh.getProductStringR(handle));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\nSerial string: " + scrh.getSerialNumberStringR(handle));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// supply invalid index, we use just for testing index 0. As per standard; String Index 0 should return a list of supported languages.
		try {
			System.out.println("\nString at index 0 is : " + scrh.getIndexedStringR(handle, 0));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\nString at index 1 is : " + scrh.getIndexedStringR(handle, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\nString at index 2 is : " + scrh.getIndexedStringR(handle, 2));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\nString at index 3 is : " + scrh.getIndexedStringR(handle, 3));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\nString at index 4 is : " + scrh.getIndexedStringR(handle, 4));
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* For dragonrise Joystick [USB Gamepad ] from frontech with idVendor=0079, idProduct=0011 
		 * report descriptor is :- 
		 * in hex : 05 01 09 04 A1 01 A1 02 14 75 08 95 03 81 01 26 FF 00 95 02 09 30 09 31 81 02 75 01 95 04 81 01 25 01 95 0A 05 09 19 01 29 0A 81 02 95 0A 81 01 C0 C0
		 * parsed : 
			    0x05, 0x01,        // Usage Page (Generic Desktop Ctrls)
				0x09, 0x04,        // Usage (Joystick)
				0xA1, 0x01,        // Collection (Application)
				0xA1, 0x02,        //   Collection (Logical)
				0x14,              //     Logical Minimum
				0x75, 0x08,        //     Report Size (8)
				0x95, 0x03,        //     Report Count (3)
				0x81, 0x01,        //     Input (Const,Array,Abs,No Wrap,Linear,Preferred State,No Null Position)
				0x26, 0xFF, 0x00,  //     Logical Maximum (255)
				0x95, 0x02,        //     Report Count (2)
				0x09, 0x30,        //     Usage (X)
				0x09, 0x31,        //     Usage (Y)
				0x81, 0x02,        //     Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
				0x75, 0x01,        //     Report Size (1)
				0x95, 0x04,        //     Report Count (4)
				0x81, 0x01,        //     Input (Const,Array,Abs,No Wrap,Linear,Preferred State,No Null Position)
				0x25, 0x01,        //     Logical Maximum (1)
				0x95, 0x0A,        //     Report Count (10)
				0x05, 0x09,        //     Usage Page (Button)
				0x19, 0x01,        //     Usage Minimum (0x01)
				0x29, 0x0A,        //     Usage Maximum (0x0A)
				0x81, 0x02,        //     Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
				0x95, 0x0A,        //     Report Count (10)
				0x81, 0x01,        //     Input (Const,Array,Abs,No Wrap,Linear,Preferred State,No Null Position)
				0xC0,              //   End Collection
				0xC0,              // End Collection
				// 50 bytes
		 */

		/* For MCP2200 report descriptor is :- 
		 * in hex : 06 00 FF 09 01 A1 01 19 01 29 10 15 00 26 FF 00 75 08 95 10 81 00 19 01 29 10 91 00 C0
		 * parsed :
		 * 	0x06, 0x00, 0xFF,  // Usage Page (Vendor Defined 0xFF00)
				0x09, 0x01,        // Usage (0x01)
				0xA1, 0x01,        // Collection (Application)
				0x19, 0x01,        //   Usage Minimum (0x01)
				0x29, 0x10,        //   Usage Maximum (0x10)
				0x15, 0x00,        //   Logical Minimum (0)
				0x26, 0xFF, 0x00,  //   Logical Maximum (255)
				0x75, 0x08,        //   Report Size (8)
				0x95, 0x10,        //   Report Count (16)
				0x81, 0x00,        //   Input (Data,Array,Abs,No Wrap,Linear,Preferred State,No Null Position)
				0x19, 0x01,        //   Usage Minimum (0x01)
				0x29, 0x10,        //   Usage Maximum (0x10)
				0x91, 0x00,        //   Output (Data,Array,Abs,No Wrap,Linear,Preferred State,No Null Position,Non-volatile)
				0xC0,              // End Collection
				// 29 bytes
		 */
		try {
			byte[] desc = scrh.getReportDescriptorR(handle);
			System.out.println("\nnumber of bytes in descriptor : " + desc.length);
			System.out.println("descriptor in hex read from device: " + scrh.formatReportToHexR(desc, " "));
		} catch (SerialComException e1) {
			e1.printStackTrace();
		}

		try {
			byte[] phydesc = scrh.getPhysicalDescriptorR(handle);
			System.out.println("\nnumber of bytes in physical descriptor : " + phydesc.length);
			System.out.println("physical descriptor in hex read from device: " + scrh.formatReportToHexR(phydesc, " "));
		} catch (SerialComException e1) {
			e1.printStackTrace();
		}

		// send cmd to mcp2200, in response it will send result which will be stored in ring buffer
		// reading input report after flush will result in everything as 0
		try {
			outputReportBuffer[0] = (byte) 0x80;
			ret = scrh.writeOutputReportR(handle, (byte) -1, outputReportBuffer);
			System.out.println("\nwriteOutputReport : " + ret);

			Thread.sleep(1000); // let the response come and saved in buffer of operating system
			System.out.println("\nflushInputReportQueueR : " + scrh.flushInputReportQueueR(handle));

			for(int q=0; q<inputReportBuffer.length; q++) {
				inputReportBuffer[q] = 0x00;
			}
			ret = scrh.readInputReportWithTimeoutR(handle, inputReportBuffer, 100);
			System.out.println("\nreadInputReportWithTimeout : " + ret);
			System.out.println(scrh.formatReportToHexR(inputReportBuffer, " "));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\ndriver : "+ scrh.findDriverServingHIDDeviceR(PORT));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// windows mouse
			System.out.println("\ndriver : "+ scrh.findDriverServingHIDDeviceR("HID\\VID_04CA&PID_0061\\6&35F47D18&0&0000"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("\ncloseHidDevice : " + scrh.closeHidDeviceR(handle));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
