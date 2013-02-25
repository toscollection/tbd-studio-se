// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap.ui.expressionutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapExpressionManager {

    private final static String EXPRESSION_PATTERN = "(\\[)\\s*(\\w+)\\s*\\.(\\w+\\s*:\\s*(/.+?)+(/@.+?)*)\\s*(\\])|((?!\\[)\\s*\\w+)\\s*\\.\\s*(\\w+(?!\\]))";

    private final String SUBS_PATTERN_FOR_REPLACE_LOCATION = "{0}(\\s*){1}(\\s*)\\.(\\s*){2}(\\s*){3}";

    private Perl5Matcher matcher = new Perl5Matcher();

    private Perl5Compiler compiler = new Perl5Compiler();

    private Pattern pattern;

    private PatternMatcherInput patternMatcherInput;

    public List<TableEntryLocation> parseTableEntryLocation(String expression) {
        List<TableEntryLocation> locations = new ArrayList<TableEntryLocation>();
        recompilePatternIfNecessary(EXPRESSION_PATTERN);
        patternMatcherInput = new PatternMatcherInput(expression);

        while (matcher.contains(patternMatcherInput, pattern)) {
            MatchResult matchResult = matcher.getMatch();
            if (matchResult.group(1) != null) {
                TableEntryLocation location = new TableEntryLocation(matchResult.group(1), matchResult.group(2),
                        matchResult.group(3), matchResult.group(6));
                locations.add(location);
            } else if (matchResult.group(matchResult.groups() - 1) != null) {
                TableEntryLocation location = new TableEntryLocation(matchResult.group(matchResult.groups() - 2),
                        matchResult.group(matchResult.groups() - 1));
                locations.add(location);
            }
        }
        return locations;

    }

    public List<String> getMatchedExpression(String expression) {
        List<String> matched = new ArrayList<String>();
        if (expression == null) {
            return matched;
        }
        recompilePatternIfNecessary(EXPRESSION_PATTERN);
        patternMatcherInput = new PatternMatcherInput(expression);
        while (matcher.contains(patternMatcherInput, pattern)) {
            MatchResult matchResult = matcher.getMatch();
            if (matchResult.group(0) != null) {
                matched.add(matchResult.group(0).trim());
            }
        }
        return matched;
    }

    private void recompilePatternIfNecessary(String regexpPattern) {
        if (pattern == null || !regexpPattern.equals(pattern.getPattern())) {
            try {
                pattern = compiler.compile(regexpPattern);
            } catch (MalformedPatternException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String replaceExpression(String expression, TableEntryLocation oldLocation, TableEntryLocation newLocation) {
        return "";
    }
}
