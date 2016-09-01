package com.lanyine.manifold.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @Description: 生产验证码
 * @author shadow
 * @date 2016年3月3日 上午8:42:33
 * 
 */
public class VerificationCodeUtil {
	private static int width = 100;// 定义图片的width
	private static int height = 32;// 定义图片的height
	private static int codeCount = 5;// 定义图片上显示验证码的个数
	private static int unitX = 10;
	private static int unitY = 18;
	private static int fontHeight = 20;
	private static char[] codeSequence = "ABCDEFGHIJKLMNPQRSTUVWXYZ0123456789".toCharArray();
	private static Random random = new Random();

	public static Code authcode() throws IOException {
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics gd = buffImg.getGraphics();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生43条干扰线
		for (int i = 0; i < 19; i++) {
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			int[] rgb = rgbRandom();
			gd.setColor(new Color(rgb[0], rgb[1], rgb[2]));
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();

		// 随机产生codeCount数字的验证码。
		int unitPerX = 0, unitPerY = 0;
		for (int i = 0; i < codeCount; i++) {
			unitPerX += unitX + random.nextInt(10);
			unitPerY = unitY + random.nextInt(7);
			// 得到随机产生的验证码数字。
			String code = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(Color.BLACK);
			gd.drawString(code, unitPerX, unitPerY);

			// 将产生的四个随机数组合在一起。
			randomCode.append(code);
		}

		return new Code(buffImg, randomCode.toString());
	}

	private static int[] rgbRandom() {
		int r = 0, g = 0, b = 0;
		r = random.nextInt(255);
		g = random.nextInt(255);
		b = random.nextInt(255);
		return new int[] { r, g, b };
	}

	public static class Code {
		private BufferedImage buffImg;
		private String value;

		public Code(BufferedImage buffImg, String value) {
			super();
			this.buffImg = buffImg;
			this.value = value;
		}

		public BufferedImage getBuffImg() {
			return buffImg;
		}

		public void setBuffImg(BufferedImage buffImg) {
			this.buffImg = buffImg;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
