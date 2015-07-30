package interviews;

/**
 * <b>VersionNumber</b> supports only version strings in the format xxxx.xxxx
 * Exact one dot is guaranteed
 */
public class VersionNumber implements Comparable<VersionNumber> {
	private int first;
	private int second;
	
	VersionNumber(String version) {
		int pos = version.indexOf('.');
		first = Integer.parseInt(version.substring(0, pos));
		second = Integer.parseInt(version.substring(pos+1));
		//System.out.println("version " + version + " is actually: " + first + " . " + second);
	}


	@Override
	public int compareTo(VersionNumber v) {
		if(first != v.first) {
			return first > v.first ? 1 : -1;
		}
		if(second != v.second) {
			return second > v.second ? 1 : -1;
		}
		return 0;
	}
	
	public static int compareVersion(String version1, String version2) {
		VersionNumber versionNumber1 = new VersionNumber(version1);
		VersionNumber versionNumber2 = new VersionNumber(version2);
		return versionNumber1.compareTo(versionNumber2);
	}
	
	
	public static void testVersionNumber() {
		System.out.println("Result of comparing 123.45 to 123.7 is: " + 
							VersionNumber.compareVersion("123.45", "123.7"));
		
		System.out.println("Result of comparing 12.45 to 123.7 is: " + 
				VersionNumber.compareVersion("12.45", "123.7"));
		
		System.out.println("Result of comparing 123.45 to 123.45 is: " + 
				VersionNumber.compareVersion("123.45", "123.45"));
		
		System.out.println("Result of comparing 127.45 to 123.7 is: " + 
				VersionNumber.compareVersion("127.45", "123.7"));
	}
	
	public static void main(String[] args) {
		testVersionNumber();
	}
}
