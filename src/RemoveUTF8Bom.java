import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RemoveUTF8Bom {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
//		list("D:\\eclipseworkspace\\testworkspace\\onchain-java\\src", list);
//		list("D:\\eclipseworkspace\\testworkspace\\onchain-java\\src", list);
		list("D:\\Onchain4D\\GitHub\\onchain-java\\src\\OnChain", list);
		System.out.println("list.size:"+list.size());
		list.forEach(p -> 
			{
//				if(p.endsWith("bak") || p.endsWith("bak2")){
//					new File(p).delete();
//				}
//			System.out.println(p+",3byte:"+transfer2uft8NoBom(p));
			});
	}
	
	private static void list(String path, List<String> list) {
		for(File file: new File(path).listFiles(new FileFilter(){
			@Override
			public boolean accept(File pathname) {
//				return pathname.getName().endsWith("-bak") ? !pathname.delete():true;
//				return pathname.getName().endsWith(".java") ? !pathname.renameTo(new File(pathname.getAbsolutePath()+"-src")):true;
				return pathname.getName().endsWith(".java-bak") ? !pathname.renameTo(new File(pathname.getAbsolutePath().substring(0, pathname.getAbsolutePath().length()-4))):true;
			}
			
		})) {
			if(!file.exists()) {
				continue;
			}
			if(file.isDirectory()) {
				list(file.getAbsolutePath(), list);
			}
			else if (file.isFile()) {
				list.add(file.getAbsolutePath());
			}
		}
	}
	
	private static String transfer2uft8NoBom(String path) {
		try {
			OutputStream output = new FileOutputStream(new File(path+"-bak").createNewFile() ? path+"-bak":"");
			
			InputStream input = new FileInputStream(path);
			byte[] bt = new byte[1024];
			int len = -1; boolean flag = true;StringBuilder sb = new StringBuilder();
			while((len = input.read(bt, 0, bt.length)) != -1) {
				if(flag) {
					sb.append(bt.length == 0);
					sb.append(",len="+len);
//					sb.append(Integer.toHexString(bt[0])+",");
//					sb.append(Integer.toHexString(bt[1])+",");
//					sb.append(Integer.toHexString(bt[2])+",");
//					sb.append(Integer.toHexString(bt[3])+",");
//					sb.append(Integer.toHexString(bt[4])+",");
//					sb.append(Integer.toHexString(bt[5])+",");
//					sb.append(Integer.toHexString(bt[6])+",");
//					sb.append(Integer.toHexString(bt[7])+",");
//					sb.append(Integer.toHexString(bt[8])+",");
//					sb.append(Integer.toHexString(bt[9])+",");
					output.write(new String(bt, 3, len-3, "utf-8").getBytes(), 0, len-3);	
					flag=false;continue;
				};
				output.write(new String(bt, "utf-8").getBytes(), 0, len);
			}
			output.close();
			input.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "null";
	}
}
