package edu.byui.cit.model;

import edu.byui.cit.model.Mathematics.Quadratic;


public final class Geometry {
	private Geometry() {
	}

	private static double absDiff(double v1, double v2) {
		return Math.abs(v1 - v2);
	}

	private static double epsilon(double v1, double v2) {
		return Math.min(Math.abs(v1), Math.abs(v2)) * 1e-3;
	}


	public static final class Point2D {
		public final double x, y;

		public Point2D(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			boolean eq = false;
			if (obj instanceof Point2D) {
				Point2D p1 = this;
				Point2D p2 = (Point2D)obj;
				eq = absDiff(p1.x, p2.x) < epsilon(p1.x, p2.x) &&
						absDiff(p1.y, p2.y) < epsilon(p1.y, p2.y);
			}
			return eq;
		}

		public double distance(Point2D p2) {
			Point2D p1 = this;
			double dx = p2.x - p1.x;
			double dy = p2.y - p1.y;
			return Math.sqrt(dx * dx + dy * dy);
		}

		public Point2D midpoint(Point2D p2) {
			Point2D p1 = this;
			return new Point2D((p1.x + p2.x) / 2.0, (p1.y + p2.y) / 2.0);
		}
	}


	public static final class Line2D {
	}


	public static final class Circle {
		private Circle() {
		}

		public static double radiusD(double diam) {
			return diam / 2.0;
		}

		public static double radiusC(double circum) {
			return circum / (2.0 * Math.PI);
		}

		public static double radiusA(double area) {
			return Math.sqrt(area / Math.PI);
		}

		public static double diameter(double radius) {
			return 2.0 * radius;
		}

		public static double circum(double radius) {
			return 2.0 * Math.PI * radius;
		}

		public static double area(double radius) {
			return Math.PI * radius * radius;
		}
	}


	public static final class Ellipse {
		private Ellipse() {
		}

		public static double radiusRP(double r2, double perim) {
			double part = perim / Math.PI;
			return Math.sqrt(part * part / 2.0 - r2 * r2);
		}

		public static double radiusRA(double r2, double area) {
			return area / (Math.PI * r2);
		}

		private static double radiusPA(double perim, double area) {
			double a = 2.0 * Math.PI * Math.PI;
			double b = -(perim * perim);
			double c = 2.0 * area * area;
			return Math.sqrt(Quadratic.posRoot(a, b, c));
		}

		public static double minorPA(double perim, double area) {
			double r1 = radiusPA(perim, area);
			double r2 = radiusRA(r1, area);
			return Math.min(r1, r2);
		}

		public static double majorPA(double perim, double area) {
			double r1 = radiusPA(perim, area);
			double r2 = radiusRA(r1, area);
			return Math.max(r1, r2);
		}

		public static double perimeter(double r1, double r2) {
			// P = 2pi sqrt((r1^2 + r2^2) / 2)
			double radic = (r1 * r1 + r2 * r2) / 2.0;
			return 2.0 * Math.PI * Math.sqrt(radic);
		}

		public static double area(double r1, double r2) {
			// A = pi r1 r2
			return Math.PI * r1 * r2;
		}
	}


	public static final class RightTriangle {
		private RightTriangle() {
		}

		/** Computes and returns a side from the other side and the
		 * angle that is opposite from the side to be computed. */
		public static double sideSO(double side, double oppAngle) {
			return side * Math.tan(oppAngle);
		}

		/** Computes and returns a side from the hypotenuse and the
		 * angle that is opposite from the side to be computed. */
		public static double sideHO(double hyp, double oppAngle) {
			return hyp * Math.sin(oppAngle);
		}

		/** Computes and returns a side from
		 * the other side and the hypotenuse. */
		public static double sideSH(double side, double hyp) {
			return Math.sqrt(hyp * hyp - side * side);
		}

		/** Computes and returns a side from the other side and the area. */
		public static double sideSAr(double side, double area) {
			return 2.0 * area / side;
		}

		/** Computes and returns the hypotenuse from two sides. */
		public static double hypot(double side1, double side2) {
			return Math.sqrt(side1 * side1 + side2 * side2);
		}

