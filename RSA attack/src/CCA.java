import java.math.*;
import java.util.*;


//Chosen ciphertext attack
public class CCA {
	

	 static rsa system = new rsa();
	 static Random rnd1 = new Random();
	 static Random rnd2 = new Random();
     static BigInteger p = BigInteger.probablePrime(1028/2,rnd1); 
     static BigInteger q = BigInteger.probablePrime(256/2,rnd2);
     static BigInteger n = p.multiply(q);
	 
	 static BigInteger[] keys = system.key_Gen(p, q);

	  
	 private static BigInteger inputMsg = new BigInteger("774454611464646");
	 static BigInteger c = rsa.encrypt(inputMsg, keys[0], n);
	 static Map<BigInteger, BigInteger> storeDecryptedMessage = new HashMap<>();
	 
	
	

	public static void main(String args[])
    {
		
		 System.out.println("================encryption system is initialized and above info is hindden from the third party ================= ");
		
		 System.out.println();
		 System.out.println("What the attacker know:");
		 System.out.println("known e is " + keys[0]);
	     System.out.println("known Ciphertext = " + c);
	     
	     //System.out.println(generateFakeM(c, BigInteger.valueOf(2),BigInteger.valueOf(16), BigInteger.valueOf(101)));
	     
	     //generate the fake ciphertext m* for decryption: c* = c*2^e mod n 
	     BigInteger fakeC = generateFakeM(c, BigInteger.valueOf(2),keys[0], n);
	     System.out.println("faked Ciphertext c* = " + fakeC);
	     
	     //after input c* into decryption function, returned m* = (c*2^e)^d mod n = c^d*2^ed mod n = 2m mod n
	     BigInteger decryptedM = CCAdecrypt(system, fakeC, keys[1], n);
	     System.out.println("decrypted m* = " + decryptedM);
	     
	     BigInteger m = decryptedM.divide(BigInteger.TWO).mod(n);
	     System.out.println("derived m = " + m);
	     
	     if(m.equals(inputMsg)){
	    	 System.out.println("break successfully");
	     }else {
	    	 System.out.println("derived message is not the original message");
	     }
	     
    }
	
	public static BigInteger CCAdecrypt(rsa system, BigInteger inputC, BigInteger d, BigInteger n) { 
		
		if(!inputC.equals(c)) {
			System.out.println("This is not original ciphertext, will be decrypted by the system");
    	    return system.decrypt(inputC, d, n);}
		
		BigInteger fakeM = null;
		if(storeDecryptedMessage.containsKey(inputC)) {
			fakeM = storeDecryptedMessage.get(inputC);
		}else {
	    Random rnd = new Random();
	    fakeM = new BigInteger(1024/2,rnd);  
	    storeDecryptedMessage.put(inputC, fakeM);
		}
		
		return fakeM;
		
	}
	
	
	//repeated squaring algorithm 
    public static BigInteger generateFakeM(BigInteger knownC, BigInteger base, BigInteger e, BigInteger n) { 
    	//First, the exponent number will be convert to the corresponding binary number
    	int[] exp_Bin = convertToBinary(e); 
    	
    	//for printing binary number
//    	for(int value: exp_Bin) {
//    	System.out.print(value);
//    	}
//    	System.out.println();
    	
    	BigInteger result = base;
    	int pow = 1;
    	for(int i =  1; i < exp_Bin.length; i++) {
    		result = result.pow(2).mod(n);
    		pow = pow*2;
    		if(exp_Bin[i]==1) {
    			result = result.multiply(base).mod(n);
    			pow = pow+1;
    		}
    		//System.out.println(base+"^"+pow +"mod" + n +" = "+ result);
    	}
    	
    	//c* = c*2^e
    	result = result.multiply(knownC.mod(n)).mod(n);

    	 
    	return result;
    }
    
    public static int[] convertToBinary(BigInteger value) { //function to convert integer into a binary number, will be used in function Mont_multi().
    	Stack<Integer> binary = new Stack<>();
    	
    	while(value.compareTo(BigInteger.ZERO) == 1) { //the value is greater than 0
    		binary.push(value.mod(BigInteger.TWO).intValue());
    		value = value.divide(BigInteger.TWO);
    	}
    	
    	int[] output = new int[binary.size()];
    	for(int i=0; i< output.length; i++) {
    		output[i] = binary.pop();
    	}
    	
    	return output;
    }
		
	
	
}
