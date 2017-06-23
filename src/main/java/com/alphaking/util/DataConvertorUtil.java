package com.alphaking.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DataConvertorUtil--数据格式转换的工具类
 */
public class DataConvertorUtil {

	// jackson包提供的json与javabean的转换类ObjectMapper
	private static ObjectMapper objectMapper;
	
	//日期格式化对象
	private static DateFormat sdf=null;
	//浮点数保留小数位2位
	private static DecimalFormat df = new DecimalFormat("#0.00");
	//浮点数只保留整数位
	private static DecimalFormat dff = new DecimalFormat("#0");
	
	// 初始化对象objectMapper,sdf
	static {
		if(objectMapper == null){
			objectMapper = new ObjectMapper();
		}
		
		if(sdf==null){
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	}

	
	public static Object unknowDataType2Object(String unknowDataType,String str){
		Object obj = null;
		switch (unknowDataType) {
		case "java.lang.Long":
			obj = Long.parseLong(str);
			break;
		case "java.lang.Integer":
			obj = Integer.parseInt(str);
			break;
		case "java.lang.Short":
			obj = Short.parseShort(str);
			break;
		case "java.lang.Boolean":
			obj = Boolean.parseBoolean(str);
			break;
		case "java.lang.Double":
			obj = Double.parseDouble(str);
			break;
		case "java.lang.Float":
			obj = Float.parseFloat(str);
			break;
		case "java.sql.Timestamp":
			obj = new Timestamp(Long.parseLong(str));
			break;
		case "java.sql.Date":
			obj = new java.sql.Date(Long.parseLong(str));
			break;
		default:
			obj = str;
			break;
		}
		return obj;
	}
	
	
	
	public static boolean isTimer(String unknowDataType){
		boolean isTimer;
		switch (unknowDataType) {
		case "java.sql.Timestamp":
			isTimer = true;
			break;
		case "java.sql.Date":
			isTimer = true;
			break;
		default:
			isTimer = false;
			break;
		}
		return isTimer;
	}
	
	/*
	 * 实现功能--将javabean对象转换为json对象的字符串
	 * 
	 * @param obj--要转换的javabean对象
	 * 
	 * @return String类型--json对象的字符串
	 */
	public static String object2json(Object obj) {

		// 变量json--返回用的json对象的字符串
		String json = null;

		try {
			json = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	/*
	 * 实现功能--将json对象的字符串转换为javabean对象
	 * 
	 * @param json--要转换的json对象的字符串
	 * 
	 * @param collectionClass--原始类的类对象
	 * 
	 * @param elementClasses--泛型类的类对象
	 * 
	 * @return T类型--期望的javabean对象(T类型)，调用后需要结合具体类型进行强制转换
	 */
	public static <T> T json2object(String json, Class<?> collectionClass,
			Class<?>... elementClasses) {

		// 变量object--返回用的javabean对象
		T t = null;
		JavaType javaType = getCollectionType(collectionClass, elementClasses);
		try {
			t = objectMapper.readValue(json, javaType);
		} catch (JsonParseException e) {
			System.out.println("1111111");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("22222222");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("33333333");
			e.printStackTrace();
		}

		return t;
	}

	/*
	 * 实现功能--将原始类的类对象与泛型类的类对象通过objectMapper绑定为jackson包提供的JavaType对象
	 * 
	 * @param collectionClass--原始类的类对象
	 * 
	 * @param elementClasses--泛型类的类对象
	 * 
	 * @return JavaType类型--绑定后的JavaType对象
	 */
	public static JavaType getCollectionType(Class<?> collectionClass,
			Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(
				collectionClass, elementClasses);
	}

//	public static Map xml2Map(String xml){
//		Map retMap = new HashMap();
//		try {
//			StringReader read = new StringReader(xml);
//			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML输入
//			InputSource source = new InputSource(read);
//			// 创建一个新的SAXBuilder
//			SAXBuilder sb = new SAXBuilder();
//			// 通过输入源构造一个Document
//			Document doc = (Document) sb.build(source);
//			Element root = doc.getRootElement();// 指向根节点
//			List<Element> es = root.getChildren();
//			if (es != null && es.size() != 0) {
//				for (Element element : es) {
//					retMap.put(element.getName(), element.getValue());
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return retMap;
//	}
	
	
	
	public static Field getDeclaredFieldByName(Class<?> clazz,String fieldName) throws NoSuchFieldException, SecurityException{
		return clazz.getDeclaredField(fieldName);
	}
	
	public static String getFieldDataTypeByName(Class<?> clazz,String fieldName) throws NoSuchFieldException, SecurityException{
		String fieldStr = getDeclaredFieldByName(clazz,fieldName).toString();
		String[] result = fieldStr.split(" ");
		return result[1];
	}
	
	
	
	
    /**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String,String> xmlStr2Map(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String,String> m = new HashMap<String,String>();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
  
  /**
	 * 对象序列化为数组
	 * @param object
	 * @return
	 */
  public static byte[] serialize(Object object) {
      ObjectOutputStream oos = null;
      ByteArrayOutputStream baos = null;
      try {
          // 序列化
          baos = new ByteArrayOutputStream();
          oos = new ObjectOutputStream(baos);
          oos.writeObject(object);
          byte[] bytes = baos.toByteArray();
          return bytes;
      } catch (Exception e) {
          throw new RuntimeException(e.getMessage(), e);
      }
  }

  /**
   * 数组反序列化为对象
   * @param bytes
   * @return
   */
  public static <T> T unserialize(byte[] bytes) {
      ByteArrayInputStream bais = null;
      try {
          // 反序列化
          bais = new ByteArrayInputStream(bytes);
          ObjectInputStream ois = new ObjectInputStream(bais);
          return (T)(ois.readObject());
      } catch (Exception e) {
          throw new RuntimeException(e.getMessage(), e);
      }
  }
  
  /**
	 * 字节数组转换为十六进制的字符串
	 * @param byteArray
	 * @return
	 */
  public static String byteArrayToHexStr(byte[] byteArray) {
      String strDigest = "";
      for (int i = 0; i < byteArray.length; i++) {
          strDigest += byteToHexStr(byteArray[i]);
      }
      return strDigest;
  }

  /**
   * 字节转换为十六进制的字符串
   * @param mByte
   * @return
   */
  public static String byteToHexStr(byte mByte) {
      char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
              'B', 'C', 'D', 'E', 'F' };
      char[] tempArr = new char[2];
      tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
      tempArr[1] = Digit[mByte & 0X0F];

      String s = new String(tempArr);
      return s;
  }
  
	public static Timestamp string2Timestamp(String str){
		return Timestamp.valueOf(str);
	}
	
	
	public static String timestamp2String(Timestamp timestamp){
		return sdf.format(timestamp);
	}
	
	public static Date string2Date(String str){
		Date date = new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}finally{
			return date;
		}
	}
	
	public static String date2String(Date date){
		return sdf.format(date);
	}
	
	public static String timestamp2DateStr(Timestamp timestamp) {
		if(timestamp==null){
			return "";
		}
		String dateStr = sdf.format(timestamp);
		return dateStr;

	}
	/**
	 * 判断字符串是否为阿拉伯数字
	 * @param str
	 */
	public static boolean judegeStrIsNum(String str){
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
	/**
	 * 浮点数保留2位输出
	 */
	public static String doubleToStringKeepTwo(Double number){
		return df.format(number);
	}
	/**
	 * 浮点数保留整数位输出
	 */
	public static String doubleToStringKeepNo(Double number){
		return dff.format(number);
	}

}
