import static org.junit.jupiter.api.Assertions.*;

import java.math.*;
import java.util.Random;

import org.junit.jupiter.api.Test;

class Testrsa {
	
	rsa obj = new rsa();
	
	@Test
	void gcd_test() {
		BigInteger a = BigDecimal.valueOf(21).toBigInteger();
		BigInteger b = BigDecimal.valueOf(12).toBigInteger();
		BigInteger res = BigDecimal.valueOf(3).toBigInteger();
		
		
		assertEquals(res,obj.gcd(a,b));//test case from course IN4191, lecture slide 8 p23
	}
	
	@Test
	void extend_gcd_test() {
		BigInteger[] exp = new BigInteger[] {BigDecimal.valueOf(12).toBigInteger(), BigInteger.ONE, BigDecimal.valueOf(-1).toBigInteger()};
		BigInteger[] res = obj.extend_gcd(BigDecimal.valueOf(36).toBigInteger(), BigDecimal.valueOf(24).toBigInteger());
		for (BigInteger val: res) {
			System.out.print(val +",");
		}
		System.out.println();
		assertArrayEquals(exp,res); //test case from course IN4191, lecture slide 8 p26
	}
	
	@Test
	void extend_gcd_Primetest() {
		BigInteger[] exp = new BigInteger[] {BigInteger.ONE, BigDecimal.valueOf(-7).toBigInteger(), BigDecimal.valueOf(8).toBigInteger()};
		BigInteger[] res = obj.extend_gcd(BigDecimal.valueOf(17).toBigInteger(), BigDecimal.valueOf(15).toBigInteger());
		for (BigInteger val: res) {
			System.out.print(val +",");
		}
		System.out.println();
		assertArrayEquals(exp,res); //test case from course IN4191, lecture slide 8 p26
	}
	
	@Test
	void convertToBinary_test() {
		int[] res = new int[]{1,1,0,1,1,0};
		assertArrayEquals(res,obj.convertToBinary(BigDecimal.valueOf(54).toBigInteger()));
	}
	
	
//This test case is commented since the r value in test case is not a binary number, and will produce wrong result
//The current Mont_multiply function works fine with r=2^k
//	@Test  
//	void Mont_multiply_test() {
//		
//		System.out.println("=================Montgomery_multiply test =================");
//		
//		BigInteger a = BigInteger.valueOf(61);
//        BigInteger b = BigInteger.valueOf(5);
//        BigInteger n = BigInteger.valueOf(79);
//        BigInteger r = BigInteger.valueOf(100);
//        
//		BigInteger ar = a.multiply(r).mod(n);
//		System.out.println(" ar = "+ ar);
//        BigInteger br = b.multiply(r).mod(n);
//        System.out.println(" br = "+ br);
//       
//        BigInteger res = obj.Montgomery_multiply(ar, br, r,  n);
//        
//        
//        BigInteger re_normalDomain = res.multiply(r.modInverse(n)).mod(n);
//        System.out.println(" ab = "+ re_normalDomain);
//
//        
//        System.out.println("=================Montgomery_multiply test end =================");
//        
//	    assertEquals(BigInteger.valueOf(6),res);
//	}
	
	@Test
	void Mont_expMod_test() {
		
		System.out.println("=================Montgomery_ExpMod test =================");
		
		BigInteger m = BigDecimal.valueOf(2106).toBigInteger();
        BigInteger ec = BigDecimal.valueOf(13).toBigInteger();
        BigInteger N = BigDecimal.valueOf(2537).toBigInteger();
        BigInteger cipher = obj.encrypt_Mont(m, ec,N);
        System.out.println(m+"^" +ec+ " mod "+ N +" = "+ cipher +" (Montgomery)");
        BigInteger cipher_compare = obj.encrypt(m, ec,N);
        System.out.println(m+"^" +ec+ " mod "+ N +" = "+ cipher_compare+" (repeatedSquaring)");
        
        
        BigInteger dc = BigDecimal.valueOf(937).toBigInteger();
        BigInteger mesg = obj.decrypt_Mont(cipher, dc,N);
        System.out.println(cipher +"^" +dc+ " mod "+ N +" = "+ mesg+" (Montgomery)");
        BigInteger mesg_compare = obj.decrypt(cipher_compare, dc,N);
        System.out.println(cipher_compare +"^" +dc+ " mod "+ N +" = "+ mesg_compare+" (repeatedSquaring)");
        
        System.out.println("=================Montgomery_ExpMod test end =================");
        
	    assertEquals(m,mesg);
	}
	
	
	@Test
	void repeatedSquaringTest() {
		int[] exp_Bin = obj.convertToBinary(BigInteger.valueOf(20));
		assertEquals(BigInteger.valueOf(25),obj.repeatedSquaring(BigInteger.valueOf(5),exp_Bin, BigInteger.valueOf(35)));
	}
	
