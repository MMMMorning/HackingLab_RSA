import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TimingAttackTest {
	
	TimingAttack attack = new TimingAttack();
	int attackingTimes = 50;
	int attackingTimes_Mont = 50;
	
//	@Test
//	void beforeAttack() { 
//		 int startingIndex = 2; //meaning guessing from this index of d in real life, not array index
//		 int guessUntilIndex = 20; 
//		 int[] guessD = new int[guessUntilIndex];
//		 Arrays.fill(guessD, 0);
//
//		 for(int i = 0; i < startingIndex-1; i++) {
//			 guessD[i] = attack.binaryD[i];
//		 }
//		 
//		 System.out.println("guessed d is: ");
//		 for(int val: guessD) {
//			 System.out.print(val); 
//		 }
//		 System.out.println();
//		 
//		 
//		 System.out.println("binary d is: ");
//		 for(int val: attack.binaryD) {
//			 System.out.print(val); 
//		 }
//		 System.out.println();
//		 
//		 int[] res = new int[guessUntilIndex];
//		 Arrays.fill(res, 0);
//		 res[0] = 1;
//		 
////		 System.out.println("res is: ");
////		 for(int val: res) {
////			 System.out.print(val); 
////		 }
////		 System.out.println();
//		 
//		 
//		 assertArrayEquals(res, guessD);
//	}

//======================================= attack cases of Kocher's attack ==========================	
	@Test
	void index1() {
		 System.out.println("bit length of d is " + attack.keys[1].bitLength());
		 
		 System.out.println("===================== break index 1 ======================");
		 
		 int success = 0;
		 int expectVal = attack.binaryD[1];
		 for(int i = 0; i < attackingTimes; i++) {
		 int[] guessD = new int[] {1,0};		 
		 attack.KocherAttackV2(guessD, 1);
		 if(guessD[1] == expectVal) {
			 success ++;
		 }
		 }
		  
		 System.out.println("Accuracy of revealing bit 1 is : " + success*1.0/(attackingTimes*1.0));
	}
	
	
//	@Test
//	void index9() {
//		 System.out.println("===================== break index 9 ======================");
//		 
//		 int success = 0;
//		 int expectVal = attack.binaryD[9];
//		 int[] guessD = new int[10];
//		 for(int i = 0; i <9; i++) {
//			 guessD[i] = attack.binaryD[i];
//		 }
//		 guessD[9] = 0;
//		 
//		 for(int i = 0; i < attackingTimes; i++) {
//		 int[] resetGuessD = new int[guessD.length];
//		 for(int j = 0; j < guessD.length; j++) {
//			 resetGuessD[j] = guessD[j];
//		 }
//		 attack.KocherAttackV2(resetGuessD, 9);
//		 if(resetGuessD[9] == expectVal) {
//			 success ++;
//		 }
//		 }
//		  
//		 System.out.println("Accuracy of revealing bit 9 is : " + success*1.0/(attackingTimes*1.0));
//	}
	
	
//	@Test
//	void index19() {
//		 System.out.println("===================== break index 19 ======================");
//		 
//		 int success = 0;
//		 int expectVal = attack.binaryD[19];
//		 int[] guessD = new int[20];
//		 for(int i = 0; i <19; i++) {
//			 guessD[i] = attack.binaryD[i];
//		 }
//		 guessD[19] = 0;
//		 for(int i = 0; i < attackingTimes; i++) {
//		 int[] resetGuessD = new int[guessD.length];
//		 for(int j = 0; j < guessD.length; j++) {
//			 resetGuessD[j] = guessD[j];
//		 }
//		 attack.KocherAttackV2(resetGuessD, 19);
//		 if(resetGuessD[19] == expectVal) {
//			 success ++;
//		 }
//		 }
//		  
//		 System.out.println("Accuracy of revealing bit 19 is : " + success*1.0/(attackingTimes*1.0));
//	}
	
//	@Test
//	void index99() {
//		 System.out.println("===================== break index 99 ======================");
//		 
//		 int success = 0;
//		 int expectVal = attack.binaryD[99];
//		 int[] guessD = new int[100];
//		 for(int i = 0; i <99; i++) {
//			 guessD[i] = attack.binaryD[i];
//		 }
//		 guessD[99] = 0;
//		 
//		 for(int i = 0; i < attackingTimes; i++) {
//		 int[] resetGuessD = new int[guessD.length];
//		 for(int j = 0; j < guessD.length; j++) {
//			 resetGuessD[j] = guessD[j];
//		 }
//		 attack.KocherAttackV2(resetGuessD, 99);
//		 if(resetGuessD[99] == expectVal) {
//			 success ++;
//		 }
//		 }
//		  
//		 System.out.println("Accuracy of revealing bit 99 is : " + success*1.0/(attackingTimes*1.0));
//	}
	
//	@Test
//	void index199() {
//		 System.out.println("===================== break index 199 ======================");
//		 
//		 int success = 0;
//		 int expectVal = attack.binaryD[199];
//		 int[] guessD = new int[200];
//		 for(int i = 0; i <199; i++) {
//			 guessD[i] = attack.binaryD[i];
//		 }
//		 guessD[199] = 0;
//		 for(int i = 0; i < attackingTimes; i++) {
//		 int[] resetGuessD = new int[guessD.length];
//		 for(int j = 0; j < guessD.length; j++) {
//			 resetGuessD[j] = guessD[j];
//		 }
//		 attack.KocherAttackV2(resetGuessD, 199);
//		 if(resetGuessD[199] == expectVal) {
//			 success ++;
//		 }
//		 }
//		  
//		 System.out.println("Accuracy of revealing bit 199 is : " + success*1.0/(attackingTimes*1.0));
//	}
	
//	@Test
//	void index499() {
//		 System.out.println("===================== break index 499 ======================");
//		 
//		 int success = 0;
//		 int expectVal = attack.binaryD[499];
//		 int[] guessD = new int[500];
//		 for(int i = 0; i <19; i++) {
//			 guessD[i] = attack.binaryD[i];
//		 }
//		 guessD[499] = 0;
//		 for(int i = 0; i < attackingTimes; i++) {
//		 int[] resetGuessD = new int[guessD.length];
//		 for(int j = 0; j < guessD.length; j++) {
//			 resetGuessD[j] = guessD[j];
//		 }
//		 attack.KocherAttackV2(resetGuessD, 499);
//		 if(resetGuessD[499] == expectVal) {
//			 success ++;
//		 }
//		 }
//		  
//		 System.out.println("Accuracy of revealing bit 499 is : " + success*1.0/(attackingTimes*1.0));
//	}

	//======================================== Montgomery attack test   =========================================
	
	@Test
	void index1_Mont() {
 		 System.out.println("===================== break index 1(Mont) ======================");
		 
		 int success = 0;
		 int expectVal = attack.binaryD[1];
		 for(int i = 0; i < attackingTimes_Mont; i++) {
		 int[] guessD = new int[] {1};		 
		 int res = attack.attackOnMontgomery(guessD);
		 if(res == expectVal) {
			 success ++;
		 }
		 }
		  
		 System.out.println("Accuracy of revealing bit 1 is : " + success*1.0/(attackingTimes_Mont*1.0));
	}
	
//	@Test
//	void index19_Mont() {
// 		 System.out.println("===================== break index 19(Mont) ======================");
//		 
//		 int success = 0;
//		 int expectVal = attack.binaryD[19];
//		 int[] knownD = new int[19];
//		 for(int i = 0; i <19; i++) {
//			 knownD[i] = attack.binaryD[i];
//		 }
//		 
//		 for(int i = 0; i < attackingTimes_Mont; i++) {
//			 int[] resetKnownD = new int[knownD.length];
//			 for(int j = 0; j < knownD.length; j++) {
//				 resetKnownD[j] = knownD[j];
//			 }	 
//		 int res = attack.attackOnMontgomery(knownD);
//		 if(res == expectVal) {
//			 success ++;
//		 }
//		 }
//		  
//		 System.out.println("Accuracy of revealing bit 19 is : " + success*1.0/(attackingTimes_Mont*1.0));
//	}
//	
//	@Test
//	void index49_Mont() {
// 		 System.out.println("===================== break index 49(Mont) ======================");
//		 
//		 int success = 0;
//		 int expectVal = attack.binaryD[49];
//		 int[] knownD = new int[49];
//		 for(int i = 0; i <49; i++) {
//			 knownD[i] = attack.binaryD[i];
//		 }
//		 
//		 for(int i = 0; i < attackingTimes_Mont; i++) {
//			 int[] resetKnownD = new int[knownD.length];
//			 for(int j = 0; j < knownD.length; j++) {
//				 resetKnownD[j] = knownD[j];
//			 }	 
//		 int res = attack.attackOnMontgomery(knownD);
//		 if(res == expectVal) {
//			 success ++;
//		 }
//		 }
//		  
//		 System.out.println("Accuracy of revealing bit 49 is : " + success*1.0/(attackingTimes_Mont*1.0));
//	}
//	
//	@Test
//	void index99_Mont() {
// 		 System.out.println("===================== break index 99(Mont) ======================");
//		 
//		 int success = 0;
//		 int expectVal = attack.binaryD[99];
//		 int[] knownD = new int[99];
//		 for(int i = 0; i <99; i++) {
//			 knownD[i] = attack.binaryD[i];
//		 }
//		 
//		 for(int i = 0; i < attackingTimes_Mont; i++) {
//			 System.out.println("i is "+i);
//			 int[] resetKnownD = new int[knownD.length];
//			 for(int j = 0; j < knownD.length; j++) {
//				 resetKnownD[j] = knownD[j];
//			 }	 
//		 int res = attack.attackOnMontgomery(knownD);
//		 if(res == expectVal) {
//			 success ++;
//		 }
//		 }
//		  
//		 System.out.println("Accuracy of revealing bit 99 is : " + success*1.0/(attackingTimes_Mont*1.0));
//	}
}
