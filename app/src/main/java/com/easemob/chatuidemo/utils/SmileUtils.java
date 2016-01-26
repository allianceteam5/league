/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;

import com.mine.league.R;

public class SmileUtils {
	public static final String chat_1 = "/::)";
	public static final String chat_2 = "/::~";
	public static final String chat_3 = "/::B";
	public static final String chat_4 = "/::|";
	public static final String chat_5 = "/:8-)";
	public static final String chat_6 = "/::<";
	public static final String chat_7 = "/::$";
	public static final String chat_8 = "/::X";
	public static final String chat_9 = "/::Z";
	public static final String chat_10 = "/::'(";
	public static final String chat_11 = "/::-|";
	public static final String chat_12 = "/::@";
	public static final String chat_13 = "/::P";
	public static final String chat_14 = "/::D";
	public static final String chat_15 = "/::O";
	public static final String chat_16 = "/::(";
	public static final String chat_17 = "/::+";
	public static final String chat_18 = "/:--b";
	public static final String chat_19 = "/::Q";
	public static final String chat_20 = "/::T";
	public static final String chat_21 = "/:,@P";
	public static final String chat_22 = "/:,@-D";
	public static final String chat_23 = "/::d";
	public static final String chat_24 = "/:,@o";
	public static final String chat_25 = "/::g";
	public static final String chat_26 = "/:|-)";
	public static final String chat_27 = "/::!";
	public static final String chat_28 = "/::L";
	public static final String chat_29 = "/::>";
	public static final String chat_30 = "/::,@";
	public static final String chat_31 = "/:,@f";
	public static final String chat_32 = "/::-S";
	public static final String chat_33 = "/:?";
	public static final String chat_34 = "/:,@x";
	public static final String chat_35 = "/:,@@";
	public static final String chat_36 = "/::8";
	public static final String chat_37 = "/:,@!";
	public static final String chat_38 = "/:!!!";
	public static final String chat_39 = "/:xx";
	public static final String chat_40 = "/:bye";
	public static final String chat_41 = "/:wipe";
	public static final String chat_42 = "/:dig";
	public static final String chat_43 = "/:handclap";
	public static final String chat_44 = "/:&-(";
	public static final String chat_45 = "/:B-)";
	public static final String chat_46 = "/:<@";
	public static final String chat_47 = "/:@>";
	public static final String chat_48 = "/::-O";
	public static final String chat_49 = "/:>-|";
	public static final String chat_50 = "/:P-(";
	public static final String chat_51 = "/::'|";
	public static final String chat_52 = "/:X-)";
	public static final String chat_53 = "/::*";
	public static final String chat_54 = "/:@x";
	public static final String chat_55 = "/:8*";
	public static final String chat_56 = "/:pd";
	public static final String chat_57 = "/:<W>";
	public static final String chat_58 = "/:beer";
	public static final String chat_59 = "/:basketb";
	public static final String chat_60 = "/:oo";
	public static final String chat_61 = "/:coffee";
	public static final String chat_62 = "/:eat";
	public static final String chat_63 = "/:pig";
	public static final String chat_64 = "/:rose";
	public static final String chat_65 = "/:fade";
	public static final String chat_66 = "/:showlove";
	public static final String chat_67 = "/:heart";
	public static final String chat_68 = "/:break";
	public static final String chat_69 = "/:cake";
	public static final String chat_70 = "/:li";
	public static final String chat_71 = "/:bome";
	public static final String chat_72 = "/:kn";
	public static final String chat_73 = "/:footb";
	public static final String chat_74 = "/:ladybug";
	public static final String chat_75 = "/:shit";
	public static final String chat_76 = "/:moon";
	public static final String chat_77 = "/:sun";
	public static final String chat_78 = "/:gift";
	public static final String chat_79 = "/:hug";
	public static final String chat_80 = "/:strong";
	public static final String chat_81 = "/:weak";
	public static final String chat_82 = "/:share";
	public static final String chat_83 = "/:v";
	public static final String chat_84 = "/:@)";
	public static final String chat_85 = "/:jj";
	public static final String chat_86 = "/:@@";
	public static final String chat_87 = "/:bad";
	public static final String chat_88 = "/:lvu";
	public static final String chat_89 = "/:no";
	public static final String chat_90 = "/:ok";
//	public static final String chat_91 = "/:,@f";
//	public static final String chat_92 = "/::-S";
//	public static final String chat_93 = "/:?";
//	public static final String chat_94 = "/:,@x";
//	public static final String chat_95 = "/:,@@";
//	public static final String chat_96 = "/:,@@";
//	public static final String chat_97 = "/:,@@";
//	public static final String chat_98 = "/:,@@";
//	public static final String chat_99 = "/:,@@";
//	public static final String chat_100 = "/::T";
//	public static final String chat_101 = "/:,@P";
//	public static final String chat_102 = "/:,@-D";
//	public static final String chat_103 = "/::d";
//	public static final String chat_104 = "/:,@o";
//	public static final String chat_105 = "/::g";

