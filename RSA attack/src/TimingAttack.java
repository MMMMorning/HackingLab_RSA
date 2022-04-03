import java.math.*;
import java.util.*;

import javax.lang.model.util.AbstractAnnotationValueVisitor14;


public class TimingAttack {
	 static rsa system = new rsa();
	 static Random rnd1 = new Random();
	 static Random rnd2 = new Random();
//     static BigInteger p = BigInteger.probablePrime(1028/2,rnd1); 
//     static BigInteger q = BigInteger.probablePrime(1028/2,rnd2);
     static BigInteger p = BigInteger.probablePrime(128/2,rnd1); 
     static BigInteger q = BigInteger.probablePrime(128/2,rnd2);
     static BigInteger n = p.multiply(q);
	 
	 static BigInteger[] keys = system.key_Gen(p, q);
	 static int[] binaryD = system.convertToBinary(keys[1]);


	
	public static void main(String args[])
    {

		 
		 System.out.println("================encryption system is initialized and above info is hindden from the third party ================= ");
			
		 System.out.println();
		 System.out.println("What the attacker know:");
		 System.out.println("known e is " + keys[0]);
		 
		 int dLength = keys[1].bitLength();
		 System.out.println("bit length of d is " + dLength);
		 
		 TimingAttack attack = new TimingAttack();
		 int attackingTimes = 50;
		 int attackingTimes_Mont = 50;
		 int expectVal = attack.binaryD[1];

		 //Kocher's attack
		 int success1 = 0;
		 for(int i = 0; i < attackingTimes; i++) {
		 int[] guessD = new int[] {1,0};		 
		 attack.KocherAttackV2(guessD, 1);
		 if(guessD[1] == expectVal) {
			 success1 ++;
		 }
		 }
		  
		 System.out.println("Accuracy of Kocher's attack is : " + success1*1.0/(attackingTimes*1.0));
		 
		 
		 int success2 = 0;
		 for(int i = 0; i < attackingTimes_Mont; i++) {
		 int[] guessD = new int[] {1};		 
		 int res = attack.attackOnMontgomery(guessD);
		 if(res == expectVal) {
			 success2 ++;
		 }
		 }
		  
		 System.out.println("Accuracy of improved Kocher's attack is : " + success2*1.0/(attackingTimes_Mont*1.0));
		 
    
	 
    }
	
	public static int[] generateGuessDArray(int startIndex, int endIndex) {
		int[] guessD = new int[endIndex+1];
		 Arrays.fill(guessD, 0);
		 for(int i = 0; i < startIndex; i++) {
			 guessD[i] = binaryD[i];
		 }
		 System.out.println("initialed guessed d is: ");
		 for(int val: guessD) {
			 System.out.print(val); 
		 }
		 System.out.println();
		 
		 return guessD;
	}
	
	public static BigInteger[] generateMsgs(int numberOfMsg) {
		BigInteger[] msgs = new BigInteger[numberOfMsg];
		
		for(int i = 0; i<numberOfMsg; i++) {
			BigInteger m = BigInteger.probablePrime(1024/2,new Random());
			msgs[i] = m;
		}
		return msgs;
	}
	
	
    //function to generate Y and Z
	public static BigInteger generateMessage(BigInteger maxLimit, BigInteger minLimit) {
		BigInteger difference = maxLimit.subtract(minLimit);
		 System.out.println("generating progress");
		
		Random rnd = new Random();
		int len = maxLimit.bitLength();
		BigInteger msg =  new BigInteger(len, rnd);
		if (msg.compareTo(minLimit) < 0) {
			msg = msg.add(minLimit);
		}
		if (msg.compareTo(difference) >= 0) {
			msg = msg.mod(difference).add(minLimit);
		}
		return msg;
	}
	
	public static double runTimeOfDecryp(BigInteger msg, BigInteger d, BigInteger n) {
		double totalTime = 0;
	    // for (BigInteger msg: msgs) {
	         double begin = System.nanoTime();
	         BigInteger decryptedText = system.decrypt(msg, d, n);
	         totalTime += (System.nanoTime() - begin);
	  //   }
	     
	    return totalTime;
	}
	
//================================attack when only repeated squaring algorithm is used ================

