package l3m.cyber.planner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import l3m.cyber.planner.responses.PlannerResult;
import l3m.cyber.planner.utils.Graphe;
import l3m.cyber.planner.utils.PartitionAlea;
import l3m.cyber.planner.utils.Triplet;
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


	@Test
	public void testUniquePointGraphe() {
		Graphe UniquePointGraphe = new Graphe(1);
		ArrayList<Integer> resultat = UniquePointGraphe.tsp(0);
		assertEquals( 1, resultat.size()-1);
		assertEquals( Integer.valueOf(0), resultat.get(0));
	}

	@Test
	public void testCompletGraphe() {
//		Double[][] matrix = {{0.0, 10.0, 15.0, 20.0},
//				{10.0, 0.0, 35.0, 25.0},
//				{15.0, 35.0, 0.0, 30.0},
//				{20.0, 25.0, 30.0, 0.0}};
		Graphe graph = new Graphe(4);
		graph.ajouterArete(0, 1, 10);
		graph.ajouterArete(0, 2, 15);
		graph.ajouterArete(0, 3, 20);
		graph.ajouterArete(1, 2, 35);
		graph.ajouterArete(1, 3, 25);
		graph.ajouterArete(2, 3, 30);
		ArrayList<Integer> resultat = graph.tsp(0);
		assertNotNull(resultat,"Result should not be null");
		assertEquals(4, resultat.size()-1,"Result should contain all vertices");
		assertTrue(resultat.stream().distinct().count() == 4,"Each vertex should be visited exactly once");
	}

	@Test
	public void testTSPAlgorithm() {
		// 创建图实例并添加节点和边
		Graphe graph = new Graphe(4);
		graph.ajouterArete(0, 1, 28.3);
		graph.ajouterArete(0, 2, 53.8);
		graph.ajouterArete(0, 3, 30);
		graph.ajouterArete(1, 2, 30);
		graph.ajouterArete(1, 3, 22.4);
		graph.ajouterArete(2, 3, 28.3);

		// 检查边的数量是否正确
		List<Triplet> liste = graph.listeAretes();
		System.out.println("Le size de la liste des arêtes: " + liste.size());
		assertEquals(6, liste.size());

		// 计算并输出 TSP 近似解的结果
		ArrayList<Integer> resultat = graph.tsp(0);
		for (int point : resultat) {
			System.out.println("La liste de résultat est: " + point);
		}

		// 计算近似解的总权重
		double pondre = 0.0;
		for (int j = 0; j < resultat.size() - 1; j++) {
			double segmentLength = graph.getPoids(resultat.get(j), resultat.get(j + 1));
			pondre += segmentLength;
		}
		// 加上最后一个点到起始点的距离形成回路
		pondre += graph.getPoids(resultat.get(resultat.size() - 1), resultat.get(0));

		// 计算最小生成树的权重
		double mstPondre = 0.0;
		Graphe MST = graph.Kruskal();
		List<Triplet> listeMST = MST.listeAretes();
		assertTrue(!listeMST.isEmpty());

		for (Triplet edge : listeMST) {
			mstPondre += edge.getPoids();
		}

		// 输出和比较权重
		System.out.println("TSP Total Weight: " + pondre + " vs Known Optimal (2 * MST Weight): " + (mstPondre * 2));

		// 验证TSP解的权重是否符合预期
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
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{3, 0, 1, 2}, {3, 4, 5}} )
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
		int start = 3; // assuming 3 is the index for V3
		PlannerParameter param = new PlannerParameter(matrix, k, start);
		Planner pl = new Planner(param);
		pl.result();
		ArrayList<ArrayList<Integer>> Ar = new ArrayList<>();
		Ar.add(new ArrayList<>(List.of(3, 0, 1))); // Group 1 centered around V3
		Ar.add(new ArrayList<>(List.of(3,2, 4)));    // Group 2 centered around V2

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