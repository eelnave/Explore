package edu.byui.cit.calc360;

import org.junit.Test;

import static edu.byui.cit.model.Computing.Subnet.*;
import static org.junit.Assert.*;

public final class ComputingTest {
	@Test
	public void testSubnet() {
		String goodSubnet = "255.255.255.128";
		String badSubnet = "255.255.4.0";
		String binary = "11111111111111111111111100000000";
		String ip = "192.168.52.35";

		assertEquals("192.168.52.127", getBroadcast(ip, goodSubnet));
		assertEquals("192.168.52.0", getNetworkAddress(ip, goodSubnet));
		assertEquals("255.255.255.0", binaryToIp(binary));
		assertEquals("11111111111111110000000000000000", ipToBinary("255.255.0.0"));
		assertEquals(true, isValidSubnet(goodSubnet));
		assertEquals(false, isValidSubnet(badSubnet));
		assertEquals(2, calculateNets(goodSubnet));
		assertEquals(126, calculateHosts(goodSubnet));
	}
}
