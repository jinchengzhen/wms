package common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.awt.Color; 
import java.awt.Font; 
import java.awt.FontMetrics; 
import java.awt.Graphics; 
import java.awt.image.BufferedImage; 
import java.io.IOException; 
import java.io.OutputStream; 
import org.jbarcode.JBarcode; 
import org.jbarcode.JBarcodeFactory; 
import org.jbarcode.encode.Code128Encoder; 
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.TextPainter; 
import org.jbarcode.util.ImageUtil; 
/**
 * 
* @ClassName: JbarCodeUtil
* @Description: TODO 条码生成
* @author Administrator
* @date 2019年4月1日
 */
public class JbarCodeUtil {
	//设置条形码高度 
	private static final int BARCODE_HEIGHT = 40;
	//设置条形码默认分辨率   
	private static final int BARCODE_DPI = ImageUtil.DEFAULT_DPI;
	//设置条形码字体样式    
	private static final String FONT_FAMILY = "console";
	//设置条形码字体大小    
	private static final int FONT_SIZE = 15;
	//设置条形码文本    
	public static String TEXT = "";
	//创建JBarcode   
	private static JBarcode jbc = null;
	public static JBarcode getJBarcode() throws InvalidAtributeException { 
		if (jbc == null) {
		//生成code128
		 jbc = JBarcodeFactory.getInstance().createCode128(); 
		 jbc.setEncoder(Code128Encoder.getInstance()); 
		 jbc.setTextPainter(CustomTextPainter.getInstance()); 
		 jbc.setBarHeight(BARCODE_HEIGHT); 
		 jbc.setXDimension(Double.valueOf(0.8).doubleValue()); 
		 jbc.setShowText(true); 
		}
		return jbc;
	} 
	/**
	 * @param message  条形码内容  
     * @param file   生成文件  
	* @Title: createBarcode
	* @Description: TODO
	* @return void    
	* @throws
	 */
	public static boolean createBarcode(String message, File file,String text) {
		try {
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			createBarcode(message, fos,text);
			fos.close();
			return true;
		} catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 
	* @Title: createBarcode
	* @Description: TODO
	* @param    
	* @return void    
	* @throws
	 */
	public static void createBarcode(String message, OutputStream os,String text) {
		try {
			TEXT=text;
			BufferedImage image = getJBarcode().createBarcode(message);
			ImageUtil.encodeAndWrite(image, ImageUtil.PNG, os, BARCODE_DPI, BARCODE_DPI);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 内部类 自定义打印参数
	* @ClassName: CustomTextPainter
	* @Description: TODO
	* @author Administrator
	* @date 2019年4月1日
	 */
	protected static class CustomTextPainter implements TextPainter {
		private static CustomTextPainter instance =new CustomTextPainter();
		public static CustomTextPainter getInstance(){ 
			return instance;
		}
		public void paintText(BufferedImage barCodeImage, String text, int width) {
			//绘图    
			Graphics g2d = barCodeImage.getGraphics();
			Font font = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE * width);
			g2d.setFont(font);
			FontMetrics fm = g2d.getFontMetrics();
			int height = fm.getHeight();
			int center = (barCodeImage.getWidth() - fm.stringWidth(text)) / 2;
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, barCodeImage.getWidth(), barCodeImage.getHeight() * 1 / 20);
			g2d.fillRect(0, barCodeImage.getHeight() - (height * 9 / 10), barCodeImage.getWidth(), (height * 9 / 10));
			g2d.setColor(Color.BLACK);
			g2d.drawString(TEXT, 0, 145);
			g2d.drawString(text, center, barCodeImage.getHeight() - (height / 10) - 2);
			
		}
	}
//	public static void main(String[] args) {
//		String message = "KJ4.1-0129-0001";
//		String path = System.getProperty("user.dir")+File.separator+"result.png";
//		JbarCodeUtil.createBarcode(message, new File(path),"jincz");
//	}
}
