package kkckkc.syntaxpane.parse;

import kkckkc.syntaxpane.regex.Matcher;

import java.util.BitSet;
import java.util.Iterator;

public class MatcherCollectionIterator implements Iterator<Integer> {
	private Matcher[] matchers = null;
	private Boolean[] matchState = null;
	private BitSet ignored;
	private IteratorPointer pointer = new IteratorPointer();
	
	public MatcherCollectionIterator(Matcher[] matchers) {
		this.matchers = matchers;
        this.ignored = new BitSet(matchers.length);
	}
	
	public void setPosition(int position) {
		pointer.reposition(position);
	}

    public void ignore() {
        this.ignored.set(pointer.getMatcher());
    }

	@Override
	public boolean hasNext() {
		if (matchState == null) init();

        int currentPosition = pointer.getPosition();
		int offset = Integer.MAX_VALUE;
		int match = -1;
		for (int i = 0; i < matchers.length; i++) {
			if (matchers[i] == null) continue;

            // If this is ignored
            if (ignored.get(i)) continue;

			// If matcher doesn't find any more matches in string
			if (matchState[i] != null && ! matchState[i]) continue;

			// If match is obsolete or has never been initialized, find a new match
			if (matchState[i] == null || matchers[i].start() < currentPosition) {
				matchState[i] = matchers[i].find(currentPosition);
				
				// Abort if no match was found
				if (! matchState[i]) continue;
			}
			
			// If this match better than previous matches
			if (matchers[i].start() < offset) {
				offset = matchers[i].start();
				match = i;
			}
			
			// If we have already found a "best" match, no need to search anymore
			if (offset == currentPosition) break;
		}
		
		// If no match was found
		if (match == -1) return false;
		
		// Update iterator state
		pointer.update(match);

        ignored.clear();

		return true;
	}

	private void init() {
		matchState = new Boolean[matchers.length];
		for (int i = 0; i < matchers.length; i++) {
			if (matchers[i] == null) continue;
			matchState[i] = matchers[i].find();
            if (matchers[i].start() == 0) return;
		}
	}

	@Override
	public Integer next() {
		return pointer.getMatcher();
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	
	class IteratorPointer {
		private int position = 0;
		private int matcher;

		public int getPosition() {
			return position;
		}
		
		public void update(int matcher) {
			this.matcher = matcher;
			position = matchers[this.matcher].end();
		}

		public void reposition(int position) {
			this.position = position;
		}

		public int getMatcher() {
			return matcher;
		}
	}
}
