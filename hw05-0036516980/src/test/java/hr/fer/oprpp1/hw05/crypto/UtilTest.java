package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilTest {
	@Test
	public void hexToByteTest() {
		byte[] arr = {1, -82, 34};
		Assertions.assertArrayEquals(arr, Util.hexToByte("01aE22"));
		Assertions.assertThrows(NullPointerException.class, () -> Util.hexToByte(null));
		Assertions.assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("01ae2"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("01Pe2"));
	}
	@Test
	public void byteToHexTest() {
		byte[] arr = {1, -82, 34};
		Assertions.assertEquals("01ae22", Util.byteToHex(arr));
		Assertions.assertThrows(NullPointerException.class, () -> Util.byteToHex(null));
	}
}
