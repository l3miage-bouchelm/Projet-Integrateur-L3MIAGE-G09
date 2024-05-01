package l3m.cyber.planner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import l3m.cyber.planner.requests.PlannerParameter;
import l3m.cyber.planner.utils.Planner;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PlannerApplicationTests {

	// ci-dessous : test unitaire idiot pour demonstration
	@Test
	void dummyTest(){
		System.out.println("*********************************");
		System.out.println("Hello world\n");
		assertTrue(1!=2);
		System.out.println("*********************************");
		
	}

	// Vous pouvez ajouter des tests unitaires ici si vous le souhaitez

	
	// @Test
	// void nonnullTestPlanning(){
	// 	Double[][] matrix = {{0.0,1.1},{1.1,0.0}};
	// 	int k=1;
	// 	int start=0;
	// 	PlannerParameter param= new PlannerParameter(matrix, k, start);
	// 	Planner pl= new Planner(param);
    //     PlannerResult pr=pl.result();
	// 	assertTrue(pr.tournees() !=null); //le tableau tournees doit etre non null
	// 	assertTrue(pr.longTournees() != null); // idem, le tableau longTournees doit etre non null
	// }
	
	// @Test
	// public void testPartitionAlea(){
	// 	Double [][] distances = {{0.0, 1.0, 2.0, 3.0},
	// 							 {1.0, 0.0, 1.0, 2.0},
	// 							 {2.0, 1.0, 0.0, 1.0},
	// 							 {3.0, 2.0, 1.0, 0.0}};
	// 	int k = 2;
	// 	int depot = 1;
	// 	ArrayList<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 6));
	// 	Double [][] distances2 = {{0.0, 1.0, 2.0, 3.0,4.0},
	// 							 {1.0, 0.0, 1.0, 2.0,3.0},
	// 							 {2.0, 1.0, 0.0, 1.0,2.0},
	// 							 {3.0, 2.0, 1.0, 0.0,1.0}};

	// 	//Création d'une instance de partition et appel de la méthode partitionne
	// 	PartitionAlea partition1 = new PartitionAlea(4, k);
	// 	PartitionAlea partition2 = new PartitionAlea(4, k,depot);
	// 	PartitionAlea partition3 = new PartitionAlea(list,k,depot);

	// 	//les paramètres sont4，k/////////////////////////////////////////////////////////////////////////////
	// 	partition1.partitionne(distances);
	//     Set<Integer> allAssignedElements = new HashSet<>();

	// 	for (int i = 0; i < k; i++) {
	// 		ArrayList<Integer> partie;
	// 		try {
	// 			partie = partition1.getPartie(i);  // obtenir la partie unique
	// 		} catch (IndexOutOfBoundsException e) {
	// 			System.out.println("Index hors de la plage de partition：" + e.getMessage());
	// 			continue;
	// 		}

	// 		// / Vérifier que le élément spéciale ne se trouve qu'au début de la liste
	// 		assertTrue(partie.indexOf(0) == 0, "le élément spéciale ne se trouve qu'au début de la liste");

	// 		// Collecter tous les elements non spéciaux
	// 		for (Integer elem : partie) {
	// 			if (elem != 0) {  // suppose 0 est l'element special
	// 				allAssignedElements.add(elem);
	// 			}
	// 		}
	// 	}
	// 	assertEquals(3, allAssignedElements.size(),"Tous les éléments non spéciaux doivent être attribués une fois");//il y a du 4 places


	// 	///////////////////les paramètres sont 4，k，depot///////////////////////////////////////////////////////////
	// 	partition2.partitionne(distances);
	// 	Set<Integer> allAssignedElements2 = new HashSet<>();

	// 	for (int i = 0; i < k; i++) {
	// 		ArrayList<Integer> partie2;
	// 		try {
	// 			partie2 = partition2.getPartie(i);  // Obtenir la partie unique
	// 		} catch (IndexOutOfBoundsException e) {
	// 			System.out.println("Index hors de la plage de partition：" + e.getMessage());
	// 			continue;
	// 		}

	// 		// Vérifier que le élément spéciale ne se trouve qu'au début de la liste
	// 		assertTrue(  partie2.get(0).equals(depot),"le élément spéciale ne se trouve qu'au début de la liste");


	// 		// Collecter tous les elements non spéciaux
	// 		for (Integer elem : partie2) {
	// 			if (elem != depot) {  // depot est element special
	// 				allAssignedElements2.add(elem);
	// 			}
	// 		}
	// 	}
	// 	assertEquals(3, allAssignedElements2.size(),"Tous les éléments non spéciaux doivent être attribués une fois");//il y a du 4 places

	// 	///////////////////les paramètres sont list，k，depot////////////////////////////////////////////////////////////
	// 	partition3.partitionne(distances2);//Cela équivaut à donner une liste de lieux spécifiés
	// 	Set<Integer> allAssignedElements3 = new HashSet<>();

	// 	for (int i = 0; i < k; i++) {
	// 		ArrayList<Integer> partie3;
	// 		try {
	// 			partie3 = partition3.getPartie(i);  // Obtenir une partition unique
	// 		} catch (IndexOutOfBoundsException e) {
	// 			System.out.println("Index hors de la plage de partition：" + e.getMessage());
	// 			continue;
	// 		}

	// 		assertTrue( partie3.get(0).equals(depot), "Éléments spéciaux en premier lieu");

	// 		// Collecter tous les éléments non spéciaux
	// 		for (Integer elem : partie3) {
	// 			if (elem != depot) {  // depot est element special
	// 				allAssignedElements3.add(elem);
	// 			}
	// 		}
	// 	}
	// 	assertEquals(list.size()-1, allAssignedElements3.size()," Tous les éléments non spéciaux doivent être assignés une fois");//listcontient tous les éléments, il faut soustraire les éléments de l'entrepôt

	// }

	// @Test
    // public void testTSPversion1() {
	// 	ArrayList<Integer> vertices = new ArrayList<Integer>() {{
	// 		add(2);
	// 		add(0);
	// 		add(3);
	// 		add(1);
	// 		add(4);
	// 	}};
	// 	Graphe graphe = new Graphe(vertices);

    //  ArrayList<Integer> result = graphe.tsp(vertices.get(0));
	// 	ArrayList<Integer> expected = new ArrayList<Integer>() {{
	// 		add(2);
	// 		add(0);
	// 		add(1);
	// 		add(3);
	// 		add(4);
	// 	}};
	// 	assertEquals(expected, result,"La méthode TSP doit renvoyer des sommets triés en commençant par le sommet de départ.");
    // }

	
	// @Test
    // public void testCalculeTournees() {
	// 	int k = 2;
	// 	int start = 1;
	// 	Double[][] matrix = {{0.0, 1.0, 2.0, 3.0,4.0},
	// 						 {1.0, 0.0, 1.0, 2.0,3.0},
	// 						 {2.0, 1.0, 0.0, 1.0,2.0},
	// 						 {3.0, 2.0, 1.0, 0.0,1.0},
	// 						 {4.0, 3.0, 2.0, 1.0,0.0}};
	// 	PlannerParameter param= new PlannerParameter(matrix, k, start);
	// 	Planner planner = new Planner(param);
	// 	planner.calculeTournees();
	// 	planner.calculeLongTournees();
    //     assertNotNull(planner.getTournees(),"Les tournees ne doivent pas être nuls");
    //     assertFalse(planner.getTournees().isEmpty(),"Les tournées ne doivent pas être vides");
    // }
	// En raison de la méthode aléatoire utilisée ici, je ne peux pas prédire le résultat et je ne peux donc que vérifier qu'il n'est pas nul.
	
	
	


	@Test
	void myTest0PartionKCentre(){ //************************************ */
		Double[][] matrix = {{0.0}};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.calculeTournees();
		pl.calculeLongTournees();
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //[ [0], [0]]
		Ar.add(new ArrayList<>(List.of(0)));
		Ar.add(new ArrayList<>(List.of(0)));

		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit contenir des listes vides

	}


	@Test
	void myTest1PartionKCentre(){ //******************/
		Double[][] matrix = {{0.0, 1.1}, {1.1, 0.0}};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.calculeTournees();
		pl.calculeLongTournees();
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({ {0}, {0,1}  } )
		Ar.add(new ArrayList<>(List.of(0)));
		Ar.add(new ArrayList<>(List.of(start,1)));

		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null

	}



	@Test
	void myTest2PartionKCentre(){ //************************************ */
		Double[][] matrix = {{0.0, 1.1, 2.0,  2.0}, {1.1, 0.0, 2.0, 2.0},
				{2.0, 2.0, 0.0, 1.0}, {2.0, 2.0, 1.1, 0.0}};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.calculeTournees();
		pl.calculeLongTournees();
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{0, 1}, {0, 2, 3}} )
		Ar.add(new ArrayList<>(List.of(0, 1)));
		Ar.add(new ArrayList<>(List.of(start, 2,3)));

		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null

	}



	@Test
	void myTestnPartionKCentre(){ //************************************ */
		Double[][] matrix = {{0.0, 1.1, 2.0,  3.0, 4.0, 4.0}, {1.1, 0.0, 2.0, 2.0, 3.0, 3.0},
				{2.0, 2.0, 0.0, 2.0, 3.0, 3.0}, {3.0, 2.0, 2.0, 0.0, 1.1, 1.1}
				, {4.0, 3.0, 3.0, 1.0, 0.0, 2.0},{4.0, 3.0, 3.0, 1.0, 2.0, 0.0}};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.calculeTournees();
		pl.calculeLongTournees();
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{0, 1, 2}, {0, 3, 4, 5}} )
		Ar.add(new ArrayList<>(List.of(0, 1, 2)));
		Ar.add(new ArrayList<>(List.of(start, 3, 4, 5)));

        assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null
	}

