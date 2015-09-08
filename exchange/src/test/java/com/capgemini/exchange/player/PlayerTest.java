package com.capgemini.exchange.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testPlayerHas10kPLNAtStart() {
		Player player = new Player();
		assertEquals(new Double(10e3), player.getPLNWallet().value());
	}

}
