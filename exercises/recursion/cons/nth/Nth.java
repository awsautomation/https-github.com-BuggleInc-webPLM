package recursion.cons.nth;

import plm.universe.bat.BatTest;
import plm.universe.bat.BatWorld;
import plm.universe.cons.ConsExercise;
import plm.universe.cons.ConsWorld;

public class Nth extends ConsExercise {

	public Nth() {
		super("Nth", "Nth");
		
		BatWorld myWorld = new ConsWorld("nth");
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 2, 3, 4}), 1);
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 2, 3, 4}), 2);
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 2, 3, 4}), 3);
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 2, 3, 4}), 4);
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 1, 1}), 2);
		myWorld.addTest(BatTest.VISIBLE,   (new int[]{1, 2, 1, 3, 2}), 5);
		myWorld.addTest(BatTest.INVISIBLE, (new int[]{2, 4, 6, 8, 10}), 2);
		myWorld.addTest(BatTest.INVISIBLE, (new int[]{6}), 1);

		setup(myWorld);
	}
}
