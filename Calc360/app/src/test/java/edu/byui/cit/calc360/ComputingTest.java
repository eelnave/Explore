package edu.byui.cit.calc360;

import org.junit.Test;

import edu.byui.cit.model.Computing;

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

	@Test
	public void testPasswordAttack() {
		int domain = 52;
		int length = 8;
		int keysSecond = 1000000;
		int machines = 1;
		double keyspace = Computing.PasswordAttack.getKeySpace(domain, length);

		double delta = 1e-6;
		assertEquals(53459728531456.0, keyspace, delta);

		assertEquals(5.3459728531456E7, Computing.PasswordAttack.
				getTimeSeconds(keyspace, keysSecond, machines), delta);

		assertEquals(14849.924592071111, Computing.PasswordAttack.
				getTimeHours(keyspace, keysSecond, machines), delta);

		assertEquals(618.746858002963, Computing.PasswordAttack.
				getTimeDays(keyspace, keysSecond, machines), delta);

		assertEquals(1.6951968712409946, Computing.PasswordAttack.
				getTimeYears(keyspace, keysSecond, machines), delta);
	}
}
