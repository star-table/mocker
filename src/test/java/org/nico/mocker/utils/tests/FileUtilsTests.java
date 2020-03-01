package org.nico.mocker.utils.tests;

import org.junit.Assert;
import org.junit.Test;
import org.nico.mocker.utils.FileUtils;

public class FileUtilsTests {

	@Test
	public void testParseFileSuffixTest() {
		String suffix = FileUtils.parseUrlAndFileSuffix("https://byte-animation.oss-cn-shanghai.aliyuncs.com/native/90081130-1-6.mp4?Expires=1557848533&OSSAccessKeyId=TMP.AgFyD583Q1pRnpUBup2Na-MOmGH08E6Ttujr168YoFp5sVOiKLNAKjVCeGpoADAtAhUAuBuC_8hMiiwNxVacpaxrn7VggEQCFBgVthxvVxZ8y-twI-VZmaSkq9xj&Signature=COFealOXhFgmNmJ%2B%2FyeUk5wYbVk%3D");
		Assert.assertEquals(suffix, "mp4");
	}
	
}
