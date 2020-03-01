package org.nico.mocker.random;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.nico.mocker.utils.HttpContextUtils;
import org.springframework.stereotype.Component;

@Component
public class SmartRandom implements AbstractRandom{
	
	private Random random = new Random();
	
	private DecimalFormat decimalFormat = new DecimalFormat("######0.0000");  
	
	private static List<String> strings = Arrays.asList(
			"孙悟空","孙悟饭","孙悟天","孙悟饭(爷爷)","牛魔王","琪琪","龟仙人","鲍姆","小林","人造人8号","16号", "17号","18号",
			"19号","20号","贝吉塔","巴达克","拉蒂兹","那巴","特兰克斯","布尔玛","布娜","乐平","普洱","乌龙","天津饭", "饺子","鹤仙人",
			"查巴王","短笛","神仙","内鲁","丹迪","波波先生","黑元帅","青副官","蓝将军"," 格罗博士","银大佐","白将军","紫罗兰曹长",
			"桃白白","兰奇","皮拉夫","索巴","常胜","铁军曹","那木","巴巴特里安","基朗","加林仙人","基纽","吉尔多","力高","吉斯","利库姆","巴特",
			" 弗利萨","萨博","多多利亚","丘夷","布欧","弥次郎兵卫","沙鲁","界王","界王神","老界王神","撒旦","比迪丽","佩佩","巴菲迪","达普拉","大长老",
			"水晶婆婆","阎罗王","吸血鬼","透明人","木乃伊","地狱使者","泡芙","人狼","阿拉蕾","老短笛","大鼓","铜钹","铜鼓","钢琴","武泰斗","欧波","波拉",
			"乌巴","村长","黄大佐","阿拉","千兵卫","山吹绿","贝贝","弗利萨王","卡路","穆利长老","巴布鲁斯","兰芳","兔子团");
	
	public static List<String> keys = Arrays.asList(
			"grace","eternity","tranquility","cherish","pearl","paradise","rainbow","passion",
			"angle","sweet","bless","golden","strawberry","kitty","impression","windy","shine",
			"china","paris","ocean","wedding","cookie");
	
	
	@Override
	public String randomString() {
		return strings.get(random.nextInt(strings.size()));
	}
	
	@Override
	public String randomKey() {
		return keys.get(random.nextInt(keys.size()));
	}

	@Override
	public int randomInteger() {
		return random.nextInt(1024 * 1024);
	}

	@Override
	public long randomLong() {
		return randomInteger();
	}

	@Override
	public double randomDouble() {
		return Double.valueOf(decimalFormat.format(random.nextDouble()));
	}

	@Override
	public Object randomDate() {
		Date date = new Date(1583079322000L + random.nextInt((int)(new Date().getTime() - 1583079322000l)));
		if("timestamp".equalsIgnoreCase(HttpContextUtils.getDateFormat())) {
			return date.getTime();
		}
		return new SimpleDateFormat(HttpContextUtils.getDateFormat()).format(date);
	}

	@Override
	public boolean randomBoolean() {
		return random.nextInt(2) == 1;
	}

	@Override
	public Object random() {
		int t = random.nextInt(6);
		switch (t) {
		case 0:
			return randomString();
		case 1:
			return randomInteger();
		case 2:
			return randomLong();
		case 3:
			return randomDouble();
		case 4:
			return randomDate();
		case 5:
			return randomBoolean();
		default:
			return randomString();
		}
	}
	
}
