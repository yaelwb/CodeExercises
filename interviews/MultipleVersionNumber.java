package interviews;

/**
 * <b>VersionNumber</b> supports multiple sections
 * 
 */
public class MultipleVersionNumber implements Comparable<MultipleVersionNumber> {
	private int[] sections;
	
	MultipleVersionNumber(String version) {
		String[] sectionStrs = version.split("\\.");
		sections = new int[sectionStrs.length];
		for(int i=0; i<sections.length; i++)
			sections[i] = Integer.parseInt(sectionStrs[i]);
	}


	@Override
	public int compareTo(MultipleVersionNumber v) {
		int count = sections.length > v.sections.length? v.sections.length : sections.length;
		for(int i=0; i<count; i++) {
			if(sections[i] > v.sections[i])
				return 1;
			if(sections[i] < v.sections[i])
				return -1;
		}
		
		if(sections.length > count)
			return 1;
		if(v.sections.length > count)
			return -1;
		
		return 0;
	}
	
	public static int compareVersion(String version1, String version2) {
		MultipleVersionNumber versionNumber1 = new MultipleVersionNumber(version1);
		MultipleVersionNumber versionNumber2 = new MultipleVersionNumber(version2);
		return versionNumber1.compareTo(versionNumber2);
	}
	
	
	public static void main(String[] args) {
		System.out.println("Result of comparing 123.45.5 to 123.7.90 is: " + 
				MultipleVersionNumber.compareVersion("123.45.5", "123.7.90"));
		
		System.out.println("Result of comparing 12.45.5 to 123.7.5 is: " + 
				MultipleVersionNumber.compareVersion("12.45.5", "123.7.5"));
		
		System.out.println("Result of comparing 123.45.1 to 123.45 is: " + 
				MultipleVersionNumber.compareVersion("123.45.1", "123.45"));
		
		System.out.println("Result of comparing 123.45.7 to 123.45.7 is: " + 
				MultipleVersionNumber.compareVersion("123.45.7", "123.45.7"));
		System.out.println("Result of comparing 123 to 123.45.7 is: " + 
				MultipleVersionNumber.compareVersion("123", "123.45.7"));
	}

}
