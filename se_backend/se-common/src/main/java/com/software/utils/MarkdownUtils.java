package com.software.utils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

public class MarkdownUtils {
    public static String removeMarkdownTags(String markdownText) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownText);
        TextContentRenderer renderer = TextContentRenderer.builder().build();
        return renderer.render(document);
    }
}

