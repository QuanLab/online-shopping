package com.quanpv.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;  
import java.util.Locale;  
import java.util.regex.Pattern;

/**
 * @author quanpv
 */
public class Slug {  
  
  private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

  /**
   * generate slug from title to improve Search Engine Optimization
   * @param input
   * @return
   */
  public static String makeSlug(String input) {
    String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
    String normalized = Normalizer.normalize(noWhitespace, Form.NFD);
    String slug = NON_LATIN.matcher(normalized).replaceAll("");
    return slug.toLowerCase(Locale.ENGLISH);
  }  
}  