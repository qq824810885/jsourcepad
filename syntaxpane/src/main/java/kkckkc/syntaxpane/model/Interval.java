package kkckkc.syntaxpane.model;

public class Interval implements Comparable<Interval> {

	protected int start;
	protected int end;
	
	public Interval(int start, int end) {
		if (end < start) {
			this.start = end;
			this.end = start;
		} else {
			this.start = start;
			this.end = end;
		}
	}

	public final int getStart() {
		return start;
	}
	
	public final int getEnd() {
		return end;
	}
	
	public final boolean contains(int i) {
		return i >= start && i <= end;
	}
	
	public final boolean contains(Interval oi) {
		return oi.start >= this.start && oi.end <= this.end;
	}
	
	public final boolean overlaps(Interval oi) {
		return this.start <= oi.end && oi.start <= this.end;
	}
	
	public final Interval overlap(Interval oi) {
		return new Interval(
					Math.max(this.start, oi.getStart()),
					Math.min(this.end, oi.getEnd()));
	}
	
	public int hashCode() {
		return this.start << 8 | this.end;
	}
	
	public final boolean isEmpty() {
		return end <= start;
	}
	
	public boolean equals(Object other) {
        return other instanceof Interval && ((Interval) other).start == start && ((Interval) other).end == end;
    }
	
	public String toString() {
		return "[" + start + " - " + end + "]";
	}

	public int compareTo(Interval o) {
        if (start > o.start) return 1;
        if (start < o.start) return -1;
        return 0;
	}

	public final int getLength() {
		return end - start;
	}

	public static Interval createWithLength(int start, int len) {
	    return new Interval(start, start + len);
    }

	public static Interval createEmpty(int start) {
	    return createWithLength(start, 0);
    }

    public static Interval offset(Interval interval, int offset) {
        return new Interval(interval.getStart() + offset, interval.getEnd() + offset);
    }

}
