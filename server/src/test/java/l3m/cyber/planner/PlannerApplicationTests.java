package l3m.cyber.planner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import l3m.cyber.planner.responses.PlannerResult;
import l3m.cyber.planner.utils.Graphe;
import l3m.cyber.planner.utils.PartitionAlea;
import l3m.cyber.planner.requests.PlannerParameter;
import l3m.cyber.planner.utils.Graphe;
import l3m.cyber.planner.utils.PartitionAlea;
import l3m.cyber.planner.utils.Planner;
import l3m.cyber.planner.utils.Triplet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


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

	
	 @Test
	 void nonnullTestPlanning(){
	 	Double[][] matrix = {{0.0,1.1},{1.1,0.0}};
	 	int k=1;
	 	int start=0;
	 	PlannerParameter param= new PlannerParameter(matrix, k, start);
	 	Planner pl= new Planner(param);
         PlannerResult pr=pl.result();
	 	assertTrue(pr.tournees() !=null); //le tableau tournees doit etre non null
	 	assertTrue(pr.longTournees() != null); // idem, le tableau longTournees doit etre non null
	 }
	
	 @Test
	 public void testPartitionAlea(){
	 	Double [][] distances = {{0.0, 1.0, 2.0, 3.0},
	 							 {1.0, 0.0, 1.0, 2.0},
	 							 {2.0, 1.0, 0.0, 1.0},
	 							 {3.0, 2.0, 1.0, 0.0}};
	 	int k = 2;
	 	int depot = 1;
	 	ArrayList<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 6));
	 	Double [][] distances2 = {{0.0, 1.0, 2.0, 3.0,4.0},
	 							 {1.0, 0.0, 1.0, 2.0,3.0},
	 							 {2.0, 1.0, 0.0, 1.0,2.0},
	 							 {3.0, 2.0, 1.0, 0.0,1.0},
				                 {4.0, 3.0, 2.0, 1.0,0.0}};

	 	//Création d'une instance de partition et appel de la méthode partitionne
	 	PartitionAlea partition1 = new PartitionAlea(4, k);
	 	PartitionAlea partition2 = new PartitionAlea(4, k,depot);
	 	PartitionAlea partition3 = new PartitionAlea(list,k,depot);

	 	//les paramètres sont4，k/////////////////////////////////////////////////////////////////////////////
	 	partition1.partitionne(distances);
	     Set<Integer> allAssignedElements = new HashSet<>();

	 	for (int i = 0; i < k; i++) {
	 		ArrayList<Integer> partie;
	 		try {
	 			partie = partition1.getPartie(i);  // obtenir la partie unique
	 		} catch (IndexOutOfBoundsException e) {
	 			System.out.println("Index hors de la plage de partition：" + e.getMessage());
	 			continue;
	 		}

	 		// / Vérifier que le élément spéciale ne se trouve qu'au début de la liste
	 		assertTrue(partie.indexOf(0) == 0, "le élément spéciale ne se trouve qu'au début de la liste");

	 		// Collecter tous les elements non spéciaux
	 		for (Integer elem : partie) {
	 			if (elem != 0) {  // suppose 0 est l'element special
	 				allAssignedElements.add(elem);
	 			}
	 		}
	 	}
	 	assertEquals(3, allAssignedElements.size(),"Tous les éléments non spéciaux doivent être attribués une fois");//il y a du 4 places


	 	///////////////////les paramètres sont 4，k，depot///////////////////////////////////////////////////////////
	 	partition2.partitionne(distances);
	 	Set<Integer> allAssignedElements2 = new HashSet<>();

	 	for (int i = 0; i < k; i++) {
	 		ArrayList<Integer> partie2;
	 		try {
	 			partie2 = partition2.getPartie(i);  // Obtenir la partie unique
	 		} catch (IndexOutOfBoundsException e) {
	 			System.out.println("Index hors de la plage de partition：" + e.getMessage());
	 			continue;
	 		}

	 		// Vérifier que le élément spéciale ne se trouve qu'au début de la liste
	 		assertTrue(  partie2.get(0).equals(depot),"le élément spéciale ne se trouve qu'au début de la liste");


	 		// Collecter tous les elements non spéciaux
	 		for (Integer elem : partie2) {
	 			if (elem != depot) {  // depot est element special
	 				allAssignedElements2.add(elem);
	 			}
	 		}
	 	}
	 	assertEquals(3, allAssignedElements2.size(),"Tous les éléments non spéciaux doivent être attribués une fois");//il y a du 4 places

	 	///////////////////les paramètres sont list，k，depot////////////////////////////////////////////////////////////
	 	partition3.partitionne(distances2);//Cela équivaut à donner une liste de lieux spécifiés
	 	Set<Integer> allAssignedElements3 = new HashSet<>();

	 	for (int i = 0; i < k; i++) {
	 		ArrayList<Integer> partie3;
	 		try {
	 			partie3 = partition3.getPartie(i);  // Obtenir une partition unique
	 		} catch (IndexOutOfBoundsException e) {
	 			System.out.println("Index hors de la plage de partition：" + e.getMessage());
	 			continue;
	 		}

	 		assertTrue( partie3.get(0).equals(depot), "Éléments spéciaux en premier lieu");

	 		// Collecter tous les éléments non spéciaux
	 		for (Integer elem : partie3) {
	 			if (elem != depot) {  // depot est element special
	 				allAssignedElements3.add(elem);
	 			}
	 		}
	 	}
	 	assertEquals(list.size()-1, allAssignedElements3.size()," Tous les éléments non spéciaux doivent être assignés une fois");//listcontient tous les éléments, il faut soustraire les éléments de l'entrepôt

	 }


	
	 @Test
     public void testCalculeTournees() {
	 	int k = 2;
	 	int start = 1;
	 	Double[][] matrix = {{0.0, 1.0, 2.0, 3.0,4.0},
	 						 {1.0, 0.0, 1.0, 2.0,3.0},
	 						 {2.0, 1.0, 0.0, 1.0,2.0},
	 						 {3.0, 2.0, 1.0, 0.0,1.0},
	 						 {4.0, 3.0, 2.0, 1.0,0.0}};
	 	PlannerParameter param= new PlannerParameter(matrix, k, start);
	 	Planner planner = new Planner(param);
	 	planner.result();
         assertNotNull(planner.getTournees(),"Les tournees ne doivent pas être nuls");
         assertFalse(planner.getTournees().isEmpty(),"Les tournées ne doivent pas être vides");
     }
	 //En raison de la méthode aléatoire utilisée ici, je ne peux pas prédire le résultat et je ne peux donc que vérifier qu'il n'est pas nul.


	//由于构造器的问题，权重矩阵为null，所以tsp没法顺利调用，那么测试也没法通过
	@Test
	public void testUniquePointGraphe() {
		Graphe UniquePointGraphe = new Graphe(1);
		ArrayList<Integer> resultat = UniquePointGraphe.tsp(0);

		assertEquals( 1, resultat.size()-1);

		assertEquals( Integer.valueOf(0), resultat.get(0));
	}

	@Test
	public void testCompletGraphe() {
		Graphe graph = new Graphe(4);
		graph.ajouterArete(0, 1, 5);
		graph.ajouterArete(0, 2, 6);
		graph.ajouterArete(0, 3, 7);
		graph.ajouterArete(1, 2, 4);
		graph.ajouterArete(1, 3, 6);
		graph.ajouterArete(2, 3, 5);

		// Tester l'algorithme de Kruskal
		Graphe mst = graph.Kruskal();
		List<Triplet> edges = mst.listeAretes();
		assertEquals(3, edges.size(), "L'arbre couvrant minimum (MST) devrait avoir n-1 arêtes");
		double totalWeight = edges.stream().mapToDouble(Triplet::getPoids).sum();
		assertEquals(14.0, totalWeight, "Le poids total du MST devrait être 14");

		// Tester l'algorithme TSP
		ArrayList<Integer> resultat = graph.tsp(0);
		assertNotNull(resultat, "Le résultat ne devrait pas être nul");
		assertEquals(4, resultat.size()-1, "Le résultat devrait contenir tous les sommets");
		assertTrue(resultat.stream().distinct().count() == 4, "Chaque sommet devrait être visité exactement une fois");

		// Calculer le poids total du TSP
		double tspWeight = 0.0;
		for (int i = 0; i < resultat.size() - 1; i++) {
			tspWeight += graph.getPoids(resultat.get(i), resultat.get(i + 1));
		}

		System.out.println("le résultat: " + resultat);
		System.out.println("Poids total du TSP: " + tspWeight);
		assertTrue(tspWeight >= totalWeight, "Le poids du TSP devrait être au moins aussi grand que le poids du MST");
		System.out.println("Poids total du TSP: " + tspWeight + " contre (2 * poids du MST): " + (totalWeight * 2));
		assertTrue(tspWeight <= 2 * totalWeight);
	}

	@Test
	public void testSmallGraph() {
		Graphe graph = new Graphe(3);
		graph.ajouterArete(0, 1, 1);
		graph.ajouterArete(1, 2, 1);
		graph.ajouterArete(0, 2, 1);

		// Tester l'algorithme de Kruskal
		Graphe mst = graph.Kruskal();
		List<Triplet> edges = mst.listeAretes();
		assertEquals(2, edges.size(), "Le MST devrait avoir n-1 arêtes");
		double totalWeight = edges.stream().mapToDouble(Triplet::getPoids).sum();
		assertEquals(2.0, totalWeight, 0.1, "Le poids total du MST devrait être 2");

		// Tester l'algorithme TSP
		ArrayList<Integer> resultat = graph.tsp(0);
		assertNotNull(resultat, "Le résultat ne devrait pas être nul");
		assertEquals(3, resultat.size()-1, "Le résultat devrait contenir tous les sommets");
		assertTrue(resultat.stream().distinct().count() == 3, "Chaque sommet devrait être visité exactement une fois");

		// Calculer le poids total du TSP
		double tspWeight = 0.0;
		for (int i = 0; i < resultat.size() - 1; i++) {
			tspWeight += graph.getPoids(resultat.get(i), resultat.get(i + 1));
		}

		System.out.println("Poids total du TSP: " + tspWeight);
		assertTrue(tspWeight <= 2 * totalWeight, "Le poids du TSP devrait être inférieur ou égal à deux fois le poids du MST");
	}

	@Test
	public void testMediumGraph() {
		Graphe graph = new Graphe(6);
		graph.ajouterArete(0, 1, 10);
		graph.ajouterArete(0, 2, 15);
		graph.ajouterArete(0, 3, 20);
		graph.ajouterArete(1, 4, 25);
		graph.ajouterArete(2, 4, 30);
		graph.ajouterArete(3, 5, 35);

		// Tester l'algorithme de Kruskal
		Graphe mst = graph.Kruskal();
		List<Triplet> edges = mst.listeAretes();
		assertEquals(5, edges.size(), "Le MST devrait avoir n-1 arêtes");
		double totalWeight = edges.stream().mapToDouble(Triplet::getPoids).sum();
		assertEquals(105.0, totalWeight, 0.1, "Le poids total du MST devrait être 105");

		// Tester l'algorithme TSP
		ArrayList<Integer> resultat = graph.tsp(0);
		assertNotNull(resultat, "Le résultat ne devrait pas être nul");
		assertEquals(6, resultat.size()-1, "Le résultat devrait contenir tous les sommets");
		assertTrue(resultat.stream().distinct().count() == 6, "Chaque sommet devrait être visité exactement une fois");

		// Calculer le poids total du TSP
		double tspWeight = 0.0;
		for (int i = 0; i < resultat.size() - 1; i++) {
			tspWeight += graph.getPoids(resultat.get(i), resultat.get(i + 1));
		}

		System.out.println("Poids total du TSP: " + tspWeight);
		assertTrue(tspWeight <= 2 * totalWeight, "Le poids du TSP devrait être inférieur ou égal à deux fois le poids du MST");

	}





	@Test
	public void testLargeGraph() {
		int n = 50;
		System.out.println("Génération d'un graphe aléatoire...");
		Graphe graph = Graphe.generateRandomGraphWithTriangleInequality(n);
		System.out.println("Graphe généré.");

		// Tester l'algorithme de Kruskal
		long startTime = System.currentTimeMillis();
		System.out.println("Exécution de l'algorithme de Kruskal...");
		Graphe mst = graph.Kruskal();
		long endTime = System.currentTimeMillis();
		System.out.println("Algorithme de Kruskal terminé.");
		System.out.println("Temps d'exécution de l'algorithme de Kruskal: " + (endTime - startTime) + "ms");

		List<Triplet> edges = mst.listeAretes();
		assertEquals(n - 1, edges.size(), "Le MST devrait avoir n-1 arêtes");

		// Tester l'algorithme TSP
		startTime = System.currentTimeMillis();
		System.out.println("Exécution de l'algorithme TSP...");
		ArrayList<Integer> resultat = graph.tsp(0);
		endTime = System.currentTimeMillis();
		System.out.println("Algorithme TSP terminé.");
		System.out.println("Temps d'exécution de l'algorithme TSP: " + (endTime - startTime) + "ms");

		assertNotNull(resultat, "Le résultat ne devrait pas être nul");
		assertEquals(n, resultat.size()-1, "Le résultat devrait contenir tous les sommets");
		assertTrue(resultat.stream().distinct().count() == n, "Chaque sommet devrait être visité exactement une fois");

		// Calculer le poids total du TSP
		double tspWeight = 0.0;
		for (int i = 0; i < resultat.size() - 1; i++) {
			tspWeight += graph.getPoids(resultat.get(i), resultat.get(i + 1));
		}

		double mstWeight = edges.stream().mapToDouble(Triplet::getPoids).sum();
		System.out.println("Poids total du TSP: " + tspWeight);
		assertTrue(tspWeight <= 2 * mstWeight, "Le poids du TSP devrait être inférieur ou égal à deux fois le poids du MST");
	}




	@Test
	public void testTSPAlgorithm() {
		// Créer une instance de graphe et ajouter des arêtes
		Graphe graph = new Graphe(4);
		graph.ajouterArete(0, 1, 28.3);
		graph.ajouterArete(0, 2, 53.8);
		graph.ajouterArete(0, 3, 30);
		graph.ajouterArete(1, 2, 30);
		graph.ajouterArete(1, 3, 22.4);
		graph.ajouterArete(2, 3, 28.3);

		// Vérifier si le nombre d'arêtes est correct
		List<Triplet> liste = graph.listeAretes();
		System.out.println("Le nombre d'arêtes: " + liste.size());
		assertEquals(6, liste.size());

		// Calculer et afficher le résultat approximatif du TSP
		ArrayList<Integer> resultat = graph.tsp(0);
		for (int point : resultat) {
			System.out.println("La liste de résultats est: " + point);
		}

		// Calculer le poids total de la solution approximative du TSP
		double pondre = 0.0;
		for (int j = 0; j < resultat.size() - 1; j++) {
			double segmentLength = graph.getPoids(resultat.get(j), resultat.get(j + 1));
			pondre += segmentLength;
		}

		// Calculer le poids de l'arbre couvrant minimum
		double mstPondre = 0.0;
		Graphe MST = graph.Kruskal();
		List<Triplet> listeMST = MST.listeAretes();
		assertTrue(!listeMST.isEmpty());

		for (Triplet edge : listeMST) {
			mstPondre += edge.getPoids();
		}

		// Afficher et comparer les poids
		System.out.println("Poids total du TSP: " + pondre + " contre (2 * poids du MST): " + (mstPondre * 2));

		// Vérifier si le poids du TSP respecte les attentes
		assertTrue(pondre <= 2 * mstPondre);
	}







	@Test
	void myTestn2PartionKCentre(){ //************************************ */
		Double[][] matrix = {{0.0, 1.1, 2.0,  2.0}, {1.1, 0.0, 2.0, 2.0},
				{2.0, 2.0, 0.0, 1.0}, {2.0, 2.0, 1.1, 0.0}};
		int k=2;
		int start=3;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.result();
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{3, 0, 1}, {3, 2}} )
		Ar.add(new ArrayList<>(List.of(start, 2)));
		Ar.add(new ArrayList<>(List.of(3, 0, 1)));

		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null

	}

	@Test
	void myTestn3PartionKCentre(){ //************************************ */
		Double[][] matrix = {{0.0, 30.0, 40.0, 41.2, 22.4, 28.3},
				{30.0, 0.0, 10.0, 44.8, 44.8, 22.4},
				{40.0, 10.0, 0.0, 50.0, 53.9, 28.3},
				{41.2, 44.8, 50.0, 0.0, 28.3, 22.4},
				{22.4, 44.8, 53.9, 28.3, 0.0, 30.0},
				{28.3, 22.4, 28.3, 22.4, 30.0, 0.0}};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.result();
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >();

		Ar.add(new ArrayList<>(List.of(start, 1, 2,4)));
		Ar.add(new ArrayList<>(List.of(start, 5, 3)));

		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null
	}
