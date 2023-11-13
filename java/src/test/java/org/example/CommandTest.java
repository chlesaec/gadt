package org.example;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CommandTest {
  /**
   * Method under test: {@link Command#add(Command)}
   */
  @Test
  void testAdd() {
    Command.Face face = new Command.Face(Command.Direction.NORTH);
    assertTrue(face.add(new Command.Composite<>()) instanceof Command.Composite);
  }

  /**
   * Method under test: {@link Command#build(Command, Command)}
   */
  @Test
  void testBuild() {
    Command.Composite<Command.State, Command.State> c1 = new Command.Composite<>();
    Command<Command.State, Command.State> buildResult = Command.build(c1, new Command.Composite<>());
    Command.Composite<Command.State, Command.State> c12 = new Command.Composite<>();
    assertTrue(Command.build(c12, new Command.Composite<>()) instanceof Command.Chain);
  }

  /**
   * Method under test: {@link Command.Composite#add(Command)}
   */
  @Test
  void testCompositeAdd() {
    Command.Composite<Command.State, Command.State> composite = new Command.Composite<>();
    assertSame(composite, composite.add(new Command.Composite<>()));
  }

  /**
   * Method under test: {@link Command.Composite#add(Command)}
   */
  @Test
  void testCompositeAdd2() {
    Command.Composite<Command.State, Command.State> composite = new Command.Composite<>();
    assertSame(composite, composite.add(null));
  }

  /**
   * Method under test: {@link Command.Start#getInstance()}
   */
  @Test
  void testStartGetInstance() {
    Command.Start actualInstance = Command.Start.getInstance();
    assertSame(actualInstance, actualInstance.getInstance());
  }

  /**
   * Method under test: {@link Command.Stop#getInstance()}
   */
  @Test
  void testStopGetInstance() {
    Command.Stop actualInstance = Command.Stop.getInstance();
    assertSame(actualInstance, actualInstance.getInstance());
  }
}
