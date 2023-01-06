package org.example;

import java.util.ArrayList;
import java.util.List;

public sealed interface Command<Before extends Command.State, After extends Command.State>
    permits Command.Face, Command.Start, Command.Stop, Command.Chain, Command.Composite {

  sealed interface State permits Command.Idle,  Command.Moving {}

  final class Idle implements State {}
  final class Moving implements State {}

  default <C extends Command.State> Command<Before, C> add(Command<After, C> cmd) {
    return new Composite<Before, Before>().add(this).add(cmd);
  }

  record Face(Direction direction) implements Command<Idle, Idle> {
  }

  final class Start implements Command<Idle, Moving> {
    private Start() {}
    private static final Start instance = new Start();
    public static Start getInstance() {
      return Start.instance;
    }
  }

  final class Stop implements Command<Moving, Idle> {
    private Stop() {}
    private static final Stop instance = new Stop();
    public static Stop getInstance() {
      return Stop.instance;
    }
  }

  final class Chain<A extends Command.State, B  extends Command.State, C  extends Command.State> implements Command<A, C> {
    private final Command<A, B> cmd1;
    private final Command<B, C> cmd2;

    private Chain(final Command<A, B> cmd1, final Command<B, C> cmd2) {
      this.cmd1 = cmd1;
      this.cmd2 = cmd2;
    }
  }

  final class Composite<A extends Command.State, B  extends Command.State>  implements Command<A, B> {
    private List<Command> commands = new ArrayList<>();

    @Override
    public <C extends Command.State> Command<A, C> add(Command<B, C> subCommand) {
      if (subCommand instanceof Command.Composite<B,C> cp) {
        this.commands.addAll(cp.commands);
      }
      else {
        this.commands.add(subCommand);
      }
      return (Command<A, C>) this;
    }
  }

  static <A extends Command.State, B  extends Command.State, C  extends Command.State> Command<A, C> build(Command<A, B> c1,
      Command<B, C> c2) {
    return new Chain<>(c1, c2);
  }


  enum Direction {
    NORTH, EAST, SOUTH, WEST;
  }
}