		public static double angleA(double angle) {
			double other = Math.PI / 2.0 - angle;
			if (other < 0) {
				other = Double.NaN;
			}
			return other;
		}

		/** Computes and returns an angle from the side opposite
		 * from the angle to be computed and the hypotenuse. */
		public static double angleOH(double oppSide, double hyp) {
			return Math.asin(oppSide / hyp);
		}

		public static double perimeter(double side1, double side2, double hyp) {
			return side1 + side2 + hyp;
		}

		public static double area(double side1, double side2) {
			return side1 * side2 / 2.0;
		}
	}


	public static final class Triangle {
		private Triangle() {
		}

		// Law of sines:       a           b            c
		//                ---------- = --------- = ---------- = d
		//                sin(alpha)   sin(beta)   sin(gamma)
		// where d is the diameter of the triangle's circumcircle.
		//
		// Law of cosines:  c^2 = a^2 + b^2 - 2ab cos(gamma)

		/** Computes and returns the angle opposite of side. */
		public static double angleSines(double side, double angleOther, double sideOther) {
			// Uses law of sines
			return Math.asin(side * Math.sin(angleOther) / sideOther);
		}

		/** Computes and returns the angle opposite of side3 in radians. */
		public static double angleCosines(double side3, double side1, double side2) {
			// Uses law of cosines
			double numer = side1 * side1 + side2 * side2 - side3 * side3;
			double denom = 2.0 * side1 * side2;
			return Math.acos(numer / denom);
		}

		public static double angleAA(double angle1, double angle2) {
			double other = Math.PI - (angle1 + angle2);
			if (other < 0) {
				other = Double.NaN;
			}
			return other;
		}

		public static double sideSines(double angle, double sideOther, double angleOther) {
			// Uses law of sines
			return Math.sin(angle) * (sideOther / Math.sin(angleOther));
		}

		public static double sideCosines(double side1, double angleBetween, double side2) {
			// Uses law of cosines
			double first = side1 * side1 + side2 * side2;
			double second = 2.0 * side1 * side2 * Math.cos(angleBetween);
			return Math.sqrt(first - second);

			// Equivalent form that could be used when gamma is small
			// c^2 = (a - b)^2 + 4ab sin^2(gamma/2)
		}

		public static double sideSSP(double side1, double side2, double perim) {
			double side3 = perim - (side1 + side2);
			if (side3 < 0) {
				side3 = Double.NaN;
			}
			return side3;
		}

		/* Using Heron's formula and a lot of algebra, we find that
		 * c = sqrt(a^2 + b^2 + 2 sqrt(a^2 b^2 - 4A^2))
		 *		OR
		 * c = sqrt(a^2 + b^2 - 2 sqrt(a^2 b^2 - 4A^2))
		 * In other words, given two sides and the area of a triangle,
		 * there are two possible lengths for the third side unless the
		 * triangle is an equilateral triangle. */
		public static double sideSSA(double side1, double side2, double area) {
			double a2 = side1 * side1;
			double b2 = side2 * side2;
			double A2 = area * area;
			double left = a2 + b2;
			double right = 2.0 * Math.sqrt(a2 * b2 - 4.0 * A2);
			double c2 = left + right;
			return Math.sqrt(c2);
		}

		public static double perimeter(double side1, double side2, double side3) {
			return side1 + side2 + side3;
		}

		/** Calculates and returns the area of a triangle from
		 * the length of its three sides. Uses Heron's formula:
		 * A = sqrt(s * (s-a) * (s - b) * (s - c))
		 * where s = (a + b + c) / 2
		 */
		public static double area(double a, double b, double c) {
			double s = (a + b + c) / 2.0;
			return Math.sqrt(s * (s - a) * (s - b) * (s - c));
		}
	}


	public static final class Square {
		private Square() {
		}

		public static double sideD(double diag) {
			return diag / Math.sqrt(2.0);
		}

		public static double sideP(double perim) {
			return perim / 4.0;
		}

		public static double sideA(double area) {
			return Math.sqrt(area);
		}

		public static double diagonal(double side) {
			return Math.sqrt(2.0) * side;
		}

