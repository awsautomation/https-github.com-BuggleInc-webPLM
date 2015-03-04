package models

import org.scalatest._
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import org.mockito.Mockito._
import java.util.Vector
import actors.PLMActor
import spies.ExecutionSpy
import plm.core.model.lesson.Exercise
import plm.universe.World
import plm.core.model.lesson.Exercise.WorldKind

class PLMSpec extends PlaySpec with MockitoSugar {

  "PLM#switchLesson" should {
    "set the selected lesson as the current one" in {
      val mockSpy = mock[ExecutionSpy]
      when(mockSpy.clone) thenReturn mock[ExecutionSpy]
      
      val expectedLessonID = "welcome"
      PLM.switchLesson(expectedLessonID, mockSpy, mockSpy)
      val actualLectID = PLM.game.getCurrentLesson.getId
      actualLectID mustBe expectedLessonID
    }
    
    "set the first exercise as the current one" in {
      val mockSpy = mock[ExecutionSpy]
      when(mockSpy.clone) thenReturn mock[ExecutionSpy]
      
      val lessonID = "welcome"
      val expectedExerciseID = "welcome.lessons.welcome.environment.Environment"
      PLM.switchLesson(lessonID, mockSpy, mockSpy)
      val actualExerciseID = PLM.game.getCurrentLesson.getCurrentExercise.getId
      actualExerciseID mustBe expectedExerciseID
    }
    
    "return the current exercise" in {
      val mockSpy = mock[ExecutionSpy]
      when(mockSpy.clone) thenReturn mock[ExecutionSpy]
      
      val actualLecture = PLM.switchLesson("welcome", mockSpy, mockSpy)
      val expectedLecture = PLM.game.getCurrentLesson.getCurrentExercise
      actualLecture mustBe expectedLecture
    }
  }
  
  "PLM#addExecutionSpy" should {
    "add a spy's clone to each world" in {
      val mockPLMActor = mock[PLMActor]
      val executionSpy = new ExecutionSpy(mockPLMActor, "testOperation")
      
      val worlds = new Vector[World]()   
      var mockFirstWorld = mock[World]
      var mockSecondWorld = mock[World]
      var mockThirdWorld = mock[World]
      
      worlds.add(mockFirstWorld)
      worlds.add(mockSecondWorld)
      worlds.add(mockThirdWorld)
      
      val exo = mock[Exercise]
      when(exo.getWorlds(WorldKind.CURRENT)) thenReturn worlds
      PLM.addExecutionSpy(exo, executionSpy, WorldKind.CURRENT)
      verify(mockFirstWorld, times(1)).addWorldUpdatesListener(executionSpy)
      verify(mockSecondWorld, times(1)).addWorldUpdatesListener(executionSpy)
      verify(mockThirdWorld, times(1)).addWorldUpdatesListener(executionSpy)
    }
    
    "register each clone in PLMActor" in {
      val mockPLMActor = mock[PLMActor]
      val executionSpy = new ExecutionSpy(mockPLMActor, "testOperation")
      
      val worlds = new Vector[World]()   
      var mockFirstWorld = mock[World]
      var mockSecondWorld = mock[World]
      var mockThirdWorld = mock[World]
      
      worlds.add(mockFirstWorld)
      worlds.add(mockSecondWorld)
      worlds.add(mockThirdWorld)
      
      val exo = mock[Exercise]
      when(exo.getWorlds(WorldKind.CURRENT)) thenReturn worlds
      PLM.addExecutionSpy(exo, executionSpy, WorldKind.CURRENT)
      verify(mockPLMActor, times(3)).registerSpy(executionSpy)
    }
    
    
  }
  
}