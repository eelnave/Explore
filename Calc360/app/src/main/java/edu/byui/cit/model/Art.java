package edu.byui.cit.model;

public final class Art {
	private Art() {
	}

	public static final class StarExposure {
		private static final int fiveHundredRuleNum = 500;

		private StarExposure () {
		}

		public static double calculateStarExposureLength(double focalLength, double cropFactor) {
			return fiveHundredRuleNum / (cropFactor * focalLength);
		}
	}

	public static final class MusicDuration {
		private MusicDuration() {
		}

		public static int calculateSongDuration(double measures, double bpm, double timeSig) {
			return (int)Math.round(timeSig * (measures / bpm) * 60);
		}

		public static int calculateHours(int duration) {
			return duration / 3600;
		}

		public static int calculateMinutes(int duration) {
			return (duration % 3600) / 60;
		}

		public static int calculateSeconds(int duration) {
			return duration % 60;
		}
	}
}
