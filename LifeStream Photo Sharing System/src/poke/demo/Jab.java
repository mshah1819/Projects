/*
 * copyright 2012, gash
 * 
 * Gash licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package poke.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import poke.client.ClientConnection;

public class Jab {
	private String tag;
	private int count;

	// public Jab(String tag) {
	// this.tag = tag;
	// }

	public void run() {
		byte[] byteImage = null;

		HashMap<String, Integer> nodePortLog = new HashMap<String, Integer>();
		nodePortLog.put("1", 5570);
		nodePortLog.put("2", 6570);
		nodePortLog.put("3", 7570);
		nodePortLog.put("4", 8570);
		int port = 1 + (int) (Math.random() * 4);
		ClientConnection cc = ClientConnection
				.initConnection("localhost", port);

		// Adding user
		cc.addUser("tyro");

		// Add an image
		String fileSource = "/home/krunal/Pictures/krunal.jpg";
		try {
			byteImage = ImageToByte(new File(fileSource));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// The code should first find the Server having the User and then add the image to that Server
		// Currently coded considering the server is known
		cc.addImage(21, byteImage);

		// Give a userId and retrieve images
		// Retrieving images for a particular user
		cc.retrieveImage(21);

	}

	public static byte[] ImageToByte(File file) throws FileNotFoundException {
		byte[] bytes = null;
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
				// System.out.println("read " + readNum + " bytes,");
			}
			bytes = bos.toByteArray();
			bos.close();
			fis.close();
		} catch (IOException ex) {
		}

		return bytes;
	}

	public static void byteToImage(byte[] imageInByte, String imageFileLocation)
			throws IOException {
		try {
			// convert byte array back to BufferedImage
			System.out.println("Converting byteToImage");
			InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "jpg", new File(imageFileLocation));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			Jab jab = new Jab();
			jab.run();

			Thread.sleep(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
