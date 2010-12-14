package kkckkc.syntaxpane.parse.grammar.util;

import com.google.common.base.Supplier;
import kkckkc.syntaxpane.regex.Pattern;
import kkckkc.syntaxpane.regex.PatternFactory;



public class DefaultPatternSupplier implements Supplier<Pattern> {

	private String patternString;
	private PatternFactory factory;

	public DefaultPatternSupplier(String patternString, PatternFactory factory) {
		this.patternString = patternString;
		this.factory = factory;
	}
	
	@Override
	public Pattern get() {
		if (patternString == null) return null;
		return factory.create(patternString);
	}

}
