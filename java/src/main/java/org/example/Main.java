package org.example;

public class Main {
  public static void main(String... args) {
    Command<Command.Idle, Command.Moving> start = Command.Start.getInstance();
    Command.Stop stop = Command.Stop.getInstance();
    Command<Command.Idle, Command.Idle> faceEast = new Command.Face(Command.Direction.EAST);

    Command<Command.Idle, Command.Moving> build = Command.build(faceEast, start);

    Command<Command.Idle, Command.Idle> compose1 = faceEast.add(start).add(stop);


    // start.add(faceEast); // this code doesn't compile.

  }
}
