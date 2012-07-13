package org.mimacom.mega.translator.web;

import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MainTest {
	@Test
	public void testTestMe() throws Exception {
		Main main = new Main();
		int i = main.testMe(10);
		assertEquals(10, i);
	}
}
