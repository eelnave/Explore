package edu.byui.cit.calc360;

import org.junit.Test;

import static edu.byui.cit.model.Art.*;
import static org.junit.Assert.assertEquals;

public final class ArtTest {
	@Test
	public void testStarExposure() {
		double focalLength = 14, cropFactor = 1.4;
		double delta = 0.1;
		assertEquals(25.5, StarExposure.calculateStarExposureLength(focalLength, cropFactor), delta);
	}

	@Test
	public void testMusicDuration() {
		double measures = 85, bpm = 75, timeSig = 4;
		int duration = 272;
		double delta = 0.1;
		assertEquals(272, MusicDuration.calculateSongDuration(measures, bpm, timeSig), delta);
		assertEquals(0, MusicDuration.calculateHours(duration), delta);
		assertEquals(4, MusicDuration.calculateMinutes(duration), delta);
		assertEquals(32, MusicDuration.calculateSeconds(duration), delta);
	}

}
