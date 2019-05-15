package test;

import java.util.ArrayList;
import java.util.List;

public class test {

//	public void print() {
//		System.out.println("hello");
//	}
//	static {
//		test te=new test();
//		te.print();
//		System.exit(0);
//	}
//	
	public static void main(String[] args) {
		int aft=0;
		List<Integer> list =new ArrayList<>();
		for(int i=1;i<10;i++) {
			
			if(i>2) {
				int printNo=list.get(i-2)+list.get(i-3);
				System.out.println(printNo);
				list.add(printNo);
			}
			else {
				System.out.println(i);
				aft=i;
				list.add(aft);
			}
		}
	}
}
