package com.example.navigationdrawer;


public class testCOUNT {

	private radomID radom_id = null;
	private int maxID = 0;
	public int count = -1;
	public int now_id = 0;
	
	public testCOUNT(int maxID){
		this.maxID = maxID;
		radom_id = new radomID(maxID);
	}
	
	public int test_id(){
		if(count<maxID-1){
			count++;
		}
		else{
			count=0;		
		}
		now_id = radom_id.testID[count];
		return radom_id.testID[count];
	}	
}