		public static double perimeter(double side) {
			return 4.0 * side;
		}

		public static double area(double side) {
			return side * side;
		}
	}


	public static final class Rectangle {
		private Rectangle() {
		}

		public static double sideSD(double side, double diag) {
			return RightTriangle.sideSH(side, diag);
		}

		public static double sideSP(double side, double perim) {
			return perim / 2.0 - side;
		}

		public static double sideSA(double side, double area) {
			return area / side;
		}

		public static double sideDP(double diag, double perim) {
			double a = 2.0, b = -perim, c = perim * perim / 4.0 - diag * diag;
			return Quadratic.smallerRoot(a, b, c);
		}

		public static double sideDA(double diag, double area) {
			double a = 1.0, b = -diag * diag, c = area * area;
			return Math.sqrt(Quadratic.smallerRoot(a, b, c));
		}

		public static double sidePA(double perim, double area) {
			double b = -perim / 2.0;
			return Quadratic.smallerRoot(1, b, area);
		}

		public static double diagonal(double w, double h) {
			return RightTriangle.hypot(w, h);
		}

		public static double perimeter(double w, double h) {
			return 2.0 * (w + h);
		}

		public static double area(double w, double h) {
			return w * h;
		}
	}


	public static final class Point3D {
		public double x, y, z;

		public Point3D() {
		}

		public Point3D(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public boolean equals(Object obj) {
			boolean eq = false;
			if (obj instanceof Point3D) {
				Point3D p1 = this;
				Point3D p2 = (Point3D)obj;
				eq = absDiff(p1.x, p2.x) < epsilon(p1.x, p2.x) &&
						absDiff(p1.y, p2.y) < epsilon(p1.y, p2.y) &&
						absDiff(p1.z, p2.z) < epsilon(p1.z, p2.z);
			}
			return eq;
		}

		public double distance(Point3D p2) {
			Point3D p1 = this;
			double dx = p2.x - p1.x;
			double dy = p2.y - p1.y;
			double dz = p2.z - p1.z;
			return Math.sqrt(dx * dx + dy * dy + dz * dz);
		}

		public Point3D midpoint(Point3D p2) {
			Point3D p1 = this;
			return new Point3D(
					(p1.x + p2.x) / 2.0,
					(p1.y + p2.y) / 2.0,
					(p1.z + p2.z) / 2.0);
		}
	}


	public static final class Line3D {
	}


	public static final class Sphere {
		private Sphere() {
		}

		public static double radiusD(double diam) {
			return diam / 2.0;
		}

		public static double radiusA(double A) {
			return Math.sqrt(A / (4.0 * Math.PI));
		}

		public static double radiusV(double V) {
			return Math.cbrt((3.0 * V) / (4.0 * Math.PI));
		}

		public static double diameter(double r) {
			return r * 2.0;
		}

		public static double surfArea(double r) {
			return 4.0 * Math.PI * r * r;
		}

		public static double volume(double r) {
			return 4.0 * Math.PI * r * r * r / 3.0;
		}
	}


	public static final class Ellipsoid {
		private Ellipsoid() {
		}

		public static double radiusRRV(double r1, double r2, double V) {
			return 3.0 * V / (4.0 * Math.PI * r1 * r2);
		}

		public static double volume(double r1, double r2, double r3) {
			return 4.0 * Math.PI * r1 * r2 * r3 / 3.0;
		}
	}


	public static final class Torus {
		private Torus() {
		}

		public static double inner(double major, double minor) {
			return major - minor;
		}

		public static double innerTA(double outer, double surfArea) {
			double pi2 = Math.PI * Math.PI;
			double a = pi2;
			double b = 0;
			double c = -pi2 * outer * outer + surfArea;
			return Quadratic.largerRoot(a, b, c);
		}

