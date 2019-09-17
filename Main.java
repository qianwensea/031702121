import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static List<Map<String, String>> addressResolution(String address) {

		String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^镇]+镇|.+乡)?(?<road>[^路].+路|.+巷|.+街)?(?<numb>[\\d]+号|)(?<village>.*)";
		Matcher m = Pattern.compile(regex).matcher(address);
		String province = null, city = null, county = null, town = null, road = null, numb = null, village = null;
		List<Map<String, String>> table = new ArrayList<Map<String, String>>();
		Map<String, String> row = null;
		row = new LinkedHashMap<String, String>();
		while (m.find()) {
			
			province = m.group("province");
			row.put("province", province == null ? "" : province.trim());
			city = m.group("city");
			row.put("city", city == null ? "" : city.trim());
			county = m.group("county");
			row.put("county", county == null ? "" : county.trim());
			town = m.group("town");
			row.put("town", town == null ? "" : town.trim());
			road = m.group("road");
			row.put("road", road == null ? "" : road.trim());
			numb = m.group("numb");
			row.put("numb", numb == null ? "" : numb.trim());
			village = m.group("village");
			row.put("village", village == null ? "" : village.trim());
			
		}
		table.add(row);
		return table;
	}

	public static String phone_number(String num) {
		if (num == null || num.length() == 0) {
			return "";
		}
		Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
		Matcher matcher = pattern.matcher(num);
		StringBuffer bf = new StringBuffer(64);
		while (matcher.find()) {
			bf.append(matcher.group()).append(",");
		}
		int len = bf.length();
		if (len > 0) {
			bf.deleteCharAt(len - 1);
		}
		return bf.toString();
	}

	public static void main(String[] args) throws FileNotFoundException {

//		File inputFile = new File("in.txt");
		File inputFile = new File(args[0]);
		Scanner in = new Scanner(inputFile);
//		PrintWriter out = new PrintWriter("2.txt");
		PrintWriter out = new PrintWriter(args[1]);
		// Scanner in = new Scanner(System.in);
		String [] Address = new String [5000];
		for(int j=0;in.hasNext();j++)
		{
			//Address [i] = new String();
			Address [j] = in.nextLine();
		}
		int len=0;
		for (int i=0;Address[i]!=null;i++)
		{
			len++;
		}
		out.print("[\r\n");
		for(int i=0;i<len;i++) {
			String address = Address[i];
			
			String diff = address.substring(0, address.indexOf("!"));
			//System.out.println(diff);
			String address_1 = address.substring(address.indexOf("!") + 1);
			String name = address_1.substring(0, address_1.indexOf(","));
			String address_2 = address_1.substring(address_1.indexOf(",") + 1);
			String mobile = phone_number(address_2);
			String address_3 = address_2.replace(mobile, "");
			String address_4 = address_3.substring(0, address_3.indexOf("."));
			// System.out.println(address_2);
			out.print("{\"姓名\":" + "\"" + name + "\",");
			out.print("\"手机\":" + "\"" + mobile + "\",");
			out.print("\"地址\":[");
			List<Map<String, String>> table = addressResolution(address_4);
			// System.out.println(table);
			out.print("\"" + table.get(0).get("province") + "\",");
			out.print("\"" + table.get(0).get("city") + "\",");
			out.print("\"" + table.get(0).get("county") + "\",");
			out.print("\"" + table.get(0).get("town") + "\",");
			out.print("\"" + table.get(0).get("road") + "\",");
			out.print("\"" + table.get(0).get("numb") + "\",");
			out.print("\"" + table.get(0).get("village") + "\"");
			if(i!=len-1)
			out.print("]},\r\n");
			else
			out.print("]}\r\n");

		}
		out.print("]");
		in.close();
		out.close();
		}
}