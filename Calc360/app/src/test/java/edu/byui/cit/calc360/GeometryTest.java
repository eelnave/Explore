package edu.byui.cit.calc360;

import org.junit.Test;

import edu.byui.cit.model.Geometry.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public final class GeometryTest {
	private static final double delta = 1e-9, epsilon = 1e-8;

	private static double delta(double v) {
		return v * epsilon;
	}

	@Test
	public void testPoint2D() {
		Point2D p1 = new Point2D(1, 2);
		Point2D mid = new Point2D(2, 3);
		Point2D p2 = new Point2D(3, 4);
		double dist = 2.0 * Math.sqrt(2.0);
		assertTrue(p1.equals(p1.midpoint(p1)));
		assertTrue(p2.equals(p2.midpoint(p2)));
		assertTrue(mid.equals(p1.midpoint(p2)));
		assertTrue(mid.equals(p2.midpoint(p1)));

		assertEquals(0, p1.distance(p1), delta);
		assertEquals(0, p2.distance(p2), delta);
		assertEquals(dist, p1.distance(p2), delta);
		assertEquals(dist, p2.distance(p1), delta);
	}

	@Test
	public void testLine2D() {
	}

	@Test
	public void testCircle() {
		double r = 5, d = 2.0 * r, c = 2.0 * Math.PI * r, a = Math.PI * r * r;
		assertEquals(r, Circle.radiusD(d), delta);
		assertEquals(r, Circle.radiusC(c), delta);
		assertEquals(r, Circle.radiusA(a), delta);
		assertEquals(d, Circle.diameter(r), delta);
		assertEquals(c, Circle.circum(r), delta);
		assertEquals(a, Circle.area(r), delta);
	}

	@Test
	public void testEllipse() {
		double minor = 3, major = 5;
		assertEquals(Circle.circum(minor), Ellipse.perimeter(minor, minor), delta);
		assertEquals(Circle.area(major), Ellipse.area(major, major), delta);
		double perim = Ellipse.perimeter(minor, major);
		double area = Ellipse.area(minor, major);
		assertEquals(minor, Ellipse.radiusRP(major, perim), delta);
		assertEquals(major, Ellipse.radiusRP(minor, perim), delta);
		assertEquals(minor, Ellipse.radiusRA(major, area), delta);
		assertEquals(major, Ellipse.radiusRA(minor, area), delta);
		assertEquals(minor, Ellipse.minorPA(perim, area), delta);
		assertEquals(major, Ellipse.majorPA(perim, area), delta);
	}

	@Test
	public void testRightTriangle() {
		double a = 15, b = 7, hyp = Math.sqrt(a * a + b * b);
		double P = a + b + hyp;
		double A = a * b / 2.0;
		assertEquals(a, RightTriangle.sideSH(b, hyp), delta);
		assertEquals(b, RightTriangle.sideSH(a, hyp), delta);
		assertEquals(hyp, RightTriangle.hypot(a, b), delta);
		assertEquals(P, RightTriangle.perimeter(a, b, hyp), delta);
		assertEquals(A, RightTriangle.area(a, b), delta);

		double alpha = RightTriangle.angleOH(a, hyp);
		double beta = RightTriangle.angleOH(b, hyp);
		double alpha2 = RightTriangle.angleA(beta);
		double beta2 = RightTriangle.angleA(alpha);
		assertEquals(alpha, alpha2, delta);
		assertEquals(beta, beta2, delta);

		assertEquals(a, RightTriangle.sideSH(b, hyp), delta);
		assertEquals(b, RightTriangle.sideSH(a, hyp), delta);
		assertEquals(a, RightTriangle.sideSO(b, alpha), delta);
		assertEquals(b, RightTriangle.sideSO(a, beta), delta);
		assertEquals(a, RightTriangle.sideSAr(b, A), delta);
		assertEquals(b, RightTriangle.sideSAr(a, A), delta);
		assertEquals(a, RightTriangle.sideHO(hyp, alpha), delta);
		assertEquals(b, RightTriangle.sideHO(hyp, beta), delta);
	}

	@Test
	public void testTriangle() {
		double a = 3, b = 4, c = 5;
		double gamma = Math.PI / 2.0;
		double beta = Math.asin(b * Math.sin(gamma) / c);
		double alpha = Math.PI - gamma - beta;
		double delta = 1e-7;

		assertEquals(alpha, Triangle.angleAA(beta, gamma), delta);
		assertEquals(beta, Triangle.angleAA(gamma, alpha), delta);
		assertEquals(gamma, Triangle.angleAA(alpha, beta), delta);

		assertEquals(alpha, Triangle.angleCosines(a, b, c), delta);
		assertEquals(alpha, Triangle.angleCosines(a, c, b), delta);
		assertEquals(beta, Triangle.angleCosines(b, c, a), delta);
		assertEquals(beta, Triangle.angleCosines(b, a, c), delta);
		assertEquals(gamma, Triangle.angleCosines(c, a, b), delta);
		assertEquals(gamma, Triangle.angleCosines(c, b, a), delta);

		assertEquals(alpha, Triangle.angleSines(a, beta, b), delta);
		assertEquals(alpha, Triangle.angleSines(a, gamma, c), delta);
		assertEquals(beta, Triangle.angleSines(b, alpha, a), delta);
		assertEquals(beta, Triangle.angleSines(b, gamma, c), delta);
		assertEquals(gamma, Triangle.angleSines(c, alpha, a), delta);
		assertEquals(gamma, Triangle.angleSines(c, beta, b), delta);

		assertEquals(a, Triangle.sideSines(alpha, b, beta), delta);
		assertEquals(a, Triangle.sideSines(alpha, c, gamma), delta);
		assertEquals(b, Triangle.sideSines(beta, c, gamma), delta);
		assertEquals(b, Triangle.sideSines(beta, a, alpha), delta);
		assertEquals(c, Triangle.sideSines(gamma, a, alpha), delta);
		assertEquals(c, Triangle.sideSines(gamma, b, beta), delta);

		assertEquals(a, Triangle.sideCosines(b, alpha, c), delta);
		assertEquals(a, Triangle.sideCosines(c, alpha, b), delta);
		assertEquals(b, Triangle.sideCosines(c, beta, a), delta);
		assertEquals(b, Triangle.sideCosines(a, beta, c), delta);
		assertEquals(c, Triangle.sideCosines(a, gamma, b), delta);
		assertEquals(c, Triangle.sideCosines(b, gamma, a), delta);

		assertEquals(a+b+c, Triangle.perimeter(a, b, c), delta);
		assertEquals(RightTriangle.area(a, b), Triangle.area(a, b, c), delta);

		a = 3; b = 4; c = 6;
		double perim = Triangle.perimeter(a, b, c);
		assertEquals(a, Triangle.sideSSP(b, c, perim), delta);
		assertEquals(b, Triangle.sideSSP(a, c, perim), delta);
		assertEquals(c, Triangle.sideSSP(a, b, perim), delta);
		double area = Triangle.area(a, b, c);
		assertTrue(
				Math.abs(a - Triangle.sideSSA(b, c, area)) < a * delta ||
				Math.abs(b - Triangle.sideSSA(a, c, area)) < b * delta ||
				Math.abs(c - Triangle.sideSSA(a, b, area)) < c * delta);
	}

	@Test
	public void testSquare() {
		double side = 3;
		double diag = Math.sqrt(2) * side;
		double perim = 4.0 * side;
		double area = side * side;
		assertEquals(side, Square.sideD(diag), delta);
		assertEquals(side, Square.sideP(perim), delta);
		assertEquals(side, Square.sideA(area), delta);
		assertEquals(diag, Square.diagonal(side), delta);
		assertEquals(perim, Square.perimeter(side), delta);
		assertEquals(area, Square.area(side), delta);
	}

	@Test
	public void testRectangle() {
		double w = 5, h = 3, d = Math.sqrt(w * w + h * h);
		double p = 2 * (w + h), a = w * h;
		assertEquals(w, Rectangle.sideSD(h, d), delta);
		assertEquals(w, Rectangle.sideSP(h, p), delta);
		assertEquals(w, Rectangle.sideSA(h, a), delta);
		assertEquals(h, Rectangle.sideDP(d, p), delta);
		assertEquals(h, Rectangle.sideDA(d, a), delta);
		assertEquals(h, Rectangle.sidePA(p, a), delta);
		assertEquals(d, Rectangle.diagonal(w, h), delta);
		assertEquals(p, Rectangle.perimeter(w, h), delta);
		assertEquals(a, Rectangle.area(w, h), delta);
	}

	@Test
	public void testSphere() {
		double r = 5;
		double d = 2.0 * r;
		double A = 4.0 * Math.PI * r * r;
		double V = 4.0 * Math.PI * r * r * r / 3.0;
		assertEquals(r, Sphere.radiusD(d), delta);
		assertEquals(r, Sphere.radiusA(A), delta);
		assertEquals(r, Sphere.radiusV(V), delta);
		assertEquals(d, Sphere.diameter(r), delta);
		assertEquals(A, Sphere.surfArea(r), delta);
		assertEquals(V, Sphere.volume(r), delta);
	}

	@Test
	public void testEllipsoid() {
		double a = 3, b = 5, c = 7;
		double V = 4.0 * Math.PI * a * b * c / 3.0;
		assertEquals(a, Ellipsoid.radiusRRV(b, c, V), delta);
		assertEquals(b, Ellipsoid.radiusRRV(a, c, V), delta);
		assertEquals(c, Ellipsoid.radiusRRV(a, b, V), delta);
		assertEquals(V, Ellipsoid.volume(a, b, c), delta);
	}

	@Test
	public void testTorus() {
		double major = 7, minor = 3;
		double inner = major - minor, outer = major + minor;
		double A = 4.0 * Math.PI * Math.PI * major * minor;
		double V = 2.0 * Math.PI * Math.PI * major * minor * minor;
		assertEquals(inner, Torus.inner(major, minor), delta(inner));
		assertEquals(inner, Torus.innerTA(outer, A), delta(inner));
		assertEquals(inner, Torus.innerTV(outer, V), delta(inner));
		assertEquals(outer, Torus.outer(major, minor), delta(outer));
		assertEquals(outer, Torus.outerIA(inner, A), delta(outer));
		assertEquals(outer, Torus.outerIV(inner, V), delta(outer));
		assertEquals(major, Torus.majorIT(inner, outer), delta(major));
		assertEquals(major, Torus.majorNI(minor, inner), delta(major));
		assertEquals(major, Torus.majorNT(minor, outer), delta(major));
		assertEquals(major, Torus.majorNA(minor, A), delta(major));
		assertEquals(major, Torus.majorNV(minor, V), delta(major));
		assertEquals(minor, Torus.minorJI(major, inner), delta(minor));
		assertEquals(minor, Torus.minorJT(major, outer), delta(minor));
		assertEquals(minor, Torus.minorJA(major, A), delta(minor));
		assertEquals(minor, Torus.minorJV(major, V), delta(minor));
		assertEquals(A, Torus.surfArea(major, minor), delta(A));
		assertEquals(V, Torus.volume(major, minor), delta(V));
	}

	@Test
	public void testCylinder() {
		double r = 3, h = 5;
		double A = 2.0 * Math.PI * r * (r + h);
		double V = Math.PI * r * r * h;
		assertEquals(r, Cylinder.radiusHA(h, A), delta);
		assertEquals(r, Cylinder.radiusHV(h, V), delta);
		assertEquals(h, Cylinder.heightRA(r, A), delta);
		assertEquals(h, Cylinder.heightRV(r, V), delta);
		assertEquals(A, Cylinder.surfArea(r, h), delta);
		assertEquals(V, Cylinder.volume(r, h), delta);
	}

	@Test
	public void testCone() {
		double r = 2, h = 5;
		double s = Math.sqrt(r * r + h * h);
		double A = Math.PI * r * (r + s);
		double V = Math.PI * r * r * h / 3;

		assertEquals(s, Cone.sideLen(r, h), delta);
		assertEquals(A, Cone.surfArea(r, h), delta);
		assertEquals(V, Cone.volume(r, h), delta);

		assertEquals(r, Cone.radiusHS(h, s), delta);
		assertEquals(r, Cone.radiusHA(h, A), delta);
		assertEquals(r, Cone.radiusHV(h, V), delta);

		assertEquals(h, Cone.heightRS(r, s), delta);
		assertEquals(h, Cone.heightRA(r, A), delta);
		assertEquals(h, Cone.heightRV(r, V), delta);
	}

	@Test
	public void testRectangularPrism() {
		double w = 3, h = 5, g = 7;
		double A = 2 * (w * h + h * g + w * g);
		double V = w * h * g;
		assertEquals(w, RectangularPrism.sideSSA(h, g, A), delta);
		assertEquals(h, RectangularPrism.sideSSA(w, g, A), delta);
		assertEquals(g, RectangularPrism.sideSSA(w, h, A), delta);

		assertEquals(w, RectangularPrism.sideSSV(h, g, V), delta);
		assertEquals(h, RectangularPrism.sideSSV(w, g, V), delta);
		assertEquals(g, RectangularPrism.sideSSV(w, h, V), delta);

		assertEquals(h, RectangularPrism.sideSAV(w, A, V), delta);
//		assertEquals(w, RectPrism.sideSAV(h, A, V), delta);
		assertEquals(h, RectangularPrism.sideSAV(g, A, V), delta);

		assertEquals(A, RectangularPrism.surfArea(w, h, g), delta);
		assertEquals(V, RectangularPrism.volume(w, h, g), delta);
	}

	@Test
	public void testPyramid() {
		double wid = 3, len = 1.5, hei = 2, vol = wid * len * hei / 3.0;
		assertEquals(wid, Pyramid.width(len, hei, vol), delta);
		assertEquals(len, Pyramid.length(wid, hei, vol), delta);
		assertEquals(hei, Pyramid.height(wid, len, vol), delta);
		assertEquals(vol, Pyramid.volume(wid, len, hei), delta);
	}
}
