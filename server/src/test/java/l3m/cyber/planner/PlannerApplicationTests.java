package l3m.cyber.planner;

import l3m.cyber.planner.requests.PlannerParameter;
import l3m.cyber.planner.responses.PlannerResult;
import l3m.cyber.planner.utils.Partition;
import l3m.cyber.planner.utils.PartitionKCentre;
import l3m.cyber.planner.utils.Planner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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



	void myTestNullPlanning(){ //************************************ */
		Double[][] matrix = {};
		int k=0;  // Pas de livreurs
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.partition = new PartitionKCentre( pl.distances.length, k);
		pl.partition.partitionne(matrix);
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //[ [], []]
		Ar.add(new ArrayList<>(List.of()));
		Ar.add(new ArrayList<>(List.of()));

		assertEquals( Ar, pl.partition.parties); //le tableau tournees doit null

	}


	@Test
	void myTest0Planning(){ //************************************ */
		Double[][] matrix = {};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.partition = new PartitionKCentre( pl.distances.length, k);
		pl.partition.partitionne(matrix);
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //[ [], []]
		Ar.add(new ArrayList<>(List.of()));
		Ar.add(new ArrayList<>(List.of()));

		assertEquals( Ar, pl.partition.parties); //le tableau tournees doit contenir des listes vides

	}


	@Test
	void myTest1Planning(){ //************************************ */
		Double[][] matrix = {{0.0, 1.1}, {1.1, 0.0}};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.partition = new PartitionKCentre( pl.distances.length, k);
		pl.partition.partitionne(matrix);
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({ {0}, {1}  } )
		Ar.add(new ArrayList<>(List.of(0)));
		Ar.add(new ArrayList<>(List.of(1)));

		assertEquals( Ar, pl.partition.parties); //le tableau tournees doit etre non null

	}



	@Test
	void myTest2Planning(){ //************************************ */
		Double[][] matrix = {{0.0, 1.1, 2.0,  2.0}, {1.1, 0.0, 2.0, 2.0},
				{2.0, 2.0, 0.0, 1.0}, {2.0, 2.0, 1.1, 0.0}};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.partition = new PartitionKCentre( pl.distances.length, k);
		pl.partition.partitionne(matrix);
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{0, 1}, {2, 3}} )
		Ar.add(new ArrayList<>(List.of(0, 1)));
		Ar.add(new ArrayList<>(List.of(2,3)));

		assertEquals( Ar, pl.partition.parties); //le tableau tournees doit etre non null

	}



	@Test
	void myTestnPlanning(){ //************************************ */
		Double[][] matrix = {{0.0, 1.1, 2.0,  3.0, 4.0, 4.0}, {1.1, 0.0, 2.0, 2.0, 3.0, 3.0},
				{2.0, 2.0, 0.0, 2.0, 3.0, 3.0}, {3.0, 2.0, 2.0, 0.0, 1.1, 1.1}
				, {4.0, 3.0, 3.0, 1.0, 1.0, 0.0, 2.0},{4.0, 3.0, 3.0, 1.1, 2.0, 0.0}};
		int k=2;
		int start=0;
		PlannerParameter param= new PlannerParameter(matrix, k, start);
		Planner pl= new Planner(param);
		pl.partition = new PartitionKCentre( pl.distances.length, k);
		pl.partition.partitionne(matrix);
		ArrayList< ArrayList<Integer>> Ar = new ArrayList< ArrayList<Integer> >(); //({{0, 1, 2}, {3, 4, 5}} )
		Ar.add(new ArrayList<>(List.of(0, 1, 2)));
		Ar.add(new ArrayList<>(List.of(3, 4, 5)));

        assertEquals( Ar, pl.partition.parties); //le tableau tournees doit etre non null

	}


}