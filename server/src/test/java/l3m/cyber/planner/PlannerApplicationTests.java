package l3m.cyber.planner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import l3m.cyber.planner.requests.PlannerParameter;
import l3m.cyber.planner.responses.PlannerResult;
import l3m.cyber.planner.utils.Graphe;
import l3m.cyber.planner.utils.PartitionAlea;
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
		double [][] distances = {{0.0, 1.0, 2.0, 3.0},
								 {1.0, 0.0, 1.0, 2.0},
								 {2.0, 1.0, 0.0, 1.0},
								 {3.0, 2.0, 1.0, 0.0}};
		int k = 2;
		int depot = 1;
		ArrayList<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 6));
		double [][] distances2 = {{0.0, 1.0, 2.0, 3.0,4.0},
								 {1.0, 0.0, 1.0, 2.0,3.0},
								 {2.0, 1.0, 0.0, 1.0,2.0},
								 {3.0, 2.0, 1.0, 0.0,1.0}};

		//创建partition实例并调用partitionne方法
		PartitionAlea partition1 = new PartitionAlea(4, k);
		PartitionAlea partition2 = new PartitionAlea(4, k,depot);
		PartitionAlea partition3 = new PartitionAlea(list,k,depot);

		//参数为4，k/////////////////////////////////////////////////////////////////////////////
		partition1.partitionne(distances);
		Set<Integer> allAssignedElements = new HashSet<>();

		for (int i = 0; i < k; i++) {
			ArrayList<Integer> partie;
			try {
				partie = partition1.getPartie(i);  // 获取单个分区
			} catch (IndexOutOfBoundsException e) {
				System.out.println("索引超出分区范围：" + e.getMessage());
				continue;
			}

			// 检查特殊元素只在列表的开始位置
			assertTrue(partie.indexOf(0) == 0, "特殊元素位于首位");

			// 收集所有非特殊元素
			for (Integer elem : partie) {
				if (elem != 0) {  // 假设0是特殊元素
					allAssignedElements.add(elem);
				}
			}
		}
		assertEquals(3, allAssignedElements.size(),"所有非特殊元素都应被分配一次");//因为一共有四个地点


		///////////////////参数为4，k，depot///////////////////////////////////////////////////////////		
		partition2.partitionne(distances);
		Set<Integer> allAssignedElements2 = new HashSet<>();

		for (int i = 0; i < k; i++) {
			ArrayList<Integer> partie2;
			try {
				partie2 = partition2.getPartie(i);  // 获取单个分区
			} catch (IndexOutOfBoundsException e) {
				System.out.println("索引超出分区范围：" + e.getMessage());
				continue;
			}

			// 检查特殊元素只在列表的开始位置
			//assertTrue( !partie2.contains(depot) , "特殊元素不应被重新分配");
			assertTrue(  partie2.get(0).equals(depot), "仓库元素位于首位");


			// 收集所有非特殊元素
			for (Integer elem : partie2) {
				if (elem != depot) {  // depot是特殊元素
					allAssignedElements2.add(elem);
				}
			}
		}
		assertEquals(3, allAssignedElements2.size(),"所有非特殊元素都应被分配一次");//因为一共有四个地点

		///////////////////参数为list，k，depot////////////////////////////////////////////////////////////
		partition3.partitionne(distances2);//相当于给一个指定的地点liste
		Set<Integer> allAssignedElements3 = new HashSet<>();

		for (int i = 0; i < k; i++) {
			ArrayList<Integer> partie3;
			try {
				partie3 = partition3.getPartie(i);  // 获取单个分区
			} catch (IndexOutOfBoundsException e) {
				System.out.println("索引超出分区范围：" + e.getMessage());
				continue;
			}

			// 检查特殊元素只在列表的开始位置
			assertTrue( partie3.get(0).equals(depot), "特殊元素位于首位");

			// 收集所有非特殊元素
			for (Integer elem : partie3) {
				if (elem != depot) {  // depot是特殊元素
					allAssignedElements3.add(elem);
				}
			}
		}
		assertEquals(list.size()-1, allAssignedElements3.size(),"所有非特殊元素都应被分配一次");//list包含所有元素，需要减去仓库元素

	}

	@Test
    public void testTSPversion1() {
        int nbSommets = 5;
        Graphe graphe = new Graphe(nbSommets);
        ArrayList<Integer> tour = graphe.tsp(0);  // 从顶点0开始

        assertNotNull(tour,"Tour should not be null");
        assertEquals( nbSommets + 1, tour.size(),"Tour should have correct length");
        assertEquals(tour.get(0), tour.get(tour.size() - 1),"Tour should start and end at the start vertex");

        // 验证回路包含所有顶点
        boolean allVerticesVisited = tour.subList(1, tour.size() - 1)
                                        .stream()
                                        .distinct()
                                        .count() == nbSommets - 1;
        assertTrue(allVerticesVisited,"All vertices should be visited exactly once");
    }


}