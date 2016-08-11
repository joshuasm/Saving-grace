package game.gos.main;

public class VectorToF {

	public float xpos;
	public float ypos;

	public static float worldXPos;
	public static float worldYPos;

	public VectorToF() {
		this.xpos = 0.0f;
		this.ypos = 0.0f;
	}

	public VectorToF(float xpos, float ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public static VectorToF zero() {
		return new VectorToF(0, 0);
	}

	public void normalize() {
		double length = Math.sqrt(xpos * xpos + ypos * ypos);

		if (length != 0.0) {
			float s = 1.0f / (float) length;
			xpos = xpos * s;
			ypos = ypos * s;

		}
	}

	public VectorToF getScreenLocation() {
		return new VectorToF(xpos, ypos);
	}

	public VectorToF getWorldLocation() {
		return new VectorToF(xpos - worldXPos, ypos - worldYPos);
	}

	public boolean equals(VectorToF vec) {
		return (this.xpos == vec.xpos && this.ypos == vec.ypos);
	}

	public VectorToF copy(VectorToF vec) {
		xpos = vec.xpos;
		ypos = vec.ypos;

		return new VectorToF(xpos, ypos);
	}

	public VectorToF add(VectorToF vec) {
		xpos = xpos + vec.xpos;
		ypos = ypos + vec.ypos;

		return new VectorToF(xpos, ypos);
	}

	public static void setWorldVariables(float wx, float wy) {
		worldXPos = wx;
		worldYPos = wy;
	}

	public static double getDistanceOnScreen(VectorToF vec1, VectorToF vec2) {
		float v1 = vec1.xpos - vec2.xpos;
		float v2 = vec2.ypos - vec2.ypos;

		return Math.sqrt(v1 * v1 + v2 * v2);
	}

	public double getDistanceBetweenWorldVectors(VectorToF vec) {
		float dx = Math.abs(getWorldLocation().xpos
				- vec.getWorldLocation().xpos);
		float dy = Math.abs(getWorldLocation().ypos
				- vec.getWorldLocation().ypos);

		return Math.abs(dx * dx - dy * dy);
	}

}
