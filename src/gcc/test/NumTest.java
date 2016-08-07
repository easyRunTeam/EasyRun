package gcc.test;

import com.EasyMarathon.util.NumIdentify;

public class NumTest {
	public static void main(String[] args)
	{
		String fname = "G:\\EasyRunServer\\src\\gcc\\util\\123.JPG";
		NumIdentify ni = new NumIdentify();
		System.out.println("run dll");
		int ans = ni.GetID(fname);
		System.out.println("ans:"+ans);
	}
	public static int test()
	{
		String fname = NumIdentify.curpath + "134.jpg";
		System.out.println("pic path:"+fname);
		try
		{
			NumIdentify ni = new NumIdentify();
			System.out.println("run dll");
			int ans = ni.GetID(fname);
			System.out.println("ans:"+ans);
			return ans;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -999;
		}
	}
}
