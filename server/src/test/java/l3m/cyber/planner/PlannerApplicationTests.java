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

	// 	//创建partition实例并调用partitionne方法
	// 	PartitionAlea partition1 = new PartitionAlea(4, k);
	// 	PartitionAlea partition2 = new PartitionAlea(4, k,depot);
	// 	PartitionAlea partition3 = new PartitionAlea(list,k,depot);

	// 	//参数为4，k/////////////////////////////////////////////////////////////////////////////
	// 	partition1.partitionne(distances);
	//     Set<Integer> allAssignedElements = new HashSet<>();

	// 	for (int i = 0; i < k; i++) {
	// 		ArrayList<Integer> partie;
	// 		try {
	// 			partie = partition1.getPartie(i);  // 获取单个分区
	// 		} catch (IndexOutOfBoundsException e) {
	// 			System.out.println("索引超出分区范围：" + e.getMessage());
	// 			continue;
	// 		}

	// 		// 检查特殊元素只在列表的开始位置
	// 		assertTrue(partie.indexOf(0) == 0, "特殊元素位于首位");

	// 		// 收集所有非特殊元素
	// 		for (Integer elem : partie) {
	// 			if (elem != 0) {  // 假设0是特殊元素
	// 				allAssignedElements.add(elem);
	// 			}
	// 		}
	// 	}
	// 	assertEquals(3, allAssignedElements.size(),"所有非特殊元素都应被分配一次");//因为一共有四个地点


	// 	///////////////////参数为4，k，depot///////////////////////////////////////////////////////////		
	// 	partition2.partitionne(distances);
	// 	Set<Integer> allAssignedElements2 = new HashSet<>();

	// 	for (int i = 0; i < k; i++) {
	// 		ArrayList<Integer> partie2;
	// 		try {
	// 			partie2 = partition2.getPartie(i);  // 获取单个分区
	// 		} catch (IndexOutOfBoundsException e) {
	// 			System.out.println("索引超出分区范围：" + e.getMessage());
	// 			continue;
	// 		}

	// 		// 检查特殊元素只在列表的开始位置
	// 		//assertTrue( !partie2.contains(depot) , "特殊元素不应被重新分配");
	// 		assertTrue(  partie2.get(0).equals(depot), "仓库元素位于首位");


	// 		// 收集所有非特殊元素
	// 		for (Integer elem : partie2) {
	// 			if (elem != depot) {  // depot是特殊元素
	// 				allAssignedElements2.add(elem);
	// 			}
	// 		}
	// 	}
	// 	assertEquals(3, allAssignedElements2.size(),"所有非特殊元素都应被分配一次");//因为一共有四个地点

	// 	///////////////////参数为list，k，depot////////////////////////////////////////////////////////////
	// 	partition3.partitionne(distances2);//相当于给一个指定的地点liste
	// 	Set<Integer> allAssignedElements3 = new HashSet<>();

	// 	for (int i = 0; i < k; i++) {
	// 		ArrayList<Integer> partie3;
	// 		try {
	// 			partie3 = partition3.getPartie(i);  // 获取单个分区
	// 		} catch (IndexOutOfBoundsException e) {
	// 			System.out.println("索引超出分区范围：" + e.getMessage());
	// 			continue;
	// 		}

	// 		// 检查特殊元素只在列表的开始位置
	// 		assertTrue( partie3.get(0).equals(depot), "特殊元素位于首位");

	// 		// 收集所有非特殊元素
	// 		for (Integer elem : partie3) {
	// 			if (elem != depot) {  // depot是特殊元素
	// 				allAssignedElements3.add(elem);
	// 			}
	// 		}
	// 	}
	// 	assertEquals(list.size()-1, allAssignedElements3.size(),"所有非特殊元素都应被分配一次");//list包含所有元素，需要减去仓库元素

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

	// 	// 测试 tsp 方法，假设传入的 debut 是列表中的第一个有效顶点
    //     ArrayList<Integer> result = graphe.tsp(vertices.get(0));
    //     // 预期结果应该是从顶点 0 开始的排序列表
	// 	ArrayList<Integer> expected = new ArrayList<Integer>() {{
	// 		add(2);
	// 		add(0);
	// 		add(1);
	// 		add(3);
	// 		add(4);
	// 	}};
	// 	assertEquals(expected, result,"TSP method should return sorted vertices starting from the debut vertex");
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
    //     // 验证结果
    //     assertNotNull(planner.getTournees(),"Tournees should not be null");
    //     assertFalse(planner.getTournees().isEmpty(),"Tournees should not be empty");
    // }
	//因为这里是随机的分配方法，我无法预测结果所以只能验证结果不为空

	
	
	


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
	void myTest1PartionKCentre(){ //************************************ */
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

	@Test
	void myTestn2PartionKCentre(){ //************************************ */
		Double[][] matrix = {{0.0, 1.1, 2.0,  2.0}, {1.1, 0.0, 2.0, 2.0},
				{2.0, 2.0, 0.0, 1.0}, {2.0, 2.0, 1.1, 0.0}};
		int k=2;
		int start=3;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.calculeTournees();
		pl.calculeLongTournees();
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{3, 0, 1}, {3, 2}} )
		Ar.add(new ArrayList<>(List.of(3, 0, 1)));
		Ar.add(new ArrayList<>(List.of(start, 2)));

		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null

	}

	@Test
	void myTestn3PartionKCentre(){ //************************************ */
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
		Ar.add(new ArrayList<>(List.of(start, 0, 1, 2)));
		Ar.add(new ArrayList<>(List.of(start, 4, 5)));

		assertEquals( Ar, pl.getTournees()); //le tableau tournees doit etre non null
	}





}