	private static final Factory spannableFactory = Factory
	        .getInstance();
	
	private static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();

	static {
		
//	    addPattern(emoticons, chat_1, R.drawable.chat_1);
		addPattern(emoticons, chat_1, R.drawable.chat_1);
	    addPattern(emoticons, chat_2, R.drawable.chat_2);
	    addPattern(emoticons, chat_3, R.drawable.chat_3);
	    addPattern(emoticons, chat_4, R.drawable.chat_4);
	    addPattern(emoticons, chat_5, R.drawable.chat_5);
	    addPattern(emoticons, chat_6, R.drawable.chat_6);
	    addPattern(emoticons, chat_7, R.drawable.chat_7);
	    addPattern(emoticons, chat_8, R.drawable.chat_8);
	    addPattern(emoticons, chat_9, R.drawable.chat_9);
	    addPattern(emoticons, chat_10, R.drawable.chat_10);
	    addPattern(emoticons, chat_11, R.drawable.chat_11);
	    addPattern(emoticons, chat_12, R.drawable.chat_12);
	    addPattern(emoticons, chat_13, R.drawable.chat_13);
	    addPattern(emoticons, chat_14, R.drawable.chat_14);
	    addPattern(emoticons, chat_15, R.drawable.chat_15);
	    addPattern(emoticons, chat_16, R.drawable.chat_16);
	    addPattern(emoticons, chat_17, R.drawable.chat_17);
	    addPattern(emoticons, chat_18, R.drawable.chat_18);
	    addPattern(emoticons, chat_19, R.drawable.chat_19);
	    addPattern(emoticons, chat_20, R.drawable.chat_20);
	    addPattern(emoticons, chat_21, R.drawable.chat_21);
	    addPattern(emoticons, chat_22, R.drawable.chat_22);
	    addPattern(emoticons, chat_23, R.drawable.chat_23);
	    addPattern(emoticons, chat_24, R.drawable.chat_24);
	    addPattern(emoticons, chat_25, R.drawable.chat_25);
	    addPattern(emoticons, chat_26, R.drawable.chat_26);
	    addPattern(emoticons, chat_27, R.drawable.chat_27);
	    addPattern(emoticons, chat_28, R.drawable.chat_28);
	    addPattern(emoticons, chat_29, R.drawable.chat_29);
	    addPattern(emoticons, chat_30, R.drawable.chat_30);
	    addPattern(emoticons, chat_31, R.drawable.chat_31);
	    addPattern(emoticons, chat_32, R.drawable.chat_32);
	    addPattern(emoticons, chat_33, R.drawable.chat_33);
	    addPattern(emoticons, chat_34, R.drawable.chat_34);
	    addPattern(emoticons, chat_35, R.drawable.chat_35);
		addPattern(emoticons, chat_36, R.drawable.chat_36);
		addPattern(emoticons, chat_37, R.drawable.chat_37);
		addPattern(emoticons, chat_38, R.drawable.chat_38);
		addPattern(emoticons, chat_39, R.drawable.chat_39);
		addPattern(emoticons, chat_40, R.drawable.chat_40);
		addPattern(emoticons, chat_41, R.drawable.chat_41);
		addPattern(emoticons, chat_42, R.drawable.chat_42);
		addPattern(emoticons, chat_43, R.drawable.chat_43);
		addPattern(emoticons, chat_44, R.drawable.chat_44);
		addPattern(emoticons, chat_45, R.drawable.chat_45);
		addPattern(emoticons, chat_46, R.drawable.chat_46);
		addPattern(emoticons, chat_47, R.drawable.chat_47);
		addPattern(emoticons, chat_48, R.drawable.chat_48);
		addPattern(emoticons, chat_49, R.drawable.chat_49);
		addPattern(emoticons, chat_50, R.drawable.chat_50);
		addPattern(emoticons, chat_51, R.drawable.chat_51);
		addPattern(emoticons, chat_52, R.drawable.chat_52);
		addPattern(emoticons, chat_53, R.drawable.chat_53);
		addPattern(emoticons, chat_54, R.drawable.chat_54);
		addPattern(emoticons, chat_55, R.drawable.chat_55);
		addPattern(emoticons, chat_56, R.drawable.chat_56);
		addPattern(emoticons, chat_57, R.drawable.chat_57);
		addPattern(emoticons, chat_58, R.drawable.chat_58);
		addPattern(emoticons, chat_59, R.drawable.chat_59);
		addPattern(emoticons, chat_60, R.drawable.chat_60);
		addPattern(emoticons, chat_71, R.drawable.chat_71);
		addPattern(emoticons, chat_72, R.drawable.chat_72);
		addPattern(emoticons, chat_73, R.drawable.chat_73);
		addPattern(emoticons, chat_74, R.drawable.chat_74);
		addPattern(emoticons, chat_75, R.drawable.chat_75);
		addPattern(emoticons, chat_76, R.drawable.chat_76);
		addPattern(emoticons, chat_77, R.drawable.chat_77);
		addPattern(emoticons, chat_78, R.drawable.chat_78);
		addPattern(emoticons, chat_79, R.drawable.chat_79);
		addPattern(emoticons, chat_80, R.drawable.chat_80);
		addPattern(emoticons, chat_81, R.drawable.chat_81);
		addPattern(emoticons, chat_82, R.drawable.chat_82);
		addPattern(emoticons, chat_83, R.drawable.chat_83);
		addPattern(emoticons, chat_84, R.drawable.chat_84);
		addPattern(emoticons, chat_85, R.drawable.chat_85);
		addPattern(emoticons, chat_86, R.drawable.chat_86);
		addPattern(emoticons, chat_87, R.drawable.chat_87);
		addPattern(emoticons, chat_88, R.drawable.chat_88);
		addPattern(emoticons, chat_89, R.drawable.chat_89);
		addPattern(emoticons, chat_90, R.drawable.chat_90);
	}