////Il y a encore quelques problèmes avec la méthode kcentre, donc pour ne pas signaler d'erreurs, commentez d'abord ces deux tests.

	@Test
	void myTestn4PartionKCentre() {
		Double[][] matrix = {
				{0.0, 2.0, 3.0, 1.0, 4.0},
				{2.0, 0.0, 1.0, 2.5, 3.0},
				{3.0, 1.0, 0.0, 3.5, 2.0},
				{1.0, 2.5, 3.5, 0.0, 5.0},
				{4.0, 3.0, 2.0, 5.0, 0.0}
		};
		int k = 2;
		int start = 3;

		PlannerParameter param = new PlannerParameter(matrix, k, start);
		Planner pl = new Planner(param);
		pl.result();
		ArrayList<ArrayList<Integer>> Ar = new ArrayList<>();
		Ar.add(new ArrayList<>(List.of(3, 0, 1)));
		Ar.add(new ArrayList<>(List.of(3,2, 4)));

		assertEquals(Ar, pl.getTournees());
	}

	@Test
	void myTestn5PartionKCentre(){ //************************************ */
		Double[][] matrix = {{0.0, 1.1, 1.0,  3.0, 4.0, 4.0}, {1.1, 0.0, 2.0, 2.0, 3.0, 3.0},
				{1.0, 2.0, 0.0, 2.0, 3.0, 3.0}, {3.0, 2.0, 2.0, 0.0, 1.1, 1.1}
				,{4.0, 3.0, 3.0, 1.0, 0.0, 2.0},{4.0, 3.0, 3.0, 1.0, 2.0, 0.0}};
		int k=2;
		int start=3;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.calculeTournees();
		pl.calculeLongTournees();

		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{3, 0, 1, 2}, {3, 4, 5}} )
		Ar.add(new ArrayList<>(List.of(start, 5, 4)));
		Ar.add(new ArrayList<>(List.of(start, 1, 0, 2)));

		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null
	}


}