		/* Newton's method:
		 *             f(t)
		 * tNew = t - -----
		 *            f'(t)
		 * Starting from equation V = 2 pi^2 R r^2
		 * f(i) = pi^2 (t^3 - t^2i - ti^2 + i^3) / 4 - V
		 * f'(i) = pi^2 (-t^2 - 2ti + 3i^2) / 4
		 */
		public static double innerTV(double outer, double volume) {
			final double pi2_4 = Math.PI * Math.PI / 4.0;
			final double t = outer;
			final double t2 = t * t;
			double i = outer * 0.4;
			double iNew, diff;
			do {
				double i2 = i * i;
				double f = pi2_4 * (t2*t - t2*i - t*i2 + i2*i) - volume;
				double fp = pi2_4 * (3*i2 - 2*t*i - t2);
				iNew = i - f / fp;
				diff = Math.abs(iNew - i);
				i = iNew;
			} while (diff > Math.min(i, iNew) * 1e-9);
			return i;
		}

		public static double outer(double major, double minor) {
			return major + minor;
		}

		public static double outerIA(double inner, double surfArea) {
			double pi2 = Math.PI * Math.PI;
			double a = pi2;
			double b = 0;
			double c = -(pi2 * inner * inner + surfArea);
			return Quadratic.largerRoot(a, b, c);
		}

		/* Newton's method:
		 *             f(t)
		 * tNew = t - -----
		 *            f'(t)
		 * Starting from equation V = 2 pi^2 R r^2
		 * f(t) = pi^2 (t^3 - t^2i - ti^2 + i^3) / 4 - V
		 * f'(t) = pi^2 (3t^2 - 2ti - i^2) / 4
		 */
		public static double outerIV(double inner, double volume) {
			final double pi2_4 = Math.PI * Math.PI / 4.0;
			final double i = inner;
			final double i2 = i * i;
			double t = inner * 1.5;
			double tNew, diff;
			do {
				double t2 = t * t;
				double f = pi2_4 * (t2*t - t2*i - t*i2 + i2*i) - volume;
				double fp = pi2_4 * (3*t2 - 2*t*i - i2);
				tNew = t - f / fp;
				diff = Math.abs(tNew - t);
				t = tNew;
			} while (diff > Math.min(t, tNew) * 1e-9);
			return t;
		}

		public static double majorIT(double inner, double outer) {
			return (inner + outer) / 2.0;
		}

		public static double majorNI(double minor, double inner) {
			return inner + minor;
		}

		public static double majorNT(double minor, double outer) {
			return outer - minor;
		}

		public static double majorNA(double minor, double surfArea) {
			return surfArea / (4.0 * Math.PI * Math.PI * minor);
		}

		public static double majorNV(double minor, double volume) {
			return volume / (2.0 * Math.PI * Math.PI * minor * minor);
		}

//		public static double minorIT(double inner, double outer) {
//			return (outer - inner) / 2.0;
//		}

		public static double minorJI(double major, double inner) {
			return major - inner;
		}

		public static double minorJT(double major, double outer) {
			return outer - major;
		}

		public static double minorJA(double major, double surfArea) {
			return surfArea / (4.0 * Math.PI * Math.PI * major);
		}

		public static double minorJV(double major, double volume) {
			return Math.sqrt(volume / (2.0 * Math.PI * Math.PI * major));
		}

		/* A = 4 pi^2 j n */
		public static double surfArea(double major, double minor) {
			return 4.0 * Math.PI * Math.PI * major * minor;
		}

		/* V = 2 pi^2 j n^2 */
		public static double volume(double major, double minor) {
			return 2.0 * Math.PI * Math.PI * major * minor * minor;
		}
	}


	public static final class Cylinder {
		private Cylinder() {
		}

		public static double radiusHA(double h, double A) {
			double c = -A / (2.0 * Math.PI);
			return Quadratic.posRoot(1, h, c);
		}

		public static double radiusHV(double h, double V) {
			return Math.sqrt(V / (Math.PI * h));
		}

		public static double heightRA(double r, double A) {
			return A / (2.0 * Math.PI * r) - r;
		}

		public static double heightRV(double r, double V) {
			return V / (Math.PI * r * r);
		}

		public static double surfArea(double r, double h) {
			return 2.0 * Math.PI * r * (r + h);
		}

		public static double volume(double r, double h) {
			return Math.PI * r * r * h;
		}
	}


	public static final class Cone {
		private Cone() {
		}