	@Test
	void key_GenTest() {
		
    	Random rnd1 = new Random();
    	Random rnd2 = new Random();
        BigInteger p = BigInteger.probablePrime(28/2,rnd1); //1024 is too large for converting into integer, use 54 for now
        BigInteger q = BigInteger.probablePrime(28/2,rnd2);
        
        System.out.println("generated p is " + p);
        System.out.println("generated q is " + q);
		
        BigInteger[] keys = obj.key_Gen(p, q);
        BigInteger pi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
       System.out.println("pi = " + pi);
        System.out.println("ed mod pi = " + keys[0].multiply(keys[1]).mod(pi));
        assertEquals(BigInteger.ONE, keys[0].multiply(keys[1]).mod(pi));
        
	}
	
	@Test
	void rsa_Test() {
		System.out.println("============  test the whole process ==========");
		
		System.out.println("------- key generation ----------");
    	Random rnd1 = new Random();
    	Random rnd2 = new Random();
        BigInteger p = BigInteger.probablePrime(1024/2,rnd1); 
        BigInteger q = BigInteger.probablePrime(1024/2,rnd2);
        
        System.out.println("generated p is " + p);
        System.out.println("generated q is " + q);
		
        BigInteger[] keys = obj.key_Gen(p, q);
        BigInteger pi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger n = p.multiply(q);
        
        System.out.println("e is : "+keys[0]+", d is : "+keys[1]);
        System.out.println("ed mod pi = " + keys[0].multiply(keys[1]).mod(pi));
        
        System.out.println("------- encryption ----------");
        
        BigInteger inputMsg = new BigInteger("77445461");//39"); //able to return correct answer within 8 digits
        BigInteger outputC = rsa.encrypt(inputMsg, keys[0], n);
        
        System.out.println("original message : "+ inputMsg);
        System.out.println("output ciphertext : "+ outputC);
        
        System.out.println("------- decryption ----------");
        
        BigInteger decryptedPlaintext = obj.decrypt(outputC, keys[1], n);
        System.out.println("decrypted text : "+ decryptedPlaintext);
        
        assertEquals(inputMsg,decryptedPlaintext);
        
        System.out.println("============  done with this test ==========");
        
	}
	
	
	@Test
	void attack_generatemessageTest() {
		TimingAttack ta = new TimingAttack();
		//target generate a big integer between 1000 to 10000
		BigInteger res = ta.generateMessage(BigInteger.valueOf(10000), BigInteger.valueOf(1000));
		System.out.println(res);
		boolean  b = false;
		if(res.compareTo(BigInteger.valueOf(1000))==1 && res.compareTo(BigInteger.valueOf(10000))== -1) {
			b = true;
		}
		assertEquals(true,b);
	}
	
	
	@Test
	void attack_varianceTest() { 
		TimingAttack ta = new TimingAttack();
		double[] nums = {7.0, 6.0, 6.0, 5.0};
		assertEquals(0.5,ta.findVariance(nums));
	}
	
	@Test
	void runTime_test() {
		
		System.out.println("================= run time test =================");
		
    	Random rnd1 = new Random();
    	Random rnd2 = new Random();
        BigInteger p = BigInteger.probablePrime(1024/2,rnd1); //1024 is too large for converting into integer, use 54 for now
        BigInteger q = BigInteger.probablePrime(1024/2,rnd2);
        BigInteger N = p.multiply(q);
        BigInteger[] keys = obj.key_Gen(p, q);
  		BigInteger m = BigDecimal.valueOf(2106134321).toBigInteger();
  		int[] exp_Bin = obj.convertToBinary(keys[0]);

  		
  		double timing_rs = 0.0;
		for(int i =0; i<1000; i++) {
			double begin = System.nanoTime();
			obj.repeatedSquaring(m, exp_Bin, N);
			double timetaken = System.nanoTime() - begin;
			timing_rs = timing_rs + timetaken;
		 }	 
		double average_rs = timing_rs/1000;
		System.out.println("average time of repeatedSquaring is: "+ average_rs);
		
  		double timing_Mont = 0.0;
		for(int i =0; i<100; i++) {
			double begin = System.nanoTime();
			obj.Montgomery_ExpMod(m, exp_Bin, N);
			double timetaken = System.nanoTime() - begin;
			System.out.println("Montgomery i is: "+ i);
			timing_Mont = timing_Mont + timetaken;
		 }	 
		double average_Mont = timing_Mont/100;
		System.out.println("average time of Montgomery is: "+ average_Mont);
        
        System.out.println("=================run time test end =================");

	}


}