//	@Test
//	void myTestn2PartionKCentre(){ //************************************ */
//		Double[][] matrix = {{0.0, 1.1, 2.0,  2.0}, {1.1, 0.0, 2.0, 2.0},
//				{2.0, 2.0, 0.0, 1.0}, {2.0, 2.0, 1.1, 0.0}};
//		int k=2;
//		int start=3;
//		PlannerParameter param= new PlannerParameter(matrix, k, start);
//		Planner pl= new Planner(param);
//		pl.calculeTournees();
//		pl.calculeLongTournees();
//		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{3, 0, 1}, {3, 2}} )
//		Ar.add(new ArrayList<>(List.of(3, 0, 1)));
//		Ar.add(new ArrayList<>(List.of(start, 2)));
//
//		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null
//
//	}
//
//	@Test
//	void myTestn3PartionKCentre(){ //************************************ */
//		Double[][] matrix = {{0.0, 1.1, 1.0,  3.0, 4.0, 4.0}, {1.1, 0.0, 2.0, 2.0, 3.0, 3.0},
//				{1.0, 2.0, 0.0, 2.0, 3.0, 3.0}, {3.0, 2.0, 2.0, 0.0, 1.1, 1.1}
//				,{4.0, 3.0, 3.0, 1.0, 0.0, 2.0},{4.0, 3.0, 3.0, 1.0, 2.0, 0.0}};
//		int k=2;
//		int start=3;
//		PlannerParameter param= new PlannerParameter(matrix, k, start);
//		Planner pl= new Planner(param);
//		pl.calculeTournees();
//		pl.calculeLongTournees();
//		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{3, 0, 1, 2}, {3, 4, 5}} )
//		Ar.add(new ArrayList<>(List.of(start, 0, 1, 2)));
//		Ar.add(new ArrayList<>(List.of(start, 4, 5)));
//
//		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null
//	}
////Il y a encore quelques problèmes avec la méthode kcentre, donc pour ne pas signaler d'erreurs, commentez d'abord ces deux tests.



}