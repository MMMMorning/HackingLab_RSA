import java.math.*;
import java.util.*;
	
public class rsa {
	
	static int amplification = 20;

	    public static void main(String args[])
	    {
	    	Random rnd1 = new Random();
	    	Random rnd2 = new Random();
	        BigInteger p = BigInteger.probablePrime(1024/2,rnd1); 
	        BigInteger q = BigInteger.probablePrime(1024/2,rnd2);
	     
	        System.out.println(p);
	        System.out.println(q);
	        
	        
	        //Montgomery_ExpMod(BigInteger.ONE, BigInteger.ONE, BigInteger.valueOf(1982736662));


	        //int[] test = key_Gen(p_i,q_i);

//	        BigInteger inputMsg = BigInteger.TEN;
//	        BigInteger n = p.multiply(q); 
//	        BigInteger[] keyPair = key_Gen(p,q);
//	        BigInteger encryptedMsg = encrypt(inputMsg, keyPair[0], n);
	    }
	    
	    public static BigInteger[] key_Gen(BigInteger p, BigInteger q) { //with input two large primes p and q, the function will return the public key e and private key d
	    	BigInteger pi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));  //pi = (p-1)*(q-1)
	    	BigInteger n = p.multiply(q); // n = p*q;
	    	BigInteger e, d;
	    	BigInteger[] keys = new BigInteger[2]; //key[0] = e and key[1] = d;
	    	
	    	//generate e:
	    	Random rnd = new Random();
	    	e = BigInteger.probablePrime(pi.bitLength()-1, rnd);
	    	//System.out.println("generated e is " + e);
	    	
	    	while(!gcd(pi,e).equals(BigInteger.ONE)) {
	    		e = BigDecimal.valueOf(65537).toBigInteger(); //65537 is a value often used in practice
	    	}
	    	
	    	//System.out.println("final e is " + e);
	    	
	    	BigInteger[] a_x_y = extend_gcd(e,pi);
	    	if(a_x_y[1].compareTo(BigInteger.ZERO) == -1) {
	    		a_x_y[1] = a_x_y[1].add(pi);
	    	}
	    	d = a_x_y[1];
	    	
	    	//System.out.println("generated d is " + d);
	    	
	    	keys[0] = e;
	    	keys[1] = d;
	    	return keys;
	    }
	
	    //RSA with only repeated squaring algorithm
	    public static BigInteger encrypt(BigInteger m, BigInteger e, BigInteger n) { //encryption process: input a message, public key and large number N, return the corresponding ciphertext
	    	int[] exp_Bin = convertToBinary(e);
	    	BigInteger c = repeatedSquaring(m, exp_Bin, n);//Mont_multi(m,e,n);
	    	return c;
	    }
	    
	    public BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n) { //decryption process: with given ciphertext, known private key and large number N, the function will return corresponding original message
	    	int[] exp_Bin = convertToBinary(d);
	    	BigInteger msg = repeatedSquaring(c, exp_Bin, n);//Mont_multi(c,d,n);
	    	return msg;
	    	
	    }
	    
	    
	    //RSA with Montgomery algorithm
	    public static BigInteger encrypt_Mont(BigInteger m, BigInteger e, BigInteger n) { //encryption process: input a message, public key and large number N, return the corresponding ciphertext
	    	int[] exp_Bin = convertToBinary(e);
	    	BigInteger c = Montgomery_ExpMod(m,exp_Bin,n);
	    	return c;
	    }
	    
	    public BigInteger decrypt_Mont(BigInteger c, BigInteger d, BigInteger n) { //decryption process: with given ciphertext, known private key and large number N, the function will return corresponding original message
	    	int[] exp_Bin = convertToBinary(d);
	    	BigInteger msg = Montgomery_ExpMod(c, exp_Bin, n);
	    	return msg;
	    	
	    }
	    
	    //repeated squaring algorithm
	    public static BigInteger repeatedSquaring(BigInteger base, int[] exp_Bin, BigInteger n) { 
	    	
	    	BigInteger result = base;
	    	for(int i =  1; i < exp_Bin.length; i++) {
	    		result = result.pow(2).mod(n);
	    		if(exp_Bin[i]==1) {
	    			result = result.multiply(base).mod(n);
	    			
	    			//Amplification
	    			for(int am = 0; am<=amplification; am++) {
	    				result.multiply(base).mod(n);
	    			}
	    		}
	    	} 
	    	return result;
	    }
	    
	    public static BigInteger Montgomery_ExpMod(BigInteger base, int[] exp_Bin, BigInteger n) {
	    	
	    	//generate r, which r=2^k, r > n and gcd(r,n) = 1
	    	int n_length = n.bitLength();
	    	BigInteger r = null;
	    	for(int i = n_length-1; i>=0; i--) {
	    		if(n.testBit(i)) { //if bit i of n is 1
	    			r = BigInteger.ZERO.setBit(i+1);
	    			break;
	    		}
	    	}
	    	
	    	//convert into Montgomerg domain: computing base*r=base*R mod N 
	    	BigInteger base_r = base.multiply(r).mod(n);
	    	BigInteger result = base_r;
	    	
	    	
	    	for(int i =  1; i < exp_Bin.length; i++) {
	    		result = Montgomery_multiply(result, result, r,n); //=base^2r
	    		if(exp_Bin[i]==1) {
	    			result = Montgomery_multiply(result, base_r, r, n); //=base^3R
	    		}
	    		//System.out.println(base+"^"+pow +"mod" + n +" = "+ result);
	    	}
	    	
	    	result = result.multiply(r.modInverse(n)).mod(n);
	    	
	    	return result;
	    }
	    
	    
	    //input a, b, r, n
	    //return abr
	    public static BigInteger Montgomery_multiply(BigInteger ar, BigInteger br, BigInteger r, BigInteger n) { //Implementation of Montgomery algorithm

	    	BigInteger x = ar.multiply(br);
	    	//System.out.println("abrr = " + x);

	    	BigInteger r_inverse = r.modInverse(n);
	    	BigInteger n_inverse = n.modInverse(r).multiply(BigInteger.valueOf(-1));
	    	//System.out.println("inverse of r = " + r_inverse);
	    	
	    	//System.out.println("inverse of n = " + n_inverse);
	    	//computing m = (x mod r)*n_inverse mod r
	    	BigInteger m = (x.mod(r)).multiply(n_inverse).mod(r);
	    	//System.out.println("m = " + m);
	    	
	    	//res = (x+mn)/r
	    	
	    	//BigInteger res = (x.add(m.multiply(n))).divide(r);
	    	BigInteger res = (x.add(m.multiply(n))).shiftRight(((r.subtract(BigInteger.ONE)).bitCount()));
	    	
	    	if(res.compareTo(n) >= 0) {
	    		res = res.subtract(n); //extra reduction
	    		
    			//Amplification
    			for(int am = 0; am<=amplification; am++) {
    				res.subtract(n);
    			}
    			
	    	}
	    	
	    	//System.out.println(" abr = "+ res);
	    	
	    	return res;
	    	
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
      
	    
	    public static BigInteger gcd(BigInteger a, BigInteger b) //Euclidean Algorithm: return gcd(a,b)
	    {
//	    	if(a.compareTo(b)==-1) { //if input a is greater than b, the order of a and b will be switched
//	    		gcd(b,a);
//	    	}
	    	
	    	//System.out.println("gcd( " +a+", "+b+")");	    	
	        if (b.equals(BigInteger.ZERO)) {
	            return a;
	        }else {
	            return gcd(b, a.mod(b));}
	    }
	    
	    
	    public static BigInteger[] extend_gcd(BigInteger a, BigInteger b) //Extend Euclidean Algorithm, return gcd(a,b),x and y, which xa+yb = gcd(a,b)
	    {
	    	BigInteger x = BigInteger.ONE, x1 = BigInteger.ZERO, y =BigInteger.ZERO, y1 = BigInteger.ONE, d, reminder = a;

	    	while(!b.equals(BigInteger.ZERO)){
	    		d = a.divide(b);
	    		reminder = a.subtract(d.multiply(b)); //a-d*b
	    		BigInteger xMid = x1;
	    		x1 = x.subtract(d.multiply(x1));//x - d*x1;
	    		x = xMid;
	    		BigInteger yMid = y1;
	    		y1 = y.subtract(d.multiply(y1));//y - d*y1;
	    		y = yMid;
	    		a = b;
	    		b = reminder;	
	    	}
	    	
	    	BigInteger[] a_x_y = new BigInteger[3];
	    	//a = a(input)*x+b(input)*y
	    	a_x_y[0] = a;
	    	a_x_y[1] = x;//t_b;
	    	a_x_y[2] = y;//s_b;
	    	
	    	return a_x_y ;

	    }

	    

}
