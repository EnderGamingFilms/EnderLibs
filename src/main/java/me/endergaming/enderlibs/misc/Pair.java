package me.endergaming.enderlibs.misc;

public class Pair<A, B> {

	private A a;
	private B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A getA() {
		return this.a;
	}

	public void setA(A a) {
		this.a = a;
	}

	public B getB() {
		return this.b;
	}

	public void setB(B b) {
		this.b = b;
	}

	public boolean matchOr(Object o) {
		return this.a.equals(o) || this.b.equals(o);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Pair)) {
			return false;
		}

		Pair<?, ?> p = (Pair<?, ?>) o;
		return this.a.equals(p.getA()) && this.b.equals(p.getB());
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.a.hashCode();
		return hash * 31 + this.b.hashCode();
	}

	public static Pair<?, ?> fromString(String s) {
		String[] nums = s.split(",");
		return new Pair<>(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
	}

	@Override
	public String toString() {
		return this.a + "," + this.b;
	}

	public static <A, B> Pair<A, B> of(A a, B b) {
		return new Pair<>(a, b);
	}

}