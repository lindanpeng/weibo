package com.alphaking.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alphaking.pojo.weibo.ParsedText;
/**
 * Html文本转换工具
 * @author lindanpeng
 *
 */
public class HtmlTextUtil {
	private static final String REGEX="@[^\\s]+";
	private static final Pattern pattern=Pattern.compile(REGEX);
	private static final String AtPeopleHtmlStart="<span class='atPeople'>";
	private static final String AtPeopleHtmlEnd="</span>";
  public static ParsedText parseText(String content){
	  int startOffset=0;
	  int endOffset=AtPeopleHtmlStart.length();
	  Matcher matcher=pattern.matcher(content);
		 List<String> people=new ArrayList<>(10);
		 StringBuffer buffer=new StringBuffer(content);
		 while(matcher.find()){
			 buffer.insert(matcher.start()+startOffset,AtPeopleHtmlStart);
			 buffer.insert(matcher.end()+endOffset, AtPeopleHtmlEnd);
			 people.add(matcher.group().substring(1));
			 startOffset+=AtPeopleHtmlEnd.length()+AtPeopleHtmlStart.length();
			 endOffset=startOffset+AtPeopleHtmlStart.length();
		 }	
	  String parsedContent=buffer.toString().replace("\n", "<br>");
	  ParsedText parsedText=new ParsedText();
	  parsedText.setParsedContent(parsedContent);
	  parsedText.setPeople(people);
	  return parsedText;
  }
}