	private static void addPattern(Map<Pattern, Integer> map, String smile,
	        int resource) {
	    map.put(Pattern.compile(Pattern.quote(smile)), resource);
	}

	/**
	 * replace existing spannable with smiles
	 * @param context
	 * @param spannable
	 * @return
	 */
	public static boolean addSmiles(Context context, Spannable spannable) {
	    boolean hasChanges = false;
	    for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
	        Matcher matcher = entry.getKey().matcher(spannable);
	        while (matcher.find()) {
	            boolean set = true;
	            for (ImageSpan span : spannable.getSpans(matcher.start(),
	                    matcher.end(), ImageSpan.class))
	                if (spannable.getSpanStart(span) >= matcher.start()
	                        && spannable.getSpanEnd(span) <= matcher.end())
	                    spannable.removeSpan(span);
	                else {
	                    set = false;
	                    break;
	                }
	            if (set) {
	                hasChanges = true;
	                spannable.setSpan(new ImageSpan(context, entry.getValue()),
	                        matcher.start(), matcher.end(),
	                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	            }
	        }
	    }
	    return hasChanges;
	}

	public static Spannable getSmiledText(Context context, CharSequence text) {
	    Spannable spannable = spannableFactory.newSpannable(text);
	    addSmiles(context, spannable);
	    return spannable;
	}
	
	public static boolean containsKey(String key){
		boolean b = false;
		for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
	        Matcher matcher = entry.getKey().matcher(key);
	        if (matcher.find()) {
	        	b = true;
	        	break;
	        }
		}
		
		return b;
	}
	
	
	
}