		public static double radiusHS(double h, double s) {
			// r = sqrt(s^2 - h^2)
			return Math.sqrt(s * s - h * h);
		}

		/* Newton's method:
		 *             f(r)
		 * rNew = r - -----
		 *            f'(r)
		 * Starting from equation (1) we can get:
		 * f(r) = (pi^2 h^2 + 2 pi A)r^2 - A^2
		 * f'(r) = 2 * (pi^2 h^2 + 2 pi A)r
		 */
		public static double radiusHA(double h, double A) {
			double r0 = Math.sqrt(A);
			double diff;
			do {
				double temp = Math.PI * Math.PI * h * h + 2.0 * Math.PI * A;
				double f = temp * r0 * r0 - A * A;
				double fp = 2.0 * temp * r0;
				double r1 = r0 - f / fp;
				diff = Math.abs(r1 - r0);
				r0 = r1;
			} while (diff > 1e-9);
			return r0;
		}

		public static double radiusHV(double h, double V) {
			// r = sqrt(3V / (pi h))
			return Math.sqrt(3.0 * V / (Math.PI * h));
		}


		public static double heightRS(double r, double s) {
			// h = sqrt(s^2 - r^2)
			return Math.sqrt(s * s - r * r);
		}

		public static double heightRA(double r, double A) {
			// h = sqrt((A / (pi r) - r)^2 - r^2)
			double temp = A / (Math.PI * r) - r;
			return Math.sqrt(temp * temp - r * r);
		}

		public static double heightRV(double r, double V) {
			// h = 3V / (pi r^2)
			return (3.0 * V) / (Math.PI * r * r);
		}

		/* Multiple solutions:
		 * r from s, A
		 * r from s, V
		 * r from s, A, V
		 * h from s, A
		 * h from s, V
		 * h from s, A, V
		 */

		public static double sideLen(double r, double h) {
			// s = sqrt(r^2 + h^2)
			return Math.sqrt(r * r + h * h);
		}

		public static double surfArea(double r, double h) {
			// A = pi r (r + sqrt(r^2 + h^2))
			return Math.PI * r * (r + Math.sqrt(r * r + h * h));
		}

		public static double volume(double r, double h) {
			// V = pi r^2 h / 3
			return Math.PI * r * r * h / 3.0;
		}
	}


	public static final class RectangularPrism {
		private RectangularPrism() {
		}

		public static double sideSSA(double s2, double s3, double A) {
			double s1 = (A / 2 - s2 * s3) / (s2 + s3);
			return checkNeg(s1);
		}

		public static double sideSSV(double s2, double s3, double V) {
			double s1 = V / (s2 * s3);
			return checkNeg(s1);
		}

		public static double sideSAV(double s, double A, double V) {
			double s0 = s;
			double diff;
			do {
				double ss0 = s * s0;
				double As2 = A * s / 2.0;
				double f = ss0 * ss0 - As2 * s0 + V * s0 + V * s;
				double fp = 2.0 * ss0 * s - As2 + V;
				double s1 = s0 - f / fp;
				diff = Math.abs(s1 - s0);
				s0 = s1;
			} while (diff > 1e-9);
			return s0;
		}

		public static double surfArea(double w, double h, double g) {
			double A = 2.0 * (w * h + w * g + h * g);
			return checkNeg(A);
		}

		public static double volume(double w, double h, double g) {
			double V = w * h * g;
			return checkNeg(V);
		}

		private static double checkNeg(double x) {
			if (x < 0) {
				x = Double.NaN;
			}
			return x;
		}
	}


	public static final class Pyramid {
		private Pyramid() {
		}

		public static double width(double len, double hei, double vol) {
			// Compute width:  w = 3V / gh
			return (3.0 * vol) / (len * hei);
		}

		public static double length(double wid, double hei, double vol) {
			// Compute length:  g = 3V / wh
			return (3.0 * vol) / (wid * hei);
		}

		public static double height(double wid, double len, double vol) {
			// Compute height:  h = 3V / wg
			return (3.0 * vol) / (wid * len);
		}

		public static double volume(double wid, double len, double hei) {
			// Compute volume:  V = wgh / 3
			return wid * len * hei / 3.0;
		}
	}
}
