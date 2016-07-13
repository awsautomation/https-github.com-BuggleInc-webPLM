package maze.wallfindfollow;

import java.io.IOException;

import plm.core.model.lesson.Exercise;
import plm.core.model.lesson.ExerciseTemplated;
import plm.universe.BrokenWorldFileException;
import plm.universe.World;
import plm.universe.bugglequest.BuggleWorld;

public class WallFindFollowMaze extends ExerciseTemplated {

	public WallFindFollowMaze() throws IOException, BrokenWorldFileException {
		super("WallFindFollowMaze", "WallFindFollowMaze");
		tabName = "Escaper";
		
		setup(new World[] {
				((BuggleWorld) BuggleWorld.newFromFile(Exercise.directory + "/maze/wallfindfollow/WallFindFollowMaze")).ignoreDirectionDifference(),
				((BuggleWorld) BuggleWorld.newFromFile(Exercise.directory + "/maze/wallfindfollow/WallFindFollowMaze2")).ignoreDirectionDifference()
		});
	}
}
