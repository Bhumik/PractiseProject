package com.bhumik.practiseproject.styling;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.bhumik.practiseproject.R;

/**
 * Created by bhumik on 23/5/16.
 */
public class SpannableDemo extends AppCompatActivity {

    String mSpanContentStr = getResources().getString(R.string.span_text);
    private static final int START_INDEX = 14;
    private static final int END_INDEX = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_spannable);

        showConent();
    }

    public void showConent() {
        ((TextView) findViewById(R.id.txt_style_fontSizeTxtId)).setText(SpannableUtils.setTextSize(mSpanContentStr, START_INDEX, END_INDEX, 32));
        ((TextView) findViewById(R.id.txt_style_boldTxtId)).setText(SpannableUtils.setTextBold(mSpanContentStr, START_INDEX, END_INDEX));
        ((TextView) findViewById(R.id.txt_style_boldTxtId)).setText(SpannableUtils.setTextBold(mSpanContentStr, START_INDEX, END_INDEX));
        ((TextView) findViewById(R.id.txt_style_italicTxtId)).setText(SpannableUtils.setTextItalic(mSpanContentStr, START_INDEX, END_INDEX));
        ((TextView) findViewById(R.id.txt_style_bolditalicTxtId)).setText(SpannableUtils.setTextBoldItalic(mSpanContentStr, START_INDEX, END_INDEX));
        ((TextView) findViewById(R.id.txt_style_urlTxtId)).setText(SpannableUtils.setTextURL(mSpanContentStr, 22, 23, "mailto:zmywly8866@gmail.com"));
        ((TextView) findViewById(R.id.txt_style_foregroundTxtId)).setText(SpannableUtils.setTextForeground(mSpanContentStr, START_INDEX, END_INDEX, Color.RED));
        ((TextView) findViewById(R.id.txt_style_backgroundTxtId)).setText(SpannableUtils.setTextBackground(mSpanContentStr, START_INDEX, END_INDEX, Color.GREEN));
        ((TextView) findViewById(R.id.txt_style_underlineTxtId)).setText(SpannableUtils.setTextUnderline(mSpanContentStr, START_INDEX, END_INDEX));
        ((TextView) findViewById(R.id.txt_style_strikeTxtId)).setText(SpannableUtils.setTextStrikethrough(mSpanContentStr, START_INDEX, END_INDEX));
        ((TextView) findViewById(R.id.txt_style_subTxtId)).setText(SpannableUtils.setTextSub(mSpanContentStr, START_INDEX, END_INDEX));
        ((TextView) findViewById(R.id.txt_style_superTxtId)).setText(SpannableUtils.setTextSuper(mSpanContentStr, START_INDEX, END_INDEX));

        Drawable drawable = getResources().getDrawable(R.drawable.wall);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ((TextView) findViewById(R.id.txt_style_imageTxtId)).setText(SpannableUtils.setTextImg(mSpanContentStr, START_INDEX, END_INDEX, drawable));

    }


    public static class SpannableUtils {
        private SpannableUtils() {

        }

        /**
         * setTextSize("",24,0,2) = null;
         * setTextSize(null,24,0,2) = null;
         * setTextSize("abc",-2,0,2) = null;
         * setTextSize("abc",24,0,4) = null;
         * setTextSize("abc",24,-2,2) = null;
         * setTextSize("abc",24,0,2) = normal string
         */
        public static SpannableString setTextSize(String content, int startIndex, int endIndex, int fontSize) {
            if (TextUtils.isEmpty(content) || fontSize <= 0 || startIndex >= endIndex || startIndex < 0 || endIndex >= content.length()) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new AbsoluteSizeSpan(fontSize), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextSub(String content, int startIndex, int endIndex) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new SubscriptSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextSuper(String content, int startIndex, int endIndex) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new SuperscriptSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextStrikethrough(String content, int startIndex, int endIndex) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new StrikethroughSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextUnderline(String content, int startIndex, int endIndex) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextBold(String content, int startIndex, int endIndex) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextItalic(String content, int startIndex, int endIndex) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextBoldItalic(String content, int startIndex, int endIndex) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextForeground(String content, int startIndex, int endIndex, int foregroundColor) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new ForegroundColorSpan(foregroundColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextBackground(String content, int startIndex, int endIndex, int backgroundColor) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new BackgroundColorSpan(backgroundColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        /**
         * ÉèÖÃÎÄ±¾µÄ³¬Á´½Ó
         *
         * @param content    ÐèÒª´¦ÀíµÄÎÄ±¾
         * @param startIndex
         * @param endIndex   ±»´¦ÀíÎÄ±¾ÖÐÐèÒª´¦Àí×Ö´®µÄ¿ªÊ¼ºÍ½áÊøË÷Òý
         * @param url        ÎÄ±¾¶ÔÓ¦µÄÁ´½ÓµØÖ·£¬ÐèÒª×¢Òâ¸ñÊ½£º
         *                   £¨1£©µç»°ÒÔ"tel:"´òÍ·£¬±ÈÈç"tel:02355692427"
         *                   £¨2£©ÓÊ¼þÒÔ"mailto:"´òÍ·£¬±ÈÈç"mailto:zmywly8866@gmail.com"
         *                   £¨3£©¶ÌÐÅÒÔ"sms:"´òÍ·£¬±ÈÈç"sms:02355692427"
         *                   £¨4£©²ÊÐÅÒÔ"mms:"´òÍ·£¬±ÈÈç"mms:02355692427"
         *                   £¨5£©µØÍ¼ÒÔ"geo:"´òÍ·£¬±ÈÈç"geo:68.426537,68.123456"
         *                   £¨6£©ÍøÂçÒÔ"http://"´òÍ·£¬±ÈÈç"http://www.google.com"
         */
        public static SpannableString setTextURL(String content, int startIndex, int endIndex, String url) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new URLSpan(url), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }

        public static SpannableString setTextImg(String content, int startIndex, int endIndex, Drawable drawable) {
            if (TextUtils.isEmpty(content) || startIndex < 0 || endIndex >= content.length() || startIndex >= endIndex) {
                return null;
            }

            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(new ImageSpan(drawable), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }
    }

}
