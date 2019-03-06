package common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.wms.dictloader.Dictionary;

public class GenerateDataUtil {
	public static SimpleDateFormat df_datetime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	public static SimpleDateFormat df_date = new SimpleDateFormat("YYYY-MM-dd");
	private static Random rand = new Random();
	
	/**
	* @Title: getRandom
	* @Description: 获取指定位数的随机数字符串
	* @param    
	* @return String    
	* @throws
	 */
	public static String getRandom(int length,boolean letter) {
		String val = "";
		if(letter) {
			for (int i = 0; i < length; i++) {
				if(rand.nextBoolean()) {
					val +=  (char) (97 + rand.nextInt(26));
				}else {
					val += String.valueOf(rand.nextInt(10));
				}
			}
		}else {
			for (int i = 0; i < length; i++) {
				val += String.valueOf(rand.nextInt(10));
			}
		}
		return val;
	}
	private static String lastNameSingle = "赵钱孙李周吴郑王冯陈诸卫蒋沈韩杨朱秦尤许何吕施张" + 
			"孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎" + 
			"鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤" + 
			"滕殷罗毕郝邬安常乐于时傅皮卡齐康伍余元卜顾孟平黄" + 
			"和穆萧尹姚邵堪汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞" + 
			"熊纪舒屈项祝董粱杜阮蓝闵席季麻强贾路娄危江童颜郭" + 
			"梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫" + 
			"经房裘缪干解应宗丁宣贲邓郁单杭洪包诸左石崔吉钮龚" + 
			"程嵇邢滑裴陆荣翁荀羊於惠甄魏家封芮羿储靳汲邴糜松" + 
			"井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫" + 
			"宁仇栾暴甘钭厉戎祖武符刘景詹束龙叶幸司韶郜黎蓟薄" + 
			"印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双" + 
			"闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通" + 
			"边扈燕冀郏浦尚农温别庄晏柴翟阎充慕连茹习宦艾鱼容" + 
			"向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东" + 
			"殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空"+
			"曾毋沙乜养鞠须丰巢关蒯相查后荆红游竺权逯盖后桓公";
	private static String lastNameDouble = "万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊" + 
			"澹台公冶宗政濮阳淳于单于太叔申屠公孙仲孙轩辕令狐" + 
			"钟离宇文长孙慕容鲜于闾丘司徒司空亓官司寇仉督子车" + 
			"颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱" + 
			"晋楚闫法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生" + 
			"岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫" + 
			"墨哈谯笪年爱阳佟第五言福";
	private static String firstNmae = 
			"鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滑裴陆荣翁荀羊於惠甄魏家封芮羿不著撰人名氏录"+
			"古今谚语及方言标其原始凡经史小学诸书皆见援据其采自说部者并各注书名於其下虽释常言而考证颇近於"+
			"古然如释大夫称主流行在人群众中的定型语句故俗语云画地作狱议不可入刻木为吏期不可对此皆疾吏之风"+
			"悲痛之辞也说苑贵德故俗语云刻木为吏期不对此皆疾吏之风悲痛之辞浦尚农温别庄晏柴翟阎充慕连茹习刁"+
			"钟徐邱骆高夏蔡田樊胡祁毛禹狄米贝于珍珠岩具有因冷凝作用形成的圆弧形裂";
	/**
	 * 
	* @Title: getName
	* @Description: 随机获取姓名
	* @param    
	* @return String    
	* @throws
	 */
	public static String getName() {
		String str = "";
		if( rand.nextFloat() >= 0.3) {
			int n = rand.nextInt(lastNameSingle.length());
			str += lastNameSingle.charAt(n);
		}else {
			int n = rand.nextInt(lastNameDouble.length());
			if(n%2 != 0) {
				n--;
			}
			int m = n+2;
			str += lastNameDouble.substring(n, m);
		}
		int num = rand.nextInt(firstNmae.length());
		str += firstNmae.charAt(num);
		if(rand.nextBoolean()) {
			int num2 = rand.nextInt(firstNmae.length());
			str += firstNmae.charAt(num2);
			
		}
		return str;
	}
	//根据身份证生成帐号
	public static String toPersonCode(String IDcard) {
		return IDcard.substring(0, 2)+Dictionary.getRandom(4)+IDcard.substring(14, 18);
	}
	public static int toSex(String IDcard) {
		return Integer.parseInt(IDcard.substring(16, 17))%2 == 0 ? 2:1;
	}
	public static String toBirth(String IDcard) {
		String birthstr = IDcard.substring(6, 14);
		String birth = birthstr.substring(0, 4)+"-"+birthstr.substring(4, 6)+"-"+birthstr.substring(6, 8);
		return birth;
	}
	@SuppressWarnings("deprecation")
	public static int calculateAge(String birth) {
		try {
			return (new Date().getYear() - Dictionary.df_DTIME.parse(birth+" 00:00:00").getYear());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	//生成sku码
	public static String toSKU(Map<String,Object> map) {
		return null;
	}
}