	//not used version:
//	public static void KocherAttack(int[] guessD, int guessIndex) {
//		int numberOfMsg = 100;
//		
//		//generate random C[] = [C0,C1,..... Cj]
//		//and calculate T(Cj)
//		BigInteger[] randomMsg = new BigInteger[numberOfMsg];
//		double[] decryptTime = new double[numberOfMsg];
//		for(int i = 0; i < numberOfMsg; i++) {
//			 randomMsg[i] = new BigInteger(n.bitLength() - 1, new Random());
//			 double begin = System.nanoTime();
//			 BigInteger decryptedText = system.decrypt(randomMsg[i], keys[1], n);
//			 decryptTime[i] = (System.nanoTime() - begin);			 
//		}
//		
//		//two guess of D
//		int[] guessD1 = new int[guessIndex+1];
//		int[] guessD0 = new int[guessIndex+1];
//		for (int i = 0; i < guessIndex; i++) {
//			guessD1[i] = guessD[i];
//			guessD0[i] = guessD[i];
//		}
//		guessD1[guessIndex] = 1;
//		guessD0[guessIndex] = 0;
//		System.out.println("guess 1 is: ");
//		for(int val: guessD1) {
//			System.out.print(val);
//		}
//		System.out.println("; ");
//		System.out.println("guess 0 is: ");
//		for(int val: guessD0) {
//			System.out.print(val);
//		}
//		System.out.println("; ");
//		
//		double[] emulatedTime1 = new double[numberOfMsg];
//		double[] emulatedTime0 = new double[numberOfMsg];
//		double[] difference1 = new double[numberOfMsg];
//		double[] difference0 = new double[numberOfMsg];
//		for(int i = 0; i < numberOfMsg; i++) {
//			 double begin1 = System.nanoTime();
//			 BigInteger emulatedText1 = system.repeatedSquaring(randomMsg[i], guessD1, n);
//			 emulatedTime1[i] = (System.nanoTime() - begin1);	
//			 difference1[i] = decryptTime[i] -  emulatedTime1[i];
//			 double begin0 = System.nanoTime();
//			 BigInteger emulatedText0 = system.repeatedSquaring(randomMsg[i], guessD0, n);
//			 emulatedTime0[i] = (System.nanoTime() - begin0);	
//			 difference0[i] = decryptTime[i] -  emulatedTime0[i];
//		}
//		System.out.println("T(Cj)    |    t1     |     t0");
//		for(int i = 0; i < numberOfMsg; i++) {
//			System.out.println(decryptTime[i]+"   |   "+ emulatedTime1[i]+"  |   "+ emulatedTime0[i]);
//		}
//		
//		double variance1 = findVariance(difference1) ;
//		double variance0 = findVariance(difference0) ;
//		System.out.println("variance1 is: "+variance1+"; variance0 is: " + variance0);
//		
//		if(variance1 < variance0) {
//			guessD[guessIndex] = 1;
//		} else if(variance1 > variance0) {
//			guessD[guessIndex] = 0;
//		}
//		
//	}
	
	public static void KocherAttackV2(int[] guessD, int guessIndex) {
		
		int numberOfMsg = 1000;//10000;
		
		//generate random C[] = [C0,C1,..... Cj]
		//and calculate T(Cj)
		BigInteger[] randomMsg = new BigInteger[numberOfMsg];
		double[] decryptTime = new double[numberOfMsg];
		for(int i = 0; i < numberOfMsg; i++) {
			 randomMsg[i] = new BigInteger(n.bitLength() - 1, new Random());
			 double begin = System.nanoTime();
			 BigInteger decryptedText = system.decrypt(randomMsg[i], keys[1], n);
			 decryptTime[i] = (System.nanoTime() - begin);			 
		}
		
		int[] knownD = new int[guessIndex];
		for (int i = 0; i < knownD.length; i++) {
			knownD[i] = guessD[i];
		}
		double[] emulatedTime1 = new double[numberOfMsg];
		double[] emulatedTime0 = new double[numberOfMsg];
		BigInteger[] decryptRecord = new BigInteger[numberOfMsg];
		for(int i = 0; i < numberOfMsg; i++) {
			double begin = System.nanoTime();
			decryptRecord[i] = system.repeatedSquaring(randomMsg[i], knownD, n);
			double time = (System.nanoTime() - begin);
			emulatedTime1[i] = time;
			emulatedTime0[i] = time;
		}

		
		double[] difference1 = new double[numberOfMsg];
		double[] difference0 = new double[numberOfMsg];
		//suppose next bit is 1:
		for(int i = 0; i < numberOfMsg; i++) {
			double begin = System.nanoTime();
			BigInteger num = decryptRecord[i].pow(2).mod(n);
			num = num.multiply(randomMsg[i]).mod(n);
			double time = (System.nanoTime() - begin);
			emulatedTime1[i] += time;
			difference1[i] = decryptTime[i] -  emulatedTime1[i];
		}
		
		//suppose next bit is 0;
		for(int i = 0; i < numberOfMsg; i++) {
			double begin = System.nanoTime();
			BigInteger num = decryptRecord[i].pow(2).mod(n);
			double time = (System.nanoTime() - begin);
			emulatedTime0[i] += time;
			difference0[i] = decryptTime[i] -  emulatedTime0[i];
		}


//		System.out.println("T(Cj)    |    t1     |     t0");
//		for(int i = 0; i < numberOfMsg; i++) {
//			System.out.println(decryptTime[i]+"   |   "+ emulatedTime1[i]+"  |   "+ emulatedTime0[i]);
//		}
		
		double variance1 = findVariance(difference1) ;
		double variance0 = findVariance(difference0) ;
//		System.out.println("variance1 is: "+variance1+"; variance0 is: " + variance0);
		
		 
		if(variance1 < variance0) {
			guessD[guessIndex] = 1;
		} else if(variance1 > variance0) {
			guessD[guessIndex] = 0;
		}
		
		
	}
		
