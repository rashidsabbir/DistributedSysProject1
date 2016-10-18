package distributed;

import java.io.IOException;
import java.util.LinkedHashMap;

public class IOFunctions {
	
	public static void Create( LinkedHashMap<String,String> tokenMap, String tokenName) throws IOException {

		tokenMap.put(tokenName, "");
		
//		return token;
//		File file = new File(token);
//		file.createNewFile();
	}

	public static String Read( LinkedHashMap<String,String> tokenMap, String tokenName) throws IOException {
		
		return tokenMap.get(tokenName);
/*		BufferedReader reader = new BufferedReader(new FileReader(token));
		String input = reader.readLine();
		String result = input;
		while(input!=null)
		{
			input=reader.readLine();
			if(input!=null)
				result = result + "\n"+ input;
		}
		reader.close();
		return result;
		
*/		
	}
	
	public static void Delete( LinkedHashMap<String,String> tokenMap, String tokenName) throws IOException {
		
		tokenMap.remove(tokenName);
		//token = "";
//		Runtime.getRuntime().exec(new String[]{"bash","-c","rm " + token});
		
	}
	
	public static void Append( LinkedHashMap<String,String> tokenMap, String tokenName, String line)throws IOException {

		tokenMap.put(tokenName, tokenMap.get(tokenName) + line + "\n");
		
	}

}
