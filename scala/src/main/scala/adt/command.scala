package adt

enum Direction {
  case North, East, South, West
}

final abstract class Idle
final abstract class Moving

sealed trait Command[Before, After]

object Command {
  case class  Face(dir: Direction) extends Command[Idle, Idle]
  case object Start  extends Command[Idle, Moving]
  case object Stop   extends Command[Moving, Idle]

  case class Chain[A, B, C](
      cmd1: Command[A, B],
      cmd2: Command[B, C]
  ) extends Command[A, C]
}

implicit class Compose[A, B](cmd1: Command[A, B]) {
  def ->[C](cmd2 : Command[B, C]) : Command[A,C] = Command.Chain(cmd1, cmd2)
}

@main
def main() = {
  val c1 : Command[Idle, Idle] = Command.Chain(Command.Chain(Command.Face(Direction.North), Command.Start), Command.Stop)
  val c2 = Command.Face(Direction.North) -> Command.Start -> Command.Stop
 // val c3 = Command.Start -> Command.Face(Direction.North)
 // val c4 = Command.Chain(Command.Start,  Command.Face(Direction.North))
 if (c1.equals(c2)) {
   println("OK")
 }
}