    public static double findVariance(double[] nums) {
    	double sum = 0;
    	for(double num: nums) {
    		sum+=num;
    	}
    	double mean = sum/nums.length;
    	double variance = 0;
    	for (double num: nums) {
    		variance += Math.pow(num - mean, 2);
    	}
    	return variance/nums.length;
    }
    
//================================attack when Montgomery algorithm is used ================================

    public static int attackOnMontgomery(int[] knownD) {
    	
    	//initial r 
    	int n_length = n.bitLength();
    	BigInteger r = null;
    	for(int i = n_length-1; i>=0; i--) {	
    		if(n.testBit(i)) { //if bit i of n is 1
    			r = BigInteger.ZERO.setBit(i+1);
    			break;
    		}
    	}  	
    	
	    //generate random C[] = [C0,C1,..... Cj]
		int numberOfMsg = 1000;//10000;
		double[] accumulatedTime = new double[] {0.0, 0.0, 0.0, 0.0}; 
		
		for(int i = 0; i < numberOfMsg; i++) {
	        BigInteger randomMsg = new BigInteger(n.bitLength() - 1, new Random());
	        BigInteger tempMsg= system.Montgomery_ExpMod(randomMsg, knownD, n);
	        
	        //Call oracle to set group for tempMsg
	        int[] group = setGroup(tempMsg, r);
	        
	        double begin = System.nanoTime();
	        system.Montgomery_ExpMod(randomMsg, binaryD, n);
	        double time = (System.nanoTime() - begin);
	        
	        
	        for(int val: group) {
	        	accumulatedTime[val] += time;
	        }
		}
		
		double[] averageTime = new double[4];
		for(int i = 0; i< averageTime.length;i++ ) {
			averageTime[i] = accumulatedTime[i]/numberOfMsg;
		}
		
		int guessingBit = 0;
		if((averageTime[1]-averageTime[0])>(averageTime[3]-averageTime[2])) {
			guessingBit = 1;
		}
		
		return guessingBit;    	
    }
    
    public static int[] setGroup(BigInteger temp, BigInteger r) {
    	BigInteger r_inverse = r.modInverse(n);
    	BigInteger n_inverse = n.modInverse(r).multiply(BigInteger.valueOf(-1));
    	
    	BigInteger temp_r = temp.multiply(r).mod(n);
    	BigInteger res = system.Montgomery_multiply(temp_r, temp_r, r, n);
    	
    	//index 0: the situation when the next bit of d is 0
    	//index 1: the situation when the next bit of d is 1
    	int[] group = new int[] {0,0}; //initial the return set
    	
    	//If the next bit is 0:
    	//no extra multiplication
    	if(hasExtraReduction(res,res,r,n)) {
    		group[0] = 1;
    	}
    	
    	//If the next bit is 1:
    	//There is an extra multiplication of mes_r and res
    	BigInteger bitIsOne = system.Montgomery_multiply(res, temp_r, r, n);
    	if(hasExtraReduction(bitIsOne,bitIsOne,r,n)) {
    		group[1] = 3;
    	}else {
    		group[1] =2;
    	}
    	
    	return group;
    }


    public static boolean hasExtraReduction(BigInteger ar, BigInteger br, BigInteger r, BigInteger n) {
    	BigInteger x = ar.multiply(br);

    	BigInteger r_inverse = r.modInverse(n);
    	BigInteger n_inverse = n.modInverse(r).multiply(BigInteger.valueOf(-1));
    	BigInteger m = (x.mod(r)).multiply(n_inverse).mod(r);
    	
    	//res = (x+mn)/r
    	BigInteger res = (x.add(m.multiply(n))).shiftRight(((r.subtract(BigInteger.ONE)).bitCount()));
    	
    	return res.compareTo(n) >= 0; 
    	
    }
    
    
    
//=========================================accuracy calculation ==========================================
    
    public static double calculateAcc(int[] reference, int[] input, int startIndex, int endIndex) {
    	int success = 0;
    	for(int i = startIndex; i<= endIndex; i++) {
    		if(reference[i] == input[i]) {
    			//System.out.println("D["+i+"] = guessedD["+i+"]");
    			success = success +1;
    		}
    	}
    	Double accurancy = Double.valueOf(success)/Double.valueOf(endIndex-startIndex+1);
    	System.out.println("sum of success: " + success+", accurancy is: "+ accurancy);
    	
    	
    	
    	return accurancy;
    }

}
