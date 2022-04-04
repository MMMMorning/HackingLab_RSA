# HackingLab_RSA

There are five java files in src directory:

- rsa.java:
This file contains the RSA algorithm implementation; by selecting encrypt(or decrypt) and encrypt Mont(or decrypt Mont), it is possible to select between only using repeated squaring algorithm and using Montgomery algorithm.
- Testrsa.java: this file contains the test cases for functions in rsa.java

- CCA.java: This is a preliminary investigation into RSA attacks by reproducing the naive RSA attack mentioned in the lecture of security and cryptography.

- TimingAttack.java: this files contains both Kocher's attack and an improved version proposed by paper "A practical implementation of the timing attack".
- TimingAttackAttack.java: this file contains the some attack cases for TimingAttack.java
