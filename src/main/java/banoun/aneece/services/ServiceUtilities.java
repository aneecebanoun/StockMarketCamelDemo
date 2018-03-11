package banoun.aneece.services;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ServiceUtilities {
	
	public static final String UP = "\u25b2";
	public static final String DOWN = "\u25bc";
	public static final String SAME = "\u25c0\u25b6";
	public static final String OPEN_BRAKET = "\u3010";
	public static final String CLOSE_BRAKET = "\u3011";
	private ServiceUtilities(){}
	public static Boolean isNull(final Object...os){
		return Arrays.asList(os).stream().map(e->e==null).collect(Collectors.toList()).contains(true);
	}
}
