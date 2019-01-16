package com.rjp.fastframework;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpanUtils {

//    private String target;
//    private String key;
//    private List<Object> spans;
//
//    public static class Builder{
//        private String target;
//        private String key;
//        private List<Object> spans = new ArrayList<>();
//
//        public Builder target(String target){
//            this.target = target;
//            return this;
//        }
//
//        public Builder key(String key){
//            this.key = key;
//            return this;
//        }
//
//        public Builder addSpan(Object span){
//            this.spans.add(span);
//            return this;
//        }
//
//        public SpanUtils build(){
//            SpanUtils spanUtils = new SpanUtils();
//            spanUtils.target = target;
//            spanUtils.key = key;
//            spanUtils.spans = spans;
//            return spanUtils;
//        }
//    }
//
//    public SpannableString getTextSpan(){
//        SpannableString spannableString = new SpannableString(target);
//        Matcher matcher = getMatcher(target, key);
//        while (matcher.find()){
////            for (Object span : spans) {
//                spannableString.setSpan(spans.get(0), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
////            }
//        }
//        return spannableString;
//    }

    public static SpannableString setTextColorSpan(SpannableString spannableString, String target, String key, int color){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static SpannableString setTextBackgroundColorSpan(SpannableString spannableString, String target, String key, int color){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new BackgroundColorSpan(color), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static SpannableString setTextSizeSpan(SpannableString spannableString, String target, String key, float size){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new RelativeSizeSpan(size), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static SpannableString setTextMiddleLineSpan(SpannableString spannableString, String target, String key){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new StrikethroughSpan(), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static SpannableString setTextUnderLineSpan(SpannableString spannableString, String target, String key){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new UnderlineSpan(), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 右上角标
     * @param target
     * @param key
     * @return
     */
    public static SpannableString setTextSuperscriptSpan(SpannableString spannableString, String target, String key){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new SuperscriptSpan(), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 右下角标
     * @param target
     * @param key
     * @return
     */
    public static SpannableString setTextSubscriptSpan(SpannableString spannableString, String target, String key){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new SubscriptSpan(), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 设置粗体
     * @param target
     * @param key
     * @return
     */
    public static SpannableString setTextBoldSpan(SpannableString spannableString, String target, String key){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 设置斜体
     * @param target
     * @param key
     * @return
     */
    public static SpannableString setTextItalicSpan(SpannableString spannableString, String target, String key){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new StyleSpan(Typeface.ITALIC), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static SpannableString setTextImageSpan(SpannableString spannableString, String target,  String key, Drawable drawable){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new ImageSpan(drawable), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static SpannableString setTextClickableSpan(SpannableString spannableString, String target,  String key, ClickableSpan clickableSpan){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(clickableSpan, matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static SpannableString setTextURLSpan(SpannableString spannableString, String target,  String key, String linkUrl){
        Matcher matcher = getMatcher(target, key);
        while (matcher.find()){
            spannableString.setSpan(new URLSpan(linkUrl), matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    @NonNull
    private static Matcher getMatcher(String target, String key) {
//        int length = key.length();
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            char charAt = key.charAt(i);
//            sb.append("(").append(charAt).append(")").append("|");
//        }
//        sb.deleteCharAt(sb.length() - 1);
//        String reg = sb.toString();
        Pattern pattern = Pattern.compile(key);
        return pattern.matcher(target);
    }
}
