package com.example.navigationdrawer;


public class radomID {
	
	public int maxID ;
	public  int[] testID; 
	
	public radomID(int maxID){
		this.maxID = maxID;
		testID = radom_id();
	}
	public int[] radom_id(){
		int[] radomID = new int[maxID];
		for(int i=0 ; i<maxID ; i++){
			radomID[i]=i+1;
		}
		for(int i=0 ; i<maxID ; i++) { 
            int j = (int) (Math.random() * maxID);
            int tmp = radomID[i]; 
            radomID[i] = radomID[j]; 
            radomID[j] = tmp; 
        } 
		return radomID;
	}
}
