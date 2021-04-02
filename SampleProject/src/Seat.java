import java.util.HashMap;
import java.util.Map;


public class Seat {
	public static String change(String str){
		str += "456";
		return str;
	}

	public static void main(String[] args) {
		
		Object[][] seat = new Object[10][10];
		int sum = 1;
		char A = 65;
		/*for(int i = 0; i < seat.length; i++){
			for(int j = 0; j < seat.length; j++){
				seat[i][j] = "'A'"+sum;
				sum++;
			}
			A++;
			System.out.println(Arrays.toString(seat[i]));
		}
		
		for(int i = 0; i < seat.length; i++){
			for(int j = 0; j < seat.length; j++){
				if(seat[i][j] == "A1"){
					
				}
			}
			System.out.println(Arrays.toString(seat[i]));
		}
		*/
		for(int i =0 ; i <seat.length; i++){
			for(int j = 0; j < seat[0].length;j++){
				
				seat[i][j] = "□";
				seat[0][0] = "A";
				seat[0][0] = "B";
				seat[0][0] = "C";
			}
			System.out.print("□" + " ");
			System.out.println();
		}
		String str = "ABC123";
		System.out.println(str);
		String str1 = change(str);
		System.out.println(str1);
	}

}
