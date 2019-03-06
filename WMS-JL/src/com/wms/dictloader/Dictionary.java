package com.wms.dictloader;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.wms.manage.MenuManage;



public class Dictionary {
	public static SimpleDateFormat df_DTIME = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	public static SimpleDateFormat df_DAY = new SimpleDateFormat("YYYYMMdd");
	
	private static Random rand = new Random();
	private static Logger logger = Logger.getLogger(Dictionary.class);
	public static void init(){
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask_Dict(), 1000, 24*60*60*1000);//字典数据每24小时更新一次
		timer.schedule(new MyTimerTask_Table(), 1000, 24*60*60*1000);//表信息每24小时更新一次
		timer.schedule(new MyTimerTask_SD(), 2000, 2*60*60*1000);//下拉框数据每2小时更新一次
		timer.schedule(new MyTimerTask_NI(), 3000, 2*60*60*1000);//Name-ID转换每2小时零1秒更新一次
	}
	public static void flushDict_SD_NI() {
		Dictloader_SelectData.getDictInfo();//下拉框数据更新
		Dictloader_NameID.getDictInfo();//字典刷新
	}
	//初始化
	public static void getDictInfo() {
		//初始化菜单
		logger.info("菜单数据正在初始化......");
		if(!MenuManage.initMenu()) {
			logger.info("菜单数据初始化失败！！！");
		}else {
			logger.info("菜单数据初始化成功！！！");
		}
	}
	
	//获取随机数
	public static String getRandom(int n) {
		String str = "";
		for(int i = 0;i < n;i++) {
			str += rand.nextInt(10);//产生一个0~9之间的自然数
		}
		return str;
	}
	
	//MD5加密
	public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	public static Map<String,String> checkMD5(String md5str, String flaginfo) {
		String signinfo = MD5(md5str);
		Map<String,String> map = null;
		if(!flaginfo.equals(signinfo)) {
			map = new HashMap<String, String>();
			map.put("message", "您提交的信息被恶意篡改，请注意浏览器安全！");
		}
		return map;
	}
}
class MyTimerTask_Dict extends TimerTask{
	private static Logger log = Logger.getLogger(MyTimerTask_Dict.class);
	@Override
	public void run() {
		log.info("字典信息正在更新中.......");
		Dictionary.getDictInfo();
		log.info("字典信息更新完成！！！！！");
	}
}