package kkckkc.jsourcepad.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SedUtils {
    private static Logger logger = LoggerFactory.getLogger(SedUtils.class);

	private static final Pattern EXPRESSION = Pattern.compile("s/([^/]+)/([^/]+)/([^;]*);"); 
	
	public static String applySedExpressions(String text, String expressions) {
		Matcher matcher = EXPRESSION.matcher(expressions);
		while (matcher.find()) {
			text = applySedExpression(text, matcher.group());
		}
		return text;
	}
	
	public static String applySedExpression(String text, String expression) {
		Matcher matcher = EXPRESSION.matcher(expression);
		if (! matcher.find()) return text;
		
		String search = matcher.group(1);
		String replace = matcher.group(2);
		String options = matcher.group(3);
		
		if (! "g".equals(options)) {
			// TODO: Check this
            logger.error("Option " + options + " not supported yet");
		}
		
		return text.replaceAll(search, replace);
	}
}
