package TabularFunctions;

import java.util.Scanner;

import LinkedList.SLinkedList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TabularEvaluation test = new TabularEvaluation();
		Scanner scan = new Scanner(System.in);
		
		SLinkedList mintermsD = new SLinkedList();
		
		System.out.println("Welcome to Tabular's");
		System.out.println("\n===========================");
		System.out.print("\nEnter the number of variables : ");


		int numOfVariables = 4;
//		while (true) {
//		try {
//		numOfVariables = scan.nextInt();
//		} catch (Exception e) {
//			System.out.print("\nInvalid input (Enter an Integer value) : ");
//			scan.next();
//			continue;
//		}
//		break;
//		}

		
		int NOM = (int)Math.pow(2, numOfVariables);
		
		
		System.out.println("\n===========================");
		System.out.print("\nEnter " + NOM + " to finish. ");

		System.out.print("\nEnter the minterms (Smaller than : " + NOM + " ) : ");

		
		
		scan.close();
		
		
		String minU = "0,1,2,5,6";
		int[] minterm = test.setMinterms(minU, numOfVariables);
		test.setRealMinterms(minterm);
		int[] dont;
		try{
		dont = test.setDontCares("7", numOfVariables);
		} catch (Exception e) {
			dont = null;
		}
		
		if (test.isValidInput(minterm, dont)) {

			int[] minterms;
			if (dont != null && dont.length != 0) {
			minterms = new int[minterm.length + dont.length];
			for (int i = 0 ; i < minterm.length ; i++) {
				minterms[i] = minterm[i]; 
			}
			for (int i = minterm.length ; i < minterm.length + dont.length ; i++) {
				minterms[i] = dont[i-minterm.length];
			}
			} else {
				minterms = minterm;
			}

			
			
			test.setParameters(minterms, numOfVariables, dont);
	
			SLinkedList res = test.findPrimeImplicants();
			//test.printTest(res);
			String mapped = test.mapPrimeImplicants(res);
			System.out.println(mapped);
	
			String[] allPrimeImplicants = mapped.split("\\s*,\\s*");
	

			SLinkedList pet = test.rowColumnChart(res);
			//System.out.println(((petrikGroup)pet.get(0)).primeImplicant.size());

			for (int i = 0; i < ((petrikGroup)pet.get(0)).primeImplicant.size() ; i++) {
				System.out.println(((petrikGroup)pet.get(0)).primeImplicant.get(i));
			}
			
			
			String[] allSolutions = test.mapAllSolutions(pet, allPrimeImplicants);
			SLinkedList min = test.getMinSolutions(pet);
			String[] minSolutions = test.mapAllSolutions(min, allPrimeImplicants);


			//System.out.println(allSolutions.length);

			for (int i = 0 ; i < allSolutions.length ; i++) {
				System.out.println(allSolutions[i]);
			}
			System.out.println("Min solu : ");

			for (int i = 0 ; i < minSolutions.length ; i++) {
				System.out.println(minSolutions[i]);
			}
		} else {
			System.out.println("invalid");
		}
		
		
	}


}
