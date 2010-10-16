package Test;
import java.util.ArrayList;
import java.util.List;
import wh.XMLParser.XMLParser;

public class TestXMLParse {
	public static void main(String[] args){
		List<String> keyIdList,valueList;
		keyIdList=new ArrayList<String>();
		valueList=new ArrayList<String>();
		XMLParser.ParseXML("main.xml", keyIdList, valueList);
		System.out.println(keyIdList.toString());
		System.out.println(valueList.toString());
	}
}