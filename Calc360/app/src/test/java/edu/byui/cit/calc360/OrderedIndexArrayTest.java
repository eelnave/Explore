package edu.byui.cit.calc360;

import org.junit.Test;

import edu.byui.cit.model.OrderedIndexArray;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class OrderedIndexArrayTest {
	private static final byte P = 1, V = 2, n = 3, R = 4, T = 5;

	@Test
	public void testAdd() {
		OrderedIndexArray list = new OrderedIndexArray(4, 6);
		assertFalse(list.contains((byte)0));
		assertFalse(list.contains(P));
		assertEquals(0x0, list.bitset());
		assertEquals(0, list.size());

		list.add(P);
		assertTrue(list.contains(P));
		assertEquals(0x02, list.bitset());
		assertEquals(1, list.size());

		list.add(P);
		assertTrue(list.contains(P));
		assertEquals(0x02, list.bitset());
		assertEquals(1, list.size());

		list.add(V);
		list.add(n);
		list.add(R);
		assertTrue(list.contains(P));
		assertTrue(list.contains(V));
		assertTrue(list.contains(n));
		assertTrue(list.contains(R));
		assertEquals(0x1e, list.bitset());
		assertEquals(4, list.size());

		list.add(P);
		list.add(n);
		list.add(R);
		assertTrue(list.contains(P));
		assertTrue(list.contains(V));
		assertTrue(list.contains(n));
		assertTrue(list.contains(R));
		assertEquals(0x1e, list.bitset());
		assertEquals(4, list.size());

		list.add(T);
		assertFalse(list.contains(P));
		assertTrue(list.contains(V));
		assertTrue(list.contains(n));
		assertTrue(list.contains(R));
		assertTrue(list.contains(T));
		assertEquals(0x3c, list.bitset());
		assertEquals(4, list.size());
	}


	@Test
	public void testRemove() {
		OrderedIndexArray list = new OrderedIndexArray(4, 6);
		list.remove(P);
		assertFalse(list.contains(P));
		assertEquals(0x0, list.bitset());
		assertEquals(0, list.size());

		list.add(P);
		list.remove(P);
		assertFalse(list.contains(P));
		assertEquals(0x0, list.bitset());
		assertEquals(0, list.size());

		list.add(P);
		list.add(V);
		list.remove(V);
		assertTrue(list.contains(P));
		assertFalse(list.contains(V));
		assertEquals(0x02, list.bitset());
		assertEquals(1, list.size());

		list.remove(P);
		assertFalse(list.contains(P));
		assertFalse(list.contains(V));
		assertEquals(0x0, list.bitset());
		assertEquals(0, list.size());

		list.add(P);
		list.add(V);
		list.remove(P);
		assertFalse(list.contains(P));
		assertTrue(list.contains(V));
		assertEquals(0x04, list.bitset());
		assertEquals(1, list.size());

		list.remove(V);
		assertFalse(list.contains(P));
		assertFalse(list.contains(V));
		assertEquals(0x0, list.bitset());
		assertEquals(0, list.size());

		list.add(P);
		list.add(V);
		list.add(n);
		list.add(R);
		list.remove(R);
		assertTrue(list.contains(P));
		assertTrue(list.contains(V));
		assertTrue(list.contains(n));
		assertFalse(list.contains(R));
		assertEquals(0x0e, list.bitset());
		assertEquals(3, list.size());

		list.add(R);
		list.remove(V);
		assertTrue(list.contains(P));
		assertFalse(list.contains(V));
		assertTrue(list.contains(n));
		assertTrue(list.contains(R));
		assertEquals(0x1a, list.bitset());
		assertEquals(3, list.size());

		list.add(V);
		list.add(T);
		assertFalse(list.contains(P));
		assertTrue(list.contains(V));
		assertTrue(list.contains(n));
		assertTrue(list.contains(R));
		assertTrue(list.contains(T));
		assertEquals(0x3c, list.bitset());
		assertEquals(4, list.size());

		list.remove(V);
		assertFalse(list.contains(P));
		assertFalse(list.contains(V));
		assertTrue(list.contains(n));
		assertTrue(list.contains(R));
		assertTrue(list.contains(T));
		assertEquals(0x38, list.bitset());
		assertEquals(3, list.size());
	}
}
