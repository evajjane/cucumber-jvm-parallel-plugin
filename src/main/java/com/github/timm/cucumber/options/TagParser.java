package com.github.timm.cucumber.options;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.join;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

public class TagParser {

    private static final String QUOTE = Pattern.quote("\"");
    private static final String NOT_QUOTE_CHARS = "[^" + QUOTE + "]*?";
    private static final String COMMA = ",";
    private static final String TAG_GROUP = "(" + QUOTE + NOT_QUOTE_CHARS + QUOTE + ")";
    private static final Pattern TAG_GROUP_PATTERN = Pattern.compile(TAG_GROUP);
    private static final String FEATURE_TAG = "@(\\w)*";
    private static final Pattern FEATURE_TAG_PATTERN = Pattern.compile(FEATURE_TAG);

    public static String parseTags(final List<String> tags) {

        if (tags.isEmpty()) {
            return "";
        }

        return '"' + join(tags, "\",\"") + '"';

    }

    public static List<List<String>> splitQuotedTagsIntoParts(final String quotedTags) {

        final Matcher matcher = TAG_GROUP_PATTERN.matcher(quotedTags);
        final List<List<String>> allTags = new ArrayList<List<String>>();

        while (matcher.find()) {

            final String tags = matcher.group(1);

            allTags.add(asList(tags.replaceAll("\\\"", "").split(COMMA)));

        }

        return allTags;
    }

    public static List<String> getFeatureTags(final String contents) {

        final Matcher matcher = FEATURE_TAG_PATTERN.matcher(contents);
        List<String> featureTags = new ArrayList<String>();

        while (matcher.find()) {
            featureTags.add(matcher.group());
        }

        return featureTags;
    }

    public static Collection<String> filterTagsByName(final Collection<String> allTags, final String namePart) {
        return Collections2.filter(allTags, Predicates.containsPattern("^@?" + namePart));
    }
}
